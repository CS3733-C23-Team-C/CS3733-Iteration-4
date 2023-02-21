package edu.wpi.capybara.objects;

import edu.wpi.capybara.objects.orm.NodeEntity;
import edu.wpi.capybara.Main;
import edu.wpi.capybara.controllers.PathfindingController;
import edu.wpi.capybara.objects.orm.LocationnameEntity;
import edu.wpi.capybara.objects.orm.MoveEntity;
import edu.wpi.capybara.objects.orm.NodeEntity;
import java.sql.Date;
import java.util.*;

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
