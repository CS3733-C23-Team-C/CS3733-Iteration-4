package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.objects.orm.TransportationSubmission;

import java.util.HashMap;

public interface TransportationDAO {

  HashMap<Integer, TransportationSubmission> getTransportationSubs();

  TransportationSubmission getTransportation(int id);

  void addTransportation(TransportationSubmission submission);

  void deleteTransportation(int id);
}
