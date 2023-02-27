package edu.wpi.cs3733.C23.teamC.controllers.mapeditor;

import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.MapEditorMapView;
import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.MapEditorTableView;
import edu.wpi.cs3733.C23.teamC.objects.Floor;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.EdgeEntity;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.LocationnameEntity;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.MoveEntity;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.NodeEntity;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

public class MapEditorController {

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
    mapView = new MapEditorMapView(mapViewRoot);
    mapView.showLabelsProperty().bind(showNames.selectedProperty());
    tableView =
        new MapEditorTableView(
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
                mapView.setShownFloor((Floor) oldValue.getUserData());
              } else {
                mapView.setShownFloor((Floor) newValue.getUserData());
              }
            }));

    mapView.setOnNodeSelected(tableView::select);
    mapView.setOnEdgeSelected(tableView::select);
    mapView.setOnNodeDeselected(tableView::deselect);
    mapView.setOnEdgeDeselected(tableView::deselect);
  }
}
