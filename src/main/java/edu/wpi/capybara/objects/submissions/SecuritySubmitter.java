package edu.wpi.capybara.objects.submissions;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.objects.hibernate.SecuritysubmissionEntity;
import java.sql.Date;

public class SecuritySubmitter implements ISubmission {

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
    SecuritysubmissionEntity sub =
        new SecuritysubmissionEntity(
            currStaffID,
            location,
            requestSpecific,
            outputNotes,
            status,
            assignedID,
            submissionID,
            emergencyLevel,
            createdDate,
            dueDate);
    Main.db.addSecurity(sub);
  }
}
