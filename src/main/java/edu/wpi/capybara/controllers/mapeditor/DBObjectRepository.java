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

  private final SimpleListProperty<NodeAdapter> nodes;
  private final SimpleListProperty<EdgeAdapter> edges;
  private final SimpleListProperty<LocationNameAdapter> locationNames;
  private final SimpleListProperty<MoveAdapter> moves;

  public DBObjectRepository() {
    nodes = dbToListProperty(DatabaseConnect.getNodes(), NodeAdapter::new);
    edges = dbToListProperty(DatabaseConnect.getEdges(), EdgeAdapter::new);
    locationNames = dbToListProperty(DatabaseConnect.getLocationNames(), LocationNameAdapter::new);
    moves = dbToListProperty(DatabaseConnect.getMoves(), MoveAdapter::new);

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

  public NodeAdapter createNode(
      String nodeID, int xCoord, int yCoord, String floor, String building) {
    final var nodeEntity = new NodeEntity(nodeID, xCoord, yCoord, floor, building);
    final var newNode = new NodeAdapter(nodeEntity);
    nodes.add(newNode);
    return newNode;
  }

  private void addNode(NodeAdapter node) {
    log.info("addNode");
    DatabaseConnect.insertNode(node.getEntity());
  }

  private void deleteNode(NodeAdapter node) {
    log.info("deleteNode");
    node.getEntity().delete();
  }

  private void addEdge(EdgeAdapter edge) {
    log.info("addEdge");
    DatabaseConnect.insertEdge(edge.getEntity());
  }

  private void deleteEdge(EdgeAdapter edge) {
    log.info("deleteEdge");
    edge.getEntity().delete();
  }

  public LocationNameAdapter createLocationName(
      String longName, String shortName, String locationType) {
    final var locationNameEntity = new LocationnameEntity(longName, shortName, locationType);
    final var newLocationName = new LocationNameAdapter(locationNameEntity);
    locationNames.add(newLocationName);
    return newLocationName;
  }

  private void addLocationName(LocationNameAdapter locationName) {
    log.info("addLocationName");
    DatabaseConnect.insertLocationName(locationName.getEntity());
  }

  private void deleteLocationName(LocationNameAdapter locationName) {
    log.info("deleteLocationName");
    locationName.getEntity().delete();
  }

  private void addMove(MoveAdapter move) {
    log.info("addMove");
    DatabaseConnect.insertMove(move.getEntity());
  }

  private void deleteMove(MoveAdapter move) {
    log.info("deleteMove");
    move.getEntity().delete();
  }

  public ObservableList<NodeAdapter> getNodes() {
    return nodes.get();
  }

  public void setNodes(ObservableList<NodeAdapter> nodes) {
    this.nodes.set(nodes);
  }

  public SimpleListProperty<NodeAdapter> nodesProperty() {
    return nodes;
  }

  public ObservableList<EdgeAdapter> getEdges() {
    return edges.get();
  }

  public void setEdges(ObservableList<EdgeAdapter> edges) {
    this.edges.set(edges);
  }

  public SimpleListProperty<EdgeAdapter> edgesProperty() {
    return edges;
  }

  public ObservableList<LocationNameAdapter> getLocationNames() {
    return locationNames.get();
  }

  public void setLocationNames(ObservableList<LocationNameAdapter> locationNames) {
    this.locationNames.set(locationNames);
  }

  public SimpleListProperty<LocationNameAdapter> locationNamesProperty() {
    return locationNames;
  }

  public ObservableList<MoveAdapter> getMoves() {
    return moves.get();
  }

  public void setMoves(ObservableList<MoveAdapter> moves) {
    this.moves.set(moves);
  }

  public SimpleListProperty<MoveAdapter> movesProperty() {
    return moves;
  }
}
