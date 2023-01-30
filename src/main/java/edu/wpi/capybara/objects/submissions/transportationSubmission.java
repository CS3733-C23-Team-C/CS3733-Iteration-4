package edu.wpi.capybara.objects.submissions;

public class transportationSubmission {
  private String employeeID;
  private String currRoomNum;
  private String destRoomNum;

  public transportationSubmission(String id, String currRoom, String destRoom) {
    this.employeeID = id;
    this.currRoomNum = currRoom;
    this.destRoomNum = destRoom;
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
}
