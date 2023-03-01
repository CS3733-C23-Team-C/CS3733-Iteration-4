package edu.wpi.cs3733.C23.teamC.Pathfinding;

import edu.wpi.cs3733.C23.teamC.database.hibernate.NodeEntity;
import java.util.Comparator;

public class NodeAlphabetComparator implements Comparator<NodeEntity> {
  @Override
  public int compare(NodeEntity n1, NodeEntity n2) {
    return n1.getShortName().compareTo(n2.getShortName());
  }
}
