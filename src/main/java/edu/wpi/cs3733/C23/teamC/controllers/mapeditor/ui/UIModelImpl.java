package edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui;

import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.elements.*;
import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.gfx.EdgeGFX;
import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.gfx.LocationGFX;
import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.gfx.MoveGFX;
import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.gfx.NodeGFX;
import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.row.EdgeRow;
import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.row.LocationRow;
import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.row.MoveRow;
import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.row.NodeRow;
import edu.wpi.cs3733.C23.teamC.objects.Floor;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.EdgeEntity;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.LocationnameEntity;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.MoveEntity;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.NodeEntity;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.SetChangeListener;
import javafx.scene.control.TableView;

public class UIModelImpl implements UIModel {
  private final ReadOnlySetWrapper<Element> elements =
      new ReadOnlySetWrapper<>(FXCollections.observableSet());
  private final ReadOnlySetWrapper<Element> selected =
      new ReadOnlySetWrapper<>(FXCollections.observableSet());

  private final SimpleBooleanProperty showLabels = new SimpleBooleanProperty(false);
  private final SimpleObjectProperty<Floor> shownFloor = new SimpleObjectProperty<>(Floor.F1);

  public UIModelImpl(
      TableView<NodeEntity> nodeTableView,
      TableView<EdgeEntity> edgeTableView,
      TableView<MoveEntity> moveTableView,
      TableView<LocationnameEntity> locationTableView) {
    selected.addListener(
        (SetChangeListener<? super Element>)
            change -> {
              if (change.wasAdded()) change.getElementAdded().showAsSelected();
              if (change.wasRemoved()) change.getElementRemoved().showAsDeselected();
            });

    final Function<NodeEntity, NodeElement> nodeElementFactory =
        entity ->
            new NodeElement(
                this,
                new NodeGFX(entity, showLabels, shownFloor),
                new NodeRow(nodeTableView, entity),
                entity);
    final Function<EdgeEntity, EdgeElement> edgeElementFactory =
        entity -> {
          var edgeElement =
              new EdgeElement(
                  this,
                  new EdgeGFX(entity, shownFloor),
                  new EdgeRow(edgeTableView, entity),
                  entity);
          // edgeElement.getOnMap().bind();
          return edgeElement;
        };
    final Function<MoveEntity, MoveElement> moveElementFactory =
        entity -> new MoveElement(this, new MoveGFX(), new MoveRow(moveTableView, entity), entity);
    final Function<LocationnameEntity, LocationElement> locationElementFactory =
        entity ->
            new LocationElement(
                this, new LocationGFX(), new LocationRow(locationTableView, entity), entity);
    Main.getRepo().getNodes().values().stream().map(nodeElementFactory).forEach(this::add);
    Main.getRepo().getEdges().stream().map(edgeElementFactory).forEach(this::add);
    Main.getRepo().getMoves().stream().map(moveElementFactory).forEach(this::add);
    Main.getRepo().getLocationNames().values().stream()
        .map(locationElementFactory)
        .forEach(this::add);
    Main.getRepo().getNodes().addListener(createMapListener(nodeElementFactory));
    Main.getRepo().getEdges().addListener(createListListener(edgeElementFactory));
    Main.getRepo().getMoves().addListener(createListListener(moveElementFactory));
    Main.getRepo().getLocationNames().addListener(createMapListener(locationElementFactory));
  }

  private <K, E> MapChangeListener<K, E> createMapListener(Function<E, ? extends Element> factory) {
    return change -> {
      if (change.wasAdded()) add(factory.apply(change.getValueAdded()));
      if (change.wasRemoved()) deleteEntity(change.getValueRemoved());
    };
  }

  private <E> ListChangeListener<E> createListListener(Function<E, ? extends Element> factory) {
    return change -> {
      while (change.next()) {
        if (change.wasAdded()) change.getAddedSubList().stream().map(factory).forEach(this::add);
        if (change.wasRemoved()) change.getRemoved().forEach(this::deleteEntity);
      }
    };
  }

  @Override
  public ReadOnlySetProperty<Element> elementsProperty() {
    return elements.getReadOnlyProperty();
  }

  @Override
  public void add(Element element) {
    elements.add(element);
  }

  @Override
  public void delete(Element element) {
    elements.remove(element);
    selected.remove(element);
  }

  public <T> void deleteEntity(T entity) {
    Set.copyOf(
            elements.stream()
                .filter(
                    element -> {
                      if (element instanceof NodeElement node) {
                        return node.getInRepo().equals(entity);
                      } else if (element instanceof EdgeElement edge) {
                        return edge.getInRepo().equals(entity);
                      } else if (element instanceof MoveElement move) {
                        return move.getInRepo().equals(entity);
                      } else if (element instanceof LocationElement location) {
                        return location.getInRepo().equals(entity);
                      }
                      return false;
                    })
                .collect(Collectors.toSet()))
        .forEach(this::delete);
  }

  @Override
  public ReadOnlySetProperty<Element> selectedProperty() {
    return selected.getReadOnlyProperty();
  }

  @Override
  public void select(Element element) {
    selected.add(element);
  }

  @Override
  public void deselect(Element element) {
    selected.remove(element);
  }

  @Override
  public void deselectAll() {
    selected.clear();
  }

  @Override
  public boolean isShowLabels() {
    return showLabels.get();
  }

  @Override
  public SimpleBooleanProperty showLabelsProperty() {
    return showLabels;
  }

  @Override
  public void setShowLabels(boolean showLabels) {
    this.showLabels.set(showLabels);
  }

  @Override
  public Floor getShownFloor() {
    return shownFloor.get();
  }

  @Override
  public SimpleObjectProperty<Floor> shownFloorProperty() {
    return shownFloor;
  }

  @Override
  public void setShownFloor(Floor shownFloor) {
    this.shownFloor.set(shownFloor);
  }
}
