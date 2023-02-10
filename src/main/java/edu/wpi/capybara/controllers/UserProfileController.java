package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

public class UserProfileController {

  @Getter @Setter private String currFirstName;
  @Getter @Setter private String currLastName;
  @Getter @Setter private String currPassword;

  @FXML private MFXTextField firstNameField;
  @FXML private MFXTextField lastNameField;
  @FXML private MFXPasswordField passwordField;
  @FXML private MFXButton firstNameEdit;
  @FXML private MFXButton lastNameEdit;
  @FXML private MFXButton passwordEdit;
  @FXML private MFXButton firstNameSave;
  @FXML private MFXButton lastNameSave;
  @FXML private MFXButton passwordSave;
  @FXML private MFXButton backButton;
  @FXML private Text helloText;
  @FXML private Text successFirstName;
  @FXML private Text successLastName;
  @FXML private Text successPassword;
  @FXML private Text staffID;
  @FXML private MFXButton logOut;

  @FXML
  public void initialize() {
    if (App.getUser() != null) {
      helloText.setText("Hello " + App.getUser().getFirstname() + "!");
      staffID.setText("Staff ID:" + App.getUser().getStaffid());
      firstNameField.setText(App.getUser().getFirstname());
      lastNameField.setText(App.getUser().getLastname());
      passwordField.setText(App.getUser().getPassword());
      currFirstName = firstNameField.getText();
      currLastName = lastNameField.getText();
      currPassword = passwordField.getText();
    } else {
      currFirstName = "";
      currLastName = "";
      currPassword = "";
    }
  }

  public void returnHome() {
    Navigation.navigate(Screen.HOME);
  }

  public void editFirstName() {
    firstNameField.setDisable(false);
    successFirstName.setVisible(false);
    firstNameEdit.setDisable(true);
  }

  public void editLastName() {
    lastNameField.setDisable(false);
    successLastName.setVisible(false);
    lastNameEdit.setDisable(true);
  }

  public void editPassword() {
    passwordField.setDisable(false);
    successPassword.setVisible(false);
    passwordEdit.setDisable(true);
  }

  public void validateSaveFN() {
    if (!currFirstName.equals(firstNameField.getText()) && firstNameField.getText().length() > 0)
      firstNameSave.setDisable(false);
    else firstNameSave.setDisable(true);
  }

  public void validateSaveLN() {
    if (!currLastName.equals(lastNameField.getText()) && lastNameField.getText().length() > 0)
      lastNameSave.setDisable(false);
    else lastNameSave.setDisable(true);
  }

  public void validateSavePW() {
    if (!currPassword.equals(passwordField.getText()) && passwordField.getText().length() > 6)
      passwordSave.setDisable(false);
    else passwordSave.setDisable(true);
  }

  public void saveFirstName() {
    String newName = firstNameField.getText();
    App.getUser().setFirstname(newName);
    // DATABASE STUFF???
    currFirstName = newName;
    firstNameField.setDisable(true);
    firstNameSave.setDisable(true);
    successFirstName.setVisible(true);
    firstNameEdit.setDisable(false);
  }

  public void saveLastName() {
    String newName = lastNameField.getText();
    App.getUser().setLastname(newName);
    // DATABASE STUFF???
    currLastName = newName;
    lastNameField.setDisable(true);
    lastNameSave.setDisable(true);
    successLastName.setVisible(true);
    lastNameEdit.setDisable(false);
  }

  public void savePassword() {
    String newPassword = passwordField.getText();
    App.getUser().setPassword(newPassword);
    // DATABASE STUFF???
    currPassword = newPassword;
    passwordField.setDisable(true);
    passwordSave.setDisable(true);
    successPassword.setVisible(true);
    passwordEdit.setDisable(false);
  }

  public void logOut() {
    currFirstName = "";
    currLastName = "";
    currPassword = "";
    RootController.hideMenu();
    Navigation.navigate(Screen.LOG_IN_PAGE);
  }
}
