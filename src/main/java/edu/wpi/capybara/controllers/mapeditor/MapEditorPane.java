package edu.wpi.capybara.controllers.mapeditor;

import edu.wpi.capybara.database.MapEditorDBFacade;
import java.util.HashMap;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.scene.Cursor;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class MapEditorPane extends StackPane {

  private static class Vec2d {
    double x, y;

    public Vec2d(double x, double y) {
      this.x = x;
      this.y = y;
    }
  }

  private static class GFXNode extends Circle {

    private final NodeAdapter node;

    GFXNode(NodeAdapter node) {
      this.node = node;
      setRadius(10);
      translateXProperty().bind(node.xCoordProperty());
      translateYProperty().bind(node.yCoordProperty());
    }
  }

  private static class GFXEdge extends Line {

    private final MapEditorDBFacade repo;
    private final EdgeAdapter edge;

    GFXEdge(MapEditorDBFacade repo, EdgeAdapter edge) {
      this.repo = repo;
      this.edge = edge;
      // since edges can be edited without deleting and recreating them in the database, we need to
      // account for the
      // start and end nodes possibly changing on us.
      bindStartCoords();
      bindEndCoords();
      edge.startNodeProperty().addListener(observable -> bindStartCoords());
      edge.endNodeProperty().addListener(observable -> bindEndCoords());
    }

    private void bindStartCoords() {
      final var startNode = repo.findNodeByID(edge.getStartNode());
      startXProperty().bind(startNode.xCoordProperty());
      startYProperty().bind(startNode.yCoordProperty());
    }

    private void bindEndCoords() {
      final var endNode = repo.findNodeByID(edge.getEndNode());
      endXProperty().bind(endNode.xCoordProperty());
      endYProperty().bind(endNode.yCoordProperty());
    }
  }

  private final MapEditorDBFacade repo;
  private final HashMap<NodeAdapter, GFXNode> nodes;
  private final HashMap<EdgeAdapter, GFXEdge> edges;
  private final SimpleDoubleProperty viewX, viewY, zoom;

  public MapEditorPane(MapEditorDBFacade repo) {
    this.repo = repo;

    nodes = new HashMap<>();
    edges = new HashMap<>();

    repo.nodesProperty().forEach(this::addNode);
    repo.nodesProperty()
        .addListener(
            (ListChangeListener<NodeAdapter>)
                c -> {
                  while (c.next()) {
                    c.getAddedSubList().forEach(this::addNode);
                    c.getRemoved().forEach(this::deleteNode);
                  }
                });

    repo.edgesProperty().forEach(this::addEdge);
    repo.edgesProperty()
        .addListener(
            (ListChangeListener<EdgeAdapter>)
                c -> {
                  while (c.next()) {
                    c.getAddedSubList().forEach(this::addEdge);
                    c.getRemoved().forEach(this::deleteEdge);
                  }
                });

    viewX = new SimpleDoubleProperty(0);
    viewY = new SimpleDoubleProperty(0);
    zoom = new SimpleDoubleProperty(1);

    /*final var floor1 =
        new Floor(
            new Image(Objects.requireNonNull(App.class.getResourceAsStream("images/blankL1.png"))));
    floor1.translateXProperty().bind(viewX);
    floor1.translateYProperty().bind(viewY);
    floor1.scaleXProperty().bind(zoom);
    floor1.scaleYProperty().bind(zoom);
    getChildren().add(floor1);*/

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

  private void addChild(javafx.scene.Node node) {
    getChildren().add(node);
    node.setClip(this);
  }

  private void addNode(NodeAdapter node) {
    final var gfxNode = new GFXNode(node);
    nodes.put(node, gfxNode);
    addChild(gfxNode);
  }

  private void deleteNode(NodeAdapter node) {
    final var gfxNode = nodes.remove(node);
    if (gfxNode != null) {
      getChildren().remove(gfxNode);
    }
  }

  private void addEdge(EdgeAdapter edge) {
    final var gfxEdge = new GFXEdge(repo, edge);
    edges.put(edge, gfxEdge);
    addChild(gfxEdge);
  }

  private void deleteEdge(EdgeAdapter edge) {
    final var gfxEdge = edges.remove(edge);
    if (gfxEdge != null) {
      getChildren().remove(gfxEdge);
    }
  }
}
