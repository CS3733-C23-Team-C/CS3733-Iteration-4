package edu.wpi.capybara.objects.submissions;

public class securitySubmission {

  private String employeeID;

  private String room;

  private String floor;

  private String notesUpdate;

  public securitySubmission(
      String outputnotes, String outputeID, String outputfloorNumber, String outputroomNumber1) {}

  public String getID() {
    return this.employeeID;
  }

  public String getRoom() {

    return this.room;
  }

  public String getFloor() {
    return this.floor;
  }

  public String getNotes() {
    return this.notesUpdate;
  }
}
