package edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui;

import edu.wpi.cs3733.C23.teamC.App;
import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.gfx.EdgeGFX;
import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.gfx.NodeGFX;
import edu.wpi.cs3733.C23.teamC.database.RepoFacade2;
import edu.wpi.cs3733.C23.teamC.objects.Floor;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.EdgeEntity;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.NodeEntity;
import edu.wpi.cs3733.C23.teamC.objects.math.Vector2;
import java.util.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import lombok.Getter;

public class MapEditorMapView {
  // UI components
  private final ImageView floorF1, floorF2, floorF3, floorL1, floorL2;
  private final SimpleDoubleProperty viewX, viewY, zoom;
  private final StackPane rootPane;
  @Getter private final Pane viewPane;
  private final Group nodeGroup;
  private final Group edgeGroup;
  private final SimpleObjectProperty<Floor> shownFloor = new SimpleObjectProperty<>(Floor.F1);
  private final SimpleBooleanProperty showLabels = new SimpleBooleanProperty(false);

  // rootPane
  //   |- viewPane (translates)
  //        |- floorF1
  //        |- floorF2
  //        |- floorF3
  //        |- floorL1
  //        |- floorL2
  //        |- edgeGroup
  //        |- nodeGroup

  private enum State {
    IDLE,
    DRAGGING_MAP,
    DRAGGING_SELECTION,
    SELECTING
  }

  private State state = State.IDLE;

  private final Map<NodeEntity, NodeGFX> nodes = new HashMap<>();
  private final Map<EdgeEntity, EdgeGFX> edges = new HashMap<>();

  public MapEditorMapView(StackPane rootPane) {
    this.rootPane = rootPane;
    rootPane.setAlignment(Pos.TOP_LEFT);

    floorF1 = createFloorImage("blankF1.png");
    floorF2 = createFloorImage("blankF2.png");
    floorF3 = createFloorImage("blankF3.png");
    floorL1 = createFloorImage("blankL1.png");
    floorL2 = createFloorImage("blankL2.png");

    bindFloorVisibility(floorF1, Floor.F1);
    bindFloorVisibility(floorF2, Floor.F2);
    bindFloorVisibility(floorF3, Floor.F3);
    bindFloorVisibility(floorL1, Floor.L1);
    bindFloorVisibility(floorL2, Floor.L2);

    viewX = new SimpleDoubleProperty(0);
    viewY = new SimpleDoubleProperty(0);
    zoom = new SimpleDoubleProperty(1);

    nodeGroup = new Group();
    edgeGroup = new Group();
    viewPane = new Pane(floorF1, floorF2, floorF3, floorL1, floorL2, edgeGroup, nodeGroup);

    viewPane.translateXProperty().bind(viewX);
    viewPane.translateYProperty().bind(viewY);
    rootPane.scaleXProperty().bind(zoom);
    rootPane.scaleYProperty().bind(zoom);

    rootPane.getChildren().add(viewPane);

    final var dragOffsetVector = new Vector2(0, 0);
    rootPane.addEventFilter(
        MouseEvent.MOUSE_PRESSED,
        event -> {
          if (event.getButton() == MouseButton.PRIMARY) {
            dragOffsetVector.setX(event.getX() - viewX.get());
            dragOffsetVector.setY(event.getY() - viewY.get());
          }
        });
    rootPane.addEventHandler(
        MouseEvent.MOUSE_PRESSED,
        event -> {
          if (event.getButton() == MouseButton.PRIMARY) {
            rootPane.setCursor(Cursor.CLOSED_HAND);
          }
        });
    rootPane.addEventHandler(
        MouseEvent.MOUSE_DRAGGED,
        event -> {
          rootPane.setCursor(Cursor.CLOSED_HAND);
          final var eventLocation = new Vector2(event.getX(), event.getY());
          eventLocation.subtract(dragOffsetVector);
          viewX.setValue(eventLocation.getX());
          viewY.setValue(eventLocation.getY());
        });
    rootPane.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> rootPane.setCursor(Cursor.DEFAULT));
    //        rootPane.addEventHandler(
    //                MouseEvent.MOUSE_RELEASED,
    //                event -> {
    //                    if (event.getButton() == MouseButton.PRIMARY && event.isStillSincePress())
    // {
    //                        // deselectAll();
    //                        // todo
    //                    }
    //                });

