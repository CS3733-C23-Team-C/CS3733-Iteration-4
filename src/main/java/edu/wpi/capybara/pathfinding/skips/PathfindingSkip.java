package edu.wpi.capybara.pathfinding.skips;

import edu.wpi.capybara.objects.hibernate.NodeEntity;

public interface PathfindingSkip {
  boolean shouldSkip(NodeEntity start, NodeEntity end);
}
