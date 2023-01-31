package edu.wpi.capybara.objects.submissions;

import lombok.Getter;

public class cleaningSubmission {

  @Getter public String memberID;
  @Getter public String location;
  @Getter public String hazardLevel;
  @Getter public String description;
  @Getter public submissionStatus status;

  public cleaningSubmission(
      String memberID, String location, String hazardLevel, String description) {
    this.memberID = memberID;
    this.location = location;
    this.hazardLevel = hazardLevel;
    this.description = description;
    this.status = submissionStatus.BLANK;
  }

<<<<<<< HEAD
=======
  public enum submissionStatus {
    BLANK,
    PROCESSING,
    DONE
  }
>>>>>>> main

  public String getMemberID() {
    return this.memberID;
  }

  public String getlocation() {
    return this.location;
  }

  public String getHazardLevel() {
    return this.hazardLevel;
  }

  public String getDescription() {
    return this.description;
  }
}
