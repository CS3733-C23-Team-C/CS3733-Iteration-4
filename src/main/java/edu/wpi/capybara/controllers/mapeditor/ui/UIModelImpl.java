package edu.wpi.capybara.controllers.mapeditor.ui;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.controllers.mapeditor.ui.elements.*;
import edu.wpi.capybara.controllers.mapeditor.ui.gfx.EdgeGFX;
import edu.wpi.capybara.controllers.mapeditor.ui.gfx.LocationGFX;
import edu.wpi.capybara.controllers.mapeditor.ui.gfx.MoveGFX;
import edu.wpi.capybara.controllers.mapeditor.ui.gfx.NodeGFX;
import edu.wpi.capybara.controllers.mapeditor.ui.row.EdgeRow;
import edu.wpi.capybara.controllers.mapeditor.ui.row.LocationRow;
import edu.wpi.capybara.controllers.mapeditor.ui.row.MoveRow;
import edu.wpi.capybara.controllers.mapeditor.ui.row.NodeRow;
import edu.wpi.capybara.objects.Floor;
import edu.wpi.capybara.objects.hibernate.EdgeEntity;
import edu.wpi.capybara.objects.hibernate.LocationnameEntity;
import edu.wpi.capybara.objects.hibernate.MoveEntity;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import java.util.function.Function;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.SetChangeListener;

public class UIModelImpl implements UIModel {
  private final ReadOnlySetWrapper<Element> elements =
      new ReadOnlySetWrapper<>(FXCollections.observableSet());
  private final ReadOnlySetWrapper<Element> selected =
      new ReadOnlySetWrapper<>(FXCollections.observableSet());

  private final SimpleBooleanProperty showLabels = new SimpleBooleanProperty();
  private final SimpleObjectProperty<Floor> shownFloor = new SimpleObjectProperty<>(Floor.F1);

  public UIModelImpl() {
    // TODO: 2/18/23 add listeners to elements and selected
    selected.addListener(
        (SetChangeListener<? super Element>)
            change -> {
              if (change.wasAdded()) change.getElementAdded().showAsSelected();
              if (change.wasRemoved()) change.getElementRemoved().showAsDeselected();
            });

    final Function<NodeEntity, NodeElement> nodeElementFactory =
        entity ->
            new NodeElement(
                this, new NodeGFX(entity, showLabels, shownFloor), new NodeRow(), entity);
    final Function<EdgeEntity, EdgeElement> edgeElementFactory =
        entity -> new EdgeElement(this, new EdgeGFX(entity, shownFloor), new EdgeRow(), entity);
    final Function<MoveEntity, MoveElement> moveElementFactory =
        entity -> new MoveElement(this, new MoveGFX(), new MoveRow(), entity);
    final Function<LocationnameEntity, LocationElement> locationElementFactory =
        entity -> new LocationElement(this, new LocationGFX(), new LocationRow(), entity);
    Main.getRepo().getNodes().values().stream().map(nodeElementFactory).forEach(this::add);
    Main.getRepo().getEdges().stream().map(edgeElementFactory).forEach(this::add);
    Main.getRepo().getMoves().stream().map(moveElementFactory).forEach(this::add);
    Main.getRepo().getLocationNames().values().stream()
        .map(locationElementFactory)
        .forEach(this::add);
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
