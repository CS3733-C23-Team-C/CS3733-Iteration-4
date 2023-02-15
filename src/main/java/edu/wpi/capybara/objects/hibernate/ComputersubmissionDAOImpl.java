package edu.wpi.capybara.objects.hibernate;

import java.util.HashMap;

public class ComputersubmissionDAOImpl implements ComputersubmissionDAO {
  HashMap<Integer, ComputersubmissionEntity> computerSubs = new HashMap();

  @Override
  public HashMap<Integer, ComputersubmissionEntity> getComputerSubs() {
    return computerSubs;
  }

  @Override
  public ComputersubmissionEntity getComputer(int id) {
    return computerSubs.get(id);
  }

  @Override
  public void addComputer(ComputersubmissionEntity submission) {
    newDBConnect.insertNew(submission);
    this.computerSubs.put(submission.getSubmissionid(), submission);
  }

  @Override
  public void deleteComputer(int id) {
    newDBConnect.delete(getComputer(id));
    computerSubs.remove(id);
  }

  public ComputersubmissionDAOImpl(HashMap<Integer, ComputersubmissionEntity> computerSubs) {
    this.computerSubs = computerSubs;
  }
}
