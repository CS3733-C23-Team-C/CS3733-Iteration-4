package edu.wpi.cs3733.C23.teamC.controllers;

import edu.wpi.cs3733.C23.teamC.App;
import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.navigation.Navigation;
import edu.wpi.cs3733.C23.teamC.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import lombok.Getter;
import org.w3c.dom.Text;

public class ForgotPasswordController extends Throwable {

  @Getter private String currCode;
  @Getter private String currPassword;

  @Getter private String currFirstName;
  @Getter private String currLastName;

  @FXML private MFXTextField staffID;
  @FXML private MFXTextField firstNameField;
  @FXML private MFXTextField lastNameField;
  @FXML private MFXTextField emailAddress;

  @FXML private MFXButton submitButton;

  @FXML private MFXTextField valCode;

  @FXML private MFXButton valButton;

  @FXML private MFXButton backButton;

  @FXML private Text errorTxt;

  public javafx.scene.text.Text errorTextP1;

  public void clearFields() {
    staffID.clear();
    firstNameField.clear();
    lastNameField.clear();
    emailAddress.clear();
  }

  Session newSession = null;
  MimeMessage mimeMessage = null;

  @FXML
  public void initialize1() {
    errorTextP1.setText("");
  }

  public void submitButtonPressed(ActionEvent ActionEvent) throws MessagingException, IOException {
    initialize1();

    if (staffID.getText() != ""
        && firstNameField.getText() != ""
        && lastNameField.getText() != ""
        && emailAddress.getText() != "") {

      App.setTempUser(
          Main.db.getStaff3(staffID.getText(), firstNameField.getText(), lastNameField.getText()));

      if (App.getTempuser() == null) {
        clearFields();
        errorTextP1.setText("Invalid Staff ID, First Name or Last Name");
      } else {
        System.out.printf("the id matched an employee\n");

        Singleton singleton = Singleton.getInstance();
        singleton.setData(emailAddress.getText());

        Singleton2 singleton2 = Singleton2.getInstance();
        singleton2.setData(App.getTempuser().getPassword());

        Singleton3 singleton3 = Singleton3.getInstance();
        singleton3.setData(App.getTempuser().getPassword());
        //  initialize();
        Navigation.navigate(Screen.FORGOT_PASSWORD_SECOND_SCREEN);
      }
    } else {
      clearFields();
      errorTextP1.setText("Please fill out all of the above fields");
    }
  }

  public void backToLogin(MouseEvent mouseEvent) {
    Navigation.navigate(Screen.LOG_IN_PAGE);
  }
}
