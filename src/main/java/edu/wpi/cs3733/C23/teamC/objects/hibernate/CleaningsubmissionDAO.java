package edu.wpi.cs3733.C23.teamC.objects.hibernate;

import java.util.HashMap;

public interface CleaningsubmissionDAO {

  HashMap<Integer, CleaningsubmissionEntity> getCleaningSubs();

  CleaningsubmissionEntity getCleaning(int id);

  void addCleaning(CleaningsubmissionEntity submission);

  void deleteCleaning(int id);
}
