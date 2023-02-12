package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.objects.NodeAlphabetComparator;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import edu.wpi.capybara.objects.hibernate.SecuritysubmissionEntity;
import edu.wpi.capybara.objects.submissions.submissionStatus;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.sql.Date;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javax.swing.*;

public class SecurityController {

  @FXML private MFXTextField employeeID;
  @FXML private MFXFilterComboBox<String> Location;
  @FXML private MFXComboBox<String> employeeType;
  @FXML private MFXComboBox<String> emergencyLevel;
  @FXML private MFXDatePicker date;
  @FXML private MFXTextField notes;
  @FXML private MFXButton clearButton;
  @FXML private MFXButton submitButton;

  public void clearFields() {
    employeeID.clear();
    Location.getSelectionModel().selectFirst();
    employeeType.getSelectionModel().selectFirst();
    emergencyLevel.getSelectionModel().selectFirst();
    notes.clear();
    date.clear();
    // currentStatus = submissionStatus.BLANK;
    submitButton.setDisable(true);
  }

  @FXML
  public void initialize() {
    System.out.println("I am from securityController");
    // currentStatus = submissionStatus.BLANK;

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

    employeeType.getItems().addAll("Police Department", "Fire Department", "Health Department");
    employeeType.getSelectionModel().selectFirst();
  }

  public void submit(ActionEvent actionEvent) {
    String outputID = employeeID.getText();
    String outputLocation = "" + Location.getValue();

    String outputType = "" + employeeType.getValue();
    String outputEmergency = "" + emergencyLevel.getValue();
    Date outputDate = Date.valueOf(date.getValue());
    String outputNotes = notes.getText();

    // Change to accommodate database and storage system
    //    SecuritysubmissionEntity addSubmission =
    //        new SecuritysubmissionEntity(
    //            App.getUser().getStaffid(),
    //            outputLocation,
    //            outputType,
    //            outputNotes,
    //            submissionStatus.BLANK);
    //    App.getTotalSubmissions().newSecuritySubmission(addSubmission);

    // String outputType = "" + Type.getValue();
    // String outputNotes = notesUpdate.getText();
    java.util.Date date = new java.util.Date();
    SecuritysubmissionEntity addSubmission =
        new SecuritysubmissionEntity(
            App.getUser().getStaffid(),
            outputLocation,
            outputType,
            outputNotes,
            submissionStatus.BLANK,
            outputID,
            (int) (Math.random() * 100000),
            outputEmergency,
            new java.sql.Date(date.getTime()),
            new java.sql.Date(date.getTime() + 86400000));
    App.getTotalSubmissions().newSecuritySubmission(addSubmission);

    // New from main use this with new fields
    // String outputType = "" + Type.getValue();
    // String outputNotes = notesUpdate.getText();
    // java.util.Date date = new java.util.Date();
    // SecuritysubmissionEntity addSubmission =
    // new SecuritysubmissionEntity(
    // App.getUser().getStaffid(),
    // outputLocation,
    // outputType,
    // outputNotes,
    // submissionStatus.BLANK,
    // null,
    // (int) (Math.random() * 100000),
    // "Blank",
    // new java.sql.Date(date.getTime()),
    // new java.sql.Date(date.getTime() + 86400000));
    // App.getTotalSubmissions().newSecuritySubmission(addSubmission);

    clearFields();
  }

  public void validateButton() {
    if (employeeID.getText() != "" && date.getText() != "" && notes.getText() != "")
      submitButton.setDisable(false);
  }
}