    rootPane.addEventFilter(
        ScrollEvent.SCROLL,
        event -> {
          if (event.getDeltaY() == 0) return;
          final var ZOOM_COEFFICIENT = 0.003;
          final var MIN_ZOOM = 0.1;
          final var MAX_ZOOM = 5;

          // final var eventOrigin = new Vector2(event.getX(), event.getY());
          // final var nodeOrigin = new Vector2(viewX.get(), viewY.get());

          // final var zoomOffset = Vector2.minus(nodeOrigin, eventOrigin);
          // final var zoomOffsetPoint =
          //    viewPane.screenToLocal(event.getScreenX(), event.getScreenY());
          // final var zoomOffset = new Vector2(-zoomOffsetPoint.getX(), -zoomOffsetPoint.getY());
          final var zoomDelta = ZOOM_COEFFICIENT * event.getDeltaY();
          var newZoom = zoom.get() * (1 + zoomDelta);
          if (newZoom > MAX_ZOOM) newZoom = MAX_ZOOM;
          else if (newZoom < MIN_ZOOM) newZoom = MIN_ZOOM;

          // normalize, rescale, then offset zoomOffset
          // zoomOffset.multiply(newZoom).add(eventOrigin);
          // viewX.set(zoomOffset.getX());
          // viewY.set(zoomOffset.getY());

          zoom.set(newZoom);
        });

    Main.getRepo()
        .getNodes()
        .addListener(RepoFacade2.createMapListener(this::addNode, this::removeNode));
    Main.getRepo()
        .getEdges()
        .addListener(RepoFacade2.createListListener(this::addEdge, this::removeEdge));

    Main.getRepo().getNodes().values().forEach(this::addNode);
    Main.getRepo().getEdges().forEach(this::addEdge);

    // zoom out a bit for the user's convenience
    // zoom.set(0.4);

