package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import edu.wpi.capybara.objects.hibernate.StaffEntity;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javax.swing.*;

public class LogInPageController {

  @FXML private MFXTextField username;
  @FXML private MFXPasswordField password;
  @FXML private MFXButton loginButton;
  @FXML private Text errorTxt;

  public void clearFields() {
    username.clear();
    password.clear();
    loginButton.setDisable(true);
  }

  @FXML
  public void initialize() {
    System.out.println("I am from the Log in Page!");
  }

  public void login(ActionEvent actionEvent) throws IOException {

    // Get username and password and check if the combination exists
    StaffEntity s = null;
    if (username.getText() != "" && password.getText() != "") {
      String outputUsername = username.getText();
      String outputPassword = password.getText();
      System.out.print("This is the employee username" + outputUsername);
      s = DatabaseConnect.getStaff(outputUsername, outputPassword);
    }

    // Clear fields if username/password is incorrect
    if (s == null) {
      clearFields();
      errorTxt.setText("Incorrect username or password.");
    } else {

      App.setUser(s);
      System.out.println("First name is " + s.getFirstname());
      System.out.println("Last name is " + s.getFirstname());

      Navigation.navigate(Screen.HOME);
      Navigation.addMenu(Screen.MENU);
    }
  }

  public void enableLogin(KeyEvent keyEvent) {
    if (username.getText() != "" && password.getText() != "") loginButton.setDisable(false);
  }
}
