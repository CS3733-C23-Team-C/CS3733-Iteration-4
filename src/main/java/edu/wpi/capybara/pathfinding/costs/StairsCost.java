package edu.wpi.capybara.pathfinding.costs;

import edu.wpi.capybara.objects.hibernate.NodeEntity;
import edu.wpi.capybara.pathfinding.AstarPathfinder;

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
    int startFloor = AstarPathfinder.floorToNum(start.getFloor().toString());
    int endFloor = AstarPathfinder.floorToNum(end.getFloor().toString());

    int diff = endFloor - startFloor;

    if (diff < 0) return 600 * Math.abs(diff);
    return 1000 * Math.abs(diff);
  }
}
