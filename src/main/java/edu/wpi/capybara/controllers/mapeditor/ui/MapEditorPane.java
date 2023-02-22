package edu.wpi.capybara.controllers.mapeditor.ui;

import static edu.wpi.capybara.Main.db;

import edu.wpi.capybara.App;
import edu.wpi.capybara.controllers.mapeditor.adapters.EdgeAdapter;
import edu.wpi.capybara.controllers.mapeditor.adapters.NodeAdapter;
import edu.wpi.capybara.objects.Floor;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import edu.wpi.capybara.objects.math.Vector2;
import java.util.Objects;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.SetChangeListener;
import javafx.scene.Cursor;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import lombok.NonNull;

public class MapEditorPane extends SplitPane {

  // todo: extract GFXNode
  // todo: extract GFXEdge
  // todo: decouple MapEditorPane and MapEditorController
  // both should be capable of editing things, but each should specialize to their specific
  // capabilities

  /** Graphical representation of a Node. */
  private class GFXNode extends Circle implements OldSelectable {

    private final NodeAdapter node;
    private final PseudoClassSelectionHandler pseudoClassHandler;

    GFXNode(NodeAdapter node) {
      this.node = node;
      pseudoClassHandler = new PseudoClassSelectionHandler(this);

      setRadius(10);
      centerXProperty().bind(node.xCoordProperty());
      centerYProperty().bind(node.yCoordProperty());
      getStyleClass().add("selectable");

      final var floor = Floor.fromString(node.getFloor());
      if (floor != null) {
        visibleProperty().bind(lookupFloorImage(floor).visibleProperty());
      }

      addEventFilter(
          MouseEvent.MOUSE_PRESSED,
          event -> {
            if (!event.isPrimaryButtonDown()) return;
            if (!event.isShiftDown()) {
              // remove all other selections
              deselectAll();
            }
            select();
            // don't let the click propagate back to the map
            event.consume();
          });
      addEventFilter(MouseEvent.MOUSE_RELEASED, MouseEvent::consume);
    }

    @Override
    public void select() {
      selections.add(this);
      pseudoClassHandler.onSelected();
    }

    @Override
    public void deselect() {
      selections.remove(this);
      pseudoClassHandler.onDeselected();
    }

    @Override
    public Object getSelectedObject() {
      return node;
    }
  }

  /** Graphical representation of an Edge. */
  private class GFXEdge extends Line implements OldSelectable {
    private final EdgeAdapter edge;
    private final PseudoClassSelectionHandler pseudoClassHandler;

    GFXEdge(EdgeAdapter edge) {
      this.edge = edge;
      pseudoClassHandler = new PseudoClassSelectionHandler(this);

      setStrokeWidth(5);
      // since edges can be edited without deleting and recreating them in the database, we need to
      // account for the start and end nodes possibly changing on us.
      bindStartNodeProps();
      bindEndNodeProps();
      edge.startNodeProperty().addListener(observable -> bindStartNodeProps());
      edge.endNodeProperty().addListener(observable -> bindEndNodeProps());
      getStyleClass().add("selectable");

      addEventFilter(
          MouseEvent.MOUSE_PRESSED,
          event -> {
            if (!event.isPrimaryButtonDown()) return;
            if (!event.isShiftDown()) {
              // remove all other selections
              deselectAll();
            }
            select();
            // don't let the click propagate to the map or any nodes
            event.consume();
          });
      addEventFilter(MouseEvent.MOUSE_RELEASED, MouseEvent::consume);
    }

    private void bindStartNodeProps() {
      final NodeEntity startNode = db.getNode(edge.getStartNode());
      // the *only* reason we can get away with not binding the properties is because editing x and
      // y values of
      // nodes requires deleting and replacing them. if we could actually edit the x & y values,
      // then this code
      // would be prone to missing the side effects of node editing.
      startXProperty().setValue(startNode.getXcoord());
      startYProperty().setValue(startNode.getYcoord());

      final var floor = startNode.getFloor();
      if (floor != null) {
        visibleProperty().bind(lookupFloorImage(floor).visibleProperty());
      }
    }

