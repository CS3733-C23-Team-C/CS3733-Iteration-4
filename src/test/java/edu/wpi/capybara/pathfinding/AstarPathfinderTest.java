package edu.wpi.capybara.pathfinding;

import edu.wpi.capybara.database.RepoFacade;
import edu.wpi.capybara.objects.hibernate.EdgeEntity;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import java.util.*;

public class AstarPathfinderTest {

  static NodeEntity current, n1, n2, goal;
  static HashMap<String, NodeEntity> nodes, mockNodes;
  static HashMap<Integer, EdgeEntity> edges, mockEdges;

  public static RepoFacade db;

  //  @BeforeAll
  //  public static void init() {
  //    current = mock(NodeEntity.class);
  //    when(current.getNodeid()).thenReturn("testA");
  //    when(current.getXcoord()).thenReturn(0);
  //    when(current.getYcoord()).thenReturn(0);
  //    when(current.getFloor()).thenReturn("na");
  //    when(current.getBuilding()).thenReturn("building");
  //
  //    n1 = mock(NodeEntity.class);
  //    when(n1.getNodeid()).thenReturn("testB");
  //    when(n1.getXcoord()).thenReturn(1);
  //    when(n1.getYcoord()).thenReturn(0);
  //    when(n1.getFloor()).thenReturn("na");
  //    when(n1.getBuilding()).thenReturn("building");
  //
  //    n2 = mock(NodeEntity.class);
  //    when(n2.getNodeid()).thenReturn("testC");
  //    when(n2.getXcoord()).thenReturn(-1);
  //    when(n2.getYcoord()).thenReturn(0);
  //    when(n2.getFloor()).thenReturn("na");
  //    when(n2.getBuilding()).thenReturn("building");
  //
  //    goal = mock(NodeEntity.class);
  //    when(goal.getNodeid()).thenReturn("testD");
  //    when(goal.getXcoord()).thenReturn(10);
  //    when(goal.getYcoord()).thenReturn(0);
  //    when(goal.getFloor()).thenReturn("na");
  //    when(goal.getBuilding()).thenReturn("building");
  //
  //    db = new newDBConnect();
  //
  //    nodes = db.getNodes();
  //    edges = db.getEdges();
  //
  //    mockNodes = new HashMap<>();
  //    mockNodes.put("testA", current);
  //    mockNodes.put("testB", n1);
  //    mockNodes.put("testC", n2);
  //    mockNodes.put("testD", goal);
  //
  //    mockEdges = new HashMap<>();
  //  }
  //
  //  @Test
  //  public void testCost() throws Exception {
  //    AstarPathfinder pf = new AstarPathfinder(mockNodes, mockEdges);
  //
  //    Double cost1 = Whitebox.invokeMethod(pf, "cost", current, n1, goal);
  //    Double cost2 = Whitebox.invokeMethod(pf, "cost", current, n2, goal);
  //
  //    assertTrue(cost1 < cost2);
  //  }
  //
  //  @Test
  //  public void testSimplePath() {
  //    AstarPathfinder pf = new AstarPathfinder(nodes, edges);
  //    List<NodeEntity> path = pf.findPath("2X1726Y1930", "2X1686Y1931");
  //
  //    assertEquals(path.size(), 2);
  //  }
}
