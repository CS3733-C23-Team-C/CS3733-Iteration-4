package edu.wpi.teamname.database;

import static edu.wpi.teamname.database.DatabaseConnect.edges;

public class L1Edges {
  private String edgeID;
  private String startNode;
  private String endNode;

  public L1Edges(String edgeID, String startNode, String endNode) {
    this.edgeID = edgeID;
    this.startNode = startNode;
    this.endNode = endNode;
  }

  public String getEdgeID() {
    return edgeID;
  }

  public void setEdgeID(String edgeID) {
    this.edgeID = edgeID;
    DBUpdate.update("l1edges", this.edgeID, "edgeid", "'" + edgeID + "'", "edgeid");
  }

  public String getStartNode() {
    return startNode;
  }

  public void setStartNode(String startNode) {
    this.startNode = startNode;
    DBUpdate.update("l1edges", this.edgeID, "startnode", "'" + startNode + "'", "edgeid");
  }

  public String getEndNode() {
    return endNode;
  }

  public void setEndNode(String endNode) {
    this.endNode = endNode;
    DBUpdate.update("l1edges", this.edgeID, "endnode", "'" + endNode + "'", "edgeid");
  }

  public void deleteEdge() {
    // remove from hashmap
    DBUpdate.delete("l1edges", this.edgeID, "edgeid");
    edges.remove(this.edgeID);
  }

  @Override
  public String toString() {
    String s =
        "ID : "
            + edgeID
            + "\nstartNode : "
            + startNode.toString()
            + "\nendNode : "
            + endNode.toString();
    return s;
  }
}
