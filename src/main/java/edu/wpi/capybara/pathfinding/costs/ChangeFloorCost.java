package edu.wpi.capybara.pathfinding.costs;

import edu.wpi.capybara.objects.hibernate.NodeEntity;

public class ChangeFloorCost implements PathfindingCost {

  public ChangeFloorCost() {}

  @Override
  public boolean doesUseCost(NodeEntity start, NodeEntity end) {
    return !start.getFloor().equals(end.getFloor());
  }

  @Override
  public double calculateCost(NodeEntity start, NodeEntity end) {
    return 500;
  }
}
