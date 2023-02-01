package edu.wpi.capybara.controllers;

import edu.wpi.capybara.controllers.mapeditor.CellPropertyAdapter;
import edu.wpi.capybara.controllers.mapeditor.NodePropertyAdapter;
import edu.wpi.capybara.database.DatabaseConnect;
import java.util.function.Function;
import javafx.beans.property.Property;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.NumberStringConverter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MapEditorController {

  @FXML private TableView<NodePropertyAdapter> tableView;

  @FXML
  public void initialize() {
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
    tableView
        .getColumns()
        .setAll(nodeIDColumn, xCoordColumn, yCoordColumn, floorColumn, buildingColumn);
    tableView.setEditable(true);

    DatabaseConnect.importData();
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
