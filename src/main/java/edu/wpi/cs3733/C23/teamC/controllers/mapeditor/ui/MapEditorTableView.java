package edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui;

import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.SQLDateStringConverter;
import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.adapters.*;
import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.dialogs.AddLocationNameDialog;
import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.dialogs.AddMoveDialog;
import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.elements.*;
import edu.wpi.cs3733.C23.teamC.objects.Floor;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.EdgeEntity;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.LocationnameEntity;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.MoveEntity;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.NodeEntity;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import javafx.beans.property.Property;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.NumberStringConverter;

public class MapEditorTableView {
  private final TableView<NodeEntity> nodeTableView;
  private final TableView<EdgeEntity> edgeTableView;
  private final TableView<MoveEntity> moveTableView;
  private final TableView<LocationnameEntity> locationNameTableView;

  private final ToggleGroup editorTabs;
  private final ToggleButton nodeToggle;
  private final ToggleButton edgeToggle;
  private final ToggleButton moveToggle;
  private final ToggleButton locationToggle;

  public MapEditorTableView(
      TableView<NodeEntity> nodeTable,
      TableView<EdgeEntity> edgeTable,
      TableView<MoveEntity> moveTable,
      TableView<LocationnameEntity> locationTable,
      ToggleGroup editorTabs,
      ToggleButton nodeToggle,
      ToggleButton edgeToggle,
      ToggleButton moveToggle,
      ToggleButton locationToggle) {
    this.nodeTableView = nodeTable;
    this.edgeTableView = edgeTable;
    this.moveTableView = moveTable;
    this.locationNameTableView = locationTable;
    this.editorTabs = editorTabs;
    this.nodeToggle = nodeToggle;
    this.edgeToggle = edgeToggle;
    this.moveToggle = moveToggle;
    this.locationToggle = locationToggle;

    initializeNodeTable();
    initializeEdgeTable();
    initializeMoveTable();
    initializeLocationNameTable();

    // add listeners to add and remove elements from the ui model
    Main.getRepo().getNodes().addListener(createMapListener(this::addNode, this::removeNode));
    Main.getRepo().getEdges().addListener(createListListener(this::addEdge, this::removeEdge));
    Main.getRepo().getMoves().addListener(createListListener(this::addMove, this::removeMove));
    Main.getRepo()
        .getLocationNames()
        .addListener(createMapListener(this::addLocation, this::removeLocation));

    // add the extant elements
    Main.getRepo().getNodes().values().forEach(this::addNode);
    Main.getRepo().getEdges().forEach(this::addEdge);
    Main.getRepo().getMoves().forEach(this::addMove);
    Main.getRepo().getLocationNames().values().forEach(this::addLocation);
  }

  public void select(NodeEntity node) {
    nodeTableView.getSelectionModel().select(node);
    nodeTableView.scrollTo(node);
  }

  public void select(EdgeEntity edge) {
    edgeTableView.getSelectionModel().select(edge);
    edgeTableView.scrollTo(edge);
  }

  public void deselect(NodeEntity node) {
    nodeTableView.getSelectionModel().clearSelection(nodeTableView.getItems().indexOf(node));
  }

  public void deselect(EdgeEntity edge) {
    edgeTableView.getSelectionModel().clearSelection(edgeTableView.getItems().indexOf(edge));
  }

  private <K, E> MapChangeListener<K, E> createMapListener(Consumer<E> add, Consumer<E> remove) {
    return change -> {
      if (change.wasAdded()) add.accept(change.getValueAdded());
      if (change.wasRemoved()) remove.accept(change.getValueRemoved());
    };
  }

  private <E> ListChangeListener<E> createListListener(Consumer<E> add, Consumer<E> remove) {
    return change -> {
      while (change.next()) {
        if (change.wasAdded()) change.getAddedSubList().forEach(add);
        if (change.wasRemoved()) change.getRemoved().forEach(remove);
      }
    };
  }

  private void addNode(NodeEntity node) {
    nodeTableView.getItems().add(node);
  }

  private void removeNode(NodeEntity node) {
    nodeTableView.getItems().remove(node);
  }

  private void addEdge(EdgeEntity edge) {
    edgeTableView.getItems().add(edge);
  }

  private void removeEdge(EdgeEntity edge) {
    edgeTableView.getItems().remove(edge);
  }

  private void addMove(MoveEntity move) {
    moveTableView.getItems().add(move);
  }

  private void removeMove(MoveEntity move) {
    moveTableView.getItems().remove(move);
  }

  private void addLocation(LocationnameEntity locationname) {
    locationNameTableView.getItems().add(locationname);
  }

  private void removeLocation(LocationnameEntity locationname) {
    locationNameTableView.getItems().remove(locationname);
  }

  private void initializeNodeTable() {
    final var nodeIDColumn =
        createTableColumn("Node ID", NodeEntity::nodeIDProperty, new DefaultStringConverter());
    final var xCoordColumn =
        createTableColumn("X", NodeEntity::xcoordProperty, new NumberStringConverter());
    final var yCoordColumn =
        createTableColumn("Y", NodeEntity::ycoordProperty, new NumberStringConverter());
    final var floorColumn =
        createTableColumn("Floor", NodeEntity::floorProperty, new Floor.StringConverter());
    final var buildingColumn =
        createTableColumn("Building", NodeEntity::buildingProperty, new DefaultStringConverter());

    // don't allow primary keys to be edited
    nodeIDColumn.setEditable(false);

    //noinspection unchecked
    nodeTableView
        .getColumns()
        .setAll(nodeIDColumn, xCoordColumn, yCoordColumn, floorColumn, buildingColumn);
    nodeTableView.setEditable(true);

    nodeTableView.visibleProperty().bind(nodeToggle.selectedProperty());
    nodeTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    nodeTableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
  }

