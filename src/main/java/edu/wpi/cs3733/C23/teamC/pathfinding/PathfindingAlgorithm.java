package edu.wpi.cs3733.C23.teamC.pathfinding;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.NodeEntity;
import java.util.List;

public interface PathfindingAlgorithm {

  List<NodeEntity> findPath(NodeEntity start, NodeEntity end);

  List<NodeEntity> findPath(String start, String end);
}
