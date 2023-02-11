package edu.wpi.capybara.objects.submissions;

import java.time.LocalDate;

public interface ISubmission {
  public void newSubmission(
      String currStaffID,
      String outputAssignedStaffID,
      String outputLocation,
      String outputRequestSpecific,
      String outputELevel,
      LocalDate outputDate,
      String outputNotes);
}
