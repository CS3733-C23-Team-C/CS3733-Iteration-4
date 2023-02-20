package edu.wpi.capybara.pathfinding;

import edu.wpi.capybara.objects.hibernate.EdgeEntity;
import edu.wpi.capybara.objects.orm.Edge;
import edu.wpi.capybara.objects.orm.Node;

import java.util.*;

public class BFSPathfinder implements PathfindingAlgorithm {
  private final Map<String, Node> nodes;
  private final List<Edge> edges;

  public Map<String, Node> getNodes() {
    return nodes;
  }

  public List<Edge> getEdges() {
    return edges;
  }

  public String toString() {
    return "BFS Pathfinder";
  }

  public List<Node> findPath(String start, String end) {
    Node startNode = getNodeFromNodeID(start);
    Node endNode = getNodeFromNodeID(end);

    if (startNode == null || endNode == null) {
      throw new RuntimeException("One of the NodeID doesn't exist!");
    }

    return bfs(startNode, endNode);
  }

  public List<Node> findPath(Node start, Node end) {

    if (start == null || end == null) {
      throw new RuntimeException("One of the NodeID doesn't exist!");
    }

    return bfs(start, end);
  }

  public Node getNodeFromNodeID(String s) {
    for (Node node : nodes.values()) {
      if (node.getNodeid().equals(s)) return node;
    }
    return null;
  }

  public BFSPathfinder(Map<String, Node> nodes, List<Edge> edges) {
    this.nodes = nodes;
    this.edges = edges;
  }

  private List<Node> bfs(Node start, Node goal) {

    LinkedList<BFSPathNode> queue = new LinkedList<BFSPathNode>();
    queue.add(new BFSPathNode(start, new LinkedList<>()));
    ArrayList<Node> checkedNodes = new ArrayList<>();

    while (queue.peek() != null) {

      BFSPathNode bfsnode1 = queue.remove();
      Node node1 = bfsnode1.getNode();
      List<Node> nodePath = bfsnode1.getPath();

      checkedNodes.add(node1);
      nodePath.add(node1);

      HashSet<EdgeEntity> edges1 = node1.getEdges();
      for (EdgeEntity edge : edges1) {
        Node node2 = this.getNodeFromNodeID(edge.getOtherNode(node1));
        if (node2.equals(goal)) {
          nodePath.add(goal);
          return nodePath;
        } else if (!checkedNodes.contains(node2)) {
          queue.add(new BFSPathNode(node2, new LinkedList<>(nodePath)));
        }
      }
    }

    return null; // case if no path
  }

  private static class BFSPathNode {

    private Node node;
    private List<Node> path;

    public BFSPathNode(Node node, List<Node> path) {
      this.node = node;
      this.path = path;
    }

    public Node getNode() {
      return node;
    }

    public void setNode(Node node) {
      this.node = node;
    }

    public List<Node> getPath() {
      return path;
    }
  }
}
