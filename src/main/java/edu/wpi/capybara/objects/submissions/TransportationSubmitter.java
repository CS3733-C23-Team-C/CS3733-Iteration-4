package edu.wpi.capybara.objects.submissions;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.objects.hibernate.TransportationsubmissionEntity;
import java.sql.Date;

public class TransportationSubmitter implements ISubmission {

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
    System.out.println(submissionID + "submitter");
    TransportationsubmissionEntity sub =
        new TransportationsubmissionEntity(
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
    Main.db.addTransportation(sub);
  }
}
