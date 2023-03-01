package edu.wpi.cs3733.C23.teamC.mapeditor.ui;

import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.Pathfinding.ImageLoader;
import edu.wpi.cs3733.C23.teamC.database.RepoFacade2;
import edu.wpi.cs3733.C23.teamC.database.hibernate.EdgeEntity;
import edu.wpi.cs3733.C23.teamC.database.hibernate.NodeEntity;
import edu.wpi.cs3733.C23.teamC.mapeditor.Floor;
import edu.wpi.cs3733.C23.teamC.mapeditor.Vector2;
import edu.wpi.cs3733.C23.teamC.mapeditor.dialogs.ErrorMessage;
import edu.wpi.cs3733.C23.teamC.mapeditor.ui.gfx.EdgeGFX;
import edu.wpi.cs3733.C23.teamC.mapeditor.ui.gfx.NodeGFX;
import jakarta.persistence.PersistenceException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Transform;
import lombok.Getter;
import lombok.Setter;
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
    Thread.currentThread().setUncaughtExceptionHandler(ErrorMessage::handleUncaughtException);
    // in  hindsight, I should have just used a ScrollPane for panning... but I already have the pan
    // code working. I'm not going to change it now. Right now it's just used for clipping.
    clipPane = new ScrollPane();
    clipPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    clipPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    clipPane.prefWidthProperty().bind(parent.widthProperty());
    clipPane.prefHeightProperty().bind(parent.heightProperty());
    parent.getChildren().add(clipPane);

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
    rootPane = new StackPane();
    rootPane.setAlignment(Pos.CENTER);
    rootPane.getChildren().add(viewPane);

    clipPane.setContent(rootPane);

    final var viewTranslate = Transform.translate(0, 0);
    final var scale =
        Transform.scale(
            zoom.get(), zoom.get(), 0.5 * clipPane.getWidth(), 0.5 * clipPane.getHeight());
    rootPane.getTransforms().addAll(scale, viewTranslate);

    clipPane
        .widthProperty()
        .addListener((observable, oldValue, newValue) -> scale.setPivotX(0.5 * (double) newValue));
    clipPane
        .heightProperty()
        .addListener((observable, oldValue, newValue) -> scale.setPivotY(0.5 * (double) newValue));

    viewX.addListener((observable, oldValue, newValue) -> viewTranslate.setX((double) newValue));
    viewY.addListener((observable, oldValue, newValue) -> viewTranslate.setY((double) newValue));
    zoom.addListener(
        (observable, oldValue, newValue) -> {
          scale.setX((double) newValue);
          scale.setY((double) newValue);
        });

    parent.addEventFilter(
        ScrollEvent.SCROLL,
        event -> {
          if (event.getDeltaY() == 0) {
            log.warn("Received garbage data in scroll event");
            return;
          }
          final var ZOOM_COEFFICIENT = 0.001;
          final var MIN_ZOOM = 0.1;
          final var MAX_ZOOM = 5;

          final var zoomDelta = ZOOM_COEFFICIENT * event.getDeltaY();
          var newZoom = zoom.get() + zoomDelta;
          if (newZoom > MAX_ZOOM) newZoom = MAX_ZOOM;
          else if (newZoom < MIN_ZOOM) newZoom = MIN_ZOOM;

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

    // set up interaction logic
    clipPane.addEventHandler(MouseEvent.MOUSE_PRESSED, this::onMapClicked);
    clipPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, this::onMouseDragged);
    clipPane.addEventHandler(MouseEvent.MOUSE_RELEASED, this::onMouseReleased);
    clipPane.addEventHandler(KeyEvent.KEY_PRESSED, this::onMapKeyPressed);

    log.info("Initialized. {}ms", startTime.until(Instant.now(), ChronoUnit.MILLIS));
  }

  private void calculateViewport(double x, double y, double scale) {}

  private double constrain(double number, double low, double high) {
    if (number > high) return high;
    else return Math.max(number, low);
  }

  private void translateView(double x, double y) {
    viewX.set(viewX.get() + x / zoom.get());
    viewY.set(viewY.get() + y / zoom.get());
  }

  private void addNode(NodeEntity node) {
    final var gfx = new NodeGFX(node, showLabels, shownFloor, zoom);
    gfx.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> onNodeClicked(event, node, gfx));
    nodes.put(node, gfx);
    nodeGroup.getChildren().add(gfx);
    log.debug("Node added");
  }

  private void removeNode(NodeEntity node) {
    final var gfx = nodes.get(node);
    // gfx.removeEventHandler(MouseEvent.MOUSE_PRESSED, this::onNodeClicked);
    nodes.remove(node);
    nodeGroup.getChildren().remove(gfx);
    selectedNodes.remove(node);
    log.debug("Node removed");
  }

  private void addEdge(EdgeEntity edge) {
    final var gfx = new EdgeGFX(edge, shownFloor);
    gfx.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> onEdgeClicked(event, edge, gfx));
    edges.put(edge, gfx);
    edgeGroup.getChildren().add(gfx);
    log.debug("Edge added");
  }

  private void removeEdge(EdgeEntity edge) {
    final var gfx = edges.get(edge);
    // gfx.removeEventHandler(MouseEvent.MOUSE_PRESSED, this::onEdgeClicked);
    edges.remove(edge);
    edgeGroup.getChildren().remove(gfx);
    selectedEdges.remove(edge);
    log.debug("Edge removed");
  }

  private void bindFloorVisibility(ImageView floorImage, Floor associatedFloor) {
    final var binding = shownFloor.isEqualTo(associatedFloor);
    floorImage.visibleProperty().bind(binding);
    floorImage.managedProperty().bind(binding);
  }

  private final Set<NodeEntity> selectedNodes = new IdentitySet<>();
  private final Set<EdgeEntity> selectedEdges = new IdentitySet<>();
  @Getter @Setter private Consumer<NodeEntity> onNodeSelected;
  @Getter @Setter private Consumer<NodeEntity> onNodeDeselected;
  @Getter @Setter private Consumer<EdgeEntity> onEdgeSelected;
  @Getter @Setter private Consumer<EdgeEntity> onEdgeDeselected;

  public void select(NodeEntity node) {
    selectedNodes.add(node);
    nodes.get(node).showAsSelected();
    if (onNodeSelected != null) onNodeSelected.accept(node);
    log.info("Selected node {}\n", node.getNodeID());
  }

  public void select(EdgeEntity edge) {
    selectedEdges.add(edge);
    edges.get(edge).showAsSelected();
    if (onEdgeSelected != null) onEdgeSelected.accept(edge);
  }

  public void deselect(NodeEntity node) {
    selectedNodes.remove(node);
    nodes.get(node).showAsDeselected();
    if (onNodeDeselected != null) onNodeDeselected.accept(node);
  }

  public void deselect(EdgeEntity edge) {
    selectedEdges.remove(edge);
    edges.get(edge).showAsDeselected();
    if (onEdgeDeselected != null) onEdgeDeselected.accept(edge);
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

  private void deleteSelected() {
    Set.copyOf(selectedEdges).forEach(Main.getRepo()::deleteEdge);
    Set.copyOf(selectedNodes).forEach(this::repairEdgesAndDelete);
  }

  private void createNode(Vector2 position) {
    final var x = (int) position.getX();
    final var y = (int) position.getY();
    final var newNode =
        new NodeEntity(
            String.format("%sX%04dY%04d", shownFloor.get(), x, y),
            x,
            y,
            shownFloor.get().toString(),
            "");
    Main.getRepo().addNode(newNode);
    select(newNode);
  }

  private final Vector2 anchor = Vector2.zero();

  private void beginRectangleSelect(Vector2 anchor) {
    log.info("Beginning rectangle select");
    this.anchor.setX(anchor.getX());
    this.anchor.setY(anchor.getY());
    recalculateRectangleCoords(this.anchor, anchor);
  }

  private void updateRectangleSelect(Vector2 extent) {
    log.debug("Updating rectangle select");
    selectionRectangle.setVisible(true);
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
    if (node == null) return false;
    final var x = node.getXcoord() - selectionRectangle.getX();
    final var y = node.getYcoord() - selectionRectangle.getY();
    return x >= 0
        && x <= selectionRectangle.getWidth()
        && y >= 0
        && y <= selectionRectangle.getHeight()
        && nodes.get(node).isVisible();
  }

  private boolean inRectangle(EdgeEntity edge) {
    if (edge == null) return false;
    return inRectangle(edge.getNode1()) && inRectangle(edge.getNode2());
  }

  private final Vector2 dragOrigin = Vector2.zero();
  private final Map<NodeEntity, Vector2> nodesToMove = new IdentityHashMap<>();
  private boolean hasDragMoved = false;

  private void beginDragSelection(Vector2 origin) {
    log.info("Beginning drag selection");
    dragOrigin.setX(origin.getX());
    dragOrigin.setY(origin.getY());
    nodesToMove.clear();
    final Consumer<NodeEntity> mapper =
        node -> nodesToMove.put(node, new Vector2(node.getXcoord(), node.getYcoord()));
    selectedNodes.forEach(mapper);
    selectedEdges.forEach(
        edge -> {
          mapper.accept(edge.getNode1());
          mapper.accept(edge.getNode2());
        });
    hasDragMoved = false;
  }

  private void updateDragSelection(Vector2 position) {
    log.debug("Updating drag selection");
    final var delta = Vector2.minus(position, dragOrigin);
    nodesToMove.forEach(
        (node, origin) -> {
          node.setXcoord((int) Math.round(origin.getX() + delta.getX()));
          node.setYcoord((int) Math.round(origin.getY() + delta.getY()));
        });
    hasDragMoved = true;
  }

  private void endDragSelection() {
    log.info("Ending drag selection");
    if (hasDragMoved) {
      for (NodeEntity node : Set.copyOf(nodesToMove.keySet())) {
        repairID(node);
      }
    }
  }

  private void repairID(NodeEntity node) {
    if (node.getNodeID()
        .equals(
            String.format(
                "%sX%04dY%04d", node.getFloor().toString(), node.getXcoord(), node.getYcoord())))
      return;

    var newNode =
        new NodeEntity(
            String.format(
                "%sX%04dY%04d", node.getFloor().toString(), node.getXcoord(), node.getYcoord()),
            node.getXcoord(),
            node.getYcoord(),
            node.getFloor().toString(),
            node.getBuilding());
    Main.getRepo().addNode(newNode);

    try {
      final var n1Edges =
          Main.getRepo().getEdges().stream()
              .filter(edge -> edge.getNode1ID().equals(node.getNodeID()))
              .collect(Collectors.toSet());
      final var n2Edges =
          Main.getRepo().getEdges().stream()
              .filter(edge -> edge.getNode2ID().equals(node.getNodeID()))
              .collect(Collectors.toSet());
      final var moves =
          Main.getRepo().getMoves().stream()
              .filter(move -> move.getNode().getNodeID().equals(node.getNodeID()))
              .collect(Collectors.toSet());

      n1Edges.forEach(
          edge -> {
            log.info("Updating edge " + edge.getNode1ID() + " " + edge.getNode2ID());
            Main.getRepo().deleteEdge(edge);
            edge.setNode1(newNode);
            Main.getRepo().addEdge(edge);
          });
      n2Edges.forEach(
          edge -> {
            log.info("Updating edge " + edge.getNode1ID() + " " + edge.getNode2ID());
            Main.getRepo().deleteEdge(edge);
            edge.setNode2(newNode);
            Main.getRepo().addEdge(edge);
          });
      moves.forEach(
          move -> {
            Main.getRepo().deleteMove(move);
            move.setNodeID(newNode.getNodeID());
            Main.getRepo().addMove(move);
          });

      Main.getRepo().deleteNode(node);
    } catch (PersistenceException e) {
      // don't pollute the database
      Main.getRepo().deleteNode(newNode);
      log.error("Unable to repair ID", e);
    }
  }

  private Vector2 lastDragPosition = null;

  private void beginPanMap(Vector2 origin) {
    log.info("Beginning pan map. Origin: {}", origin);
    // lastDragPosition.setX(origin.getX());
    // lastDragPosition.setY(origin.getY());
    lastDragPosition = null;
    clipPane.setCursor(Cursor.CLOSED_HAND);
  }

  private void updatePanMap(Vector2 position) {
    log.debug("Updating pan map. Position: {}", position);
    if (lastDragPosition == null) lastDragPosition = new Vector2(position);
    final var newView = Vector2.minus(position, lastDragPosition);
    translateView(newView.getX(), newView.getY());
    lastDragPosition.setX(position.getX());
    lastDragPosition.setY(position.getY());
  }

  private void endPanMap() {
    log.info("Ending pan map");
    clipPane.setCursor(Cursor.DEFAULT);
  }

  private void repairEdgesAndDelete(NodeEntity node) {
    final var hasMoves =
        Main.getRepo().getMoves().stream()
            .anyMatch(move -> move.getNodeID().equals(node.getNodeID()));
    if (hasMoves) {
      final var alert =
          new Alert(
              Alert.AlertType.CONFIRMATION,
              "Node '"
                  + node.getNodeID()
                  + "' has moves associated with it. If you continue, they will be deleted as well.",
              ButtonType.OK,
              ButtonType.CANCEL);
      final var button = alert.showAndWait();
      if (button.isEmpty() || !button.get().equals(ButtonType.OK)) return;

      final var moves =
          Main.getRepo().getMoves().stream()
              .filter(move -> move.getNodeID().equals(node.getNodeID()))
              .collect(Collectors.toSet());
      moves.forEach(Main.getRepo()::deleteMove);
    }

    final var edges = node.getEdges();

    // find all nodes connected to this node. all nodes in this set are reachable from any other
    // node in the set.
    final var nodes = new HashSet<NodeEntity>();

    edges.forEach(
        edge -> {
          nodes.add(edge.getNode1());
          nodes.add(edge.getNode2());
        });

    // remove the deleted node, as we don't want to create edges to it.
    nodes.remove(node);

    // remove the old edges
    edges.forEach(Main.getRepo()::deleteEdge);

    // recreate the graph. if you were to include the original edges, this graph would be
    // transitive.
    connectNodes(nodes);

    // finally, delete the node.
    Main.getRepo().deleteNode(node);
  }

  private final ContextMenu contextMenu = new ContextMenu();

  private void beginMapContextMenu(MouseEvent event) {
    final var addNode = new MenuItem("Add node");
    addNode.setOnAction(actionEvent -> createNode(getCoordsPosition(event)));

    var numSelected = selectedNodes.size();
    final var connect =
        new MenuItem(String.format("Connect %d node%s", numSelected, numSelected == 1 ? "" : "s"));
    connect.setOnAction(actionEvent -> connectNodes(selectedNodes));
    connect.setDisable(numSelected < 2);

    numSelected += selectedEdges.size();
    final var delete =
        new MenuItem(String.format("Delete %d item%s", numSelected, numSelected == 1 ? "" : "s"));
    delete.setOnAction(actionEvent -> deleteSelected());
    delete.setDisable(numSelected == 0);

    final var disable = !selectedNodes.isEmpty() || selectedEdges.isEmpty();
    final var alignHL = new MenuItem("Align horizontally to left node");
    alignHL.setDisable(disable);
    alignHL.setOnAction(
        actionEvent -> Set.copyOf(selectedEdges).forEach(this::alignHorizontallyToLeft));

    final var alignHR = new MenuItem("Align horizontally to right node");
    alignHR.setDisable(disable);
    alignHR.setOnAction(
        actionEvent -> Set.copyOf(selectedEdges).forEach(this::alignHorizontallyToRight));

    final var alignVT = new MenuItem("Align vertically to top node");
    alignVT.setDisable(disable);
    alignVT.setOnAction(
        actionEvent -> Set.copyOf(selectedEdges).forEach(this::alignVerticallyToTop));

    final var alignVB = new MenuItem("Align vertically to bottom node");
    alignVB.setDisable(disable);
    alignVB.setOnAction(
        actionEvent -> Set.copyOf(selectedEdges).forEach(this::alignVerticallyToBottom));

    contextMenu.getItems().setAll(addNode, connect, delete, alignHL, alignHR, alignVT, alignVB);

    final var clickPoint = getScreenPosition(event);
    contextMenu.show(clipPane.getScene().getWindow(), clickPoint.getX(), clickPoint.getY());
    contextMenu.setAutoHide(true);
  }

  private void connectNodes(Set<NodeEntity> nodes) {
    // we're using a hashset here because we want to check deep equality here, rather than checking
    // for the same object.
    final var edges = new HashSet<EdgeEntity>();
    // unfortunately this is O(n^2), but we really shouldn't see high enough values of n to care.
    for (NodeEntity node1 : nodes) {
      for (NodeEntity node2 : nodes) {
        if (node1.equals(node2)) continue;
        final var newEdge = new EdgeEntity(node1, node2);
        if (edgeExistsIn(edges, newEdge)) continue;
        // ensure that we don't create duplicate edges.
        if (edgeExistsIn(Main.getRepo().getEdges(), newEdge)) continue;
        edges.add(newEdge);
      }
    }

    edges.forEach(Main.getRepo()::addEdge);
  }

  private boolean edgeExistsIn(Collection<EdgeEntity> edges, EdgeEntity edge) {
    return edges.stream()
        .anyMatch(
            e ->
                e.equals(edge)
                    || (e.getNode2ID().equals(edge.getNode1ID())
                        && e.getNode1ID().equals(edge.getNode2ID())));
  }

  private void alignHorizontallyToLeft(EdgeEntity edge) {
    final var node1 = edge.getNode1();
    final var node2 = edge.getNode2();

    NodeEntity leftNode, rightNode;
    if (node1.getXcoord() < node2.getXcoord()) {
      leftNode = node1;
      rightNode = node2;
    } else {
      leftNode = node2;
      rightNode = node1;
    }

    rightNode.setYcoord(leftNode.getYcoord());
    repairID(rightNode);
  }

  private void alignHorizontallyToRight(EdgeEntity edge) {
    final var node1 = edge.getNode1();
    final var node2 = edge.getNode2();

    NodeEntity leftNode, rightNode;
    if (node1.getXcoord() < node2.getXcoord()) {
      leftNode = node1;
      rightNode = node2;
    } else {
      leftNode = node2;
      rightNode = node1;
    }

    leftNode.setYcoord(rightNode.getYcoord());
    repairID(leftNode);
  }

  private void alignVerticallyToTop(EdgeEntity edge) {
    final var node1 = edge.getNode1();
    final var node2 = edge.getNode2();

    NodeEntity topNode, bottomNode;
    if (node1.getYcoord() < node2.getYcoord()) {
      topNode = node1;
      bottomNode = node2;
    } else {
      topNode = node2;
      bottomNode = node1;
    }

    bottomNode.setXcoord(topNode.getXcoord());
    repairID(bottomNode);
  }

  private void alignVerticallyToBottom(EdgeEntity edge) {
    final var node1 = edge.getNode1();
    final var node2 = edge.getNode2();

    NodeEntity topNode, bottomNode;
    if (node1.getYcoord() < node2.getYcoord()) {
      topNode = node1;
      bottomNode = node2;
    } else {
      topNode = node2;
      bottomNode = node1;
    }

    topNode.setXcoord(bottomNode.getXcoord());
    repairID(topNode);
  }

  // left-click on the map: deselect everything
  // left-click and drag on the map: deselect everything and begin rectangle select
  // shift-left-click and drag on the map: begin rectangle select
  // middle-click and drag on the map: pan map

  private void onMapClicked(MouseEvent event) {
    log.info("Map clicked.");
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
          case SECONDARY -> {
            beginMapContextMenu(event);
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

  private void onMapKeyPressed(KeyEvent event) {
    switch (event.getCode()) {
      case BACK_SPACE, DELETE -> deleteSelected();
      default -> {}
    }
  }

  private Vector2 screenToCoords(double screenX, double screenY) {
    return new Vector2(viewPane.screenToLocal(screenX, screenY));
  }

  private Vector2 getCoordsPosition(MouseEvent event) {
    return screenToCoords(event.getScreenX(), event.getScreenY());
  }

  private Vector2 getCoordsPosition(ScrollEvent event) {
    return screenToCoords(event.getScreenX(), event.getScreenY());
  }

  private Vector2 getScreenPosition(MouseEvent event) {
    return new Vector2(event.getScreenX(), event.getScreenY());
  }

  private Vector2 getScreenPosition(ScrollEvent event) {
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
