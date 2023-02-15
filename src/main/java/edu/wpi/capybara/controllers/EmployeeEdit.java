package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.Main;
import edu.wpi.capybara.objects.hibernate.StaffEntity;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

public class EmployeeEdit {

  @Getter @Setter private String currFirstName;
  @Getter @Setter private String currLastName;
  @Getter @Setter private String currPassword;
  @Getter @Setter private String currNotes;

  @FXML public MFXButton searchButton;

  @FXML public MFXButton updateButton;

  @FXML private MFXTextField firstNameField;
  @FXML private MFXTextField lastNameField;
  @FXML private MFXPasswordField passwordField;
  @FXML private MFXTextField notesField;

  @FXML private MFXTextField staffIDField;

  @FXML private MFXButton fieldsEdit;
  //  @FXML private MFXButton lastNameEdit;
  //  @FXML private MFXButton passwordEdit;
  @FXML private MFXButton fieldsSave;
  //  @FXML private MFXButton lastNameSave;
  //  @FXML private MFXButton passwordSave;

  @FXML private Text successFirstName;
  @FXML private Text successLastName;
  @FXML private Text successPassword;
  @FXML private Text staffID;
  @FXML private Text errorTxt;

  public void clearFields() {

    firstNameField.clear();
    lastNameField.clear();
    passwordField.clear();
    notesField.clear();
    staffIDField.clear();
    updateButton.setDisable(true);
  }

  public void searchEmployeeID(ActionEvent actionEvent) throws IOException {

    StaffEntity s = null;
    if (staffIDField.getText() != null) {
      String outputID = staffIDField.getText();
      System.out.print("this is the employee ID" + outputID + "");
      s = Main.db.getStaff(outputID);
    }
    if (s == null) {
      clearFields();
    } else {
      System.out.printf("the id matched an employee");
      initialize();
    }
  }

  //    if (staffIDField().getText() != "" && staffIDField.getText() != "") {
  //      String outputUsername = username.getText();
  //      String outputPassword = password.getText();
  //      System.out.println("This is the employee username " + outputUsername);
  //      System.out.println("This is the employee password " + outputPassword);
  //      s = Main.db.getStaff(outputUsername, outputPassword);
  //    }

  @FXML
  public void initialize() {
    if (Main.db.getStaff(staffIDField.getText()) != null) {
      // staffID.setText("Employee ID: " + App.getUser().getStaffid());
      firstNameField.setText(App.getUser().getFirstname());
      lastNameField.setText(App.getUser().getLastname());
      passwordField.setText(App.getUser().getPassword());
      notesField.setText(App.getUser().getNotes());

      currFirstName = firstNameField.getText();
      currLastName = lastNameField.getText();
      currPassword = passwordField.getText();
      currNotes = notesField.getText();

    } else {
      currFirstName = "";
      currLastName = "";
      currPassword = "";
      currNotes = "";
    }
  }

  public void edit() {
    firstNameField.setDisable(false);
    lastNameField.setDisable(false);
    passwordField.setDisable(false);
    notesField.setDisable(false);
  }

  public void save() {
    // validate
    if (firstNameField.getText() == "") errorTxt.setText("First name can't be empty.");
    else if (lastNameField.getText() == "") errorTxt.setText("Last name can't be empty.");
    else if (passwordField.getText() == "") errorTxt.setText("Password can't be empty.");
    else if (notesField.getText() == "") errorTxt.setText("Password can't be empty");
    else {
      // save changes to app
      String newFirst = firstNameField.getText();
      App.getUser().setFirstname(newFirst);
      String newLast = lastNameField.getText();
      App.getUser().setLastname(newLast);
      String newPass = passwordField.getText();
      App.getUser().setPassword(newPass);
      String newNote = notesField.getText();
      App.getUser().setPassword(newNote);
      // update menu

      // make fields uneditable
      firstNameField.setDisable(true);
      lastNameField.setDisable(true);
      passwordField.setDisable(true);
      notesField.setDisable(true);
    }
  }

  public void searchEmployeeID(javafx.event.ActionEvent actionEvent) {}

  //  public void editFirstName() {
  //    firstNameField.setDisable(false);
  //    successFirstName.setVisible(false);
  //    firstNameEdit.setDisable(true);
  //  }
  //
  //  public void editLastName() {
  //    lastNameField.setDisable(false);
  //    successLastName.setVisible(false);
  //    lastNameEdit.setDisable(true);
  //  }
  //
  //  public void editPassword() {
  //    passwordField.setDisable(false);
  //    successPassword.setVisible(false);
  //    passwordEdit.setDisable(true);
  //  }
  //
  //  public void validateSaveFN() {
  //    if (!currFirstName.equals(firstNameField.getText()) && firstNameField.getText().length() >
  // 0)
  //      firstNameSave.setDisable(false);
  //    else firstNameSave.setDisable(true);
  //  }
  //
  //  public void validateSaveLN() {
  //    if (!currLastName.equals(lastNameField.getText()) && lastNameField.getText().length() > 0)
  //      lastNameSave.setDisable(false);
  //    else lastNameSave.setDisable(true);
  //  }
  //
  //  public void validateSavePW() {
  //    if (!currPassword.equals(passwordField.getText()) && passwordField.getText().length() > 6)
  //      passwordSave.setDisable(false);
  //    else passwordSave.setDisable(true);
  //  }
  //
  //  public void saveFirstName() {
  //    String newName = firstNameField.getText();
  //    App.getUser().setFirstname(newName);
  //    // DATABASE STUFF???
  //    currFirstName = newName;
  //    firstNameField.setDisable(true);
  //    firstNameSave.setDisable(true);
  //    successFirstName.setVisible(true);
  //    firstNameEdit.setDisable(false);
  //  }
  //
  //  public void saveLastName() {
  //    String newName = lastNameField.getText();
  //    App.getUser().setLastname(newName);
  //    // DATABASE STUFF???
  //    currLastName = newName;
  //    lastNameField.setDisable(true);
  //    lastNameSave.setDisable(true);
  //    successLastName.setVisible(true);
  //    lastNameEdit.setDisable(false);
  //  }
  //
  //  public void savePassword() {
  //    String newPassword = passwordField.getText();
  //    App.getUser().setPassword(newPassword);
  //    // DATABASE STUFF???
  //    currPassword = newPassword;
  //    passwordField.setDisable(true);
  //    passwordSave.setDisable(true);
  //    successPassword.setVisible(true);
  //    passwordEdit.setDisable(false);
  //  }

}
