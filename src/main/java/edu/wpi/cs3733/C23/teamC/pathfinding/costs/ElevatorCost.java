package edu.wpi.cs3733.C23.teamC.pathfinding.costs;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.NodeEntity;
import edu.wpi.cs3733.C23.teamC.pathfinding.AstarPathfinder;

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
    int startFloor = AstarPathfinder.floorToNum(start.getFloor().toString());
    int endFloor = AstarPathfinder.floorToNum(end.getFloor().toString());

    return 500 * Math.abs(endFloor - startFloor);
  }
}
