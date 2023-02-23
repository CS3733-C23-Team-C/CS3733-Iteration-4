package edu.wpi.cs3733.C23.teamC.pathfinding.costs;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.NodeEntity;

public class LegDayCost extends StairsCost {
  public LegDayCost() {}

  @Override
  public double calculateCost(NodeEntity start, NodeEntity end) {
    return 0;
  }
}
