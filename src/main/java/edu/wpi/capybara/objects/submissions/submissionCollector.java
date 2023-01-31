package edu.wpi.capybara.objects.submissions;

import java.util.LinkedList;

public class submissionCollector {
  LinkedList<transportationSubmission> transportationSubs;

  LinkedList<cleaningSubmission> allCleaningSubmissions;

  /**adds a cleaning submission to the linked list
   *
   * @param cleaningSub
   */
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

  /**used to present all of the request to administor
   *
   * @return String
   */
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
