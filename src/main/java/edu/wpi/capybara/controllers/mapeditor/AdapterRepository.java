package edu.wpi.capybara.controllers.mapeditor;

import static edu.wpi.capybara.Main.db;

import edu.wpi.capybara.controllers.mapeditor.adapters.EdgeAdapter;
import edu.wpi.capybara.controllers.mapeditor.adapters.LocationNameAdapter;
import edu.wpi.capybara.controllers.mapeditor.adapters.MoveAdapter;
import edu.wpi.capybara.controllers.mapeditor.adapters.NodeAdapter;
import edu.wpi.capybara.objects.hibernate.LocationnameEntity;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import io.github.palexdev.materialfx.utils.FXCollectors;
import java.util.Collection;
import java.util.function.Function;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.SimpleListProperty;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "DBObjectRepository")
public class AdapterRepository {

  private final SimpleListProperty<NodeAdapter> nodes;
  private final SimpleListProperty<EdgeAdapter> edges;
  private final SimpleListProperty<MoveAdapter> moves;
  private final SimpleListProperty<LocationNameAdapter> locations;

  public AdapterRepository() {
    nodes = dbToListProperty(db.getNodes().values(), NodeAdapter::new);
    edges = dbToListProperty(db.getEdges(), EdgeAdapter::new);
    moves = dbToListProperty(db.getMoves(), MoveAdapter::new);
    locations = dbToListProperty(db.getLocationnames().values(), LocationNameAdapter::new);
  }

  private <ORM, E> SimpleListProperty<E> dbToListProperty(
      Collection<ORM> dbList, Function<ORM, E> adapterFactory) {
    return new SimpleListProperty<>(
        dbList.stream().map(adapterFactory).collect(FXCollectors.toList()));
  }

  public NodeAdapter createNode(
      String nodeID, int xCoord, int yCoord, String floor, String building) {
    final var nodeEntity = new NodeEntity(nodeID, xCoord, yCoord, floor, building);
    final var newNode = new NodeAdapter(nodeEntity);
    addNode(newNode);
    return newNode;
  }

  public void addNode(NodeAdapter node) {
    log.info("addNode");
    db.addNode(node.getEntity());
    nodes.add(node);
  }

  public void deleteNode(NodeAdapter node) {
    log.info("deleteNode");
    if (nodes.remove(node)) {
      db.deleteNode(node.getNodeID());
    }
  }

  public void addEdge(EdgeAdapter edge) {
    log.info("addEdge");
    db.addEdge(edge.getEntity());
    edges.add(edge);
  }

  public void deleteEdge(EdgeAdapter edge) {
    log.info("deleteEdge");
    if (edges.remove(edge)) {
      db.deleteEdge(edge.getEntity());
    }
  }

  public LocationNameAdapter createLocationName(
      String longName, String shortName, String locationType) {
    final var locationNameEntity = new LocationnameEntity(longName, shortName, locationType);
    final var newLocationName = new LocationNameAdapter(locationNameEntity);
    addLocationName(newLocationName);
    return newLocationName;
  }

  public void addLocationName(LocationNameAdapter locationName) {
    log.info("addLocationName");
    db.addLocationname(locationName.getEntity());
    locations.add(locationName);
  }

  public void deleteLocationName(LocationNameAdapter locationName) {
    log.info("deleteLocationName");
    if (locations.remove(locationName)) {
      db.deleteLocationname(locationName.getEntity().getLongname());
    }
  }

  public void addMove(MoveAdapter move) {
    log.info("addMove");
    db.addMove(move.getEntity());
    moves.add(move);
  }

  public void deleteMove(MoveAdapter move) {
    log.info("deleteMove");
    if (moves.remove(move)) {
      db.deleteMove(move.getEntity());
    }
  }

  public ReadOnlyListProperty<NodeAdapter> getNodes() {
    return new ReadOnlyListWrapper<>(nodes);
  }

  public ReadOnlyListProperty<EdgeAdapter> getEdges() {
    return new ReadOnlyListWrapper<>(edges);
  }

  public ReadOnlyListProperty<MoveAdapter> getMoves() {
    return new ReadOnlyListWrapper<>(moves);
  }

  public ReadOnlyListProperty<LocationNameAdapter> getLocations() {
    return new ReadOnlyListWrapper<>(locations);
  }
}
