package edu.wpi.capybara.objects.submissions;

import java.util.LinkedList;

public class submissionCollector {
  LinkedList<transportationSubmission> transportationSubs;

  LinkedList<cleaningSubmission> allCleaningSubmissions;

  public void addCleaningSub(cleaningSubmission cleaningSub) {
    allCleaningSubmissions = new LinkedList<cleaningSubmission>();
  }

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

  public String getCleaningData() {
    String tableHeaders = "Employee ID, Location, Hazard Level, Description";
    String dataAdded = "";
    for (cleaningSubmission data : allCleaningSubmissions) {
      dataAdded =
          dataAdded
              + "\r\n"
              + data.getMemberID()
              + ","
              + data.getlocation()
              + ","
              + data.getHazardLevel()
              + ","
              + data.getDescription();
    }
    return (tableHeaders + dataAdded);
  }
}
