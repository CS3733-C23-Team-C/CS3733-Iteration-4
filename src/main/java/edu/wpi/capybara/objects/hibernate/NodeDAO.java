package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.objects.orm.Node;

import java.util.HashMap;

public interface NodeDAO {

  HashMap<String, Node> getNodes();

  Node getNode(String nodeid);

  void addNode(Node submission);

  void deleteNode(String nodeid);
}
