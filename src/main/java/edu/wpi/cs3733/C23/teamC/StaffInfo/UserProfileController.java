package edu.wpi.cs3733.C23.teamC.StaffInfo;

import edu.wpi.cs3733.C23.teamC.App;
import edu.wpi.cs3733.C23.teamC.Home.MenuController;
import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.database.DatabaseImportDialogController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import lombok.Getter;
import lombok.Setter;

public class UserProfileController {

  @Getter @Setter private String currFirstName;
  @Getter @Setter private String currLastName;
  @Getter @Setter private String currPassword;

  @FXML private ImageView image;
  @FXML private MFXTextField firstNameField;
  @FXML private MFXTextField lastNameField;
  @FXML private MFXPasswordField passwordField;
  @FXML private MFXButton fieldsEdit;
  @FXML private MFXButton fieldsSave;
  @FXML private MFXButton updateButton;

  @FXML private Text successFirstName;
  @FXML private Text successLastName;
  @FXML private Text successPassword;
  @FXML private Text staffID;
  @FXML private Text errorTxt;
  @FXML private MFXButton databaseAccess;
  private String folderPath;

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

      BufferedImage tempimage = Main.getRepo().getImage(App.getUser().getPicid());
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
      if (!Objects.equals(folderPath, null)) {
        try {
          int val = Main.getRepo().setImage(folderPath);
          App.getUser().setPicid(val);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        BufferedImage tempimage = Main.getRepo().getImage(App.getUser().getPicid());
        image.setImage(SwingFXUtils.toFXImage(tempimage, null));
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

  public void onFileSelect() throws FileNotFoundException {
    FileChooser chooser = new FileChooser();
    chooser.setTitle("JavaFX Projects");
    File defaultDirectory = new File(System.getProperty("user.dir"));
    chooser.setInitialDirectory(defaultDirectory);
    File selectedDirectory = chooser.showOpenDialog(updateButton.getScene().getWindow());
    if (selectedDirectory != null) {
      folderPath = selectedDirectory.getAbsolutePath();
      Image tempImage = new Image(new FileInputStream(folderPath));
      image.setImage(tempImage);
    }
  }
}
