package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.objects.orm.CleaningsubmissionEntity;

import java.util.HashMap;

public interface CleaningsubmissionDAO {

  HashMap<Integer, CleaningsubmissionEntity> getCleaningSubs();

  CleaningsubmissionEntity getCleaning(int id);

  void addCleaning(CleaningsubmissionEntity submission);

  void deleteCleaning(int id);
}
