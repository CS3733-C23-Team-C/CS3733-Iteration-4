package edu.wpi.capybara.objects.submissions;

import edu.wpi.capybara.App;
import edu.wpi.capybara.objects.staff;
import lombok.Getter;

public class cleaningSubmission {
  private String staffID;
  @Getter private String location;
  @Getter private String hazardLevel;
  @Getter private String description;
  @Getter private submissionStatus status;

  public cleaningSubmission(String location, String hazardLevel, String description) {
    staff staff = App.getUser();
    if (staff != null) this.staffID = App.getUser().getStaffID();
    this.location = location;
    this.hazardLevel = hazardLevel;
    this.description = description;
    this.status = submissionStatus.BLANK;
  }

  public cleaningSubmission(
      String location, String hazardLevel, String description, submissionStatus status) {
    staff staff = App.getUser();
    if (staff != null) this.staffID = App.getUser().getStaffID();

    this.location = location;
    this.hazardLevel = hazardLevel;
    this.description = description;
    this.status = status;
  }
}
