package edu.wpi.cs3733.C23.teamC.Pathfinding.skips;

import edu.wpi.cs3733.C23.teamC.database.hibernate.NodeEntity;

public interface PathfindingSkip {
  boolean shouldSkip(NodeEntity start, NodeEntity end);
}
