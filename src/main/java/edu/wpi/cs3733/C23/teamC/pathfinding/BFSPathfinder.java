package edu.wpi.cs3733.C23.teamC.pathfinding;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.EdgeEntity;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.NodeEntity;
import java.util.*;

public class BFSPathfinder implements PathfindingAlgorithm {
  private final Map<String, NodeEntity> nodes;
  private final List<EdgeEntity> edges;

  public Map<String, NodeEntity> getNodes() {
    return nodes;
  }

  public List<EdgeEntity> getEdges() {
    return edges;
  }

  public String toString() {
    return "BFS Pathfinder";
  }

  public List<NodeEntity> findPath(String start, String end) {
    NodeEntity startNode = getNodeFromNodeID(start);
    NodeEntity endNode = getNodeFromNodeID(end);

    if (startNode == null || endNode == null) {
      throw new RuntimeException("One of the NodeID doesn't exist!");
    }

    return bfs(startNode, endNode);
  }

  public List<NodeEntity> findPath(NodeEntity start, NodeEntity end) {

    if (start == null || end == null) {
      throw new RuntimeException("One of the NodeID doesn't exist!");
    }

    return bfs(start, end);
  }

  public NodeEntity getNodeFromNodeID(String s) {
    for (NodeEntity node : nodes.values()) {
      if (node.getNodeID().equals(s)) return node;
    }
    return null;
  }

  public BFSPathfinder(Map<String, NodeEntity> nodes, List<EdgeEntity> edges) {
    this.nodes = nodes;
    this.edges = edges;
  }

  private List<NodeEntity> bfs(NodeEntity start, NodeEntity goal) {

    LinkedList<BFSPathNode> queue = new LinkedList<BFSPathNode>();
    queue.add(new BFSPathNode(start, new LinkedList<>()));
    ArrayList<NodeEntity> checkedNodes = new ArrayList<>();

    while (queue.peek() != null) {

      BFSPathNode bfsnode1 = queue.remove();
      NodeEntity node1 = bfsnode1.getNode();
      List<NodeEntity> nodePath = bfsnode1.getPath();

      checkedNodes.add(node1);
      nodePath.add(node1);

      HashSet<EdgeEntity> edges1 = node1.getEdges();
      for (EdgeEntity edge : edges1) {
        NodeEntity node2 = this.getNodeFromNodeID(edge.getOtherNode(node1));
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

    private NodeEntity node;
    private List<NodeEntity> path;

    public BFSPathNode(NodeEntity node, List<NodeEntity> path) {
      this.node = node;
      this.path = path;
    }

    public NodeEntity getNode() {
      return node;
    }

    public void setNode(NodeEntity node) {
      this.node = node;
    }

    public List<NodeEntity> getPath() {
      return path;
    }
  }
}
