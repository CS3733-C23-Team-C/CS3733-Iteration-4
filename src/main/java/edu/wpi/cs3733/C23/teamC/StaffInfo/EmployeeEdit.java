package edu.wpi.cs3733.C23.teamC.StaffInfo;

import edu.wpi.cs3733.C23.teamC.App;
import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.database.hibernate.StaffEntity;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

public class EmployeeEdit {

  @Getter @Setter private String currFirstName;
  @Getter @Setter private String currLastName;
  @Getter @Setter private String currPassword;
  @Getter @Setter private String currNotes;
  @Getter @Setter private String currRole;

  @FXML public MFXButton updateButton;

  @FXML private MFXTextField firstNameField;
  @FXML private MFXTextField lastNameField;
  @FXML private MFXPasswordField passwordField;
  @FXML private MFXTextField notesField;

  @FXML private MFXFilterComboBox<String> staffIDField;

  @FXML private MFXFilterComboBox<String> ocupationDropDown;
  @FXML private Text successTxt;

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

  //    if (staffIDField().getText() != "" && staffIDField.getText() != "") {
  //      String outputUsername = username.getText();
  //      String outputPassword = password.getText();
  //      System.out.println("This is the employee username " + outputUsername);
  //      System.out.println("This is the employee password " + outputPassword);
  //      s = Main.db.getStaff(outputUsername, outputPassword);

  //  public static void screen() {
  //    int duration = 3; // Duration of timer in seconds
  //    ScreenSaver timerThread = new ScreenSaver(duration);
  //    Thread thread = new Thread(timerThread);
  //    thread.start();
  //
  //    try {
  //      Thread.sleep(duration * 1000 + 100); // Sleep for a bit longer than the timer duration
  //    } catch (InterruptedException e) {
  //      e.printStackTrace();
  //    }
  //    System.out.println("this thread is about to be killed");
  //
  //    thread.interrupt();
  //  }

  @FXML
  public void initialize() {
    Map<String, StaffEntity> staffMap = Main.db.getStaff();
    ObservableList<String> staff = FXCollections.observableArrayList();
    for (StaffEntity s : staffMap.values()) {
      staff.add(s.getStaffid());
    }
    staffIDField.getItems().addAll(staff);
    if (Main.db.getStaff(staffIDField.getValue()) != null) {
      // staffID.setText("Employee ID: " + App.getUser().getStaffid());
      firstNameField.setText(App.getTempuser().getFirstname());
      lastNameField.setText(App.getTempuser().getLastname());
      passwordField.setText(App.getTempuser().getPassword());
      updateButton.setDisable(false);
      String notes = App.getTempuser().getNotes();
      if (notes == null) notes = "";
      notesField.setText(notes);

      currFirstName = firstNameField.getText();
      currLastName = lastNameField.getText();
      currPassword = passwordField.getText();
      currNotes = notesField.getText();

      ocupationDropDown.setItems(FXCollections.observableArrayList("staff", "admin"));
      ocupationDropDown.setText(App.getTempuser().getRole());
      currRole = ocupationDropDown.getText();
    } else {
      currFirstName = "";
      currLastName = "";
      currPassword = "";
      currNotes = "";
      ocupationDropDown.setItems(FXCollections.observableArrayList("staff", "admin"));
      ocupationDropDown.setText("staff");
    }
  }

  public void edit() {
    firstNameField.setDisable(false);
    lastNameField.setDisable(false);
    passwordField.setDisable(false);
    notesField.setDisable(false);
  }

  public void searchEmployeeID(javafx.event.ActionEvent actionEvent) throws IOException {

    StaffEntity s = null;
    if (staffIDField.getValue() != null) {
      String outputID = staffIDField.getValue();
      System.out.print("this is the employee ID" + outputID + "");
      s = Main.db.getStaff(outputID);

      App.setTempUser(s);
    }

    if (s == null) {
      clearFields();
    } else {
      System.out.printf("the id matched an employee");
      initialize();
      edit();
    }
  }

  public void clearFields() {
    staffIDField.clearSelection();
    notesField.clear();

    firstNameField.clear();
    lastNameField.clear();

    passwordField.clear();
    ocupationDropDown.setText("staff");
    updateButton.setDisable(true);
    successTxt.setVisible(false);
  }

  public void update(javafx.event.ActionEvent actionEvent) throws IOException {
    System.out.println("The update button was pressed");

    // validate
    if (Objects.equals(firstNameField.getText(), ""))
      throw new IOException("First Name cannot be Empty");
    else if (Objects.equals(lastNameField.getText(), ""))
      throw new IOException("Last Name cannot be Empty");
    else if (Objects.equals(passwordField.getText(), ""))
      throw new IOException("Password cannot be Empty");
    else {

      //      setItems()
      //      fxcollection.observial arrary list

      String newFirst = firstNameField.getText();
      App.getTempuser().setFirstname(newFirst);
      String newLast = lastNameField.getText();
      App.getTempuser().setLastname(newLast);
      String newPass = passwordField.getText();
      App.getTempuser().setPassword(newPass);
      String newNote = notesField.getText();
      App.getTempuser().setNotes(newNote);
      String newRole = ocupationDropDown.getText();
      App.getTempuser().setRole(newRole);

      ObservableList<String> options = FXCollections.observableArrayList(getCurrRole());
      final ComboBox comboBox = new ComboBox(options);
      successTxt.setVisible(true);
    }
  }

  // public void searchEmployeeID(javafx.event.ActionEvent actionEvent) {}

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
