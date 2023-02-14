package edu.wpi.capybara.database;

import edu.wpi.capybara.controllers.mapeditor.EdgeAdapter;
import edu.wpi.capybara.controllers.mapeditor.LocationNameAdapter;
import edu.wpi.capybara.controllers.mapeditor.MoveAdapter;
import edu.wpi.capybara.controllers.mapeditor.NodeAdapter;
import javafx.beans.property.ReadOnlyListProperty;

/**
 * Interface for database operations required by the map editor.
 *
 * <p>The API is structured as follows:
 *
 * <p>For each kind of entity in the database, there are 3 functions - create, delete, and get list.
 * The list contains every entity object that is managed by the database, and should have a
 * bijective relation to the entries in the database.
 *
 * <p>Clients of the API may add or remove entries from the database by calling the corresponding
 * create and delete methods. They may <b>NOT</b> modify the entity lists directly. This is enforced
 * by returning read-only views of the lists.
 *
 * <p>The entity lists use JavaFX list properties to support observer patterns.
 *
 * <p>In addition to the aforementioned methods, there are also utility methods for looking up
 * entities by ID.
 */
public interface MapEditorDBFacade {
  /**
   * Creates a new node in the database, adds it to the node list, and returns the ORM object mapped
   * to the new node.
   *
   * @return a NodeAdapter that represents the newly-created node.
   */
  NodeAdapter createNode();

  /**
   * Deletes a node from the database and the node list.
   *
   * @param node the node to be deleted
   * @return true if the node was successfully deleted, false otherwise.
   */
  boolean deleteNode(NodeAdapter node);

  /**
   * Gets a read-only view of the node list.
   *
   * @return a list property containing the nodes in the database.
   */
  ReadOnlyListProperty<NodeAdapter> nodesProperty();

  NodeAdapter findNodeByID(String nodeID);

  /**
   * Creates a new edge in the database, adds it to the edge list, and returns the ORM object mapped
   * to the new edge.
   *
   * @return an EdgeAdapter that represents the newly-created edge.
   */
  EdgeAdapter createEdge();

  /**
   * Deletes an edge from the database and the edge list.
   *
   * @param edge the edge to be deleted
   * @return true if the edge was successfully deleted, false otherwise.
   */
  boolean deleteEdge(EdgeAdapter edge);

  /**
   * Gets a read-only view of the edge list.
   *
   * @return a list property containing the edges in the database.
   */
  ReadOnlyListProperty<EdgeAdapter> edgesProperty();

  /**
   * Creates a new move in the database, adds it to the move list, and returns the ORM object mapped
   * to the new move.
   *
   * @return a MoveAdapter that represents the newly-created move.
   */
  MoveAdapter createMove();

  /**
   * Deletes a move from the database and the move list.
   *
   * @param move the move to be deleted
   * @return true if the move was successfully deleted, false otherwise.
   */
  boolean deleteMove(MoveAdapter move);

  /**
   * Gets a read-only view of the edge list.
   *
   * @return a list property containing the edges in the database.
   */
  ReadOnlyListProperty<MoveAdapter> movesProperty();

  /**
   * Creates a new location in the database, adds it to the location list, and returns the ORM
   * object mapped to the new location.
   *
   * @return a LocationNameEntity that represents the newly-created location.
   */
  LocationNameAdapter createLocation();

  /**
   * Deletes a location from the database and the location list.
   *
   * @param location the location to be deleted
   * @return true if the location was successfully deleted, false otherwise.
   */
  boolean deleteLocation(LocationNameAdapter location);

  /**
   * Gets a read-only view of the location list.
   *
   * @return a list property containing the locations in the database.
   */
  ReadOnlyListProperty<LocationNameAdapter> locationsProperty();
}
