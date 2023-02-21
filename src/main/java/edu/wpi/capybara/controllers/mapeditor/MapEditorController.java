package edu.wpi.capybara.controllers.mapeditor;

import edu.wpi.capybara.controllers.mapeditor.ui.MapEditorMapView;
import edu.wpi.capybara.controllers.mapeditor.ui.MapEditorTableView;
import edu.wpi.capybara.controllers.mapeditor.ui.UIModel;
import edu.wpi.capybara.controllers.mapeditor.ui.UIModelImpl;
import edu.wpi.capybara.objects.Floor;
import edu.wpi.capybara.objects.hibernate.EdgeEntity;
import edu.wpi.capybara.objects.hibernate.LocationnameEntity;
import edu.wpi.capybara.objects.hibernate.MoveEntity;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
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
    model = new UIModelImpl();
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
  }
}
