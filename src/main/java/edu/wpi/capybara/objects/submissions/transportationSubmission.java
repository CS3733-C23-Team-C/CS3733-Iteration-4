package edu.wpi.capybara.objects.submissions;

import edu.wpi.capybara.App;
import edu.wpi.capybara.objects.staff;

public class transportationSubmission {
  private String staffID;
  private String currRoomNum;
  private String destRoomNum;
  private String reason;

  private submissionStatus currStatus;

  public transportationSubmission(String currRoom, String destRoom, String reason) {
    staff staff = App.getUser();
    if (staff != null) this.staffID = App.getUser().getStaffID();
    this.currRoomNum = currRoom;
    this.destRoomNum = destRoom;
    this.reason = reason;
    this.currStatus = submissionStatus.BLANK;
  }

  public transportationSubmission(
      String currRoom, String destRoom, String reason, submissionStatus status) {
    staff staff = App.getUser();
    if (staff != null) this.staffID = App.getUser().getStaffID();

    this.currRoomNum = currRoom;
    this.destRoomNum = destRoom;
    this.reason = reason;
    this.currStatus = status;
  }

  public String getCurrRoom() {
    return this.currRoomNum;
  }

  public String getDestRoom() {
    return this.destRoomNum;
  }

  public String getReason() {
    return this.reason;
  }

  public submissionStatus getStatus() { // returns status of submission
    return this.currStatus;
  }

  public String getRealStatus() {
    return currStatus.toString();
  }

  public void updateStatus(
      submissionStatus
          newStatus) { // when submission is updated in database call this to update state
    this.currStatus = newStatus;
  }
}