    private void bindEndNodeProps() {
      final NodeEntity endNode = db.getNode(edge.getEndNode());
      endXProperty().setValue(endNode.getXcoord());
      endYProperty().setValue(endNode.getYcoord());
    }

    @Override
    public void select() {
      selections.add(this);
      pseudoClassHandler.onSelected();
    }

    @Override
    public void deselect() {
      selections.remove(this);
      pseudoClassHandler.onDeselected();
    }

    @Override
    public Object getSelectedObject() {
      return edge;
    }
  }

  private final SimpleMapProperty<NodeAdapter, GFXNode> nodes;
  private final SimpleMapProperty<EdgeAdapter, GFXEdge> edges;

  private final SimpleObjectProperty<Floor> shownFloor;

  // UI components
  private final ImageView floorF1, floorF2, floorF3, floorL1, floorL2;
  private final SimpleDoubleProperty viewX, viewY, zoom;
  private final Pane mapElementContainer;

  private final SimpleSetProperty<OldSelectable> selections;
  private final SimpleSetProperty<Object> selectedEntities;

  public MapEditorPane() {
    nodes = new SimpleMapProperty<>(FXCollections.observableHashMap());
    edges = new SimpleMapProperty<>(FXCollections.observableHashMap());

    shownFloor = new SimpleObjectProperty<>(Floor.F1);
    selections = new SimpleSetProperty<>(FXCollections.observableSet());
    selectedEntities = new SimpleSetProperty<>(FXCollections.observableSet());
    selections.addListener(
        (SetChangeListener<? super OldSelectable>)
            change -> {
              if (change.wasAdded())
                selectedEntities.add(change.getElementAdded().getSelectedObject());
              if (change.wasRemoved())
                selectedEntities.remove(change.getElementRemoved().getSelectedObject());
            });

    floorF1 = createFloorImage("blankF1.png", Floor.F1);
    floorF2 = createFloorImage("blankF2.png", Floor.F2);
    floorF3 = createFloorImage("blankF3.png", Floor.F3);
    floorL1 = createFloorImage("blankL1.png", Floor.L1);
    floorL2 = createFloorImage("blankL2.png", Floor.L2);

    viewX = new SimpleDoubleProperty(0);
    viewY = new SimpleDoubleProperty(0);
    zoom = new SimpleDoubleProperty(1);

    Pane mapRoot = new Pane(floorF1, floorF2, floorF3, floorL1, floorL2);
    mapRoot.translateXProperty().bind(viewX);
    mapRoot.translateYProperty().bind(viewY);
    mapRoot.scaleXProperty().bind(zoom);
    mapRoot.scaleYProperty().bind(zoom);
    getChildren().add(mapRoot);

    mapElementContainer = new Pane();
    mapRoot.getChildren().add(mapElementContainer);

    final var dragOffsetVector = new Vector2(0, 0);
    addEventFilter(
        MouseEvent.MOUSE_PRESSED,
        event -> {
          if (event.getButton() == MouseButton.PRIMARY) {
            dragOffsetVector.setX(event.getX() - viewX.get());
            dragOffsetVector.setY(event.getY() - viewY.get());
          }
        });
    addEventHandler(
        MouseEvent.MOUSE_PRESSED,
        event -> {
          if (event.getButton() == MouseButton.PRIMARY) {
            setCursor(Cursor.CLOSED_HAND);
          }
        });
    addEventHandler(
        MouseEvent.MOUSE_DRAGGED,
        event -> {
          setCursor(Cursor.CLOSED_HAND);
          final var eventLocation = new Vector2(event.getX(), event.getY());
          eventLocation.subtract(dragOffsetVector);
          viewX.setValue(eventLocation.getX());
          viewY.setValue(eventLocation.getY());
        });
    addEventFilter(MouseEvent.MOUSE_RELEASED, event -> setCursor(Cursor.DEFAULT));
    addEventHandler(
        MouseEvent.MOUSE_RELEASED,
        event -> {
          if (event.getButton() == MouseButton.PRIMARY && event.isStillSincePress()) {
            deselectAll();
          }
        });

    onScrollProperty()
        .setValue(
            event -> {
              final var ZOOM_COEFFICIENT = 0.001;
              final var MIN_ZOOM = 0.1;
              final var MAX_ZOOM = 5;

              final var eventOrigin = new Vector2(event.getX(), event.getY());
              final var nodeOrigin = new Vector2(viewX.get(), viewY.get());
              final var zoomOffset = Vector2.minus(nodeOrigin, eventOrigin);

              final var zoomDelta = ZOOM_COEFFICIENT * event.getDeltaY();
              var newZoom = zoom.get() + zoomDelta;
              if (newZoom > MAX_ZOOM) newZoom = MAX_ZOOM;
              else if (newZoom < MIN_ZOOM) newZoom = MIN_ZOOM;

              // normalize, rescale, then offset zoomOffset
              zoomOffset.divide(zoom.get()).multiply(newZoom).add(eventOrigin);
              viewX.set(zoomOffset.getX());
              viewY.set(zoomOffset.getY());

              zoom.set(newZoom);
            });

    // zoom out a bit for the user's convenience
    zoom.set(0.4);
  }

