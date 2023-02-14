package edu.wpi.capybara.objects.hibernate;

import java.util.HashMap;

public interface NodeDAO {

  HashMap<String, NodeEntity> getNodes();

  NodeEntity getNode(String nodeid);

  void addNode(NodeEntity submission);

  void deleteNode(String nodeid);
}
