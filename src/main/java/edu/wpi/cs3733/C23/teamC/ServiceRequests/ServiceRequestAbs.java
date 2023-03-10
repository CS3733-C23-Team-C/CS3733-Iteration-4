package edu.wpi.cs3733.C23.teamC.ServiceRequests;

import edu.wpi.cs3733.C23.teamC.App;
import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.Pathfinding.NodeAlphabetComparator;
import edu.wpi.cs3733.C23.teamC.ServiceRequests.submissions.ISubmission;
import edu.wpi.cs3733.C23.teamC.ServiceRequests.submissions.SubmissionStatus;
import edu.wpi.cs3733.C23.teamC.database.hibernate.MessagesEntity;
import edu.wpi.cs3733.C23.teamC.database.hibernate.NodeEntity;
import io.github.palexdev.materialfx.controls.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public abstract class ServiceRequestAbs {
  @FXML protected MFXTextField assignedStaffID;
  @FXML protected MFXFilterComboBox<String> Location;
  @FXML protected MFXComboBox<String> requestSpecific;
  @FXML protected MFXComboBox<String> emergencyLevel;
  @FXML protected MFXDatePicker date;
  @FXML protected MFXTextField notes;
  @FXML protected MFXButton clearButton;
  @FXML protected MFXButton submitButton;
  @FXML protected Text submissionReceived;
  @FXML protected Text dateError;
  protected ISubmission submission; // sets submission type

  @FXML
  public void initialize() {
    Map<String, NodeEntity> nodes = Main.db.getNodes();

    SortedSet<NodeEntity> sortedset = new TreeSet<NodeEntity>(new NodeAlphabetComparator());

    sortedset.addAll(nodes.values());

    Iterator<NodeEntity> iterator = sortedset.iterator();
    while (iterator.hasNext()) {
      NodeEntity n = iterator.next();
      // System.out.println(n.getShortName());
      Location.getItems().add(n.getLongName());
    }

    // submissionReceived.setFont(Font.font("system", FontWeight.NORMAL, FontPosture.REGULAR, 20));
    Label label = new Label();
    Insets insets = new Insets(10, 5, 10, 5);
    label.setPadding(insets);

    // Set a default variable
    // location.getSelectionModel().selectFirst();

    setRequestSpecific(); // sets dropdown options for requestSpecific field, overridden in specific
    // class
    emergencyLevel.getItems().addAll("Low", "Medium", "High", "Extreme");

    submitButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            newSubmission();
            clearFields();
            dateError.setVisible(false);
            submissionReceived.setVisible(true);
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

    Location.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            validateButton();
          }
        });

    Location.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            Location.show();
          }
        });

    requestSpecific.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            validateButton();
          }
        });

    requestSpecific.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            requestSpecific.show();
          }
        });

    emergencyLevel.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            validateButton();
          }
        });

    emergencyLevel.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            emergencyLevel.show();
          }
        });

    date.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            java.util.Date someDate = new java.util.Date();
            Date currDate = new java.sql.Date(someDate.getTime());
            dateError.setVisible(false);
            if (currDate.compareTo(Date.valueOf(date.getValue())) > 0) dateError.setVisible(true);
            else validateButton();
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
    Location.clear();
    requestSpecific.clear();
    emergencyLevel.clear();
    date.clear();
    notes.clear();
    submissionReceived.setVisible(false);
  }

  public void
      validateButton() { // ensures that information has been filled in before allowing submission
    boolean valid = false;
    if (!assignedStaffID.getText().equals("")
        && Location.getValue() != null
        && requestSpecific.getValue() != null
        && emergencyLevel.getValue() != null
        && date.getValue() != null) valid = true;
    submitButton.setDisable(!valid);
    submissionReceived.setVisible(false);
  }

  public void newSubmission() {
    String currStaffID = App.getUser().getStaffid();
    String outputAssignedStaffID = assignedStaffID.getText();
    String outputLocation = Location.getValue();
    String outputRequestSpecific = requestSpecific.getValue();
    String outputELevel = emergencyLevel.getValue();
    Date outputDate = Date.valueOf(date.getValue());
    String outputNotes = notes.getText();
    java.util.Date date = new java.util.Date();
    int submissionID = Main.db.newID();
    java.util.Date submissionDate = new java.util.Date();
    Timestamp time = new Timestamp(submissionDate.getTime());
    int messageID = Main.db.generateMessageID();
    MessagesEntity newMessage =
        new MessagesEntity(
            messageID,
            "SYSTEM",
            outputAssignedStaffID,
            time,
            "Request #" + submissionID + " has been assigned to you",
            false);
    Main.db.addMessage(newMessage);
    submissionReceived.setText("Submission #" + submissionID + " has been received!");
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
