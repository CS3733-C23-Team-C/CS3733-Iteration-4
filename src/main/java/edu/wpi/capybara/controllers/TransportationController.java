package edu.wpi.capybara.controllers;

import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.objects.NodeAlphabetComparator;
import edu.wpi.capybara.objects.hibernate.NodeEntity;

import io.github.palexdev.materialfx.controls.*;

import edu.wpi.capybara.objects.hibernate.TransportationsubmissionEntity;
import edu.wpi.capybara.objects.submissions.submissionStatus;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javax.swing.*;

public class TransportationController {

  public TransportationController() {}

  @FXML private MFXTextField employeeID;
  @FXML private MFXFilterComboBox<String> currentLocation;
  @FXML private MFXFilterComboBox<String> destinationLocation;
  @FXML private MFXComboBox<String> emergencyLevel;
  @FXML private MFXDatePicker date;
  @FXML private MFXTextField reasonField;
  @FXML private MFXButton clearButton;
  @FXML private MFXButton submitButton;

  /** Initialize controller by FXML Loader. */
  @FXML
  public void initialize() {

    System.out.println("I am from TransportationController.");

    // Add different locations

    TreeMap<String, NodeEntity> nodes = DatabaseConnect.getNodes();

    SortedSet<NodeEntity> sortedset = new TreeSet<NodeEntity>(new NodeAlphabetComparator());

    sortedset.addAll(nodes.values());

    Iterator<NodeEntity> iterator = sortedset.iterator();
    while (iterator.hasNext()) {
      NodeEntity n = iterator.next();
      // System.out.println(n.getShortName());
      currentLocation.getItems().add(n.getShortName());
      destinationLocation.getItems().add(n.getShortName());
    }

    // Set a default variable
    currentLocation.getSelectionModel().selectFirst();
    destinationLocation.getSelectionModel().selectFirst();

    emergencyLevel.getItems().addAll("Low", "Medium", "High", "Extreme");
    emergencyLevel.getSelectionModel().selectFirst();
  }

  public void submitForm(ActionEvent actionEvent)
      throws IOException { // when submit button is pressed, collects text fields

    String outputID = employeeID.getText();
    String outputCurrRoom = "" + currentLocation.getValue();
    String outputDestRoom = "" + destinationLocation.getValue();
    String outputLevel = "" + emergencyLevel.getValue();
    String outputDate = date.getText();
    String outputReason = reasonField.getText();


    // Change to accommodate database and storage system
    //        TransportationsubmissionEntity newSubmission =
    //            new TransportationsubmissionEntity(
    //                App.getUser().getStaffid(),
    //                outputCurrRoom,
    //                outputDestRoom,
    //                outputReason,
    //                submissionStatus.BLANK);
    //        App.getTotalSubmissions().newTransportationSubmission(newSubmission);
    //        System.out.println(App.getTotalSubmissions().getTransportationData());

    // From main

    // java.util.Date date = new java.util.Date();
    // String outputCurrRoom =
    //    Location.getValue(); // then creates an object to store them, clears fields
    // String outputDestRoom = destRoom.getText();
    // String outputReason = reasonField.getText();

    // TransportationsubmissionEntity newSubmission =
    //    new TransportationsubmissionEntity(
    //        App.getUser().getStaffid(),
    //        outputCurrRoom,
    //        outputDestRoom,
    //        outputReason,
    //        submissionStatus.BLANK,
    //        null,
    //       (int) (Math.random() * 100000),
    //        Urgency.BLANK,
    //       new java.sql.Date(date.getTime()),
    //        new java.sql.Date(date.getTime() + 86400000));

    // App.getTotalSubmissions().newTransportationSubmission(newSubmission);
    // System.out.println(App.getTotalSubmissions().getTransportationData());

//New from main use this but with new vars
    // System.out.println("Current Room: " + outputCurrRoom + " Destination Room: " +
    // outputDestRoom);
    //TransportationsubmissionEntity newSubmission =
        //new TransportationsubmissionEntity(
            //App.getUser().getStaffid(),
            //outputCurrRoom,
            //outputDestRoom,
            //outputReason,
            //submissionStatus.BLANK,
            //null,
            //(int) (Math.random() * 100000),
            //"Blank",
            //new java.sql.Date(date.getTime()),
            //new java.sql.Date(date.getTime() + 86400000));
    //    newSubmission.setCurrroomnum(outputCurrRoom);
    //    newSubmission.setDestroomnum(outputDestRoom);
    //    newSubmission.setReason(outputReason);
    //App.getTotalSubmissions().newTransportationSubmission(newSubmission);
    //System.out.println(App.getTotalSubmissions().getTransportationData());







    clearFields();
  }

  public void clearFields() { // clears fields of text
    employeeID.setText("");
    currentLocation.getSelectionModel().selectFirst();
    destinationLocation.getSelectionModel().selectFirst();
    emergencyLevel.getSelectionModel().selectFirst();
    date.clear();
    reasonField.setText("");
    submitButton.setDisable(true);
  }

  public void
      validateButton() { // ensures that information has been filled in before allowing submission
    if (employeeID.getText() != ""
        && reasonField.getText() != ""
        && currentLocation.getValue() != destinationLocation.getValue()
        && date.getText() != "") submitButton.setDisable(false);
  }

  //  public void backgroundMap(String path) {
  //    BackgroundSize backgroundSize =
  //        new BackgroundSize(mapPane.getWidth(), mapPane.getHeight(), false, false, true, false);
  //    BackgroundRepeat repeatX = BackgroundRepeat.NO_REPEAT;
  //    BackgroundRepeat repeatY = BackgroundRepeat.NO_REPEAT;
  //    BackgroundPosition position = BackgroundPosition.CENTER;
  //    Image imageToSet = new Image(path);
  //    BackgroundImage bri =
  //        new BackgroundImage(imageToSet, repeatX, repeatY, position, backgroundSize);
  //    mapPane.setBackground(new Background(bri));
  //  }

  //  public void updateMap() { // updates map depending on combobox
  //    String selection = dropDown.getValue();
  //    if (selection.equals("Lower Level 2")) {
  //      backgroundMap("edu/wpi/capybara/images/thelowerlevel2.png");
  //    } else if (selection.equals("Ground Floor")) {
  //      backgroundMap("edu/wpi/capybara/images/thegroundfloor.png");
  //    } else if (selection.equals("First Floor")) {
  //      backgroundMap("edu/wpi/capybara/images/thefirstfloor.png");
  //    } else if (selection.equals("Second Floor")) {
  //      backgroundMap("edu/wpi/capybara/images/thesecondfloor.png");
  //    } else if (selection.equals("Third Floor")) {
  //      backgroundMap("edu/wpi/capybara/images/thethirdfloor.png");
  //    } else {
  //      backgroundMap("edu/wpi/capybara/images/thelowerlevel1.png");
  //    }
  //  }
}
