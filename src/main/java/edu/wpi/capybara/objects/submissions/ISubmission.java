package edu.wpi.capybara.objects.submissions;

import java.sql.Date;
import java.time.LocalDate;

public interface ISubmission {


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
      String outputNotes);
}
