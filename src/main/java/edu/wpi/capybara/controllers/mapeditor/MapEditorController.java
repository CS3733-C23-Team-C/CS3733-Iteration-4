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
import java.util.Set;
import javafx.application.Platform;
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

  @FXML
  public void initialize() {
    model = new UIModelImpl(nodeTableView, edgeTableView, moveTableView, locationNameTableView);
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
            if (element instanceof NodeElement node) Main.getRepo().deleteNode(node.getInRepo());
            else if (element instanceof EdgeElement edge)
              Main.getRepo().deleteEdge(edge.getInRepo());
          }
        });
    deleteSelected.disableProperty().bind(model.selectedProperty().emptyProperty());
    final var contextMenu = new ContextMenu(addNode, addEdge, deleteSelected);

    mapViewRoot.setOnContextMenuRequested(
        event -> {
          clickX.set(event.getScreenX());
          clickY.set(event.getScreenY());
          contextMenu.show(mapViewRoot, event.getScreenX(), event.getScreenY());
        });

    mapViewRoot.addEventFilter(
        MouseEvent.MOUSE_PRESSED,
        event -> {
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
            moveState = MoveState.CLICK;
            event.consume();
          }
        });

    // this looks like it doesn't do anything but if you remove this then the edges don't update
    // when you move nodes
    // I'm not entirely sure why this works, but it seems to be related to some interaction between
    // the Hibernate
    // threads and the JavaFX Application thread
    Platform.runLater(
        () -> {
          final var converter = new MapEditorTableView.NodeConverter();
          for (EdgeEntity edge : Main.getRepo().getEdges()) {
            String str = converter.toString(edge.node1Property().get());
            edge.node1Property().set(converter.fromString(str));
            str = converter.toString(edge.node2Property().get());
            edge.node2Property().set(converter.fromString(str));
          }
        });
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
}
