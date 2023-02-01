package edu.wpi.capybara.controllers;

import edu.wpi.capybara.controllers.mapeditor.CellPropertyAdapter;
import edu.wpi.capybara.controllers.mapeditor.NodePropertyAdapter;
import edu.wpi.capybara.database.DatabaseConnect;
import java.util.function.Function;
import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MapEditorController {

  @FXML private TableView<NodePropertyAdapter> tableView;

  @FXML
  public void initialize() {
    final var nodeIDColumn = createTableColumn("Node ID", NodePropertyAdapter::nodeIDProperty);
    final var xCoordColumn = createTableColumn("X", NodePropertyAdapter::xCoordProperty);
    final var yCoordColumn = createTableColumn("Y", NodePropertyAdapter::yCoordProperty);
    final var floorColumn = createTableColumn("Floor", NodePropertyAdapter::floorProperty);
    final var buildingColumn = createTableColumn("Building", NodePropertyAdapter::buildingProperty);

    //noinspection unchecked
    tableView
        .getColumns()
        .setAll(nodeIDColumn, xCoordColumn, yCoordColumn, floorColumn, buildingColumn);

    final var nodeMap = DatabaseConnect.getNodes();
    if (nodeMap == null) {
      log.error("Problem communicating with the database: Node HashMap is null.");
    } else {
      final var nodes = DatabaseConnect.getNodes().values();
      final var adapters = nodes.stream().map(NodePropertyAdapter::new).toList();
      tableView.getItems().setAll(adapters);
    }
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
