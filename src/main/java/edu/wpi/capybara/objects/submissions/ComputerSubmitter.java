package edu.wpi.capybara.objects.submissions;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.objects.hibernate.ComputersubmissionEntity;
import edu.wpi.capybara.objects.orm.ComputerSubmission;

import java.sql.Date;

public class ComputerSubmitter implements ISubmission {

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
    ComputerSubmission sub =
        new ComputersubmissionEntity(
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
    Main.db.addComputer(sub);
  }
}
