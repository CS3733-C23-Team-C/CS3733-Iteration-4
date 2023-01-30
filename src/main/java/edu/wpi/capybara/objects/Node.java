package edu.wpi.capybara.objects;

import edu.wpi.capybara.database.DBUpdate;
import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.objects.enums.Building;
import edu.wpi.capybara.objects.enums.Floor;
import lombok.Getter;

import java.sql.ResultSet;
import java.util.HashSet;

public class Node {
  @Getter String nodeID;
  @Getter int xCoord, yCoord;
  @Getter String floor;
  @Getter String building;
  //  @Getter NodeType nodeType;
  //  @Getter String longName, shortName;
  //  @Getter Set<Edge> edges;

  public Node(String nodeID, int xCoord, int yCoord, String floor, String building) {
    this.nodeID = nodeID;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.floor = floor;
    this.building = building;
    //    this.nodeType = NodeType.strToNode(nodeType);
    //    this.longName = longName;
    //    this.shortName = shortName;
    //    this.edges = new HashSet<>();
  }

  //  public Node(
  //      String nodeID,
  //      int xCoord,
  //      int yCoord,
  //      String floor,
  //      String building,
  //      String nodeType,
  //      String longName,
  //      String shortName) {
  //    this.nodeID = nodeID;
  //    this.xCoord = xCoord;
  //    this.yCoord = yCoord;
  //    this.floor = Floor.strToFloor(floor);
  //    this.building = Building.strToBuilding(building);
  //    this.nodeType = NodeType.strToNode(nodeType);
  //    this.longName = longName;
  //    this.shortName = shortName;
  //    this.edges = new HashSet<>();
  //  }

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

  // public void addEdge(Edge e) {
  //    this.edges.add(e);
  //  }

  @Override
  public String toString() {
    String s =
        "ID : "
            + nodeID
            + "\nxcoord : "
            + String.valueOf(xCoord)
            + "\nycoord : "
            + String.valueOf(yCoord)
            + "\nfloor : "
            + floor
            + "\nbuilding : "
            + building;
    return s;
    // return nodeID;
  }

  public void setNodeID(String nodeID) {
    this.nodeID = nodeID;
    DBUpdate.update("node", this.nodeID, "nodeid", "'" + nodeID + "'", "nodeid");
  }

  public void setXCoord(int xcoord) {
    this.xCoord = xcoord;
    DBUpdate.update("node", this.nodeID, "xcoord", String.valueOf(xcoord), "nodeid");
  }

  public void setYCoord(int ycoord) {
    this.yCoord = ycoord;
    DBUpdate.update("node", this.nodeID, "ycoord", String.valueOf(ycoord), "nodeid");
  }

  public void setFloor(String floor) {
    this.floor = floor;
    DBUpdate.update("nodes", this.nodeID, "floor", "'" + floor + "'", "nodeid");
  }

  public void setBuilding(String building) {
    this.building = building;
    DBUpdate.update("nodes", this.nodeID, "building", "'" + building + "'", "nodeid");
  }

  //  public void setNodeType(String nodeType) {
  //    this.nodeType = NodeType.strToNode(nodeType);
  //    DBUpdate.update("l1nodes", this.nodeID, "nodetype", "'" + nodeType + "'", "nodeid");
  //  }
  //
  //  public void setLongName(String longName) {
  //    this.longName = longName;
  //    DBUpdate.update("l1nodes", this.nodeID, "longname", "'" + longName + "'", "nodeid");
  //  }
  //
  //  public void setShortName(String shortName) {
  //    this.shortName = shortName;
  //    DBUpdate.update("l1nodes", this.nodeID, "shortname", "'" + shortName + "'", "nodeid");
  //  }

  public void deleteNode() {
    // remove from hashmap
    DBUpdate.delete("nodes", this.nodeID, "nodeid");
    DatabaseConnect.deleteNode(this.nodeID);
  }

  public HashSet<Edge> getEdges(){
    HashSet<Edge> ret = new HashSet<Edge>();
    ResultSet rset = DBUpdate.edges(nodeID);
    try {
      while (rset.next()) { // loop through the table
        ret.add(new Edge(rset.getString("node1"), rset.getString("node2")));
      }
    }
    catch (java.sql.SQLException e){
      System.out.println(e);
    }
    return ret;
  }
}
