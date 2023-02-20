package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.objects.orm.ComputerSubmission;

import java.util.HashMap;

public interface ComputersubmissionDAO {

  HashMap<Integer, ComputerSubmission> getComputerSubs();

  ComputerSubmission getComputer(int id);

  void addComputer(ComputerSubmission submission);

  void deleteComputer(int id);
}
