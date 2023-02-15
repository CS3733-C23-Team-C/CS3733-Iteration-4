package edu.wpi.capybara.objects.submissions;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.objects.hibernate.CleaningsubmissionEntity;
import java.sql.Date;

public class CleaningSubmitter implements ISubmission {

  public void submitNewSubmission(
      String currStaffID,
      String location,
      String requestSpecific,
      SubmissionStatus status,
      String assignedID,
      int submissionID,
      String emergencyLevel,
      Date createdDate,
      Date dueDate,
      String outputNotes) {
    CleaningsubmissionEntity sub =
        new CleaningsubmissionEntity(
            submissionID,
            currStaffID,
            assignedID,
            location,
            requestSpecific,
            outputNotes,
            status,
            emergencyLevel,
            createdDate,
            dueDate);
    Main.db.addCleaning(sub);
  }
}
