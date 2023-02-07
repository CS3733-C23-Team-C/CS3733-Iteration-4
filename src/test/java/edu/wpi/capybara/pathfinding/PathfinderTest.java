package edu.wpi.capybara.pathfinding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.objects.hibernate.EdgeEntity;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import java.util.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PathfinderTest {

  static NodeEntity current, n1, n2, goal;
  static TreeMap<Integer, NodeEntity> nodes;
  static TreeMap<Integer, EdgeEntity> edges;

  @BeforeAll
  public static void init() {
    current = new NodeEntity();
    current.setNodeid("testA");
    current.setXcoord(0);
    current.setYcoord(0);
    current.setFloor("na");
    current.setBuilding("na");
    n1 = new NodeEntity();
    n1.setNodeid("testB");
    n1.setXcoord(1);
    n1.setYcoord(0);
    n1.setFloor("na");
    n1.setBuilding("na");
    n2 = new NodeEntity();
    n2.setNodeid("testC");
    n2.setXcoord(-1);
    n2.setYcoord(0);
    n2.setFloor("na");
    n2.setBuilding("na");
    goal = new NodeEntity();
    goal.setNodeid("testD");
    goal.setXcoord(10);
    goal.setYcoord(0);
    goal.setFloor("na");
    goal.setBuilding("na");

    DatabaseConnect.connect();
    DatabaseConnect.importData();

    nodes = DatabaseConnect.getNodes();
    edges = DatabaseConnect.getEdges();
  }

  @Test
  public void testCost() {
    assertTrue(Pathfinder.cost(current, n1, goal) < Pathfinder.cost(current, n2, goal));
  }

  //  @Test
  //  public void testSimplePath() {
  //    Pathfinder pf = new Pathfinder(nodes, edges);
  //    List<NodeEntity> path = pf.findPath(22, 45);
  //
  //    assertEquals(path.size(), 2);
  //  }
}
