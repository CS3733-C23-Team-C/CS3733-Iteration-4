package edu.wpi.teamname.pathfinding;

import java.util.HashSet;
import java.util.Set;

public class Node {
  String nodeID;
  int xcoord, ycoord;
  Floor floor;
  Building building;
  NodeType type;
  String longName, shortName;
  Set<Edge> edges;

  public Node(
      String nodeID,
      String xcoord,
      String ycoord,
      String floor,
      String building,
      String type,
      String longName,
      String shortName) {
    this.nodeID = nodeID;
    this.xcoord = Integer.parseInt(xcoord);
    this.ycoord = Integer.parseInt(ycoord);
    this.floor = Floor.strToFloor(floor);
    this.building = Building.strToBuilding(building);
    this.type = NodeType.strToNode(type);
    this.longName = longName;
    this.shortName = shortName;
    this.edges = new HashSet<>();
  }

  public String getID() {
    return nodeID;
  }

  public Set<Edge> getEdges() {
    return edges;
  }

  public void setID(String ID) {
    this.nodeID = ID;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj.getClass() == this.getClass()) {
      Node other = (Node) obj;
      return other.getID().equals(nodeID);
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

  public String getLongName() {
    return longName;
  }

  public NodeType getType() {
    return type;
  }
}
