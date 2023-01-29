package edu.wpi.capybara.objects;

import edu.wpi.capybara.database.DBUpdate;
import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.objects.enums.Building;
import edu.wpi.capybara.objects.enums.Floor;
import edu.wpi.capybara.objects.enums.NodeType;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

public class Node {
  @Getter String nodeID;
  @Getter int xCoord, yCoord;
  @Getter Floor floor;
  @Getter Building building;
  @Getter NodeType nodeType;
  @Getter String longName, shortName;
  @Getter Set<Edge> edges;

  public Node(
      String nodeID,
      String xCoord,
      String yCoord,
      String floor,
      String building,
      String nodeType,
      String longName,
      String shortName) {
    this.nodeID = nodeID;
    this.xCoord = Integer.parseInt(xCoord);
    this.yCoord = Integer.parseInt(yCoord);
    this.floor = Floor.strToFloor(floor);
    this.building = Building.strToBuilding(building);
    this.nodeType = NodeType.strToNode(nodeType);
    this.longName = longName;
    this.shortName = shortName;
    this.edges = new HashSet<>();
  }

  public Node(
      String nodeID,
      int xCoord,
      int yCoord,
      String floor,
      String building,
      String nodeType,
      String longName,
      String shortName) {
    this.nodeID = nodeID;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.floor = Floor.strToFloor(floor);
    this.building = Building.strToBuilding(building);
    this.nodeType = NodeType.strToNode(nodeType);
    this.longName = longName;
    this.shortName = shortName;
    this.edges = new HashSet<>();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj.getClass() == this.getClass()) {
      Node other = (Node) obj;
      return other.getNodeID().equals(nodeID);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return nodeID.hashCode();
  }

  public void addEdge(Edge e) {
    this.edges.add(e);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    String s = "ID: " + this.nodeID + "|" + "Edges: ";
    sb.append(s);
    for (Edge e : edges) sb.append(e.getEdgeID()).append(",");
    return sb.toString();

    // return nodeID;
  }

  public void setNodeID(String nodeID) {
    this.nodeID = nodeID;
    DBUpdate.update("l1nodes", this.nodeID, "nodeid", "'" + nodeID + "'", "nodeid");
  }

  public void setXCoord(int xcoord) {
    this.xCoord = xcoord;
    DBUpdate.update("l1nodes", this.nodeID, "xcoord", String.valueOf(xcoord), "nodeid");
  }

  public void setYCoord(int ycoord) {
    this.yCoord = ycoord;
    DBUpdate.update("l1nodes", this.nodeID, "ycoord", String.valueOf(ycoord), "nodeid");
  }

  public void setFloor(String floor) {
    this.floor = Floor.strToFloor(floor);
    DBUpdate.update("l1nodes", this.nodeID, "floor", "'" + floor + "'", "nodeid");
  }

  public void setBuilding(String building) {
    this.building = Building.strToBuilding(building);
    DBUpdate.update("l1nodes", this.nodeID, "building", "'" + building + "'", "nodeid");
  }

  public void setNodeType(String nodeType) {
    this.nodeType = NodeType.strToNode(nodeType);
    DBUpdate.update("l1nodes", this.nodeID, "nodetype", "'" + nodeType + "'", "nodeid");
  }

  public void setLongName(String longName) {
    this.longName = longName;
    DBUpdate.update("l1nodes", this.nodeID, "longname", "'" + longName + "'", "nodeid");
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
    DBUpdate.update("l1nodes", this.nodeID, "shortname", "'" + shortName + "'", "nodeid");
  }

  public void deleteNode() {
    // remove from hashmap
    DBUpdate.delete("l1nodes", this.nodeID, "nodeid");
    DatabaseConnect.deleteNode(this.nodeID);
  }
}
