package edu.wpi.capybara.controllers;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.objects.NodeAlphabetComparator;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import edu.wpi.capybara.objects.submissions.TransportationSubmitter;
import java.util.*;

public class TransportationController extends ServiceRequestAbs {

  public void setRequestSpecific() {
    submission = new TransportationSubmitter();

    HashMap<String, NodeEntity> nodes = Main.db.getNodes();
    SortedSet<NodeEntity> sortedset = new TreeSet<NodeEntity>(new NodeAlphabetComparator());

    sortedset.addAll(nodes.values());

    Iterator<NodeEntity> iterator = sortedset.iterator();
    while (iterator.hasNext()) {
      NodeEntity n = iterator.next();
      // System.out.println(n.getShortName());
      requestSpecific.getItems().add(n.getShortName());
    }
  }
}
