package edu.wpi.capybara.controllers.mapeditor;

import static edu.wpi.capybara.Main.db;

import edu.wpi.capybara.App;
import edu.wpi.capybara.objects.Floor;
import java.util.Objects;
import java.util.Optional;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.Cursor;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class MapEditorPane extends SplitPane {

  private static class Vec2d {
    double x, y;

    public Vec2d(double x, double y) {
      this.x = x;
      this.y = y;
    }
  }

  private class GFXNode extends Circle {

    private final NodeAdapter node;

    GFXNode(NodeAdapter node) {
      this.node = node;
      setRadius(6);
      centerXProperty().bind(node.xCoordProperty());
      centerYProperty().bind(node.yCoordProperty());

      final var floor = Floor.fromString(node.getFloor());
      if (floor != null) {
        visibleProperty().bind(lookupFloorImage(floor).visibleProperty());
      }

      onMousePressedProperty()
          .setValue(
              event -> {
                selectedNode.set(Optional.of(this.node));
                selectedEdge.set(Optional.empty());
                System.out.printf("Node %s just got clicked\n", this.node.getNodeID());
              });
    }
  }

  private class GFXEdge extends Line {
    private final EdgeAdapter edge;

    GFXEdge(EdgeAdapter edge) {
      this.edge = edge;
      // since edges can be edited without deleting and recreating them in the database, we need to
      // account for the
      // start and end nodes possibly changing on us.
      bindStartNodeProps();
      bindEndNodeProps();
      edge.startNodeProperty().addListener(observable -> bindStartNodeProps());
      edge.endNodeProperty().addListener(observable -> bindEndNodeProps());

      onMousePressedProperty()
          .setValue(
              event -> {
                selectedNode.set(Optional.empty());
                selectedEdge.set(Optional.of(this.edge));
              });
    }

    private void bindStartNodeProps() {
      final var startNode = db.getNode(edge.getStartNode());
      // the *only* reason we can get away with not binding the properties is because editing x and
      // y values of
      // nodes requires deleting and replacing them. if we could actually edit the x & y values,
      // then this code
      // would be prone to missing the side effects of node editing.
      startXProperty().setValue(startNode.getXcoord());
      startYProperty().setValue(startNode.getYcoord());

      final var floor = Floor.fromString(startNode.getFloor());
      if (floor != null) {
        visibleProperty().bind(lookupFloorImage(floor).visibleProperty());
      }
    }

    private void bindEndNodeProps() {
      final var endNode = db.getNode(edge.getEndNode());
      endXProperty().setValue(endNode.getXcoord());
      endYProperty().setValue(endNode.getYcoord());
    }
  }

  private final SimpleMapProperty<NodeAdapter, GFXNode> nodes;
  private final SimpleMapProperty<EdgeAdapter, GFXEdge> edges;

  private final SimpleObjectProperty<Floor> shownFloor;

  private final SimpleObjectProperty<Optional<NodeAdapter>> selectedNode;
  private final SimpleObjectProperty<Optional<EdgeAdapter>> selectedEdge;

  // UI components
  private final ImageView floorF1, floorF2, floorF3, floorL1, floorL2;
  private final SimpleDoubleProperty viewX, viewY, zoom;
  private final Pane mapRoot;

  public MapEditorPane() {
    nodes = new SimpleMapProperty<>(FXCollections.observableHashMap());
    edges = new SimpleMapProperty<>(FXCollections.observableHashMap());

    // db.getNodes().values().stream().map(NodeAdapter::new).forEach(this::addNode);
    /* repo.nodesProperty()
    .addListener(
            (ListChangeListener<NodeAdapter>)
                    c -> {
                        while (c.next()) {
                            c.getAddedSubList().forEach(this::addNode);
                            c.getRemoved().forEach(this::deleteNode);
                        }
                    });*/

    // db.getEdges().stream().map(EdgeAdapter::new).forEach(this::addEdge);
    /* repo.edgesProperty()
    .addListener(
            (ListChangeListener<EdgeAdapter>)
                    c -> {
                        while (c.next()) {
                            c.getAddedSubList().forEach(this::addEdge);
                            c.getRemoved().forEach(this::deleteEdge);
                        }
                    });*/

    shownFloor = new SimpleObjectProperty<>(Floor.F1);
    selectedNode = new SimpleObjectProperty<>(Optional.empty());
    selectedEdge = new SimpleObjectProperty<>(Optional.empty());

    floorF1 = createFloorImage("blankF1.png", Floor.F1);
    floorF2 = createFloorImage("blankF2.png", Floor.F2);
    floorF3 = createFloorImage("blankF3.png", Floor.F3);
    floorL1 = createFloorImage("blankL1.png", Floor.L1);
    floorL2 = createFloorImage("blankL2.png", Floor.L2);

    viewX = new SimpleDoubleProperty(0);
    viewY = new SimpleDoubleProperty(0);
    zoom = new SimpleDoubleProperty(1);

    mapRoot = new Pane(floorF1, floorF2, floorF3, floorL1, floorL2);
    mapRoot.translateXProperty().bind(viewX);
    mapRoot.translateYProperty().bind(viewY);
    mapRoot.scaleXProperty().bind(zoom);
    mapRoot.scaleYProperty().bind(zoom);
    getChildren().add(mapRoot);

    final var dragOffsetVector = new Vec2d(0, 0);
    onMousePressedProperty()
        .setValue(
            event -> {
              dragOffsetVector.x = event.getX() - viewX.get();
              dragOffsetVector.y = event.getY() - viewY.get();
              setCursor(Cursor.CLOSED_HAND);
            });
    onMouseDraggedProperty()
        .setValue(
            event -> {
              final var x = event.getX();
              final var y = event.getY();
              viewX.setValue(x - dragOffsetVector.x);
              viewY.setValue(y - dragOffsetVector.y);
            });
    onMouseReleasedProperty().setValue(event -> setCursor(Cursor.DEFAULT));

    onScrollProperty()
        .setValue(
            event -> {
              final var ZOOM_COEFFICIENT = 0.001;
              final var MIN_ZOOM = 0.1;
              final var MAX_ZOOM = 5;

              var newZoom = zoom.get() + ZOOM_COEFFICIENT * event.getDeltaY();
              if (newZoom > MAX_ZOOM) newZoom = MAX_ZOOM;
              else if (newZoom < MIN_ZOOM) newZoom = MIN_ZOOM;
              zoom.set(newZoom);
            });
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
    mapRoot.getChildren().add(node);
  }

  private void removeUIElement(javafx.scene.Node node) {
    mapRoot.getChildren().remove(node);
  }

  private ImageView lookupFloorImage(Floor floor) {
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
      removeUIElement(gfxNode);
    }
  }

  public void addEdge(EdgeAdapter edge) {
    final var gfxEdge = new GFXEdge(edge);
    edges.put(edge, gfxEdge);
    addUIElement(gfxEdge);
  }

  public void removeEdge(EdgeAdapter edge) {
    final var gfxEdge = edges.remove(edge);
    if (gfxEdge != null) {
      removeUIElement(gfxEdge);
    }
  }

  public void setShownFloor(Floor floor) {
    shownFloor.set(floor);
  }

  public Optional<NodeAdapter> getSelectedNode() {
    return selectedNode.get();
  }

  public ReadOnlyObjectProperty<Optional<NodeAdapter>> selectedNodeProperty() {
    return selectedNode;
  }

  public Optional<EdgeAdapter> getSelectedEdge() {
    return selectedEdge.get();
  }

  public ReadOnlyObjectProperty<Optional<EdgeAdapter>> selectedEdgeProperty() {
    return selectedEdge;
  }
}
