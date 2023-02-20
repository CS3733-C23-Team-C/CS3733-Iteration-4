package edu.wpi.capybara.pathfinding;

import edu.wpi.capybara.objects.hibernate.EdgeEntity;
import edu.wpi.capybara.objects.orm.Edge;
import edu.wpi.capybara.objects.orm.Node;

import java.util.*;

public class AstarPathfinder implements PathfindingAlgorithm {
  private final Map<String, Node> nodes;
  private final List<Edge> edges;

  private static class PathNode implements Comparable<PathNode> {
    List<Node> path;
    List<EdgeEntity> edges;
    Node node;
      Node current;
      Node goal;
    EdgeEntity edge;
    double weight;

    /*
    PathNode(List<Node> list, List<Edge> edges, Node node) {
      this.path = list;
      this.edges = edges;
      this.node = node;
    }*/

    PathNode(
            List<Node> list,
            List<EdgeEntity> edges,
            Node node,
            Node current,
            Node goal) {
      this.path = list;
      this.edges = edges;
      this.node = node;
      this.current = current;
      this.goal = goal;
      this.weight = getCost();
    }

    @Override
    public int compareTo(PathNode o) {
      if (current == null || o.current == null) return 0;
      return Double.compare(weight, o.weight);
    }

    private double getCost() {
      return cost(current, node, goal);
    }
  }

  private static class PNComparator implements Comparator<PathNode> {

    @Override
    public int compare(PathNode o1, PathNode o2) {
      return o1.compareTo(o2);
    }

    @Override
    public boolean equals(Object obj) {
      return false;
    }
  }

  public List<Node> findPath(String start, String end) {
    Node startNode = getNodeFromNodeID(start);
    Node endNode = getNodeFromNodeID(end);

    if (startNode == null || endNode == null) {
      throw new RuntimeException("One of the NodeID doesn't exist!");
    }

    return aStar(startNode, endNode);
  }

  public List<Node> findPath(Node start, Node end) {

    if (start == null || end == null) {
      throw new RuntimeException("One of the NodeID doesn't exist!");
    }

    return aStar(start, end);
  }

  private Node getNodeFromNodeID(String s) {
    return nodes.get(s);
  }

  private List<Node> aStar(Node start, Node goal) {
    List<PathNode> openList = new ArrayList<>();
    List<Node> closedList = new ArrayList<>();
    Node current = start;
    List<Node> currentPath = new ArrayList<>();
    List<EdgeEntity> currentEdges = new ArrayList<>();

    while (true) {
      // System.out.println("Current-" + current.toString());
      List<Node> newPath = new ArrayList<>(List.copyOf(currentPath));
      newPath.add(current);
      if (current.equals(goal)) return newPath;

      Collection<EdgeEntity> edges = current.getEdges();
      if (edges == null) return null;

      for (EdgeEntity e : edges) {
        // System.out.println("New Edge: " + e.toString());
        List<EdgeEntity> newEdges = new ArrayList<>(List.copyOf(currentEdges));
        newEdges.add(e);

        Node otherNode = getNodeFromNodeID(e.getOtherNode(current));
        // System.out.println("Other Node: " + otherNode.getNodeID());
        // System.out.println(closedList);

        if (closedList.contains(otherNode)) continue;
        // System.out.println("Adding Node to openList");

        PathNode pn = new PathNode(newPath, newEdges, otherNode, current, goal);
        openList.add(pn);
        closedList.add(otherNode);
      }

      if (openList.size() == 0) return null;
      // System.out.println("openList is greater than 0");

      openList.sort(new PNComparator());

      PathNode next = openList.remove(0);
      current = next.node;
      currentPath = next.path;
      currentEdges = next.edges;
    }
  }

  private static double cost(Node current, Node n, Node goal) {
    float multiplier = 1f;

    if (!n.getFloor().equals(current.getFloor())) {
      multiplier = 5f;
    }

    return (calculateWeight(current, n) + calculateWeight(n, goal)) * multiplier;
  }

  private static double calculateWeight(Node n1, Node n2) { // move function for a*
    float xDiff = Math.abs(n1.getXcoord() - n2.getXcoord());
    float yDiff = Math.abs(n1.getYcoord() - n2.getYcoord());

    return Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
  }

  public AstarPathfinder(Map<String, Node> nodes, List<Edge> edges) {
    this.nodes = nodes;
    this.edges = edges;
  }

  @Override
  public String toString() {
    return "A* Algorithm";
  }
}
