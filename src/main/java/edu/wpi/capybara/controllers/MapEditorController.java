package edu.wpi.capybara.controllers;

import edu.wpi.capybara.controllers.mapeditor.CellPropertyAdapter;
import edu.wpi.capybara.controllers.mapeditor.NodePropertyAdapter;
import java.util.function.Function;
import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MapEditorController {

  @FXML private TableView<NodePropertyAdapter> tableView;

  public MapEditorController() {
    final var nodeIDColumn = createTableColumn("Node ID", NodePropertyAdapter::nodeIDProperty);
    final var xCoordColumn = createTableColumn("X", NodePropertyAdapter::xCoordProperty);
    final var yCoordColumn = createTableColumn("Y", NodePropertyAdapter::yCoordProperty);
    final var floorColumn = createTableColumn("Floor", NodePropertyAdapter::floorProperty);
    final var buildingColumn = createTableColumn("Building", NodePropertyAdapter::buildingProperty);

    //noinspection unchecked
    tableView
        .getColumns()
        .setAll(nodeIDColumn, xCoordColumn, yCoordColumn, floorColumn, buildingColumn);
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
