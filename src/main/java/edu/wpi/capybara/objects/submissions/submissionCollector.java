package edu.wpi.capybara.objects.submissions;

import java.util.LinkedList;

public class submissionCollector {
  LinkedList<transportationSubmission> transportationSubs;

  LinkedList<cleaningSubmission> allCleaningSubmissions;

  public void newCleaningSub(cleaningSubmission cleaningSub) {
    this.allCleaningSubmissions.add(cleaningSub);
  }

  public submissionCollector() {
    transportationSubs = new LinkedList<transportationSubmission>();
    allCleaningSubmissions = new LinkedList<cleaningSubmission>();
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
    for (cleaningSubmission allSubs : allCleaningSubmissions) {
      dataAdded =
          dataAdded
              + "\r\n"
              + allSubs.getMemberID()
              + ","
              + allSubs.getlocation()
              + ","
              + allSubs.getHazardLevel()
              + ","
              + allSubs.getDescription();
    }
    return (tableHeaders + dataAdded);
  }
}
