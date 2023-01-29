package edu.wpi.teamname.pathfinding;

import java.io.InputStream;
import java.util.*;

public class Pathfinder {
  private Map<String, Node> nodes;
  private Map<String, Edge> edges;

  private static class PathNode {
    List<Node> path;
    Node node;

    PathNode(List<Node> list, Node node) {
      this.path = list;
      this.node = node;
    }
  }

  public Pathfinder(InputStream nodesCSV, InputStream edgesCSV) {
    Scanner nodeScanner = new Scanner(nodesCSV);
    boolean skippedFirst = false;
    nodes = new HashMap<>();
    edges = new HashMap<>();
    while (nodeScanner.hasNextLine()) {
      String line = nodeScanner.nextLine();
      if (!skippedFirst) {
        skippedFirst = true;
        continue;
      }

      String[] lineVals = line.split(",");
      String nodeID = lineVals[0];
      Node newNode =
          new Node(
              lineVals[0],
              lineVals[1],
              lineVals[2],
              lineVals[3],
              lineVals[4],
              lineVals[5],
              lineVals[6],
              lineVals[7]);
      nodes.put(nodeID, newNode);
    }

    Scanner edgeScanner = new Scanner(edgesCSV);
    skippedFirst = false;
    while (edgeScanner.hasNextLine()) {
      String line = edgeScanner.nextLine();
      if (!skippedFirst) {
        skippedFirst = true;
        continue;
      }
      // System.out.println("made it here");
      String[] lineVals = line.split(",");
      // System.out.println("vals: " + Arrays.toString(lineVals));
      String edgeID = lineVals[0];
      Edge newEdge = new Edge(edgeID);
      newEdge.setNodes(nodes.get(lineVals[1]), nodes.get(lineVals[2]));
      edges.put(edgeID, newEdge);
    }

    /*
    for (Node n : nodes.values()) {
      System.out.println("Node: " + n.toString());
    }
    for (Edge e : edges.values()) {
      System.out.println("Edge: " + e.toString());
    }*/
  }

  public Map<String, Node> getNodes() {
    return nodes;
  }

  public Map<String, Edge> getEdges() {
    return edges;
  }

  public List<Node> findPath(String start, String end) {
    Node startNode = nodes.get(start);
    Node endNode = nodes.get(end);

    if (startNode == null || endNode == null) {
      throw new RuntimeException("One of the NodeID doesn't exist!");
    }

    return dfs(new PathNode(new ArrayList<>(), startNode), endNode, new Stack<>(), new HashSet<>());
  }

  private List<Node> dfs(
      PathNode current, Node goal, Stack<PathNode> nodesLeft, Collection<Node> nodesChecked) {
    // System.out.println("DFS with: " + current.node.toString());
    // System.out.println("NodesLeft: " + nodesLeft.toString());
    // System.out.println("NodesChecked: " + nodesChecked.toString());
    if (nodesChecked.contains(current.node)) {
      // System.out.println("Node already checked, next node");
      return dfs(nodesLeft.pop(), goal, nodesLeft, nodesChecked);
    }
    nodesChecked.add(current.node);
    List<Node> newPath = new ArrayList<>(List.copyOf(current.path));
    newPath.add(current.node);
    if (current.node.equals(goal)) return newPath;

    Set<Node> borderNodes = new HashSet<>();
    for (Edge e : current.node.getEdges()) {
      Node n = e.getOtherNode(current.node);
      // System.out.println("Found Border Node " + n.toString());
      borderNodes.add(n);
    }

    for (Node n : borderNodes) {
      nodesLeft.push(new PathNode(newPath, n));
    }

    return dfs(nodesLeft.pop(), goal, nodesLeft, nodesChecked);
  }

  /*
  public Pathfinder(Collection<Node> nodes, Collection<Edge> edges) {
    this.nodes = nodes;
    this.edges = edges;
  }*/
}
