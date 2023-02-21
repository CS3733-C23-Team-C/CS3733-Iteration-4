package edu.wpi.capybara.objects;

import edu.wpi.capybara.objects.hibernate.NodeEntity;
import java.sql.Date;

public class PFNode implements PFPlace {
  private final NodeEntity node;

  public PFNode(NodeEntity node) {
    this.node = node;
  }

  public String getLongname() {
    return node.getNodeID();
  }

  public String getShortname() {
    return node.getNodeID();
  }

  @Override
  public NodeEntity getNode(Date date) {
    return node;
  }

  @Override
  public String getLocationtype() {
    return "NA";
  }

  @Override
  public boolean hasRecentNode(Date date) {
    return false;
  }

  public String toString() {
    return node.getNodeID();
  }
}
