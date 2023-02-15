package edu.wpi.capybara.objects.hibernate;

import java.util.HashMap;

public class NodeDAOImpl implements NodeDAO {
  HashMap<String, NodeEntity> nodes = new HashMap();

  @Override
  public HashMap<String, NodeEntity> getNodes() {
    return nodes;
  }

  @Override
  public NodeEntity getNode(String nodeid) {
    return nodes.get(nodeid);
  }

  @Override
  public void addNode(NodeEntity submission) {
    // Main.db.addNode(submission);
    newDBConnect.insertNew(submission);
    this.nodes.put(submission.getNodeid(), submission);
  }

  public NodeDAOImpl(HashMap<String, NodeEntity> nodes) {
    this.nodes = nodes;
  }

  @Override
  public void deleteNode(String id) {
    newDBConnect.delete(getNode(id));
    nodes.remove(id);
  }
}
