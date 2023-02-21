package edu.wpi.capybara.controllers;

import edu.wpi.capybara.database.newDBConnect;
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

  private newDBConnect dbConnect;

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
    dbConnect = new newDBConnect();
    dbConnect.importAll();
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

        s = dbConnect.getStaff2(outputFirstname, outputLastname);

        // if username and password are new
        if (s == null) {

          /*
          Main.db.addStaff(new StaffEntity(
                  outputStaffID,
                  outputFirstname,
                  outputLastname,
                  "staff",
                  outputPassword)); */

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

          s = dbConnect.getStaff(outputStaffID);
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
    if (firstName.getText() != ""
        && lastName.getText() != ""
        && password.getText() != ""
        && confirmPassword.getText() != "") signUpButton.setDisable(false);
  }

  public void back(ActionEvent actionEvent) {
    Navigation.navigate(Screen.LOG_IN_PAGE);
  }

  /*
  public void signUp(MouseEvent mouseEvent) {
      System.out.println("Hello World");
  }
   */
}
