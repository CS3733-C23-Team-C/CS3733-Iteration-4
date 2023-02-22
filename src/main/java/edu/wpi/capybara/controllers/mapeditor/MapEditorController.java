package edu.wpi.capybara.controllers.mapeditor;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.controllers.mapeditor.ui.MapEditorMapView;
import edu.wpi.capybara.controllers.mapeditor.ui.MapEditorTableView;
import edu.wpi.capybara.controllers.mapeditor.ui.UIModel;
import edu.wpi.capybara.controllers.mapeditor.ui.UIModelImpl;
import edu.wpi.capybara.objects.Floor;
import edu.wpi.capybara.objects.hibernate.EdgeEntity;
import edu.wpi.capybara.objects.hibernate.LocationnameEntity;
import edu.wpi.capybara.objects.hibernate.MoveEntity;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
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
    final var contextMenu = new ContextMenu(addNode);

    mapViewRoot.setOnContextMenuRequested(
        event -> {
          clickX.set(event.getX());
          clickY.set(event.getY());
          contextMenu.show(mapViewRoot, event.getScreenX(), event.getScreenY());
        });
  }

  private Point2D click2Coord(double clickX, double clickY) {
      return new Point2D(clickX, clickY);
  }
}
