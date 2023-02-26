package edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui;

import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.gfx.EdgeGFX;
import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.gfx.NodeGFX;
import edu.wpi.cs3733.C23.teamC.database.RepoFacade2;
import edu.wpi.cs3733.C23.teamC.objects.Floor;
import edu.wpi.cs3733.C23.teamC.objects.ImageLoader;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.EdgeEntity;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.NodeEntity;
import edu.wpi.cs3733.C23.teamC.objects.math.Vector2;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ExecutionException;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.internal.util.collections.IdentitySet;

@Slf4j
public class MapEditorMapView {
  // UI components
  private final ImageView floorF1, floorF2, floorF3, floorL1, floorL2;
  private final SimpleDoubleProperty viewX, viewY, zoom;
  private final ScrollPane clipPane;
  private final StackPane rootPane;
  @Getter private final Pane viewPane;
  private final Group nodeGroup;
  private final Group edgeGroup;
  private final Rectangle selectionRectangle;
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
  //        |- selectionRectangle

  private enum State {
    IDLE,
    PANNING_MAP,
    DRAGGING_SELECTION,
    SELECTING
  }

  private State state = State.IDLE;

  private final Map<NodeEntity, NodeGFX> nodes = new IdentityHashMap<>();
  private final Map<EdgeEntity, EdgeGFX> edges = new IdentityHashMap<>();

