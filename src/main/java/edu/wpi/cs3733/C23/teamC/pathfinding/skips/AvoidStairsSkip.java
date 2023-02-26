package edu.wpi.cs3733.C23.teamC.pathfinding.skips;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.NodeEntity;

public class AvoidStairsSkip implements PathfindingSkip {

  public AvoidStairsSkip() {}

  @Override
  public boolean shouldSkip(NodeEntity start, NodeEntity end) {
    return start.getLocationType().equals("STAI")
        && end.getLocationType().equals("STAI")
        && !end.getFloor().equals(start.getFloor());
  }
}
