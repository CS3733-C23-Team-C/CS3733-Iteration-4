package edu.wpi.cs3733.C23.teamC.pathfinding.costs;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.NodeEntity;

public interface PathfindingCost {
  boolean doesUseCost(NodeEntity start, NodeEntity end);

  double calculateCost(NodeEntity start, NodeEntity end);
}
