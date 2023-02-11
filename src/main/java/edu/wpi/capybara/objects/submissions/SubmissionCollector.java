package edu.wpi.capybara.objects.submissions;

import edu.wpi.capybara.App;
import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.objects.hibernate.*;
import java.util.LinkedList;

public class SubmissionCollector { // stores all of the submissions in different collections
  LinkedList<TransportationsubmissionEntity> transportationSubs;

  LinkedList<CleaningsubmissionEntity> allCleaningSubmissions;

  LinkedList<SecuritysubmissionEntity> securitySubs;

  LinkedList<AudiosubmissionEntity> audioSubs;

  LinkedList<ComputersubmissionEntity> computerSubs;

  public LinkedList<TransportationsubmissionEntity> getTransportationSubs() {
    return transportationSubs;
  }

  public LinkedList<CleaningsubmissionEntity> getCleaningSubmissions() {
    return allCleaningSubmissions;
  }

  public LinkedList<SecuritysubmissionEntity> getSecuritySubs() {
    return securitySubs;
  }

  public LinkedList<AudiosubmissionEntity> getAudioSubs() {
    return audioSubs;
  }

  public LinkedList<ComputersubmissionEntity> getComputerSubs() {
    return computerSubs;
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
        cleaningSub.getSubmissionstatus(),
        cleaningSub.getAssignedid(),
        cleaningSub.getSubmissionid(),
        cleaningSub.getUrgency(),
        cleaningSub.getCreatedate(),
        cleaningSub.getDuedate());
    this.allCleaningSubmissions.add(cleaningSub);
  }

  public SubmissionCollector() {
    transportationSubs = DatabaseConnect.transports();
    allCleaningSubmissions = DatabaseConnect.cleanings();
    securitySubs = DatabaseConnect.security();
  }

  public void newTransportationSubmission(TransportationsubmissionEntity submission) {
    DatabaseConnect.insertTransportation(
        App.getUser().getStaffid(),
        submission.getCurrroomnum(),
        submission.getDestroomnum(),
        submission.getReason(),
        submission.getStatus(),
        submission.getAssignedid(),
        submission.getSubmissionid(),
        submission.getUrgency(),
        submission.getCreatedate(),
        submission.getDuedate());
    this.transportationSubs.add(submission);
  }

  public void newComputerSubmission(ComputersubmissionEntity submission) {
    DatabaseConnect.insertSecurity(
        App.getUser().getStaffid(),
        submission.getLocation(),
        submission.getType(),
        submission.getNotesupdate(),
        submission.getSubmissionstatus(),
        submission.getAssignedid(),
        submission.getSubmissionid(),
        submission.getUrgency(),
        submission.getCreatedate(),
        submission.getDuedate());
    this.computerSubs.add(submission);
  }

  public void newAudioSubmission(AudiosubmissionEntity submission) {
    DatabaseConnect.insertSecurity(
        App.getUser().getStaffid(),
        submission.getLocation(),
        submission.getType(),
        submission.getNotesupdate(),
        submission.getSubmissionstatus(),
        submission.getAssignedid(),
        submission.getSubmissionid(),
        submission.getUrgency(),
        submission.getCreatedate(),
        submission.getDuedate());
    this.audioSubs.add(submission);
  }

  public void newSecuritySubmission(SecuritysubmissionEntity submission) {
    DatabaseConnect.insertSecurity(
        App.getUser().getStaffid(),
        submission.getLocation(),
        submission.getType(),
        submission.getNotesupdate(),
        submission.getSubmissionstatus(),
        submission.getAssignedid(),
        submission.getSubmissionid(),
        submission.getUrgency(),
        submission.getCreatedate(),
        submission.getDuedate());
    this.securitySubs.add(submission);
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
