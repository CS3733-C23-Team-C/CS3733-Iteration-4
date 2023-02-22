package edu.wpi.capybara.controllers;

import edu.wpi.capybara.controllers.mapeditor.AdapterRepository;
import edu.wpi.capybara.controllers.mapeditor.SQLDateStringConverter;
import edu.wpi.capybara.controllers.mapeditor.adapters.*;
import edu.wpi.capybara.controllers.mapeditor.dialogs.AddNodeDialog;
import edu.wpi.capybara.controllers.mapeditor.dialogs.ReplaceNodeDialog;
import edu.wpi.capybara.controllers.mapeditor.ui.MapEditorPane;
import edu.wpi.capybara.objects.Floor;
import edu.wpi.capybara.objects.hibernate.EdgeEntity;
import jakarta.persistence.PersistenceException;
import java.util.function.Function;
import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.SetChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.NumberStringConverter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MapEditorController {
  @FXML private TableView<NodeAdapter> nodeTableView;
  @FXML private TableView<EdgeAdapter> edgeTableView;
  @FXML private TableView<LocationNameAdapter> locationNameTableView;
  @FXML private TableView<MoveAdapter> moveTableView;

  @FXML private MapEditorPane mapEditor;
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

  private ReadOnlyListProperty<NodeAdapter> nodes;
  private ReadOnlyListProperty<EdgeAdapter> edges;
  private ReadOnlyListProperty<MoveAdapter> moves;
  private ReadOnlyListProperty<LocationNameAdapter> locations;

  private AdapterRepository repo;

  @FXML
  public void initialize() {
    log.info("Initializing repository");
    repo = new AdapterRepository();
    log.info("Repository initialized.");
    nodes = repo.getNodes();
    edges = repo.getEdges();
    moves = repo.getMoves();
    locations = repo.getLocations();

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
                mapEditor.setShownFloor((Floor) oldValue.getUserData());
              } else {
                mapEditor.setShownFloor((Floor) newValue.getUserData());
              }
            }));

    log.info("Populating map...");
    nodes.forEach(mapEditor::addNode);
    edges.forEach(mapEditor::addEdge);
    nodes.addListener(
        (ListChangeListener<? super NodeAdapter>)
            change -> {
              while (change.next()) {
                change.getAddedSubList().forEach(mapEditor::addNode);
                change.getRemoved().forEach(mapEditor::removeNode);
              }
            });
    edges.addListener(
        (ListChangeListener<? super EdgeAdapter>)
            change -> {
              while (change.next()) {
                change.getAddedSubList().forEach(mapEditor::addEdge);
                change.getRemoved().forEach(mapEditor::removeEdge);
              }
            });

    initializeNodeTable();
    initializeEdgeTable();
    initializeLocationNameTable();
    initializeMoveTable();

    mapEditor
        .selectedProperty()
        .addListener(
            (SetChangeListener<? super Object>)
                change -> {
                  if (change.wasAdded()) {
                    final var obj = change.getElementAdded();
                    // if we were using a newer version of Java we could use a pattern-matching
                    // switch,
                    // but alas we are stuck on JDK 17
                    if (obj instanceof NodeAdapter node) {
                      editorTabs.selectToggle(nodeToggle);
                      nodeTableView.getSelectionModel().select(node);
                      nodeTableView.scrollTo(node);
                      nodeTableView.requestFocus();
                    } else if (obj instanceof EdgeAdapter edge) {
                      editorTabs.selectToggle(edgeToggle);
                      edgeTableView.getSelectionModel().select(edge);
                      edgeTableView.scrollTo(edge);
                      edgeTableView.requestFocus();
                    }
                  }
                });

    final var add = new MenuItem("Add Edge");
    add.setOnAction(
        event -> {
          if (add.getUserData() instanceof NodeAdapter[] nodeArray && nodeArray.length == 2) {
            final var node1 = nodeArray[0];
            final var node2 = nodeArray[1];
            final var newEdge =
                new EdgeAdapter(new EdgeEntity(node1.getNodeID(), node2.getNodeID()), node1, node2);
            try {
              repo.addEdge(newEdge);
              mapEditor.setSelection(newEdge);
            } catch (PersistenceException e) {
              log.error("Error creating edge", e);
              mapEditor.deselectAll();
              new Alert(Alert.AlertType.ERROR, e.toString(), ButtonType.YES).show();
            }
            System.out.println("add edge");
          }
        });
    add.setDisable(true);
    final var delete = new MenuItem("Delete");
    delete.setOnAction(
        event -> {
          final var toDelete = mapEditor.selectedProperty().toArray();
          for (Object obj : toDelete) {
            if (obj instanceof NodeAdapter node) {
              repo.deleteNode(node);
            } else if (obj instanceof EdgeAdapter edge) {
              repo.deleteEdge(edge);
            }
          }
        });
    delete.disableProperty().bind(mapEditor.selectedProperty().emptyProperty());
    final var menu = new ContextMenu(add, delete);

    mapEditor
        .selectedProperty()
        .addListener(
            (InvalidationListener)
                observable -> {
                  final var selected = mapEditor.selectedProperty().get();
                  if (selected.size() == 2) {
                    final var iter = selected.iterator();
                    if (iter.next() instanceof NodeAdapter node1
                        && iter.next() instanceof NodeAdapter node2) {
                      add.setUserData(new NodeAdapter[] {node1, node2});
                      add.setDisable(false);
                      return;
                    }
                  }
                  add.setUserData(null);
                  add.setDisable(true);
                });
    mapEditor.setContextMenu(menu);
  }

  private void initializeNodeTable() {
    final var nodeIDColumn =
        createTableColumn("Node ID", NodeAdapter::nodeIDProperty, new DefaultStringConverter());
    final var xCoordColumn =
        createTableColumn("X", NodeAdapter::xCoordProperty, new NumberStringConverter());
    final var yCoordColumn =
        createTableColumn("Y", NodeAdapter::yCoordProperty, new NumberStringConverter());
    final var floorColumn =
        createTableColumn("Floor", NodeAdapter::floorProperty, new DefaultStringConverter());
    final var buildingColumn =
        createTableColumn("Building", NodeAdapter::buildingProperty, new DefaultStringConverter());

    nodeIDColumn.setEditable(false);
    xCoordColumn.setEditable(false);
    yCoordColumn.setEditable(false);

    //noinspection unchecked
    nodeTableView
        .getColumns()
        .setAll(nodeIDColumn, xCoordColumn, yCoordColumn, floorColumn, buildingColumn);
    nodeTableView.setEditable(true);

    nodeTableView.itemsProperty().bind(nodes);

    final var deleteItem = new MenuItem("Delete");
    deleteItem.setOnAction(
        event -> nodeTableView.getSelectionModel().getSelectedItems().forEach(repo::deleteNode));
    final var addItem = new MenuItem("New");
    addItem.setOnAction(
        event -> new AddNodeDialog(nodeTableView.getScene().getWindow(), repo).showAndWait());
    final var replaceItem = new MenuItem("Modify");
    replaceItem.setOnAction(
        event ->
            new ReplaceNodeDialog(
                    nodeTableView.getScene().getWindow(),
                    repo,
                    nodeTableView.getSelectionModel().getSelectedItem())
                .showAndWait());
    final var menu = new ContextMenu(addItem, replaceItem, deleteItem);
    nodeTableView.setContextMenu(menu);

    nodeTableView.visibleProperty().bind(nodeToggle.selectedProperty());
  }

  private void initializeEdgeTable() {
    final var startNodeColumn =
        createTableColumn(
            "Start Node ID", EdgeAdapter::startNodeProperty, new DefaultStringConverter());
    final var endNodeColumn =
        createTableColumn(
            "End Node ID", EdgeAdapter::endNodeProperty, new DefaultStringConverter());

    //noinspection unchecked
    edgeTableView.getColumns().setAll(startNodeColumn, endNodeColumn);
    edgeTableView.setEditable(true);

    edgeTableView.itemsProperty().bind(edges);

    edgeTableView.visibleProperty().bind(edgeToggle.selectedProperty());
  }

  private void initializeLocationNameTable() {
    final var longNameColumn =
        createTableColumn(
            "Long Name", LocationNameAdapter::longNameProperty, new DefaultStringConverter());
    final var shortNameColumn =
        createTableColumn(
            "Short Name", LocationNameAdapter::shortNameProperty, new DefaultStringConverter());
    final var locationTypeColumn =
        createTableColumn(
            "Location Type",
            LocationNameAdapter::locationTypeProperty,
            new DefaultStringConverter());

    //noinspection unchecked
    locationNameTableView.getColumns().setAll(longNameColumn, shortNameColumn, locationTypeColumn);
    locationNameTableView.setEditable(true);

    locationNameTableView.itemsProperty().bind(locations);

    final var deleteItem = new MenuItem("Delete");
    deleteItem.setOnAction(
        event ->
            locationNameTableView
                .getSelectionModel()
                .getSelectedItems()
                .forEach(repo::deleteLocationName));
    final var addItem = new MenuItem("New");
    //    addItem.setOnAction(
    //        event ->
    //            new AddLocationNameDialog(locationNameTableView.getScene().getWindow(), repo)
    //                .showAndWait());
    final var menu = new ContextMenu(addItem, deleteItem);
    locationNameTableView.setContextMenu(menu);

    locationNameTableView.visibleProperty().bind(locationToggle.selectedProperty());
  }

  private void initializeMoveTable() {
    final var nodeIDColumn =
        createTableColumn("Node ID", MoveAdapter::nodeIDProperty, new DefaultStringConverter());
    final var longNameColumn =
        createTableColumn("Long Name", MoveAdapter::longNameProperty, new DefaultStringConverter());
    final var moveDateColumn =
        createTableColumn("Move Date", MoveAdapter::moveDateProperty, new SQLDateStringConverter());

    //noinspection unchecked
    moveTableView.getColumns().setAll(nodeIDColumn, longNameColumn, moveDateColumn);
    moveTableView.setEditable(true);

    moveTableView.itemsProperty().bind(moves);

    moveTableView.visibleProperty().bind(moveToggle.selectedProperty());
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
