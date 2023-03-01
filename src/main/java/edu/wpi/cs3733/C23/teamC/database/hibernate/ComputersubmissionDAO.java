package edu.wpi.cs3733.C23.teamC.database.hibernate;

import java.util.HashMap;

public interface ComputersubmissionDAO {

  HashMap<Integer, ComputersubmissionEntity> getComputerSubs();

  ComputersubmissionEntity getComputer(int id);

  void addComputer(ComputersubmissionEntity submission);

  void deleteComputer(int id);
}
