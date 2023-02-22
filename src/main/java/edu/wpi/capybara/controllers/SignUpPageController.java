package edu.wpi.capybara.controllers;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import edu.wpi.capybara.objects.hibernate.StaffEntity;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class SignUpPageController {

  @FXML private MFXTextField staffID;
  @FXML private MFXTextField firstName;
  @FXML private MFXTextField lastName;
  @FXML private MFXPasswordField password;
  @FXML private MFXPasswordField confirmPassword;
  @FXML private MFXButton signUpButton;

  @FXML private MFXButton backButton;

  @FXML private Text errorTxt;
  @FXML private Text newUserTxt;

  public void clearFields() {
    staffID.clear();
    firstName.clear();
    lastName.clear();
    password.clear();
    confirmPassword.clear();
    signUpButton.setDisable(true);
  }

  @FXML
  public void initialize() {
    System.out.println("I am from the Sign Up Page!");
  }

  public void signUp(ActionEvent actionEvent) throws IOException {
    if (signUpButton.getText().equals("Sign Up")) {
      // Get username and password and check if the combination exists
      StaffEntity s = null;
      if (!password.getText().equals(confirmPassword.getText())) {
        clearFields();
        errorTxt.setText("Passwords must match");
      } else {
        errorTxt.setText("");
        String outputStaffID = staffID.getText();
        String outputFirstname = firstName.getText();
        String outputLastname = lastName.getText();
        String outputPassword = password.getText();

        System.out.println("This is the new staff ID " + outputStaffID);
        System.out.println("This is the new first name " + outputFirstname);
        System.out.println("This is the new last name " + outputLastname);
        System.out.println("This is the new password " + outputPassword);

        s = Main.db.getStaff2(outputFirstname, outputLastname);

        // if username and password are new
        if (s == null) {

          Main.db.addStaff(
              new StaffEntity(
                  outputStaffID, outputFirstname, outputLastname, "staff", outputPassword));

          errorTxt.setText("");
          backButton.setDisable(true);
          staffID.setDisable(true);
          firstName.setDisable(true);
          lastName.setDisable(true);
          password.setDisable(true);
          confirmPassword.setDisable(true);
          signUpButton.setText("Login");

          newUserTxt.setText(
              "User "
                  + outputLastname
                  + ","
                  + outputFirstname
                  + " successfully created with staff id "
                  + outputStaffID);
        } else {
          clearFields();
          // if username and password are already in the system
          // App.setUser(s);

          s = Main.db.getStaff(outputStaffID);
          errorTxt.setText(((s == null) ? "User" : "Staff ID") + " already exists");
        }
      }
    } else if (signUpButton.getText().equals("Login")) {
      signUpButton.setText("Sign Up");
      newUserTxt.setText("");
      Navigation.navigate(Screen.LOG_IN_PAGE);
    } else {
      System.out.println("Unknown error");
    }
  }

  public void enableSignUp(KeyEvent keyEvent) {
    signUpButton.setDisable(
        Objects.equals(firstName.getText(), "")
            || Objects.equals(lastName.getText(), "")
            || Objects.equals(password.getText(), "")
            || Objects.equals(confirmPassword.getText(), ""));
  }

  public void backToLogin(MouseEvent mouseEvent) {
    Navigation.navigate(Screen.LOG_IN_PAGE);
  }

  /*
  public void signUp(MouseEvent mouseEvent) {
      System.out.println("Hello World");
  }
   */
}
