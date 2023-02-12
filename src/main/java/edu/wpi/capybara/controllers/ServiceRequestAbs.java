package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.objects.NodeAlphabetComparator;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import edu.wpi.capybara.objects.submissions.ISubmission;
import edu.wpi.capybara.objects.submissions.SubmissionStatus;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;

import java.sql.Date;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

public abstract class ServiceRequestAbs {
  @FXML protected MFXTextField assignedStaffID;
  @FXML protected MFXComboBox<String> location;
  @FXML protected MFXComboBox<String> requestSpecific;
  @FXML protected MFXComboBox<String> emergencyLevel;
  @FXML protected MFXDatePicker date;
  @FXML protected MFXTextField notes;
  @FXML protected MFXButton clearButton;
  @FXML protected MFXButton submitButton;
  @FXML protected Text submissionRecieved;
  @FXML protected Text missingFields;
  protected ISubmission submission; // sets submission type

    @FXML
  public void initialize() {
    TreeMap<String, NodeEntity> nodes = DatabaseConnect.getNodes();
    SortedSet<NodeEntity> sortedset = new TreeSet<NodeEntity>(new NodeAlphabetComparator());

    sortedset.addAll(nodes.values());

    Iterator<NodeEntity> iterator = sortedset.iterator();
    while (iterator.hasNext()) {
      NodeEntity n = iterator.next();
      // System.out.println(n.getShortName());
      location.getItems().add(n.getShortName());
    }
    // Set a default variabl

    setRequestSpecific(); // sets dropdown options for requestSpecific field, overridden in specific
    // class

    ObservableList<String> eLevels = FXCollections.observableArrayList();
    eLevels.addAll("Low", "Medium", "High", "Extreme");
    emergencyLevel.setItems(eLevels);

    submitButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            if (submitButton.isDisabled()) missingFields.setVisible(true);
            else {
              newSubmission();
              clearFields();
              missingFields.setVisible(false);
              submissionRecieved.setVisible(true);
            }
          }
        });

    clearButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            clearFields();
          }
        });

    assignedStaffID.setOnKeyReleased(
        new EventHandler<KeyEvent>() {
          @Override
          public void handle(KeyEvent event) {
            validateButton();
          }
        });

    location.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            validateButton();
          }
        });

    requestSpecific.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            validateButton();
          }
        });

    emergencyLevel.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            validateButton();
          }
        });

    date.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            validateButton();
          }
        });

    notes.setOnKeyReleased(
        new EventHandler<KeyEvent>() {
          @Override
          public void handle(KeyEvent event) {
            validateButton();
          }
        });
  }

  public void clearFields() {
    assignedStaffID.clear();
    location.clearSelection();
    requestSpecific.clearSelection();
    emergencyLevel.clearSelection();
    date.clear();
    notes.clear();
  }

  public void
      validateButton() { // ensures that information has been filled in before allowing submission
    boolean valid = false;
    if (!assignedStaffID.getText().equals("")
        && !location.getValue().equals("")
        && !requestSpecific.getValue().equals("")
        && !emergencyLevel.getValue().equals("")
        && !date.getText().equals("")
        && !notes.getText().equals("")) valid = true;
    submitButton.setDisable(!valid);
    submissionRecieved.setVisible(false);
    missingFields.setVisible(false);
  }

  public void newSubmission() {
    String currStaffID = App.getUser().getStaffid();
    String outputAssignedStaffID = assignedStaffID.getText();
    String outputLocation = location.getValue();
    String outputRequestSpecific = requestSpecific.getValue();
    String outputELevel = emergencyLevel.getValue();
    Date outputDate = Date.valueOf(date.getValue());
    String outputNotes = notes.getText();
    java.util.Date date = new java.util.Date();
    int submissionID = (int) (Math.random() * 100000);
    submission.submitNewSubmission(
        currStaffID,
        outputLocation,
        outputRequestSpecific,
        SubmissionStatus.BLANK,
        outputAssignedStaffID,
        submissionID,
        outputELevel,
        new java.sql.Date(date.getTime()),
        outputDate,
        outputNotes);
  }

  public abstract void setRequestSpecific();
}
