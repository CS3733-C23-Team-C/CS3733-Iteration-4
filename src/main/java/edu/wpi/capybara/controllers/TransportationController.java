package edu.wpi.capybara.controllers;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.objects.NodeAlphabetComparator;
import edu.wpi.capybara.objects.orm.NodeEntity;
import edu.wpi.capybara.objects.submissions.TransportationSubmitter;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.util.*;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class TransportationController extends ServiceRequestAbs {

  public void setRequestSpecific() {
    requestSpecific = (MFXFilterComboBox) requestSpecific;
    submission = new TransportationSubmitter();

    Map<String, NodeEntity> nodes = Main.db.getNodes();
    SortedSet<NodeEntity> sortedset = new TreeSet<NodeEntity>(new NodeAlphabetComparator());

    sortedset.addAll(nodes.values());

    Iterator<NodeEntity> iterator = sortedset.iterator();
    while (iterator.hasNext()) {
      NodeEntity n = iterator.next();
      // System.out.println(n.getShortName());
      requestSpecific.getItems().add(n.getLongName());
    }
  }
}
