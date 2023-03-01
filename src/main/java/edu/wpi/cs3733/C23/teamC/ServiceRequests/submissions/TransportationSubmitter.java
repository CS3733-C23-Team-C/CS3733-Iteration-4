package edu.wpi.cs3733.C23.teamC.ServiceRequests.submissions;

import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.database.hibernate.TransportationsubmissionEntity;
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
    Main.db.addTransportation(sub);
  }
}
