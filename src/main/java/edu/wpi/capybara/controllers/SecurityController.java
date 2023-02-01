package edu.wpi.capybara.controllers;


import edu.wpi.capybara.App;
import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
//import edu.wpi.capybara.objects.submissions.Submission;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import javax.swing.*;

public class SecurityController {

  @FXML private MFXButton returnButton;
  @FXML private MFXButton submitButton;
  @FXML private MFXButton clear;
  @FXML private MFXTextField room;
  @FXML private MFXTextField floorNumber;
  @FXML private MFXTextField notes;

  @FXML private MFXTextField employeeID;


  @FXML public TextField Location;
  @FXML public TextArea Description;
  @FXML public Stage primaryStage;

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



public void clearFields(){
    room.setText("");
    floorNumber.setText("");
    notes.setText("");
}

  public void back(ActionEvent actionEvent) throws IOException {
    Navigation.navigate(Screen.HOME);
  }



  //clicking a department
  public void clickedPolice(ActionEvent actionEvent) throws IOException{
    printf("The Police Were Notified and was selected");


  }




  public void clickedFireDepartment(ActionEvent actionEvent) throws IOException{
    printf("The Fire Department was selected");

  }

  public void clickedDoctor(ActionEvent actionEvent) throws IOException{
    printf("The Doctor was selected");

  }


  private void printf(String s) {
  }
  // end


  //entered room number

  public void submitSecurityRequestForm(ActionEvent actionEvent) throws IOException{
    String outputeID = employeeID.getText();
    String outputroomNumber = room.getText();
    String outputfloorNumber = floorNumber.getText();
    String outputnotes = notes.getText();


  }




  //entered notes/update

  //entered floor number


}
