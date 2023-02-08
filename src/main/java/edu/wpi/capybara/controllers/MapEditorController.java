package edu.wpi.capybara.controllers;

import edu.wpi.capybara.controllers.mapeditor.*;
import edu.wpi.capybara.controllers.mapeditor.dialogs.AddLocationNameDialog;
import edu.wpi.capybara.controllers.mapeditor.dialogs.AddNodeDialog;
import edu.wpi.capybara.controllers.mapeditor.dialogs.ReplaceNodeDialog;
import edu.wpi.capybara.database.DatabaseConnect;
import java.util.function.Function;
import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.NumberStringConverter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MapEditorController {

  @FXML private TableView<NodePropertyAdapter> nodeTableView;
  @FXML private TableView<EdgePropertyAdapter> edgeTableView;
  @FXML private TableView<LocationNamePropertyAdapter> locationNameTableView;
  @FXML private TableView<MovePropertyAdapter> moveTableView;

  @FXML private Pane mapViewerPane;
  @FXML private Canvas mapViewerCanvas;

  // this, ideally, would be its own controller initialized by FXML. however, talking to controllers
  // managed by FXML
  // is a bit awkward, so I'm using composition as a workaround here.
  private MapViewerController mapViewerController;

  private DBObjectRepository repository;

  @FXML
  public void initialize() {
    repository = new DBObjectRepository();

    initializeNodeTable();
    initializeEdgeTable();
    initializeLocationNameTable();
    initializeMoveTable();
    // refreshData();

    mapViewerController =
        new MapViewerController(mapViewerPane, mapViewerCanvas, repository.nodesProperty());
  }

  private void initializeNodeTable() {
    final var nodeIDColumn =
        createTableColumn(
            "Node ID", NodePropertyAdapter::nodeIDProperty, new DefaultStringConverter());
    final var xCoordColumn =
        createTableColumn("X", NodePropertyAdapter::xCoordProperty, new NumberStringConverter());
    final var yCoordColumn =
        createTableColumn("Y", NodePropertyAdapter::yCoordProperty, new NumberStringConverter());
    final var floorColumn =
        createTableColumn(
            "Floor", NodePropertyAdapter::floorProperty, new DefaultStringConverter());
    final var buildingColumn =
        createTableColumn(
            "Building", NodePropertyAdapter::buildingProperty, new DefaultStringConverter());

    //noinspection unchecked
    nodeTableView
        .getColumns()
        .setAll(nodeIDColumn, xCoordColumn, yCoordColumn, floorColumn, buildingColumn);
    // nodeTableView.setEditable(true);

    nodeTableView.setItems(repository.getNodes());
    nodeTableView.itemsProperty().bind(repository.nodesProperty());

    final var deleteItem = new MenuItem("Delete");
    deleteItem.setOnAction(
        event ->
            repository.getNodes().removeAll(nodeTableView.getSelectionModel().getSelectedItems()));
    final var addItem = new MenuItem("New");
    addItem.setOnAction(
        event -> {
          new AddNodeDialog(nodeTableView.getScene().getWindow(), repository).showAndWait();
        });
    final var replaceItem = new MenuItem("Modify");
    replaceItem.setOnAction(
        event ->
            new ReplaceNodeDialog(
                    nodeTableView.getScene().getWindow(),
                    repository,
                    nodeTableView.getSelectionModel().getSelectedItem())
                .showAndWait());
    final var menu = new ContextMenu(addItem, replaceItem, deleteItem);
    nodeTableView.setContextMenu(menu);
  }

  private void initializeEdgeTable() {
    final var startNodeColumn =
        createTableColumn(
            "Start Node ID", EdgePropertyAdapter::startNodeProperty, new DefaultStringConverter());
    final var endNodeColumn =
        createTableColumn(
            "End Node ID", EdgePropertyAdapter::endNodeProperty, new DefaultStringConverter());

    //noinspection unchecked
    edgeTableView.getColumns().setAll(startNodeColumn, endNodeColumn);
    edgeTableView.setEditable(true);

    edgeTableView.setItems(repository.getEdges());
    edgeTableView.itemsProperty().bindBidirectional(repository.edgesProperty());
  }

  private void initializeLocationNameTable() {
    final var longNameColumn =
        createTableColumn(
            "Long Name",
            LocationNamePropertyAdapter::longNameProperty,
            new DefaultStringConverter());
    final var shortNameColumn =
        createTableColumn(
            "Short Name",
            LocationNamePropertyAdapter::shortNameProperty,
            new DefaultStringConverter());
    final var locationTypeColumn =
        createTableColumn(
            "Location Type",
            LocationNamePropertyAdapter::locationTypeProperty,
            new DefaultStringConverter());

    //noinspection unchecked
    locationNameTableView.getColumns().setAll(longNameColumn, shortNameColumn, locationTypeColumn);
    locationNameTableView.setEditable(true);

    locationNameTableView.setItems(repository.getLocationNames());
    locationNameTableView.itemsProperty().bindBidirectional(repository.locationNamesProperty());

    final var deleteItem = new MenuItem("Delete");
    deleteItem.setOnAction(
        event ->
            repository
                .getLocationNames()
                .removeAll(locationNameTableView.getSelectionModel().getSelectedItems()));
    final var addItem = new MenuItem("New");
    addItem.setOnAction(
        event ->
            new AddLocationNameDialog(locationNameTableView.getScene().getWindow(), repository)
                .showAndWait());
    final var menu = new ContextMenu(addItem, deleteItem);
    locationNameTableView.setContextMenu(menu);
  }

  private void initializeMoveTable() {
    final var nodeIDColumn =
        createTableColumn(
            "Node ID", MovePropertyAdapter::nodeIDProperty, new DefaultStringConverter());
    final var longNameColumn =
        createTableColumn(
            "Long Name", MovePropertyAdapter::longNameProperty, new DefaultStringConverter());
    final var moveDateColumn =
        createTableColumn(
            "Move Date", MovePropertyAdapter::moveDateProperty, new SQLDateStringConverter());

    //noinspection unchecked
    moveTableView.getColumns().setAll(nodeIDColumn, longNameColumn, moveDateColumn);
    moveTableView.setEditable(true);

    moveTableView.setItems(repository.getMoves());
    moveTableView.itemsProperty().bindBidirectional(repository.movesProperty());
  }

  private void refreshData() {
    // DatabaseConnect.importData();

    final var nodeMap = DatabaseConnect.getNodes();
    if (nodeMap == null) {
      log.error("Problem communicating with the database: Node HashMap is null.");
    } else {
      final var nodes = nodeMap.values();
      final var adapters = nodes.stream().map(NodePropertyAdapter::new).toList();
      nodeTableView.getItems().setAll(adapters);
    }

    final var edgeMap = DatabaseConnect.getEdges();
    if (edgeMap == null) {
      log.error("Problem communicating with the database: Edge HashMap is null.");
    } else {
      final var edges = edgeMap.values();
      final var adapters = edges.stream().map(EdgePropertyAdapter::new).toList();
      edgeTableView.getItems().setAll(adapters);
    }

    final var locationNameMap = DatabaseConnect.getLocationNames();
    if (locationNameMap == null) {
      log.error("Problem communicating with the database: Location Name HashMap is null.");
    } else {
      final var locationNames = locationNameMap.values();
      final var adapters = locationNames.stream().map(LocationNamePropertyAdapter::new).toList();
      locationNameTableView.getItems().setAll(adapters);
    }

    final var moveMap = DatabaseConnect.getMoves();
    if (moveMap == null) {
      log.error("Problem communicating with the database: Move HashMap is null.");
    } else {
      final var moves = moveMap.values();
      final var adapters = moves.stream().map(MovePropertyAdapter::new).toList();
      moveTableView.getItems().setAll(adapters);
    }
  }

  private static <S, T> TableColumn<S, T> createTableColumn(
      String columnName,
      Function<S, ? extends Property<T>> propertyGetter,
      StringConverter<T> converter) {
    final var tableColumn = new TableColumn<S, T>(columnName);
    tableColumn.setCellFactory(TextFieldTableCell.forTableColumn(converter));
    final var adapter = new CellPropertyAdapter<>(propertyGetter);
    tableColumn.setCellValueFactory(adapter);
    tableColumn.setOnEditCommit(adapter);
    return tableColumn;
  }
}
