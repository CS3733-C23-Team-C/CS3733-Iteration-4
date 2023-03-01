package edu.wpi.cs3733.C23.teamC.ServiceRequests;

import edu.wpi.cs3733.C23.teamC.ServiceRequests.submissions.SecuritySubmitter;

public class SecurityController extends ServiceRequestAbs {

  public void setRequestSpecific() {
    submission = new SecuritySubmitter();
    requestSpecific.getItems().addAll("Police Department", "Fire Department", "Health Department");
  }
}
