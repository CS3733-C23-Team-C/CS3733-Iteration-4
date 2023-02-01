package edu.wpi.capybara.objects.submissions;

public class transportationSubmission {
  private String employeeID;
  private String currRoomNum;
  private String destRoomNum;
  private String reason;

  private submissionStatus currStatus;

  public enum submissionStatus {
    BLANK,
    PROCESSING,
    DONE
  }

  public transportationSubmission(String id, String currRoom, String destRoom, String reason) {
    this.employeeID = id;
    this.currRoomNum = currRoom;
    this.destRoomNum = destRoom;
    this.reason = reason;
    this.currStatus = submissionStatus.BLANK;
  }

  public transportationSubmission(
      String id, String currRoom, String destRoom, String reason, submissionStatus status) {
    this.employeeID = id;
    this.currRoomNum = currRoom;
    this.destRoomNum = destRoom;
    this.reason = reason;
    this.currStatus = status;
  }

  public String getID() {
    return this.employeeID;
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

  public String getStatus() { // returns status of submission
    String retVal = "";
    switch (currStatus) {
      case BLANK:
        retVal = "Submission Incomplete";
        break;
      case PROCESSING:
        retVal = "Processing";
        break;
      case DONE:
        retVal = "Done";
        break;
    }
    return retVal;
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
