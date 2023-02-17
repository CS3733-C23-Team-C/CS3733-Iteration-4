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

  @FXML private MFXTextField username;
  @FXML private MFXPasswordField password;
  @FXML private MFXPasswordField confirmPassword;
  @FXML private MFXButton signUpButton;

  @FXML private MFXButton backButton;

  @FXML private Text errorTxt;

  private newDBConnect dbConnect;

  public void clearFields() {
    username.clear();
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

    // Get username and password and check if the combination exists
    StaffEntity s = null;
    if (!password.getText().equals(confirmPassword.getText())) {
      clearFields();
      errorTxt.setText("Passwords must match");
    } else {
      errorTxt.setText("");
      String outputUsername = username.getText();
      String outputPassword = password.getText();
      System.out.println("This is the new username " + outputUsername);
      System.out.println("This is the new password " + outputPassword);

      s = dbConnect.getStaff(outputUsername, outputPassword);

      // if username and password are new
      if (s == null) {
        // Database Magic
        System.out.println("\"Insertion\"");
      } else {
        // if username and password are already in the system
        // App.setUser(s);
        clearFields();
        errorTxt.setText("Username and Password already exist in the system");
      }
    }
  }

  public void enableSignUp(KeyEvent keyEvent) {
    if (username.getText() != "" && password.getText() != "" && confirmPassword.getText() != "")
      signUpButton.setDisable(false);
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
