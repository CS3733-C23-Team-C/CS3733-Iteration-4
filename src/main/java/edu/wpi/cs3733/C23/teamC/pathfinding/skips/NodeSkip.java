package edu.wpi.cs3733.C23.teamC.pathfinding.skips;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.NodeEntity;

public class NodeSkip implements PathfindingSkip {
  private final NodeEntity nodeToSkip;

  public NodeSkip(NodeEntity n) {
    this.nodeToSkip = n;
  }

  @Override
  public boolean shouldSkip(NodeEntity start, NodeEntity end) {
    return nodeToSkip.equals(end);
  }
}
