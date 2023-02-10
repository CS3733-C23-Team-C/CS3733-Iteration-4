package edu.wpi.capybara.pathfinding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.objects.hibernate.EdgeEntity;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import java.util.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PathfinderTest {

  static NodeEntity current, n1, n2, goal;
  static TreeMap<String, NodeEntity> nodes;
  static TreeMap<Integer, EdgeEntity> edges;

  @BeforeAll
  public static void init() {
    current = mock(NodeEntity.class);
    when(current.getNodeid()).thenReturn("testA");
    when(current.getXcoord()).thenReturn(0);
    when(current.getYcoord()).thenReturn(0);
    when(current.getFloor()).thenReturn("na");
    when(current.getBuilding()).thenReturn("building");

    n1 = mock(NodeEntity.class);
    when(n1.getNodeid()).thenReturn("testB");
    when(n1.getXcoord()).thenReturn(1);
    when(n1.getYcoord()).thenReturn(0);
    when(n1.getFloor()).thenReturn("na");
    when(n1.getBuilding()).thenReturn("building");

    n2 = mock(NodeEntity.class);
    when(n2.getNodeid()).thenReturn("testC");
    when(n2.getXcoord()).thenReturn(-1);
    when(n2.getYcoord()).thenReturn(0);
    when(n2.getFloor()).thenReturn("na");
    when(n2.getBuilding()).thenReturn("building");

    goal = mock(NodeEntity.class);
    when(goal.getNodeid()).thenReturn("testD");
    when(goal.getXcoord()).thenReturn(10);
    when(goal.getYcoord()).thenReturn(0);
    when(goal.getFloor()).thenReturn("na");
    when(goal.getBuilding()).thenReturn("building");

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
    List<NodeEntity> path = pf.findPath("2X1726Y1930", "2X1686Y1931");

    assertEquals(path.size(), 2);
  }
}
