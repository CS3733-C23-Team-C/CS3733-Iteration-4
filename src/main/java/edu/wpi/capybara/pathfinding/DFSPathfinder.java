package edu.wpi.capybara.pathfinding;

import edu.wpi.capybara.objects.hibernate.EdgeEntity;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import java.util.*;

public class DFSPathfinder implements PathfindingAlgorithm {
  private final TreeMap<String, NodeEntity> nodes;

  private static class PathNode {
    List<NodeEntity> path;
    NodeEntity node;

    PathNode(List<NodeEntity> list, NodeEntity node) {
      this.path = list;
      this.node = node;
    }
  }

  public List<NodeEntity> findPath(NodeEntity start, NodeEntity end) {

    if (start == null || end == null) {
      throw new RuntimeException("One of the NodeID doesn't exist!");
    }

    return dfs(new PathNode(new ArrayList<>(), start), end, new Stack<>(), new ArrayList<>());
  }

  @Override
  public List<NodeEntity> findPath(String start, String end) {
    NodeEntity startNode = getNodeFromNodeID(start);
    NodeEntity endNode = getNodeFromNodeID(end);

    return findPath(startNode, endNode);
  }

  private NodeEntity getNodeFromNodeID(String s) {
    return nodes.get(s);
  }

  private List<NodeEntity> dfs(
      PathNode current,
      NodeEntity goal,
      Stack<PathNode> nodesLeft,
      Collection<NodeEntity> nodesChecked) {
    // System.out.println("DFS with: " + current.node.toString());
    // System.out.println("NodesLeft: " + nodesLeft.toString());
    // System.out.println("NodesChecked: " + nodesChecked.toString());
    if (nodesChecked.contains(current.node)) {
      // System.out.println("Node already checked, next node");
      return dfs(nodesLeft.pop(), goal, nodesLeft, nodesChecked);
    }
    nodesChecked.add(current.node);
    List<NodeEntity> newPath = new ArrayList<>(List.copyOf(current.path));
    newPath.add(current.node);
    if (current.node.equals(goal)) return newPath;

    Set<NodeEntity> borderNodes = new HashSet<>();
    for (EdgeEntity e : current.node.getEdges()) {
      NodeEntity n = getNodeFromNodeID(e.getOtherNode(current.node));
      // System.out.println("Found Border Node " + n.toString());
      borderNodes.add(n);
    }

    for (NodeEntity n : borderNodes) {
      nodesLeft.push(new PathNode(newPath, n));
    }

    return dfs(nodesLeft.pop(), goal, nodesLeft, nodesChecked);
  }

  public DFSPathfinder(TreeMap<String, NodeEntity> nodes) {
    this.nodes = nodes;
  }

  @Override
  public String toString() {
    return "DFS Algorithm";
  }
}
