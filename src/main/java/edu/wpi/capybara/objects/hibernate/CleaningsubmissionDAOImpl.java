package edu.wpi.capybara.objects.hibernate;

import java.util.HashMap;

public class CleaningsubmissionDAOImpl implements CleaningsubmissionDAO {
  HashMap<Integer, CleaningsubmissionEntity> cleaningSubs = new HashMap();

  @Override
  public HashMap<Integer, CleaningsubmissionEntity> getCleaningSubs() {
    return cleaningSubs;
  }

  @Override
  public CleaningsubmissionEntity getCleaning(int id) {
    return cleaningSubs.get(id);
  }

  @Override
  public void addCleaning(CleaningsubmissionEntity submission) {
    newDBConnect.insertNew(submission);
    this.cleaningSubs.put(submission.getSubmissionid(), submission);
  }

  @Override
  public void deleteCleaning(int id) {
    cleaningSubs.remove(id);
    newDBConnect.delete(getCleaning(id));
  }

  public CleaningsubmissionDAOImpl(HashMap<Integer, CleaningsubmissionEntity> cleaningSubs) {
    this.cleaningSubs = cleaningSubs;
  }
}
