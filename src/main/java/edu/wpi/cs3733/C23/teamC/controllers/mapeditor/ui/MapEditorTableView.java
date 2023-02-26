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
import java.util.function.Function;
import java.util.stream.Collectors;
import javafx.beans.property.Property;
import javafx.collections.SetChangeListener;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.NumberStringConverter;

public class MapEditorTableView {

  private final UIModel model;
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
      UIModel model,
      TableView<NodeEntity> nodeTable,
      TableView<EdgeEntity> edgeTable,
      TableView<MoveEntity> moveTable,
      TableView<LocationnameEntity> locationTable,
      ToggleGroup editorTabs,
      ToggleButton nodeToggle,
      ToggleButton edgeToggle,
      ToggleButton moveToggle,
      ToggleButton locationToggle) {
    this.model = model;
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
    model
        .elementsProperty()
        .addListener(
            (SetChangeListener<? super Element>)
                change -> {
                  if (change.wasAdded()) addElement(change.getElementAdded());
                  if (change.wasRemoved()) removeElement(change.getElementRemoved());
                });

    // add the extant elements
    model.elementsProperty().forEach(this::addElement);

    model
        .selectedProperty()
        .addListener(
            (SetChangeListener<? super Element>)
                change -> {
                  if (change.wasAdded()) {
                    final var added = change.getElementAdded();
                    if (added instanceof NodeElement node)
                      selectItem(nodeTableView, node.getInRepo(), nodeToggle);
                    else if (added instanceof EdgeElement edge)
                      selectItem(edgeTableView, edge.getInRepo(), edgeToggle);
                    else if (added instanceof MoveElement move)
                      selectItem(moveTableView, move.getInRepo(), moveToggle);
                    else if (added instanceof LocationElement location)
                      selectItem(locationNameTableView, location.getInRepo(), locationToggle);
                  }
                  if (change.wasRemoved()) {
                    final var removed = change.getElementRemoved();
                    if (removed instanceof NodeElement node)
                      deselectItem(nodeTableView, node.getInRepo());
                    else if (removed instanceof EdgeElement edge)
                      deselectItem(edgeTableView, edge.getInRepo());
                    else if (removed instanceof MoveElement move)
                      deselectItem(moveTableView, move.getInRepo());
                    else if (removed instanceof LocationElement location)
                      deselectItem(locationNameTableView, location.getInRepo());
                  }
                });
  }

  private <T> void selectItem(TableView<T> table, T item, Toggle toggle) {
    table.getSelectionModel().select(item);
    table.scrollTo(item);
    editorTabs.selectToggle(toggle);
  }

  private <T> void deselectItem(TableView<T> table, T item) {
    table.getSelectionModel().clearSelection(table.getItems().indexOf(item));
  }

  private void addElement(Element element) {
    if (element instanceof NodeElement node) nodeTableView.getItems().add(node.getInRepo());
    else if (element instanceof EdgeElement edge) edgeTableView.getItems().add(edge.getInRepo());
    else if (element instanceof MoveElement move) moveTableView.getItems().add(move.getInRepo());
    else if (element instanceof LocationElement location)
      locationNameTableView.getItems().add(location.getInRepo());
  }

  private void removeElement(Element element) {
    if (element instanceof NodeElement node) nodeTableView.getItems().remove(node.getInRepo());
    else if (element instanceof EdgeElement edge) edgeTableView.getItems().remove(edge.getInRepo());
    else if (element instanceof MoveElement move) moveTableView.getItems().remove(move.getInRepo());
    else if (element instanceof LocationElement location)
      locationNameTableView.getItems().remove(location.getInRepo());
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

    nodeIDColumn.setEditable(false);
    // xCoordColumn.setEditable(false);
    // yCoordColumn.setEditable(false);

    //noinspection unchecked
    nodeTableView
        .getColumns()
        .setAll(nodeIDColumn, xCoordColumn, yCoordColumn, floorColumn, buildingColumn);
    nodeTableView.setEditable(true);

    nodeTableView.visibleProperty().bind(nodeToggle.selectedProperty());
    nodeTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    //    nodeTableView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<?
    // super NodeEntity>) change -> {
    //      while (change.next()) {
    //        if (change.wasAdded()) {
    //          change.getAddedSubList().forEach();
    //        }
    //      }
    //    });

    nodeTableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
  }

  private void initializeEdgeTable() {
    final var startNodeColumn =
        createTableColumn(
            "Start Node ID", EdgeEntity::node1IDProperty, new DefaultStringConverter());
    final var endNodeColumn =
        createTableColumn("End Node ID", EdgeEntity::node2IDProperty, new DefaultStringConverter());

    //noinspection unchecked
    edgeTableView.getColumns().setAll(startNodeColumn, endNodeColumn);
    edgeTableView.setEditable(true);

    edgeTableView.visibleProperty().bind(edgeToggle.selectedProperty());
    edgeTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    edgeTableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
  }

  public static class NodeConverter extends StringConverter<NodeEntity> {
    @Override
    public String toString(NodeEntity object) {
      return object.getNodeID();
    }

    @Override
    public NodeEntity fromString(String string) {
      return Main.getRepo().getNode(string);
    }
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

    //noinspection unchecked
    locationNameTableView.getColumns().setAll(longNameColumn, shortNameColumn, locationTypeColumn);
    locationNameTableView.setEditable(true);

    locationNameTableView.visibleProperty().bind(locationToggle.selectedProperty());
    locationNameTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    locationNameTableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

    final var addItem = new MenuItem("Add...");
    addItem.setOnAction(
        event ->
            new AddLocationNameDialog(locationNameTableView.getScene().getWindow()).showAndWait());
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
        Main.getRepo().getMoves().stream().anyMatch(move -> move.getLongName().equals(entity));
    if (hasMoves) {
      final var alert =
          new Alert(
              Alert.AlertType.CONFIRMATION,
              "Location '"
                  + entity.getLongname()
                  + "' has moves associated with it. If you continue, they will be deleted as well.",
              ButtonType.OK,
              ButtonType.CANCEL);
      final var button = alert.showAndWait();
      if (button.isEmpty() || !button.get().equals(ButtonType.OK)) return;

      final var moves =
          Main.getRepo().getMoves().stream()
              .filter(move -> move.getLongName().equals(entity))
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

  private static class LocationNameConverter extends StringConverter<LocationnameEntity> {
    @Override
    public String toString(LocationnameEntity object) {
      return object.getLongname();
    }

    @Override
    public LocationnameEntity fromString(String string) {
      return Main.getRepo().getLocationname(string);
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