package edu.wpi.cs3733.C23.teamC.database.hibernate;

import java.util.HashMap;

public interface NodeDAO {

  HashMap<String, NodeEntity> getNodes();

  NodeEntity getNode(String nodeid);

  void addNode(NodeEntity submission);

  void deleteNode(String nodeid);
}
