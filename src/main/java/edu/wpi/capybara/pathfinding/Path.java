package edu.wpi.capybara.pathfinding;

public class Path {
  private String startNode;
  private String endNode;

  public Path(String startNode, String endNode) {
    this.startNode = startNode;
    this.endNode = endNode;
  }

  public String getStartNode() {
    return this.startNode;
  }

  public String getEndNode() {
    return this.endNode;
  }
}
