package edu.wpi.cs3733.C23.teamC.Pathfinding.Algorithms;

import edu.wpi.cs3733.C23.teamC.Pathfinding.PathfindingAlgorithm;
import edu.wpi.cs3733.C23.teamC.Pathfinding.PathfindingController;
import edu.wpi.cs3733.C23.teamC.Pathfinding.costs.ElevatorCost;
import edu.wpi.cs3733.C23.teamC.Pathfinding.costs.PathfindingCost;
import edu.wpi.cs3733.C23.teamC.Pathfinding.costs.StairsCost;
import edu.wpi.cs3733.C23.teamC.Pathfinding.skips.PathfindingSkip;
import edu.wpi.cs3733.C23.teamC.database.hibernate.EdgeEntity;
import edu.wpi.cs3733.C23.teamC.database.hibernate.NodeEntity;
import java.util.*;
import java.util.function.Function;

public class AstarPathfinder implements PathfindingAlgorithm {
  private final Map<String, NodeEntity> nodes;
  private final List<EdgeEntity> edges;
  private final PathfindingController controller;

  private class PathNode implements Comparable<PathNode> {
    List<NodeEntity> path;
    List<EdgeEntity> edges;
    NodeEntity node, current, goal;
    EdgeEntity edge;
    double weight;

    /*
    PathNode(List<Node> list, List<Edge> edges, Node node) {
      this.path = list;
      this.edges = edges;
      this.node = node;
    }*/

    PathNode(
        List<NodeEntity> list,
        List<EdgeEntity> edges,
        NodeEntity node,
        NodeEntity current,
        NodeEntity goal,
        double pCost) {
      this.path = list;
      this.edges = edges;
      this.node = node;
      this.current = current;
      this.goal = goal;
      this.weight = getCost() + pCost;
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

  public List<NodeEntity> findPath(String start, String end) {
    NodeEntity startNode = getNodeFromNodeID(start);
    NodeEntity endNode = getNodeFromNodeID(end);

    if (startNode == null || endNode == null) {
      throw new RuntimeException("One of the NodeID doesn't exist!");
    }

    return aStar(startNode, endNode, (node) -> node.equals(endNode));
  }

  public List<NodeEntity> findPath(NodeEntity start, NodeEntity end) {

    if (start == null || end == null) {
      throw new RuntimeException("One of the Nodes doesn't exist!");
    }

    return aStar(start, end, (node) -> node.equals(end));
  }

  public List<NodeEntity> findPath(NodeEntity start, Function<NodeEntity, Boolean> condition) {
    if (start == null) {
      throw new RuntimeException("One of the NodeID doesn't exist!");
    }

    return aStar(start, start, condition);
  }

  private NodeEntity getNodeFromNodeID(String s) {
    return nodes.get(s);
  }

  private List<NodeEntity> aStar(
      NodeEntity start, NodeEntity goal, Function<NodeEntity, Boolean> condition) {
    List<PathNode> openList = new ArrayList<>();
    List<NodeEntity> closedList = new ArrayList<>();
    closedList.add(start);
    NodeEntity current = start;
    double currentWeight = 0;
    List<NodeEntity> currentPath = new ArrayList<>();
    List<EdgeEntity> currentEdges = new ArrayList<>();

    while (true) {
      // System.out.println("Current-" + current.toString() + " Cost-" + currentWeight);
      List<NodeEntity> newPath = new ArrayList<>(List.copyOf(currentPath));
      newPath.add(current);
      if (condition.apply(current)) return newPath;

      Collection<EdgeEntity> edges = current.getEdges();
      if (edges == null) return null;

      for (EdgeEntity e : edges) {
        // System.out.println("New Edge: " + e.toString());
        List<EdgeEntity> newEdges = new ArrayList<>(List.copyOf(currentEdges));
        newEdges.add(e);

        NodeEntity otherNode = getNodeFromNodeID(e.getOtherNode(current));
        // System.out.println("Other Node: " + otherNode.getNodeID());
        // System.out.println(closedList);

        if (closedList.contains(otherNode)) continue;

        boolean addNode = true;
        for (PathfindingSkip skip : getSkips()) {
          if (skip.shouldSkip(current, otherNode)) {
            // System.out.println("Skipping " + otherNode);
            addNode = false;
            break;
          }
        }

        // System.out.println("Adding Node to openList");

        PathNode pn = new PathNode(newPath, newEdges, otherNode, current, goal, currentWeight);
        if (addNode) {
          openList.add(pn);
          closedList.add(otherNode);
        }
      }

      if (openList.size() == 0) return null;
      // System.out.println("openList is greater than 0");

      openList.sort(new PNComparator());

      PathNode next = openList.remove(0);
      current = next.node;
      currentPath = next.path;
      currentEdges = next.edges;
      currentWeight = next.weight;
    }
  }

  private double cost(NodeEntity current, NodeEntity n, NodeEntity goal) {
    double additionalCost = 0;

    for (PathfindingCost cost : getCosts()) {
      if (cost.doesUseCost(current, n)) additionalCost += cost.calculateCost(current, n);
    }

    double cost = (calculateWeight(current, n) + calculateWeight(n, goal)) + additionalCost;
    // System.out.println("Cost: " + cost + " between " + current.getShortName() + " and " +
    // n.getShortName());
    return cost;
  }

  private static int floorDifference(NodeEntity n1, NodeEntity n2) {
    int n1Floor = floorToNum(n1.getFloor().toString());
    int n2Floor = floorToNum(n2.getFloor().toString());

    return n2Floor - n1Floor;
  }

  private static double calculateWeight(NodeEntity n1, NodeEntity n2) { // move function for a*
    float xDiff = Math.abs(n1.getXcoord() - n2.getXcoord());
    float yDiff = Math.abs(n1.getYcoord() - n2.getYcoord());
    float zDiff =
        Math.abs(floorToNum(n1.getFloor().toString()) - floorToNum(n2.getFloor().toString())) * 50;

    return Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2) + Math.pow(zDiff, 2));
  }

  public AstarPathfinder(
      Map<String, NodeEntity> nodes, List<EdgeEntity> edges, PathfindingController controller) {
    this.nodes = nodes;
    this.edges = edges;
    this.controller = controller;
  }

  public AstarPathfinder(Map<String, NodeEntity> nodes, List<EdgeEntity> edges) {
    this.nodes = nodes;
    this.edges = edges;
    this.controller = null;
  }

  @Override
  public String toString() {
    return "A* Algorithm";
  }

  public static int floorToNum(String floor) {
    if (floor.equals("L2")) return -1;
    if (floor.equals("L1")) return 0;
    if (floor.equals("1")) return 1;
    if (floor.equals("2")) return 2;
    if (floor.equals("3")) return 3;
    return 0;
  }

  private Collection<PathfindingCost> getCosts() {
    if (controller != null) return controller.getCosts();

    return List.of(new ElevatorCost(), new StairsCost());
  }

  private Collection<PathfindingSkip> getSkips() {
    if (controller != null) return controller.getSkips();

    return List.of();
  }
}
