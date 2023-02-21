package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.objects.orm.TransportationsubmissionEntity;

import java.util.HashMap;

public interface TransportationDAO {

  HashMap<Integer, TransportationsubmissionEntity> getTransportationSubs();

  TransportationsubmissionEntity getTransportation(int id);

  void addTransportation(TransportationsubmissionEntity submission);

  void deleteTransportation(int id);
}
