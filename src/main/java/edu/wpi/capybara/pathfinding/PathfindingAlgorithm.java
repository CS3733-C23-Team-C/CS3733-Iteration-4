package edu.wpi.capybara.pathfinding;

import edu.wpi.capybara.objects.hibernate.NodeEntity;
import java.util.List;

public interface PathfindingAlgorithm {

  List<NodeEntity> findPath(NodeEntity start, NodeEntity end);

  List<NodeEntity> findPath(String start, String end);
}
