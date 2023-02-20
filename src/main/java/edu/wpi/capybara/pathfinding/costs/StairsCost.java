package edu.wpi.capybara.pathfinding.costs;

import edu.wpi.capybara.objects.hibernate.NodeEntity;

public class StairsCost implements PathfindingCost {
  public StairsCost() {}

  @Override
  public boolean doesUseCost(NodeEntity start, NodeEntity end) {
    return start.getLocationType().equals("STAI")
        && end.getLocationType().equals("STAI")
        && !end.getFloor().equals(start.getFloor());
  }

  @Override
  public double calculateCost(NodeEntity start, NodeEntity end) {
    int startFloor = NodeEntity.floorToNum(start.getFloor());
    int endFloor = NodeEntity.floorToNum(end.getFloor());

    int diff = endFloor - startFloor;

    if (diff < 0) return 50 * Math.abs(diff);
    return 100 * Math.abs(diff);
  }
}
