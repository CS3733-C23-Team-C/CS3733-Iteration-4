package edu.wpi.cs3733.C23.teamC.Pathfinding.costs;

import edu.wpi.cs3733.C23.teamC.database.hibernate.NodeEntity;

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