  private ImageView createFloorImage(String imageName, Floor floor) {
    final var imageView =
        new ImageView(
            new Image(
                Objects.requireNonNull(App.class.getResourceAsStream("images/" + imageName))));
    imageView.visibleProperty().bind(shownFloor.isEqualTo(floor));
    return imageView;
  }

  private void addUIElement(javafx.scene.Node node) {
    mapElementContainer.getChildren().add(node);
  }

  private void addUIElementAtBack(javafx.scene.Node node) {
    // better hope this is backed by a LinkedList or this can get *really* expensive
    mapElementContainer.getChildren().add(0, node);
  }

  private void removeUIElement(javafx.scene.Node node) {
    mapElementContainer.getChildren().remove(node);
  }

  ImageView lookupFloorImage(Floor floor) {
    return switch (floor) {
      case F1 -> floorF1;
      case F2 -> floorF2;
      case F3 -> floorF3;
      case L1 -> floorL1;
      case L2 -> floorL2;
    };
  }

  public void addNode(NodeAdapter node) {
    final var gfxNode = new GFXNode(node);
    nodes.put(node, gfxNode);
    addUIElement(gfxNode);
  }

  public void removeNode(NodeAdapter node) {
    final var gfxNode = nodes.remove(node);
    if (gfxNode != null) {
      gfxNode.deselect();
      removeUIElement(gfxNode);
    }
  }

  public void addEdge(EdgeAdapter edge) {
    final var gfxEdge = new GFXEdge(edge);
    edges.put(edge, gfxEdge);
    addUIElementAtBack(gfxEdge);
  }

  public void removeEdge(EdgeAdapter edge) {
    final var gfxEdge = edges.remove(edge);
    if (gfxEdge != null) {
      gfxEdge.deselect();
      removeUIElement(gfxEdge);
    }
  }

  public void setShownFloor(Floor floor) {
    shownFloor.set(floor);
  }

  void addSelection(@NonNull OldSelectable selectable) {
    selections.add(selectable);
  }

  void removeSelection(@NonNull OldSelectable selectable) {
    selections.remove(selectable);
  }

  public void setSelection(@NonNull Object selection) {
    if (selection instanceof NodeAdapter node) {
      deselectAll();
      nodes.get(node).select();
    } else if (selection instanceof EdgeAdapter edge) {
      deselectAll();
      edges.get(edge).select();
    }
  }

  public void deselectAll() {
    // these array shenanigans are necessary to avoid concurrent modification of the selection set.
    final var selectionsArray = new OldSelectable[selections.size()];
    selections.toArray(selectionsArray);
    for (OldSelectable selectable : selectionsArray) {
      selectable.deselect();
    }
  }

  public ReadOnlySetProperty<Object> selectedProperty() {
    return selectedEntities;
  }
}
