package edu.wpi.cs3733.C23.teamC.pathfinding.skips;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.NodeEntity;

public interface PathfindingSkip {
  boolean shouldSkip(NodeEntity start, NodeEntity end);
}
