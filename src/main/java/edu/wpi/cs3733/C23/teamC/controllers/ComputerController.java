package edu.wpi.cs3733.C23.teamC.controllers;

import edu.wpi.cs3733.C23.teamC.objects.submissions.ComputerSubmitter;

public class ComputerController extends ServiceRequestAbs {

  @Override
  public void setRequestSpecific() {
    requestSpecific.getItems().addAll("Hardware", "Software", "Both", "Unknown");
    submission = new ComputerSubmitter();
  }
}
