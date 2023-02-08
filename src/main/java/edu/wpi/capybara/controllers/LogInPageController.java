package edu.wpi.capybara.controllers;

import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import edu.wpi.capybara.objects.hibernate.StaffEntity;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javax.swing.*;

public class LogInPageController {

  @FXML private MFXButton SubmitButton;
  @FXML private MFXButton ClearButton;
  @FXML private MFXButton MapsButton;

  @FXML private TextField employeeID;
  //  @FXML private TextField Username;
  //  @FXML private TextField LastName;

  public void clearFields() {
    employeeID.clear();
    // Username.clear();
    // LastName.clear();
    SubmitButton.setDisable(true);
  }

  @FXML
  public void initialize() {
    System.out.println("I am from the Log in Page!");
  }

  public void submit(ActionEvent actionEvent) {

    StaffEntity s = null;
    if (employeeID.getText() != null) {
      String outputID = employeeID.getText();
      System.out.print("this is the employee ID" + outputID + "");
      s = DatabaseConnect.getStaff(outputID);
    }
    //    } else if (Username.getText() != null && LastName.getText() != null) {
    //      String outputusername = Username.getText();
    //      String outputlastName = LastName.getText();
    //      s = DatabaseConnect.getStaff(outputusername, outputlastName);
    //    } else {
    //
    //    }

    if (s == null) {
      clearFields();
    } else {
      Navigation.navigate(Screen.HOME);
    }
    SubmitButton.setDisable(false);
  }

  public void clearButton(ActionEvent actionEvent) {
    clearFields();
  }

  public void goToMapPage(ActionEvent actionEvent) {
    Navigation.navigate(Screen.SERVICE_REQUEST_TRANSPORTATION);
  }

  public void returnHome(ActionEvent actionEvent) {
    Navigation.navigate(Screen.HOME);
  }
}
