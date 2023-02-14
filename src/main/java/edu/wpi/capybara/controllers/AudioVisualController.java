package edu.wpi.capybara.controllers;

import edu.wpi.capybara.objects.submissions.AudioVisualSubmitter;

public class AudioVisualController extends ServiceRequestAbs {

  @Override
  public void setRequestSpecific() {
    submission = new AudioVisualSubmitter();
    requestSpecific.getItems().addAll("Set up", "Removal", "Repair");
  }
}
