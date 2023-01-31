package edu.wpi.capybara.objects;

import edu.wpi.capybara.database.DBUpdate;

public class Edge {
  private String startNode, endNode;

  public Edge(String startNode, String endNode) {
    this.startNode = startNode;
    this.endNode = endNode;
  }

  public String getStartNode() {
    return startNode;
  }

  private void setStartNode(String startNode) {
    this.startNode = startNode;
    DBUpdate.update("edge", this.startNode, this.endNode, "node1", startNode, "node1", "node2");
  }

  public String getEndNode() {
    return endNode;
  }

  public String getOtherNode(Node node) {
    if (node.equals(startNode)) return endNode;
    return startNode;
  }

  private void setEndNode(String endNode) {
    this.endNode = endNode;
    DBUpdate.update("edge", this.startNode, this.endNode, "node2", endNode, "node1", "node2");
  }

  //  public void setNodes(Node startNode, Node endNode) {
  //    this.setStartNode(startNode);
  //    this.setEndNode(endNode);
  //    startNode.addEdge(this);
  //    endNode.addEdge(this);
  //  }

  //  public void deleteEdge() {
  //    // remove from hashmap
  //    DBUpdate.delete("edge", this.edgeID, "edgeid");
  //    DatabaseConnect.deleteEdge(this.edgeID);
  //  }

  @Override
  public String toString() {
    return "Starting Node : "
        + this.startNode
        + "\n"
        + "Ending Node : "
        + this.endNode
        + "\n";
  }

  public boolean equals(Edge e) {
    return this.edgeID.equals(e.getEdgeID());
  }

}
