package edu.wpi.capybara.pathfinding;

import edu.wpi.capybara.objects.orm.Node;

import java.util.List;

public interface PathfindingAlgorithm {

  List<Node> findPath(Node start, Node end);

  List<Node> findPath(String start, String end);
}
