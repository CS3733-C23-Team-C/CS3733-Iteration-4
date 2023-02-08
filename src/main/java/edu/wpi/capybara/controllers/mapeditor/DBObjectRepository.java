package edu.wpi.capybara.controllers.mapeditor;

import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.objects.hibernate.LocationnameEntity;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import io.github.palexdev.materialfx.utils.FXCollectors;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "DBObjectRepository")
public class DBObjectRepository {

  private final SimpleListProperty<NodePropertyAdapter> nodes;
  private final SimpleListProperty<EdgePropertyAdapter> edges;
  private final SimpleListProperty<LocationNamePropertyAdapter> locationNames;
  private final SimpleListProperty<MovePropertyAdapter> moves;

  public DBObjectRepository() {
    nodes = dbToListProperty(DatabaseConnect.getNodes(), NodePropertyAdapter::new);
    edges = dbToListProperty(DatabaseConnect.getEdges(), EdgePropertyAdapter::new);
    locationNames =
        dbToListProperty(DatabaseConnect.getLocationNames(), LocationNamePropertyAdapter::new);
    moves = dbToListProperty(DatabaseConnect.getMoves(), MovePropertyAdapter::new);

    nodes.addListener(createListener(this::addNode, this::deleteNode));
    edges.addListener(createListener(this::addEdge, this::deleteEdge));
    locationNames.addListener(createListener(this::addLocationName, this::deleteLocationName));
    moves.addListener(createListener(this::addMove, this::deleteMove));
  }

  private <ORM, E> SimpleListProperty<E> dbToListProperty(
      Map<?, ORM> databaseMap, Function<ORM, E> adapterFactory) {
    return new SimpleListProperty<>(
        databaseMap.values().stream().map(adapterFactory).collect(FXCollectors.toList()));
  }

  private <E> ListChangeListener<E> createListener(
      Consumer<E> addFunction, Consumer<E> removeFunction) {
    return c -> {
      while (c.next()) {
        c.getAddedSubList().forEach(addFunction);
        c.getRemoved().forEach(removeFunction);
      }
    };
  }

  public NodePropertyAdapter createNode(
      String nodeID, int xCoord, int yCoord, String floor, String building) {
    // TODO: 2/7/23 implement this
    final var nodeEntity = new NodeEntity();
    nodeEntity.setNodeid(nodeID);
    nodeEntity.setXcoord(xCoord);
    nodeEntity.setYcoord(yCoord);
    nodeEntity.setFloor(floor);
    nodeEntity.setBuilding(building);
    final var newNode = new NodePropertyAdapter(nodeEntity);
    nodes.add(newNode);
    return newNode;
  }

  private void addNode(NodePropertyAdapter node) {
    log.info("addNode");
  }

  private void deleteNode(NodePropertyAdapter node) {
    log.info("deleteNode");
  }

  private void addEdge(EdgePropertyAdapter edge) {
    log.info("addEdge");
  }

  private void deleteEdge(EdgePropertyAdapter edge) {
    log.info("deleteEdge");
  }

  public LocationNamePropertyAdapter createLocationName(
      String longName, String shortName, String locationType) {
    final var locationNameEntity = new LocationnameEntity();
    locationNameEntity.setLongname(longName);
    locationNameEntity.setShortname(shortName);
    locationNameEntity.setLocationtype(locationType);
    final var newLocationName = new LocationNamePropertyAdapter(locationNameEntity);
    locationNames.add(newLocationName);
    return newLocationName;
  }

  private void addLocationName(LocationNamePropertyAdapter locationName) {
    log.info("addLocationName");
  }

  private void deleteLocationName(LocationNamePropertyAdapter locationName) {
    log.info("deleteLocationName");
  }

  private void addMove(MovePropertyAdapter move) {
    log.info("addMove");
  }

  private void deleteMove(MovePropertyAdapter move) {
    log.info("deleteMove");
  }

  public ObservableList<NodePropertyAdapter> getNodes() {
    return nodes.get();
  }

  public void setNodes(ObservableList<NodePropertyAdapter> nodes) {
    this.nodes.set(nodes);
  }

  public SimpleListProperty<NodePropertyAdapter> nodesProperty() {
    return nodes;
  }

  public ObservableList<EdgePropertyAdapter> getEdges() {
    return edges.get();
  }

  public void setEdges(ObservableList<EdgePropertyAdapter> edges) {
    this.edges.set(edges);
  }

  public SimpleListProperty<EdgePropertyAdapter> edgesProperty() {
    return edges;
  }

  public ObservableList<LocationNamePropertyAdapter> getLocationNames() {
    return locationNames.get();
  }

  public void setLocationNames(ObservableList<LocationNamePropertyAdapter> locationNames) {
    this.locationNames.set(locationNames);
  }

  public SimpleListProperty<LocationNamePropertyAdapter> locationNamesProperty() {
    return locationNames;
  }

  public ObservableList<MovePropertyAdapter> getMoves() {
    return moves.get();
  }

  public void setMoves(ObservableList<MovePropertyAdapter> moves) {
    this.moves.set(moves);
  }

  public SimpleListProperty<MovePropertyAdapter> movesProperty() {
    return moves;
  }
}
