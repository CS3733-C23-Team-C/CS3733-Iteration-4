package edu.wpi.capybara.objects.submissions;

public class securitySubmission {

  private String employeeID;

  private String location;

  private String type;

  private String notesUpdate;

  public securitySubmission(
      String outputNotes, String outputID, String outputLocation, String outputType) {}

  public String getID() {
    return this.employeeID;
  }

  public String getLocation() {
    return this.location;
  }

  public String getType() {
    return this.type;
  }

  public String getNotes() {
    return this.notesUpdate;
  }
}
