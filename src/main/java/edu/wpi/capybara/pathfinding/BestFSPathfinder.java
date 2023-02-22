package edu.wpi.capybara.pathfinding;

import edu.wpi.capybara.objects.hibernate.EdgeEntity;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import edu.wpi.capybara.pathfinding.costs.ElevatorCost;
import edu.wpi.capybara.pathfinding.costs.PathfindingCost;
import edu.wpi.capybara.pathfinding.costs.StairsCost;
import java.util.*;

public class BestFSPathfinder implements PathfindingAlgorithm {

  private final Map<String, NodeEntity> nodes;
  private final List<EdgeEntity> edges;

  public Map<String, NodeEntity> getNodes() {
    return nodes;
  }

  public List<EdgeEntity> getEdges() {
    return edges;
  }

  public String toString() {
    return "BestFS Pathfinder";
  }

  public List<NodeEntity> findPath(String start, String end) {
    NodeEntity startNode = getNodeFromNodeID(start);
    NodeEntity endNode = getNodeFromNodeID(end);

    if (startNode == null || endNode == null) {
      throw new RuntimeException("One of the NodeID doesn't exist!");
    }

    return bestfs(startNode, endNode, new ElevatorCost());
  }

  public List<NodeEntity> findPath(NodeEntity start, NodeEntity end) {

    if (start == null || end == null) {
      throw new RuntimeException("One of the NodeID doesn't exist!");
    }

    return bestfs(start, end, new StairsCost());
  }

  public NodeEntity getNodeFromNodeID(String s) {
    for (NodeEntity node : nodes.values()) {
      if (node.getNodeID().equals(s)) return node;
    }
    return null;
  }

  public BestFSPathfinder(Map<String, NodeEntity> nodes, List<EdgeEntity> edges) {
    this.nodes = nodes;
    this.edges = edges;
  }

  private List<NodeEntity> bestfs(
      NodeEntity start, NodeEntity goal, PathfindingCost costAlgorithm) {

    LinkedList<BestFSPathfinder.BestFSPathNode> queue =
        new LinkedList<BestFSPathfinder.BestFSPathNode>();
    queue.add(new BestFSPathfinder.BestFSPathNode(start, new LinkedList<>()));
    ArrayList<NodeEntity> checkedNodes = new ArrayList<>();

    while (queue.peek() != null) {

      BestFSPathfinder.BestFSPathNode bestfsnode1 = queue.remove();
      NodeEntity node1 = bestfsnode1.getNode();
      List<NodeEntity> nodePath = bestfsnode1.getPath();

      checkedNodes.add(node1);
      nodePath.add(node1);

      HashSet<EdgeEntity> edges1 = node1.getEdges();

      ArrayList<NodeEntity> nextPathNodes = new ArrayList<NodeEntity>();

      for (EdgeEntity edge : edges1) {
        NodeEntity node2 = this.getNodeFromNodeID(edge.getOtherNode(node1));
        if (node2.equals(goal)) {
          nodePath.add(goal);
          return nodePath;
        } else if (!checkedNodes.contains(node2)) {
          if (nextPathNodes.size() == 0) {
            nextPathNodes.add(node2);
          } else {

            // sorts through the nodes the edges connects to by distance from least to greatest

            boolean unadded = true;
            for (int i = 0; i < nextPathNodes.size(); i++) {
              double currDistance = costAlgorithm.calculateCost(node1, node2),
                  otherDistance = costAlgorithm.calculateCost(node1, nextPathNodes.get(i));
              if (currDistance <= otherDistance) {
                nextPathNodes.add(i, node2);
                unadded = false;
                break;
              }
            }
            if (unadded) nextPathNodes.add(node2);
          }
        }
      }

      // adds to queue
      for (NodeEntity e : nextPathNodes) {
        queue.add(new BestFSPathfinder.BestFSPathNode(e, new LinkedList<>(nodePath)));
      }
    }

    return null; // case if no path
  }

  private static class BestFSPathNode {

    private NodeEntity node;
    private List<NodeEntity> path;

    public BestFSPathNode(NodeEntity node, List<NodeEntity> path) {
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
