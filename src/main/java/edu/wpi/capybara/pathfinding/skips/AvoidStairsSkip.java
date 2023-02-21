package edu.wpi.capybara.pathfinding.skips;

import edu.wpi.capybara.objects.hibernate.NodeEntity;

public class AvoidStairsSkip implements PathfindingSkip {

  public AvoidStairsSkip() {}

  @Override
  public boolean shouldSkip(NodeEntity start, NodeEntity end) {
    return start.getLocationType().equals("STAI")
        && end.getLocationType().equals("STAI")
        && !end.getFloor().equals(start.getFloor());
  }
}