    // set up interaction logic
    rootPane.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onMapClicked);
    rootPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::onMouseDragged);
    rootPane.addEventHandler(MouseEvent.MOUSE_RELEASED, this::onMouseReleased);
  }

  private void addNode(NodeEntity node) {
    final var gfx = new NodeGFX(node, showLabels, shownFloor);
    gfx.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> onNodeClicked(event, node, gfx));
    nodes.put(node, gfx);
    nodeGroup.getChildren().add(gfx);
  }

  private void removeNode(NodeEntity node) {
    final var gfx = nodes.get(node);
    // gfx.removeEventHandler(MouseEvent.MOUSE_PRESSED, this::onNodeClicked);
    nodes.remove(node);
    nodeGroup.getChildren().remove(gfx);
  }

  private void addEdge(EdgeEntity edge) {
    final var gfx = new EdgeGFX(edge, shownFloor);
    gfx.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> onEdgeClicked(event, edge, gfx));
    edges.put(edge, gfx);
    edgeGroup.getChildren().add(gfx);
  }

  private void removeEdge(EdgeEntity edge) {
    final var gfx = edges.get(edge);
    // gfx.removeEventHandler(MouseEvent.MOUSE_PRESSED, this::onEdgeClicked);
    edges.remove(edge);
    edgeGroup.getChildren().remove(gfx);
  }

  private static ImageView createFloorImage(String imageName) {
    return new ImageView(
        new Image(Objects.requireNonNull(App.class.getResourceAsStream("images/" + imageName))));
  }

  private void bindFloorVisibility(ImageView floorImage, Floor associatedFloor) {
    final var binding = shownFloor.isEqualTo(associatedFloor);
    floorImage.visibleProperty().bind(binding);
    floorImage.managedProperty().bind(binding);
  }

  private final Vector2 interactionStartPoint = new Vector2(0, 0);
  private final Set<NodeEntity> selectedNodes = new HashSet<>();
  private final Set<EdgeEntity> selectedEdges = new HashSet<>();

  private void select(NodeEntity node) {
    selectedNodes.add(node);
    nodes.get(node).showAsSelected();
    System.out.printf("Selected node %s\n", node.getNodeID());
  }

  private void select(EdgeEntity edge) {
    selectedEdges.add(edge);
    edges.get(edge).showAsSelected();
  }

  private void deselect(NodeEntity node) {
    selectedNodes.remove(node);
    nodes.get(node).showAsDeselected();
  }

  private void deselect(EdgeEntity edge) {
    selectedEdges.remove(edge);
    edges.get(edge).showAsDeselected();
  }

  private boolean isSelected(NodeEntity node) {
    return selectedNodes.contains(node);
  }

  private boolean isSelected(EdgeEntity edge) {
    return selectedEdges.contains(edge);
  }

  private void deselectEverything() {
    Set.copyOf(selectedNodes).forEach(this::deselect);
    Set.copyOf(selectedEdges).forEach(this::deselect);
  }

  private void beginRectangleSelection() {}

  // left-click on the map: deselect everything
  // left-click and drag on the map: deselect everything and begin rectangle selection
  // shift-left-click and drag on the map: begin rectangle selection
  // middle-click and drag on the map: pan map

  private void onMapClicked(MouseEvent event) {
    System.out.println("Map clicked.");
    switch (state) {
      case IDLE -> {
        switch (event.getButton()) {
          case PRIMARY -> {
            state = State.SELECTING;
            interactionStartPoint.setX(event.getScreenX());
            interactionStartPoint.setY(event.getScreenY());

            if (!event.isShiftDown()) {
              deselectEverything();
            }
            beginRectangleSelection();
          }
          case MIDDLE -> {
            state = State.DRAGGING_MAP;
            interactionStartPoint.setX(event.getScreenX());
            interactionStartPoint.setY(event.getScreenY());
          }
        }
      }
      case DRAGGING_MAP, DRAGGING_SELECTION, SELECTING -> {}
    }
  }

  // left-click on a node: select it and deselect everything else
  // shift-left-click on an unselected node: select it
  // shift-left-click on a selected node: deselect it
  // left-click and drag on a node: select it, deselect everything else, and start moving it.
  // deselect on release.
  // shift-left-click and drag on a node: select it and start moving everything that is selected

  private void onNodeClicked(MouseEvent event, NodeEntity node, NodeGFX gfx) {
    System.out.printf("Node %s was just clicked.\n", node.getNodeID());
    switch (state) {
      case IDLE -> {
        if (event.isPrimaryButtonDown()) {
          state = State.DRAGGING_SELECTION;
          if (!event.isShiftDown()) {
            deselectEverything();
            select(node);
          } else {
            if (!isSelected(node)) {
              select(node);
            } else {
              deselect(node);
            }
          }
        }
      }
      case DRAGGING_MAP, DRAGGING_SELECTION, SELECTING -> {}
    }
  }

  // left-click on an edge: select it and deselect everything else
  // shift-left-click on an unselected edge: select it
  // shift-left-click on a selected edge: deselect it
  // left-click and drag on an edge: select it, deselect everything else, and start moving it.
  // deselect on release.
  // shift-left-click and drag on an edge: select it and start moving everything that is selected

  private void onEdgeClicked(MouseEvent event, EdgeEntity edge, EdgeGFX gfx) {
    System.out.printf("Edge (%s, %s) was just clicked.\n", edge.getNode1ID(), edge.getNode2ID());
    switch (state) {
      case IDLE -> {
        if (event.isPrimaryButtonDown()) {
          state = State.DRAGGING_SELECTION;
          if (!event.isShiftDown()) {
            deselectEverything();
            select(edge);
          } else {
            if (!isSelected(edge)) {
              select(edge);
            } else {
              deselect(edge);
            }
          }
        }
      }
      case DRAGGING_MAP -> {}
      case DRAGGING_SELECTION -> {}
      case SELECTING -> {}
    }
  }

  private void onMouseDragged(MouseEvent event) {

    switch (state) {
      case IDLE -> {
        state = State.DRAGGING_MAP;
      }
      case DRAGGING_MAP -> {}
      case DRAGGING_SELECTION -> {}
      case SELECTING -> {}
    }
  }

  private void onMouseReleased(MouseEvent event) {
    switch (state) {
      case IDLE -> {}
      case DRAGGING_MAP -> {}
      case DRAGGING_SELECTION -> {
        if (!event.isStillSincePress() // were we dragging or just a short click?
            && !event.isShiftDown()) {
          // deselect on release after dragging.
          deselectEverything();
        }
      }
      case SELECTING -> {}
    }
    state = State.IDLE;
  }

  private Point2D screenToCoords(double screenX, double screenY) {
    return viewPane.screenToLocal(screenX, screenY);
  }

  public Floor getShownFloor() {
    return shownFloor.get();
  }

  public SimpleObjectProperty<Floor> shownFloorProperty() {
    return shownFloor;
  }

  public void setShownFloor(Floor shownFloor) {
    this.shownFloor.set(shownFloor);
  }

  public boolean isShowLabels() {
    return showLabels.get();
  }

  public SimpleBooleanProperty showLabelsProperty() {
    return showLabels;
  }

  public void setShowLabels(boolean showLabels) {
    this.showLabels.set(showLabels);
  }
}
