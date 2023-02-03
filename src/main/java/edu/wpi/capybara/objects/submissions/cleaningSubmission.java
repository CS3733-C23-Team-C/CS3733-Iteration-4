package edu.wpi.capybara.objects.submissions;

import lombok.Getter;

public class cleaningSubmission {

  @Getter private String location;
  @Getter private String hazardLevel;
  @Getter private String description;
  @Getter private submissionStatus status;

  public cleaningSubmission(String location, String hazardLevel, String description) {
    this.location = location;
    this.hazardLevel = hazardLevel;
    this.description = description;
    this.status = submissionStatus.BLANK;
  }

  public cleaningSubmission(
      String location, String hazardLevel, String description, submissionStatus status) {
    this.location = location;
    this.hazardLevel = hazardLevel;
    this.description = description;
    this.status = status;
  }

  public String getLocation() {
    return this.location;
  }

  public String getHazardLevel() {
    return this.hazardLevel;
  }

  public String getDescription() {
    return this.description;
  }
}
