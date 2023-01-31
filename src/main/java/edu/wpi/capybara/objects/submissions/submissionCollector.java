package edu.wpi.capybara.objects.submissions;

import java.util.LinkedList;

public class submissionCollector { // stores all of the submissions in different collections
  LinkedList<transportationSubmission> transportationSubs;

  public submissionCollector() {
    transportationSubs = new LinkedList<transportationSubmission>();
  }

  public void newTransportationSubmission(transportationSubmission submission) {
    this.transportationSubs.add(submission);
  }

  public String
      getTransportationData() { // creates a sort CSV file of all the data in the collection of
    // transportation submissions
    String tableHeaders = "Employee ID,Current Room,Destination Room,Reason,Status";
    String dataAdded = "";
    for (transportationSubmission data : transportationSubs) {
      dataAdded =
          dataAdded
              + "\r\n"
              + data.getID()
              + ","
              + data.getCurrRoom()
              + ","
              + data.getDestRoom()
              + ","
              + data.getReason()
              + ","
              + data.getStatus();
    }
    return (tableHeaders + dataAdded);
  }
}
