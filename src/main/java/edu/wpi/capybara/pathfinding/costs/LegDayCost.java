package edu.wpi.capybara.pathfinding.costs;

import edu.wpi.capybara.objects.hibernate.NodeEntity;

public class LegDayCost extends StairsCost {
  public LegDayCost() {}

  @Override
  public double calculateCost(NodeEntity start, NodeEntity end) {
    return 0;
  }
}
