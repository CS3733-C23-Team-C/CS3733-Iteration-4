package edu.wpi.cs3733.C23.teamC.controllers;

import edu.wpi.cs3733.C23.teamC.App;
import edu.wpi.cs3733.C23.teamC.Main;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import lombok.Getter;
import lombok.Setter;

public class UserProfileController {

  @Getter @Setter private String currFirstName;
  @Getter @Setter private String currLastName;
  @Getter @Setter private String currPassword;

  @FXML private MFXTextField folderText;
  @FXML private ImageView image;
  @FXML private MFXTextField firstNameField;
  @FXML private MFXTextField lastNameField;
  @FXML private MFXPasswordField passwordField;
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
  @FXML private MFXButton databaseAccess;

  @FXML
  public void initialize() {
    if (App.getUser() != null) {
      staffID.setText("Employee ID: " + App.getUser().getStaffid());
      firstNameField.setText(App.getUser().getFirstname());
      lastNameField.setText(App.getUser().getLastname());
      passwordField.setText(App.getUser().getPassword());
      currFirstName = firstNameField.getText();
      currLastName = lastNameField.getText();
      currPassword = passwordField.getText();
      BufferedImage tempimage = Main.getRepo().getImage(1);
      image.setImage(SwingFXUtils.toFXImage(tempimage, null));

      databaseAccess.setVisible(App.getUser().getRole().equals("admin"));
    } else {
      currFirstName = "";
      currLastName = "";
      currPassword = "";
    }
  }

  public void edit() {
    firstNameField.setDisable(false);
    lastNameField.setDisable(false);
    passwordField.setDisable(false);
  }

  public void save() {
    // validate
    if (Objects.equals(firstNameField.getText(), ""))
      errorTxt.setText("First name can't be empty.");
    else if (Objects.equals(lastNameField.getText(), ""))
      errorTxt.setText("Last name can't be empty.");
    else if (Objects.equals(passwordField.getText(), ""))
      errorTxt.setText("Password can't be empty.");
    else {
      // save changes to app
      String newFirst = firstNameField.getText();
      App.getUser().setFirstname(newFirst);
      String newLast = lastNameField.getText();
      App.getUser().setLastname(newLast);
      String newPass = passwordField.getText();
      App.getUser().setPassword(newPass);
      if (!Objects.equals(folderText.getText(), "")) {
        try {
          Main.getRepo().setImage(folderText.getText());
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
      // update menu
      MenuController.setUserProfile();
      // make fields uneditable
      firstNameField.setDisable(true);
      lastNameField.setDisable(true);
      passwordField.setDisable(true);
    }
  }

  public void databasePopup() {
    System.out.println("database popup");
    DatabaseImportDialogController didc = new DatabaseImportDialogController();
    DatabaseImportDialogController.showDialog();
  }

  public void onFileSelect() {
    FileChooser chooser = new FileChooser();
    chooser.setTitle("JavaFX Projects");
    File defaultDirectory = new File(System.getProperty("user.dir"));
    chooser.setInitialDirectory(defaultDirectory);
    File selectedDirectory = chooser.showOpenDialog(folderText.getScene().getWindow());
    if (selectedDirectory != null) folderText.setText(selectedDirectory.getAbsolutePath());
  }

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
