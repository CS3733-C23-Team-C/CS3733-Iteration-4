package edu.wpi.capybara.objects;

import java.util.Comparator;

public class NodeAlphabetComparator implements Comparator<Node> {
  @Override
  public int compare(Node n1, Node n2) {
    return n1.getShortName().compareTo(n2.getShortName());
  }
}
