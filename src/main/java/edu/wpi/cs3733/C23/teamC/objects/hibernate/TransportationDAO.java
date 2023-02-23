package edu.wpi.cs3733.C23.teamC.objects.hibernate;

import java.util.HashMap;

public interface TransportationDAO {

  HashMap<Integer, TransportationsubmissionEntity> getTransportationSubs();

  TransportationsubmissionEntity getTransportation(int id);

  void addTransportation(TransportationsubmissionEntity submission);

  void deleteTransportation(int id);
}