  public MapEditorMapView(Pane parent) {
    final var startTime = Instant.now();
    log.info("Map Editor map view initializing... {}", startTime.toString());
    // in  hindsight, I should have just used a ScrollPane for panning... but I already have the pan
    // code working. I'm not going to change it now. Right now it's just used for clipping.
    clipPane = new ScrollPane();
    clipPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    clipPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    clipPane.prefWidthProperty().bind(parent.widthProperty());
    clipPane.prefHeightProperty().bind(parent.heightProperty());
    parent.getChildren().add(clipPane);

    rootPane = new StackPane();
    clipPane.setContent(rootPane);

    log.info("Loading images {}ms", startTime.until(Instant.now(), ChronoUnit.MILLIS));
    try {
      floorF1 = new ImageView(ImageLoader.getF1().get());
      floorF2 = new ImageView(ImageLoader.getF2().get());
      floorF3 = new ImageView(ImageLoader.getF3().get());
      floorL1 = new ImageView(ImageLoader.getL1().get());
      floorL2 = new ImageView(ImageLoader.getL2().get());
    } catch (ExecutionException | InterruptedException e) {
      log.error("Error loading images.", e);
      throw new RuntimeException(e);
    }
    log.info("Loaded. {}ms", startTime.until(Instant.now(), ChronoUnit.MILLIS));

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
    selectionRectangle = new Rectangle(0, 0, 100, 100);
    selectionRectangle.getStyleClass().add("mapEditorSelect");
    selectionRectangle.managedProperty().bind(selectionRectangle.visibleProperty());
    selectionRectangle.setVisible(false);
    viewPane =
        new Pane(
            floorF1, floorF2, floorF3, floorL1, floorL2, edgeGroup, nodeGroup, selectionRectangle);
    rootPane.setAlignment(Pos.CENTER);
    rootPane.getChildren().add(viewPane);

    rootPane.translateXProperty().bind(viewX);
    rootPane.translateYProperty().bind(viewY);

    rootPane.scaleXProperty().bind(zoom);
    rootPane.scaleYProperty().bind(zoom);

    clipPane.addEventFilter(
        ScrollEvent.SCROLL,
        event -> {
          if (event.getDeltaY() == 0) return;
          final var ZOOM_COEFFICIENT = 0.003;
          final var MIN_ZOOM = 0.1;
          final var MAX_ZOOM = 5;

          /*final var eventOrigin = new Vector2(event.getX(), event.getY());
          final var nodeOrigin = new Vector2(viewX.get(), viewY.get());

          // final var zoomOffset = Vector2.minus(nodeOrigin, eventOrigin);
          final var zoomPoint =
              new Vector2(rootPane.screenToLocal(event.getScreenX(), event.getScreenY()));*/
          final var zoomDelta = 1 + ZOOM_COEFFICIENT * event.getDeltaY();
          var newZoom = zoom.get() * zoomDelta;
          if (newZoom > MAX_ZOOM) newZoom = MAX_ZOOM;
          else if (newZoom < MIN_ZOOM) newZoom = MIN_ZOOM;

          /*final var zoomPointDelta = zoomPoint.multiply(zoomDelta);
          viewX.set(viewX.get() + zoomPointDelta.getX());
          viewY.set(viewY.get() + zoomPointDelta.getY());*/

          zoom.set(newZoom);

          event.consume();
        });

    log.info("Populating map... {}ms", startTime.until(Instant.now(), ChronoUnit.MILLIS));
    Main.getRepo()
        .getNodes()
        .addListener(RepoFacade2.createMapListener(this::addNode, this::removeNode));
    Main.getRepo()
        .getEdges()
        .addListener(RepoFacade2.createListListener(this::addEdge, this::removeEdge));

    Main.getRepo().getNodes().values().forEach(this::addNode);
    Main.getRepo().getEdges().forEach(this::addEdge);
    log.info("Populated. {}ms", startTime.until(Instant.now(), ChronoUnit.MILLIS));

    // zoom out a bit for the user's convenience
    // zoom.set(0.4);

    // set up interaction logic
    clipPane.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onMapClicked);
    clipPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, this::onMouseDragged);
    clipPane.addEventHandler(MouseEvent.MOUSE_RELEASED, this::onMouseReleased);

    log.info("Initialized. {}ms", startTime.until(Instant.now(), ChronoUnit.MILLIS));
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

  private void bindFloorVisibility(ImageView floorImage, Floor associatedFloor) {
    final var binding = shownFloor.isEqualTo(associatedFloor);
    floorImage.visibleProperty().bind(binding);
    floorImage.managedProperty().bind(binding);
  }

  private final Set<NodeEntity> selectedNodes = new IdentitySet<>();
  private final Set<EdgeEntity> selectedEdges = new IdentitySet<>();

  private void select(NodeEntity node) {
    selectedNodes.add(node);
    nodes.get(node).showAsSelected();
    log.info("Selected node {}\n", node.getNodeID());
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

  private final Vector2 anchor = Vector2.zero();

  private void beginRectangleSelect(Vector2 anchor) {
    log.info("Beginning rectangle select");
    this.anchor.setX(anchor.getX());
    this.anchor.setY(anchor.getY());
    recalculateRectangleCoords(this.anchor, anchor);
    selectionRectangle.setVisible(true);
  }

  private void updateRectangleSelect(Vector2 extent) {
    log.info("Updating rectangle select");
    recalculateRectangleCoords(anchor, extent);
  }

  private void endRectangleSelect() {
    log.info("Ending rectangle select");
    selectionRectangle.setVisible(false);
    nodes.keySet().stream().filter(this::inRectangle).forEach(this::select);
    edges.keySet().stream().filter(this::inRectangle).forEach(this::select);
  }

  private void recalculateRectangleCoords(Vector2 anchor, Vector2 extent) {
    final var x = Math.min(anchor.getX(), extent.getX());
    final var y = Math.min(anchor.getY(), extent.getY());
    final var width = Math.max(anchor.getX(), extent.getX()) - x;
    final var height = Math.max(anchor.getY(), extent.getY()) - y;
    selectionRectangle.setX(x);
    selectionRectangle.setY(y);
    selectionRectangle.setWidth(width);
    selectionRectangle.setHeight(height);
  }

  private boolean inRectangle(NodeEntity node) {
    final var x = node.getXcoord() - selectionRectangle.getX();
    final var y = node.getYcoord() - selectionRectangle.getY();
    return x >= 0
        && x <= selectionRectangle.getWidth()
        && y >= 0
        && y <= selectionRectangle.getHeight()
        && nodes.get(node).isVisible();
  }

  private boolean inRectangle(EdgeEntity edge) {
    return inRectangle(edge.getNode1()) && inRectangle(edge.getNode2());
  }

  private final Vector2 lastDragPosition = Vector2.zero();
  private final HashSet<NodeEntity> nodesToMove = new HashSet<>();

  private void beginDragSelection(Vector2 origin) {
    log.info("Beginning drag selection");
    lastDragPosition.setX(origin.getX());
    lastDragPosition.setY(origin.getY());
    nodesToMove.clear();
    nodesToMove.addAll(selectedNodes);
    selectedEdges.forEach(
        edge -> {
          nodesToMove.add(edge.getNode1());
          nodesToMove.add(edge.getNode2());
        });
  }

  private void updateDragSelection(Vector2 position) {
    log.info("Updating drag selection");
    final var delta = Vector2.minus(position, lastDragPosition);
    nodesToMove.forEach(
        node -> {
          node.setXcoord(node.getXcoord() + (int) delta.getX());
          node.setYcoord(node.getYcoord() + (int) delta.getY());
        });
    lastDragPosition.setX(position.getX());
    lastDragPosition.setY(position.getY());
  }

  private void endDragSelection() {
    log.info("Ending drag selection");
  }

  private final Vector2 dragOffsetVector = Vector2.zero();

  private void beginPanMap(Vector2 origin) {
    log.info("Beginning pan map");
    dragOffsetVector.setX(origin.getX() - viewX.get());
    dragOffsetVector.setY(origin.getY() - viewY.get());
    clipPane.setCursor(Cursor.CLOSED_HAND);
  }

  private void updatePanMap(Vector2 position) {
    log.info("Updating pan map");
    final var newView = Vector2.minus(position, dragOffsetVector);
    viewX.setValue(newView.getX());
    viewY.setValue(newView.getY());
  }

  private void endPanMap() {
    log.info("Ending pan map");
    clipPane.setCursor(Cursor.DEFAULT);
  }

  // left-click on the map: deselect everything
  // left-click and drag on the map: deselect everything and begin rectangle select
  // shift-left-click and drag on the map: begin rectangle select
  // middle-click and drag on the map: pan map

  private void onMapClicked(MouseEvent event) {
    System.out.println("Map clicked.");
    switch (state) {
      case IDLE -> {
        switch (event.getButton()) {
          case PRIMARY -> {
            state = State.SELECTING;

            if (!event.isShiftDown()) {
              deselectEverything();
            }
            beginRectangleSelect(getCoordsPosition(event));
          }
          case MIDDLE -> {
            state = State.PANNING_MAP;
            beginPanMap(getScreenPosition(event));
          }
        }
      }
      case PANNING_MAP, DRAGGING_SELECTION, SELECTING -> {}
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
          beginDragSelection(getCoordsPosition(event));
        }
      }
      case PANNING_MAP, DRAGGING_SELECTION, SELECTING -> {}
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
          beginDragSelection(getCoordsPosition(event));
        }
      }
      case PANNING_MAP, DRAGGING_SELECTION, SELECTING -> {}
    }
  }

  private void onMouseDragged(MouseEvent event) {
    switch (state) {
      case IDLE -> {}
      case PANNING_MAP -> updatePanMap(getScreenPosition(event));
      case DRAGGING_SELECTION -> updateDragSelection(getCoordsPosition(event));
      case SELECTING -> updateRectangleSelect(getCoordsPosition(event));
    }
  }

  private void onMouseReleased(MouseEvent event) {
    switch (state) {
      case IDLE -> {}
      case PANNING_MAP -> endPanMap();
      case DRAGGING_SELECTION -> {
        endDragSelection();
        if (!event.isStillSincePress() // were we dragging or just a short click?
            && !event.isShiftDown()) {
          // deselect on release after dragging.
          deselectEverything();
        }
      }
      case SELECTING -> endRectangleSelect();
    }
    state = State.IDLE;
  }

  private Vector2 screenToCoords(double screenX, double screenY) {
    return new Vector2(viewPane.screenToLocal(screenX, screenY));
  }

  private Vector2 getCoordsPosition(MouseEvent event) {
    return screenToCoords(event.getScreenX(), event.getScreenY());
  }

  private Vector2 getScreenPosition(MouseEvent event) {
    return new Vector2(event.getScreenX(), event.getScreenY());
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
