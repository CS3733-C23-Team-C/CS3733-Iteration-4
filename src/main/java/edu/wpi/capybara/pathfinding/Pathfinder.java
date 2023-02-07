package edu.wpi.capybara.pathfinding;

import edu.wpi.capybara.objects.hibernate.EdgeEntity;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import java.util.*;

public class Pathfinder {
  private final TreeMap<Integer, NodeEntity> nodes;
  private final TreeMap<Integer, EdgeEntity> edges;

  private static class PathNode implements Comparable<PathNode> {
    List<NodeEntity> path;
    List<EdgeEntity> edges;
    NodeEntity node, current, goal;

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
        NodeEntity goal) {
      this.path = list;
      this.edges = edges;
      this.node = node;
      this.current = current;
      this.goal = goal;
    }

    @Override
    public int compareTo(PathNode o) {
      if (current == null || o.current == null) return 0;
      return Double.compare(getCost(), o.getCost());
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

  //  public Pathfinder(InputStream nodesCSV, InputStream edgesCSV) {
  //    Scanner nodeScanner = new Scanner(nodesCSV);
  //    boolean skippedFirst = false;
  //    nodes = new TreeMap<>();
  //    edges = new TreeMap<>();
  //    while (nodeScanner.hasNextLine()) {
  //      String line = nodeScanner.nextLine();
  //      if (!skippedFirst) {
  //        skippedFirst = true;
  //        continue;
  //      }
  //
  //      String[] lineVals = line.split(",");
  //      String nodeID = lineVals[0];
  //      NodeEntity newNode =
  //          new NodeEntity(
  //              lineVals[0],
  //              Integer.parseInt(lineVals[1]),
  //              Integer.parseInt(lineVals[2]),
  //              lineVals[3],
  //              lineVals[4]);
  //      nodes.put(nodeID, newNode);
  //    }
  //
  //    Scanner edgeScanner = new Scanner(edgesCSV);
  //    skippedFirst = false;
  //    while (edgeScanner.hasNextLine()) {
  //      String line = edgeScanner.nextLine();
  //      if (!skippedFirst) {
  //        skippedFirst = true;
  //        continue;
  //      }
  //      // System.out.println("made it here");
  //      String[] lineVals = line.split(",");
  //      // System.out.println("vals: " + Arrays.toString(lineVals));
  //      String edgeID = lineVals[0];
  //      EdgeEntity newEdge = new EdgeEntity(lineVals[1], lineVals[2]);
  //      edges.put(edgeID, newEdge);
  //    }
  //
  //    /*
  //    for (Node n : nodes.values()) {
  //      System.out.println("Node: " + n.toString());
  //    }
  //    for (Edge e : edges.values()) {
  //      System.out.println("Edge: " + e.toString());
  //    }*/
  //  }

  public Map<Integer, NodeEntity> getNodes() {
    return nodes;
  }

  public Map<Integer, EdgeEntity> getEdges() {
    return edges;
  }

  public List<NodeEntity> findPath(String start, String end) {
    NodeEntity startNode = nodes.get(start);
    NodeEntity endNode = nodes.get(end);

    if (startNode == null || endNode == null) {
      throw new RuntimeException("One of the NodeID doesn't exist!");
    }

    return aStar(startNode, endNode);

    /*
    return dfs(
        new PathNode(new ArrayList<>(), new ArrayList<>(), startNode),
        endNode,
        new Stack<>(),
        new HashSet<>());*/
  }

  public List<Node> findPath(Node start, Node end) {

    if (start == null || end == null) {
      throw new RuntimeException("One of the NodeID doesn't exist!");
    }

    return aStar(start, end);
  }

  /*
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
      Node n = nodes.get(e.getOtherNode(current.node));
      // System.out.println("Found Border Node " + n.toString());
      List<Edge> newEdges = new ArrayList<>(List.copyOf(current.edges));
      newEdges.add(e);
      nodesLeft.push(new PathNode(newPath, newEdges, n));
    }

    return dfs(nodesLeft.pop(), goal, nodesLeft, nodesChecked);
  }
   */

  public static List<EdgeEntity> getPathEdges(List<NodeEntity> path) {
    List<EdgeEntity> edges = new ArrayList<EdgeEntity>();
    NodeEntity prevNode = null;
    for (NodeEntity node : path) {
      if (prevNode == null) {
        prevNode = node;
      } else {
        edges.add(commonEdge(prevNode, node));
        prevNode = node;
      }
    }
    return edges;
  }

  private static EdgeEntity commonEdge(NodeEntity node1, NodeEntity node2) {
    Set<EdgeEntity> edges1 = node1.getEdges(), edges2 = node2.getEdges();
    Iterator<EdgeEntity> edgeIterator1 = edges1.iterator();
    while (edgeIterator1.hasNext()) {
      EdgeEntity edge1 = edgeIterator1.next();
      Iterator<EdgeEntity> edgeIterator2 = edges2.iterator();
      while (edgeIterator2.hasNext()) {
        EdgeEntity edge2 = edgeIterator2.next();
        if (edge1.equals(edge2)) return edge1;
      }
    }
    System.out.println("No common edges for the given nodes : ");
    System.out.println(node1.toString());
    System.out.println(node2.toString());
    return null;
  }

  private List<NodeEntity> aStar(NodeEntity start, NodeEntity goal) {
    List<PathNode> openList = new ArrayList<>();
    List<NodeEntity> closedList = new ArrayList<>();
    NodeEntity current = start;
    List<NodeEntity> currentPath = new ArrayList<>();
    List<EdgeEntity> currentEdges = new ArrayList<>();

    while (true) {
      // System.out.println("Current Node is " + current.getNodeID());
      List<NodeEntity> newPath = new ArrayList<>(List.copyOf(currentPath));
      newPath.add(current);
      if (current.equals(goal)) return newPath;

      Collection<EdgeEntity> edges = current.getEdges();
      if (edges == null) return null;

      for (EdgeEntity e : edges) {
        // System.out.println("New Edge: " + e.toString());
        List<EdgeEntity> newEdges = new ArrayList<>(List.copyOf(currentEdges));
        newEdges.add(e);

        NodeEntity otherNode = nodes.get(e.getOtherNode(current));
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

  public static double cost(NodeEntity current, NodeEntity n, NodeEntity goal) {
    return calculateWeight(current, n) + calculateWeight(n, goal);
  }

  private static double calculateWeight(NodeEntity n1, NodeEntity n2) { // move function for a*
    float xDiff = Math.abs(n1.getXcoord() - n2.getXcoord());
    float yDiff = Math.abs(n1.getYcoord() - n2.getYcoord());

    return Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
  }

  public Pathfinder(TreeMap<Integer, NodeEntity> nodes, TreeMap<Integer, EdgeEntity> edges) {
    this.nodes = nodes;
    this.edges = edges;
  }

  /*
  public static void main(String[] args) {
    System.out.println("test");

    DatabaseConnect.connect();
    DatabaseConnect.importData();

    Pathfinder pathfinder = new Pathfinder(DatabaseConnect.getNodes(), DatabaseConnect.getEdges());
    List<Node> path = pathfinder.findPath("22", "44");
    if (path == null) {
      System.out.println("no path :<");
    } else {
      for (Node n : path) {
        System.out.println(n);
        System.out.println();
      }
    }
  }*/
}
