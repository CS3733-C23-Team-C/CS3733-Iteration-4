package edu.wpi.capybara.objects.submissions;

import java.util.LinkedList;

public class submissionCollector {
  LinkedList<transportationSubmission> transportationSubs;

  public submissionCollector() {
    transportationSubs = new LinkedList<transportationSubmission>();
  }

  public void newTransportationSubmission(transportationSubmission submission) {
    this.transportationSubs.add(submission);
  }

  public String getTransportationData() {
    String tableHeaders = "Employee ID,Current Room,Destination Room";
    String dataAdded = "";
    for (transportationSubmission data : transportationSubs) {
      dataAdded =
          dataAdded + "\r\n" + data.getID() + "," + data.getCurrRoom() + "," + data.getDestRoom();
    }
    return (tableHeaders + dataAdded);
  }
}
