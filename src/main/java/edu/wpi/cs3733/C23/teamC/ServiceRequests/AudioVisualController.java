package edu.wpi.cs3733.C23.teamC.ServiceRequests;

import edu.wpi.cs3733.C23.teamC.ServiceRequests.submissions.AudioVisualSubmitter;

public class AudioVisualController extends ServiceRequestAbs {

  @Override
  public void setRequestSpecific() {
    submission = new AudioVisualSubmitter();
    requestSpecific.getItems().addAll("Set up", "Removal", "Repair");
  }
}
