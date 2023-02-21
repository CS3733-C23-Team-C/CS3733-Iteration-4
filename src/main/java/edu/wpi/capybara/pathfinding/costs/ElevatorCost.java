package edu.wpi.capybara.pathfinding.costs;

import edu.wpi.capybara.objects.hibernate.NodeEntity;

public class ElevatorCost implements PathfindingCost {
  public ElevatorCost() {}

  @Override
  public boolean doesUseCost(NodeEntity start, NodeEntity end) {
    return start.getLocationType().equals("ELEV")
        && end.getLocationType().equals("ELEV")
        && !end.getFloor().equals(start.getFloor());
  }

  @Override
  public double calculateCost(NodeEntity start, NodeEntity end) {
    int startFloor = NodeEntity.floorToNum(start.getFloor());
    int endFloor = NodeEntity.floorToNum(end.getFloor());

    return 500 * Math.abs(endFloor - startFloor);
  }
}
