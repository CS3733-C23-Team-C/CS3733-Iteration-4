package edu.wpi.capybara.controllers;

import edu.wpi.capybara.objects.submissions.CleaningSubmitter;

public class CleaningController extends ServiceRequestAbs {

  @Override
  public void setRequestSpecific() {
    submission = new CleaningSubmitter();
    requestSpecific.getItems().addAll("Low", "Medium", "High", "Extreme");
  }
}
