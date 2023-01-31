package edu.wpi.capybara.pathfinding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.objects.Edge;
import edu.wpi.capybara.objects.Node;
import java.util.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PathfinderTest {

  static Node current, n1, n2, goal;
  static Map<String, Node> nodes;
  static Map<String, Edge> edges;

  @BeforeAll
  public static void init() {
    current = new Node("testA", 0, 0, "na", "na");
    n1 = new Node("testB", 1, 0, "na", "na");
    n2 = new Node("testC", -1, 0, "na", "na");
    goal = new Node("testD", 10, 0, "na", "na");

    DatabaseConnect.connect();
    DatabaseConnect.importData();

    nodes = DatabaseConnect.getNodes();
    edges = DatabaseConnect.getEdges();
  }

  @Test
  public void testCost() {
    assertTrue(Pathfinder.cost(current, n1, goal) < Pathfinder.cost(current, n2, goal));
  }

  @Test
  public void testSimplePath() {
    Pathfinder pf = new Pathfinder(nodes, edges);
    List<Node> path = pf.findPath("22", "45");

    assertEquals(path.size(), 2);
  }
}
