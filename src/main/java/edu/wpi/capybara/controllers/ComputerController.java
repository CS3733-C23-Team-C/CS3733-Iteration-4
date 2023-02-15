package edu.wpi.capybara.controllers;

import edu.wpi.capybara.objects.submissions.ComputerSubmitter;

public class ComputerController extends ServiceRequestAbs {

  @Override
  public void setRequestSpecific() {
    requestSpecific.getItems().addAll("Hardware", "Software", "Both", "Unknown");
    submission = new ComputerSubmitter();
  }
}
