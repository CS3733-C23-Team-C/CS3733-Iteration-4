package edu.wpi.cs3733.C23.teamC.ServiceRequests.submissions;

import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.database.hibernate.SecuritysubmissionEntity;
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
    Main.db.addSecurity(sub);
  }
}
