package edu.wpi.teamname.database;

import static edu.wpi.teamname.database.DatabaseConnect.nodes;

// L1 node class
public class L1Nodes {
  private String nodeID;
  private int xcoord;
  private int ycoord;
  private String floor;
  private String building;
  private String nodeType;
  private String longName;
  private String shortName;

  public L1Nodes(
      String nodeID,
      int xcoord,
      int ycoord,
      String floor,
      String building,
      String nodeType,
      String longName,
      String shortName) {
    this.nodeID = nodeID;
    this.xcoord = xcoord;
    this.ycoord = ycoord;
    this.floor = floor;
    this.building = building;
    this.nodeType = nodeType;
    this.longName = longName;
    this.shortName = shortName;
  }

  public String getNodeID() {
    return nodeID;
  }

  public void setNodeID(String nodeID) {
    this.nodeID = nodeID;
    DBUpdate.update("l1nodes", this.nodeID, "nodeid", "'" + nodeID + "'", "nodeid");
  }

  public int getXcoord() {
    return xcoord;
  }

  public void setXcoord(int xcoord) {
    this.xcoord = xcoord;
    DBUpdate.update("l1nodes", this.nodeID, "xcoord", String.valueOf(xcoord), "nodeid");
  }

  public int getYcoord() {
    return ycoord;
  }

  public void setYcoord(int ycoord) {
    this.ycoord = ycoord;
    DBUpdate.update("l1nodes", this.nodeID, "ycoord", String.valueOf(ycoord), "nodeid");
  }

  public String getFloor() {
    return floor;
  }

  public void setFloor(String floor) {
    this.floor = floor;
    DBUpdate.update("l1nodes", this.nodeID, "floor", "'" + floor + "'", "nodeid");
  }

  public String getBuilding() {
    return building;
  }

  public void setBuilding(String building) {
    this.building = building;
    DBUpdate.update("l1nodes", this.nodeID, "building", "'" + building + "'", "nodeid");
  }

  public String getNodeType() {
    return nodeType;
  }

  public void setNodeType(String nodeType) {
    this.nodeType = nodeType;
    DBUpdate.update("l1nodes", this.nodeID, "nodetype", "'" + nodeType + "'", "nodeid");
  }

  public String getLongName() {
    return longName;
  }

  public void setLongName(String longName) {
    this.longName = longName;
    DBUpdate.update("l1nodes", this.nodeID, "longname", "'" + longName + "'", "nodeid");
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
    DBUpdate.update("l1nodes", this.nodeID, "shortname", "'" + shortName + "'", "nodeid");
  }

  public void deleteNode() {
    // remove from hashmap
    DBUpdate.delete("l1nodes", this.nodeID, "nodeid");
    nodes.remove(this.nodeID);
  }

  @Override
  public String toString() {
    String s =
        "ID : "
            + nodeID
            + "\nxcoord : "
            + String.valueOf(xcoord)
            + "\nycoord : "
            + String.valueOf(ycoord)
            + "\nfloor : "
            + floor
            + "\nbuilding : "
            + building
            + "\nnodetype : "
            + nodeType
            + "\nlongname : "
            + longName
            + "\nshortname : "
            + shortName;
    return s;
  }
}
