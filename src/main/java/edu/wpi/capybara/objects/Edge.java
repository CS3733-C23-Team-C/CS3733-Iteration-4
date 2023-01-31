package edu.wpi.capybara.objects;

import edu.wpi.capybara.database.DBUpdate;
import edu.wpi.capybara.database.DatabaseConnect;

public class Edge {
  private Node startNode, endNode;
  private String edgeID;

  public Edge(String id) {
    this.edgeID = id;
  }

  public Edge(String id, Node startNode, Node endNode) {
    this.edgeID = id;
    this.startNode = startNode;
    this.endNode = endNode;
  }

  public Node getStartNode() {
    return startNode;
  }

  private void setStartNode(Node startNode) {
    this.startNode = startNode;
  }

  public Node getEndNode() {
    return endNode;
  }

  public Node getOtherNode(Node node) {
    if (node.equals(startNode)) return endNode;
    return startNode;
  }

  private void setEndNode(Node endNode) {
    this.endNode = endNode;
  }

  public void setNodes(Node startNode, Node endNode) {
    this.setStartNode(startNode);
    this.setEndNode(endNode);
    startNode.addEdge(this);
    endNode.addEdge(this);
  }

  public String getEdgeID() {
    return edgeID;
  }

  public void setEdgeID() {
    this.edgeID = edgeID;
  }

  public void deleteEdge() {
    // remove from hashmap
    DBUpdate.delete("l1edges", this.edgeID, "edgeid");
    DatabaseConnect.deleteEdge(this.edgeID);
  }

  @Override
  public String toString() {
    return "ID : "
        + this.edgeID
        + "\n"
        + "Starting Node : "
        + this.startNode.getNodeID()
        + "\n"
        + "Ending Node : "
        + this.endNode.getNodeID()
        + "\n";
  }

  public boolean equals(Edge e) {
    return this.edgeID.equals(e.getEdgeID());
  }
}
