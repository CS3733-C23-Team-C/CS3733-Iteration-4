package edu.wpi.capybara.pathfinding.skips;

import edu.wpi.capybara.objects.hibernate.NodeEntity;

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
