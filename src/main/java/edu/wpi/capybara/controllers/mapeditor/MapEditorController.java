package edu.wpi.capybara.controllers.mapeditor;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.controllers.mapeditor.ui.MapEditorMapView;
import edu.wpi.capybara.controllers.mapeditor.ui.MapEditorTableView;
import edu.wpi.capybara.controllers.mapeditor.ui.UIModel;
import edu.wpi.capybara.controllers.mapeditor.ui.UIModelImpl;
import edu.wpi.capybara.controllers.mapeditor.ui.elements.EdgeElement;
import edu.wpi.capybara.controllers.mapeditor.ui.elements.Element;
import edu.wpi.capybara.controllers.mapeditor.ui.elements.NodeElement;
import edu.wpi.capybara.objects.Floor;
import edu.wpi.capybara.objects.hibernate.EdgeEntity;
import edu.wpi.capybara.objects.hibernate.LocationnameEntity;
import edu.wpi.capybara.objects.hibernate.MoveEntity;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class MapEditorController {

  private UIModel model;
  private MapEditorMapView mapView;
  private MapEditorTableView tableView;

  @FXML private StackPane mapViewRoot;

  @FXML private TableView<NodeEntity> nodeTableView;
  @FXML private TableView<EdgeEntity> edgeTableView;
  @FXML private TableView<MoveEntity> moveTableView;
  @FXML private TableView<LocationnameEntity> locationNameTableView;

  @FXML private ToggleGroup editorTabs;
  @FXML private ToggleButton nodeToggle;
  @FXML private ToggleButton edgeToggle;
  @FXML private ToggleButton moveToggle;
  @FXML private ToggleButton locationToggle;

  @FXML private ToggleGroup floors;
  @FXML private ToggleButton l1Toggle;
  @FXML private ToggleButton l2Toggle;
  @FXML private ToggleButton f1Toggle;
  @FXML private ToggleButton f2Toggle;
  @FXML private ToggleButton f3Toggle;

  @FXML private ToggleButton showNames;

  @FXML
  public void initialize() {
    model = new UIModelImpl(nodeTableView, edgeTableView, moveTableView, locationNameTableView);
    model.showLabelsProperty().bind(showNames.selectedProperty());
    mapView = new MapEditorMapView(model, mapViewRoot);
    tableView =
        new MapEditorTableView(
            model,
            nodeTableView,
            edgeTableView,
            moveTableView,
            locationNameTableView,
            editorTabs,
            nodeToggle,
            edgeToggle,
            moveToggle,
            locationToggle);

    l1Toggle.setUserData(Floor.L1);
    l2Toggle.setUserData(Floor.L2);
    f1Toggle.setUserData(Floor.F1);
    f2Toggle.setUserData(Floor.F2);
    f3Toggle.setUserData(Floor.F3);
    floors
        .selectedToggleProperty()
        .addListener(
            ((observable, oldValue, newValue) -> {
              if (newValue == null) {
                floors.selectToggle(oldValue);
                model.setShownFloor((Floor) oldValue.getUserData());
              } else {
                model.setShownFloor((Floor) newValue.getUserData());
              }
            }));

    mapViewRoot.addEventHandler(
        MouseEvent.MOUSE_PRESSED,
        event -> {
          if (event.isStillSincePress()) model.deselectAll();
        });

    final var clickX = new SimpleDoubleProperty();
    final var clickY = new SimpleDoubleProperty();
    final var addNode = new MenuItem("Add Node");
    addNode.setOnAction(
        event -> {
          final var coords = click2Coord(clickX.get(), clickY.get());
          int x = (int) coords.getX();
          int y = (int) coords.getY();
          final var newNode =
              new NodeEntity(
                  String.format("%sX%dY%d", model.getShownFloor().toString(), x, y),
                  x,
                  y,
                  model.getShownFloor().toString(),
                  "");
          Main.getRepo().addNode(newNode);
        });

    final var addEdge = new MenuItem("Add Edge");
    addEdge.setOnAction(
        event -> {
          final var iter = model.selectedProperty().iterator();
          final var node1 = (NodeElement) iter.next();
          final var node2 = (NodeElement) iter.next();

          Main.getRepo().addEdge(new EdgeEntity(node1.getInRepo(), node2.getInRepo()));
        });
    addEdge
        .disableProperty()
        .bind(
            Bindings.createBooleanBinding(
                () -> {
                  final var selected = model.selectedProperty().get();
                  return !(selected.size() == 2
                      && selected.stream().allMatch(element -> element instanceof NodeElement));
                },
                model.selectedProperty()));
    final var deleteSelected = new MenuItem("Delete");
    deleteSelected.setOnAction(
        event -> {
          final var toDelete = Set.copyOf(model.selectedProperty());
          for (Element element : toDelete) {
            if (element instanceof NodeElement node) {
              repairEdgesAndDelete(node.getInRepo());
            } else if (element instanceof EdgeElement edge)
              Main.getRepo().deleteEdge(edge.getInRepo());
          }
        });
    deleteSelected.disableProperty().bind(model.selectedProperty().emptyProperty());
    final var contextMenu = new ContextMenu(addNode, addEdge, deleteSelected);
    contextMenu.setAutoHide(true);

    mapViewRoot.setOnContextMenuRequested(
        event -> {
          clickX.set(event.getScreenX());
          clickY.set(event.getScreenY());
          contextMenu.show(mapViewRoot, event.getScreenX(), event.getScreenY());
        });
    mapViewRoot.addEventFilter(
        MouseEvent.MOUSE_PRESSED,
        event -> {
          if (contextMenu.isShowing()) contextMenu.hide();
        });

    mapViewRoot.addEventFilter(
        MouseEvent.MOUSE_PRESSED,
        event -> {
          if (!event.isPrimaryButtonDown()) return;
          System.out.println("mousePressed" + model.selectedProperty().size());
          if (model.selectedProperty().size() == 1) {
            System.out.println("one selected");
            var iter = model.selectedProperty().iterator();
            var element = iter.next();
            if (element
                .getNode()
                .contains(
                    element.getNode().screenToLocal(event.getScreenX(), event.getScreenY()))) {
              System.out.println("Within bounds");
              if (element instanceof NodeElement node) {
                System.out.println("it's a node");
                moveState = MoveState.DRAG;
                editingNode = node;
                event.consume();
                return;
              }
            }
          }

          moveState = MoveState.CLICK;
        });
    mapViewRoot.addEventFilter(
        MouseEvent.MOUSE_DRAGGED,
        event -> {
          if (moveState == MoveState.DRAG) {
            var coord = click2Coord(event.getScreenX(), event.getScreenY());
            editingNode.getInRepo().setXcoord((int) coord.getX());
            editingNode.getInRepo().setYcoord((int) coord.getY());
            event.consume();
          } else {
            moveState = MoveState.CLICK;
          }
        });
    mapViewRoot.addEventFilter(
        MouseEvent.MOUSE_RELEASED,
        event -> {
          if (moveState == MoveState.DRAG) {
            repairID(editingNode.getInRepo());
            moveState = MoveState.CLICK;
            event.consume();
          } else {
            moveState = MoveState.CLICK;
          }
        });

    // this looks like it doesn't do anything but if you remove this then the edges don't update
    // when you move nodes around
    // I'm not entirely sure why this works, but it seems to be related to some interaction between
    // the Hibernate
    // threads and the JavaFX Application thread
    /*new Thread(
        () -> {
          final var converter = new MapEditorTableView.NodeConverter();
          for (EdgeEntity edge : Main.getRepo().getEdges()) {
            String str = converter.toString(edge.node1IDProperty().get());
            edge.node1IDProperty().set(converter.fromString(str));
            str = converter.toString(edge.node2IDProperty().get());
            edge.node2IDProperty().set(converter.fromString(str));
          }
        })
    .start();*/
  }

  private enum MoveState {
    CLICK,
    DRAG,
    DELETE
  }

  private MoveState moveState;

  private NodeElement editingNode;

  private Point2D click2Coord(double clickX, double clickY) {
    return mapView.getViewPane().screenToLocal(clickX, clickY);
  }

  public static void repairID(NodeEntity node) {
    /*
    var newNode =
        new NodeEntity(
            String.format(
                "%sX%dY%d", node.getFloor().toString(), node.getXcoord(), node.getYcoord()),
            node.getXcoord(),
            node.getYcoord(),
            node.getFloor().toString(),
            node.getBuilding());
    Main.getRepo().addNode(newNode);

    final var n1Edges =
        Main.getRepo().getEdges().stream()
            .filter(edge -> edge.getNode1().getNodeID().equals(node.getNodeID()))
            .toList();
    final var n2Edges =
        Main.getRepo().getEdges().stream()
            .filter(edge -> edge.getNode2().getNodeID().equals(node.getNodeID()))
            .toList();
    final var moves =
        Main.getRepo().getMoves().stream()
            .filter(move -> move.getNode().getNodeID().equals(node.getNodeID()))
            .collect(Collectors.toSet());

    n1Edges.forEach(
        edge -> {
          System.out.println(
              "Updating edge " + edge.getNode1().getNodeID() + " " + edge.getNode2().getNodeID());
          edge.setNode1(newNode);
        });
    n2Edges.forEach(
        edge -> {
          System.out.println(
              "Updating edge " + edge.getNode1().getNodeID() + " " + edge.getNode2().getNodeID());
          edge.setNode2(newNode);
        });
    moves.forEach(move -> move.setNode(newNode));

    Main.getRepo().deleteNode(node);*/
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

    // recreate the graph. if you were to include the original edges, this graph would be
    // transitive.
    // unfortunately this is O(n^2), but we really shouldn't see high enough values of n to care.
    final var newEdges = new HashSet<EdgeEntity>();
    for (NodeEntity node1 : nodes) {
      for (NodeEntity node2 : nodes) {
        newEdges.add(new EdgeEntity(node1, node2));
      }
    }

    // remove the old edges
    edges.forEach(Main.getRepo()::deleteEdge);
    // add the new ones
    newEdges.forEach(Main.getRepo()::addEdge);

    // finally, delete the node.
    Main.getRepo().deleteNode(node);
  }
}