  private void initializeEdgeTable() {
    final var startNodeColumn =
        createTableColumn(
            "Start Node ID", EdgeEntity::node1IDProperty, new DefaultStringConverter());
    final var endNodeColumn =
        createTableColumn("End Node ID", EdgeEntity::node2IDProperty, new DefaultStringConverter());

    // don't allow primary keys to be edited
    startNodeColumn.setEditable(false);
    endNodeColumn.setEditable(false);

    //noinspection unchecked
    edgeTableView.getColumns().setAll(startNodeColumn, endNodeColumn);
    edgeTableView.setEditable(true);

    edgeTableView.visibleProperty().bind(edgeToggle.selectedProperty());
    edgeTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    edgeTableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
  }

  private void initializeLocationNameTable() {
    final var longNameColumn =
        createTableColumn(
            "Long Name", LocationnameEntity::longnameProperty, new DefaultStringConverter());
    final var shortNameColumn =
        createTableColumn(
            "Short Name", LocationnameEntity::shortnameProperty, new DefaultStringConverter());
    final var locationTypeColumn =
        createTableColumn(
            "Location Type",
            LocationnameEntity::locationtypeProperty,
            new DefaultStringConverter());

    // don't allow primary keys to be edited
    longNameColumn.setEditable(false);

    //noinspection unchecked
    locationNameTableView.getColumns().setAll(longNameColumn, shortNameColumn, locationTypeColumn);
    locationNameTableView.setEditable(true);

    locationNameTableView.visibleProperty().bind(locationToggle.selectedProperty());
    locationNameTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    locationNameTableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

    final var addItem = new MenuItem("Add...");
    addItem.setOnAction(
        event -> {
          final var dialog =
              new AddLocationNameDialog(locationNameTableView.getScene().getWindow());
          dialog.showAndWait();
          if (dialog.getResult() != null) {
            System.out.println(dialog.getResult());
          }
        });
    final var deleteItem = new MenuItem("Delete");
    deleteItem.setOnAction(
        event -> {
          final var toDelete =
              List.copyOf(locationNameTableView.getSelectionModel().getSelectedItems());
          toDelete.forEach(this::deleteLocationName);
        });

    final var contextMenu = new ContextMenu(addItem, deleteItem);
    locationNameTableView.setContextMenu(contextMenu);
  }

  private void deleteLocationName(LocationnameEntity entity) {
    final var hasMoves =
        Main.getRepo().getMoves().stream()
            .anyMatch(move -> move.getLongName().equals(entity.getLongname()));
    if (hasMoves) {
      final var alert =
          new Alert(
              Alert.AlertType.CONFIRMATION,
              "Location '"
                  + entity.getLongname()
                  + "' has moves associated with it. If you continue, they will be deleted as well.",
              ButtonType.OK,
              ButtonType.CANCEL);
      alert.setTitle("Please Confirm");
      alert.setHeaderText("Are you sure?");
      final var button = alert.showAndWait();
      if (button.isEmpty() || !button.get().equals(ButtonType.OK)) return;

      final var moves =
          Main.getRepo().getMoves().stream()
              .filter(move -> move.getLongName().equals(entity.getLongname()))
              .collect(Collectors.toSet());
      moves.forEach(Main.getRepo()::deleteMove);
    }
    Main.getRepo().deleteLocationName(entity);
  }

  private void initializeMoveTable() {
    final var nodeIDColumn =
        createTableColumn("Node ID", MoveEntity::nodeIDProperty, new DefaultStringConverter());
    final var longNameColumn =
        createTableColumn("Long Name", MoveEntity::longNameProperty, new DefaultStringConverter());
    final var moveDateColumn =
        createTableColumn("Move Date", MoveEntity::movedateProperty, new SQLDateStringConverter());

    // don't allow primary keys to be edited
    nodeIDColumn.setEditable(false);
    longNameColumn.setEditable(false);
    moveDateColumn.setEditable(false);

    //noinspection unchecked
    moveTableView.getColumns().setAll(nodeIDColumn, longNameColumn, moveDateColumn);
    moveTableView.setEditable(true);

    moveTableView.visibleProperty().bind(moveToggle.selectedProperty());
    moveTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    moveTableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

    final var addItem = new MenuItem("Add...");
    addItem.setOnAction(
        event -> new AddMoveDialog(moveTableView.getScene().getWindow()).showAndWait());
    final var deleteItem = new MenuItem("Delete");
    deleteItem.setOnAction(
        event -> {
          final var toDelete = List.copyOf(moveTableView.getSelectionModel().getSelectedItems());
          toDelete.forEach(Main.getRepo()::deleteMove);
        });

    final var contextMenu = new ContextMenu(addItem, deleteItem);
    moveTableView.setContextMenu(contextMenu);
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
