package edu.wpi.capybara.objects;

import edu.wpi.capybara.objects.orm.NodeEntity;
import java.util.Comparator;

public class NodeAlphabetComparator implements Comparator<NodeEntity> {
  @Override
  public int compare(NodeEntity n1, NodeEntity n2) {
    return n1.getShortName().compareTo(n2.getShortName());
  }
}
