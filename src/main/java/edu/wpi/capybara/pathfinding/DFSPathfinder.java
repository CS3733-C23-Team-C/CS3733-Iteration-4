package edu.wpi.capybara.pathfinding;

import edu.wpi.capybara.objects.hibernate.EdgeEntity;
import edu.wpi.capybara.objects.orm.Node;

import java.util.*;

public class DFSPathfinder implements PathfindingAlgorithm {
  private final Map<String, Node> nodes;

  private static class PathNode {
    List<Node> path;
    Node node;

    PathNode(List<Node> list, Node node) {
      this.path = list;
      this.node = node;
    }
  }

  public List<Node> findPath(Node start, Node end) {

    if (start == null || end == null) {
      throw new RuntimeException("One of the NodeID doesn't exist!");
    }

    return dfs(new PathNode(new ArrayList<>(), start), end, new Stack<>(), new ArrayList<>());
  }

  @Override
  public List<Node> findPath(String start, String end) {
    Node startNode = getNodeFromNodeID(start);
    Node endNode = getNodeFromNodeID(end);

    return findPath(startNode, endNode);
  }

  private Node getNodeFromNodeID(String s) {
    return nodes.get(s);
  }

  private List<Node> dfs(
          PathNode current,
          Node goal,
          Stack<PathNode> nodesLeft,
          Collection<Node> nodesChecked) {
    // System.out.println("DFS with: " + current.node.toString());
    // System.out.println("NodesLeft: " + nodesLeft.toString());
    // System.out.println("NodesChecked: " + nodesChecked.toString());
    if (nodesChecked.contains(current.node)) {
      // System.out.println("Node already checked, next node");
      return dfs(nodesLeft.pop(), goal, nodesLeft, nodesChecked);
    }
    nodesChecked.add(current.node);
    List<Node> newPath = new ArrayList<Node>(List.copyOf(current.path));
    newPath.add(current.node);
    if (current.node.equals(goal)) return newPath;

    Set<Node> borderNodes = new HashSet<>();
    for (EdgeEntity e : current.node.getEdges()) {
      Node n = getNodeFromNodeID(e.getOtherNode(current.node));
      // System.out.println("Found Border Node " + n.toString());
      borderNodes.add(n);
    }

    for (Node n : borderNodes) {
      nodesLeft.push(new PathNode(newPath, n));
    }

    return dfs(nodesLeft.pop(), goal, nodesLeft, nodesChecked);
  }

  public DFSPathfinder(Map<String, Node> nodes) {
    this.nodes = nodes;
  }

  @Override
  public String toString() {
    return "DFS Algorithm";
  }
}
