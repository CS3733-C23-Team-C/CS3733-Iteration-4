package edu.wpi.capybara.controllers;

import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.objects.NodeAlphabetComparator;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import edu.wpi.capybara.objects.submissions.TransportationSubmitter;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class TransportationController extends ServiceRequestAbs {

  public void setRequestSpecific() {
    requestSpecific = (MFXFilterComboBox) requestSpecific;
    submission = new TransportationSubmitter();

    TreeMap<String, NodeEntity> nodes = DatabaseConnect.getNodes();
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
