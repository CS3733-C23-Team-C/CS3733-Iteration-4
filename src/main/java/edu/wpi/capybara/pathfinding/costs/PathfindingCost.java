package edu.wpi.capybara.pathfinding.costs;

import edu.wpi.capybara.objects.hibernate.NodeEntity;

public interface PathfindingCost {
  boolean doesUseCost(NodeEntity start, NodeEntity end);

  double calculateCost(NodeEntity start, NodeEntity end);
}
