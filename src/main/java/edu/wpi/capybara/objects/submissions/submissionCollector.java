package edu.wpi.capybara.objects.submissions;

import edu.wpi.capybara.App;
import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.objects.hibernate.CleaningsubmissionEntity;
import edu.wpi.capybara.objects.hibernate.TransportationsubmissionEntity;
import java.util.LinkedList;

public class submissionCollector { // stores all of the submissions in different collections
  LinkedList<TransportationsubmissionEntity> transportationSubs;

  LinkedList<CleaningsubmissionEntity> allCleaningSubmissions;

  LinkedList<securitySubmission> securitySubs;

  public LinkedList<TransportationsubmissionEntity> getTransportationSubs() {
    return transportationSubs;
  }

  public LinkedList<CleaningsubmissionEntity> getCleaningSubmissions() {
    return allCleaningSubmissions;
  }

  public LinkedList<securitySubmission> getSecuritySubs() {
    return securitySubs;
  }

  /**
   * adds a cleaning submission to the linked list
   *
   * @param cleaningSub
   */
  public void newCleaningSub(CleaningsubmissionEntity cleaningSub) {
    DatabaseConnect.insertCleaning(
        App.getUser().getStaffid(),
        cleaningSub.getLocation(),
        cleaningSub.getHazardlevel(),
        cleaningSub.getDescription(),
        cleaningSub.getSubmissionstatus());
    this.allCleaningSubmissions.add(cleaningSub);
  }

  public submissionCollector() {
    transportationSubs = DatabaseConnect.transports();
    allCleaningSubmissions = DatabaseConnect.cleanings();
  }

  public void newTransportationSubmission(TransportationsubmissionEntity submission) {
    DatabaseConnect.insertTransportation(
        App.getUser().getStaffid(),
        submission.getCurrroomnum(),
        submission.getDestroomnum(),
        submission.getReason(),
        submission.getStatus());
    this.transportationSubs.add(submission);
  }

  /**
   * used to present all of the request to administor
   *
   * @return String
   */
  public String
      getTransportationData() { // creates a sort CSV file of all the data in the collection of
    // transportation submissions
    String tableHeaders = "Employee ID,Current Room,Destination Room,Reason,Status";
    String dataAdded = "";
    for (TransportationsubmissionEntity data : transportationSubs) {
      dataAdded =
          dataAdded
              + "\r\n"
              + App.getUser().getStaffid()
              + ","
              + data.getCurrroomnum()
              + ","
              + data.getDestroomnum()
              + ","
              + data.getReason()
              + ","
              + data.getStatus();
    }
    return (tableHeaders + dataAdded);
  }

  /**
   * used to present all of the request to administor
   *
   * @return String
   */
  public String getCleaningData() {
    String tableHeaders = "Employee ID, Location, Hazard Level, Description";
    String dataAdded = "";
    for (CleaningsubmissionEntity allSubs : allCleaningSubmissions) {
      dataAdded =
          dataAdded
              + "\r\n"
              + App.getUser().getStaffid()
              + ","
              + allSubs.getLocation()
              + ","
              + allSubs.getHazardlevel()
              + ","
              + allSubs.getDescription();
    }
    return (tableHeaders + dataAdded);
  }
}
