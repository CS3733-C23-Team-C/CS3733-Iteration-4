package edu.wpi.capybara.controllers;

import edu.wpi.capybara.controllers.mapeditor.CellPropertyAdapter;
import edu.wpi.capybara.controllers.mapeditor.NodePropertyAdapter;
import edu.wpi.capybara.objects.enums.Building;
import edu.wpi.capybara.objects.enums.Floor;
import edu.wpi.capybara.objects.enums.NodeType;
import java.util.function.Function;
import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;

public class MapEditorController {

  @FXML private TableView<NodePropertyAdapter> tableView;

  public MapEditorController() {
    var nodeIDColumn = createTableColumn("Node ID", NodePropertyAdapter::nodeIDProperty);
    var xCoordColumn = createTableColumn("X", NodePropertyAdapter::xCoordProperty);
    var yCoordColumn = createTableColumn("Y", NodePropertyAdapter::yCoordProperty);
    var floorColumn = createTableColumn("Floor", NodePropertyAdapter::floorProperty);
    var buildingColumn = createTableColumn("Building", NodePropertyAdapter::buildingProperty);
    var nodeTypeColumn = createTableColumn("Node Type", NodePropertyAdapter::nodeTypeProperty);
    var longNameColumn = createTableColumn("Long Name", NodePropertyAdapter::longNameProperty);
    var shortNameColumn = createTableColumn("Short Name", NodePropertyAdapter::shortNameProperty);

    // TODO: 1/31/23 these might need string converters. also, the enums have an ERROR value. should
    // that be shown to the user?
    floorColumn.setCellFactory((column) -> new ComboBoxTableCell<>(Floor.values()));
    buildingColumn.setCellFactory((column) -> new ComboBoxTableCell<>(Building.values()));
    nodeTypeColumn.setCellFactory((column) -> new ComboBoxTableCell<>(NodeType.values()));

    //noinspection unchecked
    tableView
        .getColumns()
        .setAll(
            nodeIDColumn,
            xCoordColumn,
            yCoordColumn,
            floorColumn,
            buildingColumn,
            nodeTypeColumn,
            longNameColumn,
            shortNameColumn);
  }

  private static <S, T> TableColumn<S, T> createTableColumn(
      String columnName, Function<S, ? extends Property<T>> propertyGetter) {
    final var tableColumn = new TableColumn<S, T>(columnName);
    final var adapter = new CellPropertyAdapter<>(propertyGetter);
    tableColumn.setCellValueFactory(adapter);
    tableColumn.setOnEditCommit(adapter);
    return tableColumn;
  }
}
