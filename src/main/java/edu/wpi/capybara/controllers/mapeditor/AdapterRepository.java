package edu.wpi.capybara.controllers.mapeditor;

import static edu.wpi.capybara.Main.db;

import edu.wpi.capybara.objects.hibernate.LocationnameEntity;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import io.github.palexdev.materialfx.utils.FXCollectors;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ListChangeListener;
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

    // nodes.addListener(createListener(this::addNode, this::deleteNode));
    // edges.addListener(createListener(this::addEdge, this::deleteEdge));
    // locationNames.addListener(createListener(this::addLocationName, this::deleteLocationName));
    // moves.addListener(createListener(this::addMove, this::deleteMove));
  }

  private <ORM, E> SimpleListProperty<E> dbToListProperty(
      Collection<ORM> dbList, Function<ORM, E> adapterFactory) {
    return new SimpleListProperty<>(
        dbList.stream().map(adapterFactory).collect(FXCollectors.toList()));
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
    addNode(newNode);
    return newNode;
  }

  public void addNode(NodeAdapter node) {
    log.info("addNode");
    nodes.add(node);
    db.addNode(node.getEntity());
  }

  public void deleteNode(NodeAdapter node) {
    log.info("deleteNode");
    if (nodes.remove(node)) {
      db.deleteNode(node.getNodeID());
    }
  }

  public void addEdge(EdgeAdapter edge) {
    log.info("addEdge");
    edges.add(edge);
    db.addEdge(edge.getEntity());
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
    locations.add(locationName);
    db.addLocationname(locationName.getEntity());
  }

  public void deleteLocationName(LocationNameAdapter locationName) {
    log.info("deleteLocationName");
    if (locations.remove(locationName)) {
      db.deleteLocationname(locationName.getEntity().getLongname());
    }
  }

  public void addMove(MoveAdapter move) {
    log.info("addMove");
    moves.add(move);
    db.addMove(move.getEntity());
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
  //  public ObservableList<NodeAdapter> getNodes() {
  //    return nodes.get();
  //  }

  //  public void setNodes(ObservableList<NodeAdapter> nodes) {
  //    this.nodes.set(nodes);
  //  }
  //
  //  public SimpleListProperty<NodeAdapter> nodesProperty() {
  //    return nodes;
  //  }
  //
  //  public ObservableList<EdgeAdapter> getEdges() {
  //    return edges.get();
  //  }
  //
  //  public void setEdges(ObservableList<EdgeAdapter> edges) {
  //    this.edges.set(edges);
  //  }
  //
  //  public SimpleListProperty<EdgeAdapter> edgesProperty() {
  //    return edges;
  //  }
  //
  //  public ObservableList<LocationNameAdapter> getLocationNames() {
  //    return locationNames.get();
  //  }
  //
  //  public void setLocationNames(ObservableList<LocationNameAdapter> locationNames) {
  //    this.locationNames.set(locationNames);
  //  }
  //
  //  public SimpleListProperty<LocationNameAdapter> locationNamesProperty() {
  //    return locationNames;
  //  }
  //
  //  public ObservableList<MoveAdapter> getMoves() {
  //    return moves.get();
  //  }
  //
  //  public void setMoves(ObservableList<MoveAdapter> moves) {
  //    this.moves.set(moves);
  //  }
  //
  //  public SimpleListProperty<MoveAdapter> movesProperty() {
  //    return moves;
  //  }
  // }
}
