package edu.wpi.capybara.controllers;

import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.objects.NodeAlphabetComparator;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class CleaningController {

  @FXML private MFXTextField employeeID;
  @FXML private MFXFilterComboBox<String> Location;
  @FXML private MFXComboBox<String> hazardLevel;
  @FXML private MFXComboBox<String> emergencyLevel;
  @FXML private MFXDatePicker date;
  @FXML private MFXTextField notes;
  @FXML private MFXButton clearButton;
  @FXML private MFXButton submitButton;

  //  public CleaningRequestController forRequests;

  /** enumeration for status of request */
  //  private submissionStatus currentStatus;

  @FXML
  public void initialize() {
    System.out.println("I am from cleaningController");
    //    currentStatus = submissionStatus.BLANK;

    // Add different locations

    TreeMap<String, NodeEntity> nodes = DatabaseConnect.getNodes();

    SortedSet<NodeEntity> sortedset = new TreeSet<NodeEntity>(new NodeAlphabetComparator());

    sortedset.addAll(nodes.values());

    Iterator<NodeEntity> iterator = sortedset.iterator();
    while (iterator.hasNext()) {
      NodeEntity n = iterator.next();
      // System.out.println(n.getShortName());
      Location.getItems().add(n.getShortName());
    }

    // Set a default variable
    Location.getSelectionModel().selectFirst();

    emergencyLevel.getItems().addAll("Low", "Medium", "High", "Extreme");
    emergencyLevel.getSelectionModel().selectFirst();

    hazardLevel.getItems().addAll("Low", "Medium", "High", "Extreme");
    hazardLevel.getSelectionModel().selectFirst();
  }

  /**
   * submit that creates a cleaning request object and then displays all of the previously created
   * request
   *
   * @param actionEvent
   */
  public void submit(ActionEvent actionEvent) {

    String outputID = employeeID.getText();
    String outputLocation = "" + Location.getValue();
    String outputHazard = "" + hazardLevel.getValue();
    String outputEmergency = "" + emergencyLevel.getValue();
    String outputDate = "" + date.getText();
    String outputNotes = notes.getText();

    // Change to accommodate database and storage system
    //    CleaningsubmissionEntity addSubmission =
    //        new CleaningsubmissionEntity(
    //            App.getUser().getStaffid(),
    //            outputLocation,
    //            outputHazard,
    //            outputNotes,
    //            submissionStatus.BLANK);

    //    App.getTotalSubmissions().newCleaningSub(addSubmission);
    //    System.out.println(App.getTotalSubmissions().getCleaningData());

    // java.util.Date date = new java.util.Date();
    // String locationInfo = "" + Location.getValue();
    // String descriptionInfo = Description.getText();
    // String hazardLevelInfo = hazardLevel.getText();
    // CleaningsubmissionEntity addSubmission =
    // new CleaningsubmissionEntity(
    // App.getUser().getStaffid(),
    // locationInfo,
    // hazardLevelInfo,
    // descriptionInfo,
    // submissionStatus.BLANK,
    // null,
    // (int) (Math.random() * 100000),
    // Urgency.BLANK,
    // new java.sql.Date(date.getTime()),
    // new java.sql.Date(date.getTime() + 86400000));

    // App.getTotalSubmissions().newCleaningSub(addSubmission);
    // System.out.println(App.getTotalSubmissions().getCleaningData());

    clearRequest();
  }

  /** clears all areas on the submission form */
  public void clearRequest() {
    employeeID.clear();
    Location.getSelectionModel().selectFirst();
    hazardLevel.getSelectionModel().selectFirst();
    emergencyLevel.getSelectionModel().selectFirst();
    date.clear();
    notes.clear();
    //    currentStatus = submissionStatus.BLANK;
    submitButton.setDisable(true);
  }

  public void validateButton() {
    if (employeeID.getText() != "" && date.getText() != "" && notes.getText() != "")
      submitButton.setDisable(false);
  }
}
