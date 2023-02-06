package edu.wpi.capybara.controllers;

import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import edu.wpi.capybara.objects.submissions.securitySubmission;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.*;

public class SecurityController {

  @FXML private MFXButton backButton;
  @FXML private MFXButton SubmitButton;
  @FXML private MFXButton clear;
  @FXML private TextField roomNumber;
  @FXML private TextField floorNumber;
  @FXML private MFXTextField notesUpdate;

  @FXML private MFXButton Police;

  @FXML private MFXButton Fire;

  @FXML private TextField employeeID;

  @FXML private TextField Location;
  @FXML private TextArea Description;
  @FXML private Stage primaryStage;

  public void returnHome(ActionEvent actionEvent) throws IOException {
    Navigation.navigate(Screen.HOME);
  }

  //  public void submitForm(ActionEvent actionEvent) throws IOException {
  //    String outputRoomNumber = room.getText();
  //    String outputFloorNumber = floorNumber.getText();
  //    String outputNotes = notes.getText();
  //    // System.out.println("Current Room: " + outputCurrRoom + " Destination Room: " +
  //    // outputDestRoom);
  //    securtySubmission newSubmission =
  //            new securtySubmission(outputRoomNumber, outputFloorNumber, outputNotes);
  //    App.totalSubmissions.newTransportationSubmission(newSubmission);
  //    System.out.println(App.totalSubmissions.getTransportationData());
  //    clearFields();
  //  }

  public void clearFields() {
    employeeID.setText("");
    roomNumber.setText("");
    floorNumber.setText("");
    notesUpdate.setText("");
  }

  public void back(ActionEvent actionEvent) throws IOException {
    Navigation.navigate(Screen.HOME);
  }

  // clicking a department
  public void clickedPolice(ActionEvent actionEvent) throws IOException {
    printf("The Police Were Notified and was selected");
  }

  public void clickedFireDepartment(ActionEvent actionEvent) throws IOException {
    printf("The Fire Department was selected");
  }

  public void clickedDoctor(ActionEvent actionEvent) throws IOException {
    printf("The Doctor was selected");
  }

  private void printf(String s) {}
  // end

  // entered room number

  public void submit(ActionEvent actionEvent) {
    String outputeID = employeeID.getText();
    String outputroomNumber1 = roomNumber.getText();
    String outputfloorNumber = floorNumber.getText();
    String outputnotes = notesUpdate.getText();
    securitySubmission addSubmission =
        new securitySubmission(outputnotes, outputeID, outputfloorNumber, outputroomNumber1);

    clearFields();
  }

  public void clearRequest() {
    floorNumber.clear();
    roomNumber.clear();
    notesUpdate.clear();
    employeeID.clear();
  }

  // entered notes/update

  // entered floor number

}
