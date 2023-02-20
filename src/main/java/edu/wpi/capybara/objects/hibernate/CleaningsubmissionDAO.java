package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.objects.orm.CleaningSubmission;

import java.util.HashMap;

public interface CleaningsubmissionDAO {

  HashMap<Integer, CleaningSubmission> getCleaningSubs();

  CleaningSubmission getCleaning(int id);

  void addCleaning(CleaningSubmission submission);

  void deleteCleaning(int id);
}
