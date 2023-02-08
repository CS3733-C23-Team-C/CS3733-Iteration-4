package edu.wpi.capybara.controllers;

import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javax.swing.*;

public class LogInPageController {

  @FXML private MFXButton tempHomeButton;
  @FXML private MFXButton SubmitButton;
  @FXML private MFXButton ClearButton;
  @FXML private MFXButton MapsButton;
  @FXML private MFXButton TempHomeButton;

  @FXML private TextField employeeID;
  @FXML private TextField Username;
  @FXML private TextField LastName;

  public void clearFields() {
    employeeID.clear();
    Username.clear();
    LastName.clear();
    SubmitButton.setDisable(true);
  }

  @FXML
  public void initialize() {
    System.out.println("I am from the Log in Page!");
  }

  public String submit(ActionEvent actionEvent) {

    String outputID = employeeID.getText();
    String outputusername = Username.getText();
    String outputlastName = LastName.getText();
    clearFields();








    // System.out.println("output ID is" + outputID + "\n");
    return outputID;
  }

  public void returnHome(ActionEvent actionEvent) {
    Navigation.navigate(Screen.HOME);
  }
}
