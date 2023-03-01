package edu.wpi.cs3733.C23.teamC.ServiceRequests;

import edu.wpi.cs3733.C23.teamC.ServiceRequests.submissions.CleaningSubmitter;

public class CleaningController extends ServiceRequestAbs {

  @Override
  public void setRequestSpecific() {
    submission = new CleaningSubmitter();
    requestSpecific.getItems().addAll("Low", "Medium", "High", "Extreme");
  }
}
