package edu.wpi.cs3733.C23.teamC.controllers;

import edu.wpi.cs3733.C23.teamC.objects.submissions.SecuritySubmitter;

public class SecurityController extends ServiceRequestAbs {

  public void setRequestSpecific() {
    submission = new SecuritySubmitter();
    requestSpecific.getItems().addAll("Police Department", "Fire Department", "Health Department");
  }
}
