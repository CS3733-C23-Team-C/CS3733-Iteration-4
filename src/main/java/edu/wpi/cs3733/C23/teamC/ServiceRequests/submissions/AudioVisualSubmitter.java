package edu.wpi.cs3733.C23.teamC.ServiceRequests.submissions;

import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.database.hibernate.AudiosubmissionEntity;
import java.sql.Date;

public class AudioVisualSubmitter implements ISubmission {
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
    AudiosubmissionEntity sub =
        new AudiosubmissionEntity(
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
    Main.db.addAudio(sub);
  }
}
