package edu.wpi.capybara.controllers.mapeditor.ui;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.controllers.mapeditor.SQLDateStringConverter;
import edu.wpi.capybara.controllers.mapeditor.adapters.*;
import edu.wpi.capybara.controllers.mapeditor.ui.row.EdgeRow;
import edu.wpi.capybara.controllers.mapeditor.ui.row.LocationRow;
import edu.wpi.capybara.controllers.mapeditor.ui.row.MoveRow;
import edu.wpi.capybara.controllers.mapeditor.ui.row.NodeRow;
import edu.wpi.capybara.objects.Floor;
import edu.wpi.capybara.objects.hibernate.EdgeEntity;
import edu.wpi.capybara.objects.hibernate.LocationnameEntity;
import edu.wpi.capybara.objects.hibernate.MoveEntity;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import javafx.beans.property.Property;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.util.function.Function;

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

    public MapEditorTableView(UIModel model, TableView<NodeEntity> nodeTable, TableView<EdgeEntity> edgeTable, TableView<MoveEntity> moveTable, TableView<LocationnameEntity> locationTable, ToggleGroup editorTabs, ToggleButton nodeToggle, ToggleButton edgeToggle, ToggleButton moveToggle, ToggleButton locationToggle) {
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
        xCoordColumn.setEditable(false);
        yCoordColumn.setEditable(false);

        //noinspection unchecked
        nodeTableView
                .getColumns()
                .setAll(nodeIDColumn, xCoordColumn, yCoordColumn, floorColumn, buildingColumn);
        nodeTableView.setEditable(true);
        nodeTableView.setRowFactory(param -> new NodeRow());

        nodeTableView.visibleProperty().bind(nodeToggle.selectedProperty());
    }

    private void initializeEdgeTable() {
        final var startNodeColumn =
                createTableColumn(
                        "Start Node ID", EdgeEntity::node1Property, new NodeConverter());
        final var endNodeColumn =
                createTableColumn(
                        "End Node ID", EdgeEntity::node2Property, new NodeConverter());

        //noinspection unchecked
        edgeTableView.getColumns().setAll(startNodeColumn, endNodeColumn);
        edgeTableView.setEditable(true);
        edgeTableView.setRowFactory(param -> new EdgeRow());

        edgeTableView.visibleProperty().bind(edgeToggle.selectedProperty());
    }

    private static class NodeConverter extends StringConverter<NodeEntity> {
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
        locationNameTableView.setRowFactory(param -> new LocationRow());

        locationNameTableView.visibleProperty().bind(locationToggle.selectedProperty());
    }

    private void initializeMoveTable() {
        final var nodeIDColumn =
                createTableColumn("Node ID", MoveEntity::nodeProperty, new NodeConverter());
        final var longNameColumn =
                createTableColumn("Long Name", MoveEntity::locationProperty, new LocationNameConverter());
        final var moveDateColumn =
                createTableColumn("Move Date", MoveEntity::movedateProperty, new SQLDateStringConverter());

        //noinspection unchecked
        moveTableView.getColumns().setAll(nodeIDColumn, longNameColumn, moveDateColumn);
        moveTableView.setEditable(true);
        moveTableView.setRowFactory(param -> new MoveRow());

        moveTableView.visibleProperty().bind(moveToggle.selectedProperty());
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
