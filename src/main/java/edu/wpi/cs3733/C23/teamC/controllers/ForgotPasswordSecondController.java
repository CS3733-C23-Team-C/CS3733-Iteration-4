package edu.wpi.cs3733.C23.teamC.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import org.w3c.dom.Text;

public class ForgotPasswordSecondController extends ForgotPasswordController {

  public javafx.scene.text.Text passwordEnter;
  public javafx.scene.text.Text errorText2;
  private String dat;

  @FXML MFXButton valButton;

  //  @FXML
  //  MFXTextField valText;

  @FXML MFXTextField valCode;

  @FXML Text printPasswordText;
  // bring over email
  // bring over genderated code

  @FXML
  public void initialize() throws AddressException, MessagingException, IOException {
    errorText2.setText("");

    Random ran = new Random();
    int top = 10;
    char data = ' ';
    dat = "";

    for (int i = 0; i <= top; i++) {
      data = (char) (ran.nextInt(25) + 97);
      dat = data + dat;
      for (int b = 0; b <= ran.nextInt(5); b++) {
        Random rand = new Random();
        int rand_int = rand.nextInt(9);
        //  System.out.println("rand_intStr" + rand_int);
        // char rand_intStr = (char) rand_int;
        String rand_intStr = Integer.toString(rand_int);
        // System.out.println("rand_intStr" + rand_intStr);

        dat = rand_intStr + dat;
      }
    }

    System.out.println(dat);

    ForgotPasswordSecondController mail = new ForgotPasswordSecondController();
    mail.setupServerProperties();
    mail.draftEmail(dat);
    mail.sendEmail();
  }

  public void printPasswordText() {

    Singleton2 singleton2 = Singleton2.getInstance();
    passwordEnter.setText("Your Password is " + singleton2.getData());

    System.out.println(singleton2.getData());
  }

  public void validateButton(ActionEvent Actionevent) throws MessagingException, IOException {

    // validateButton1();
    System.out.println(dat);
    System.out.println(valCode.getText());

    String finalGen;
    if (valCode.getText().equals(dat)) {

      // show password
      System.out.println("you did it!!!!!!!!!");
      errorText2.setText("");

      printPasswordText();

      //

      // Navigation.navigate(Screen.LOG_IN_PAGE);

    } else {
      // System.out.println("you did it!!!!!!!!!");
      errorText2.setText("The code you entered is incorrect, please try again");

      System.out.println("you didasdfasdfasdfasfasdfasdfasdfasd");
      valCode.clear();

      // show error text
    }
  }

  //  public void printPasswordText() {
  //
  //
  //    printPasswordText.setData(Main.db.getStaff().());
  //  }

  public void sendEmail() throws MessagingException {

    String fromUser = "cutecapybarabwh@gmail.com";
    String fromUserPassword = "vpyffzxmxbgcwcee";

    String emailHost = "smtp.gmail.com";
    Transport transport = newSession.getTransport("smtp");
    transport.connect(emailHost, fromUser, fromUserPassword);
    transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
    transport.close();
    System.out.println("Email succcessfully sent");
  }

  public void draftEmail(String code) throws AddressException, MessagingException, IOException {

    // String email = emailAddress.getText();

    Singleton singleton = Singleton.getInstance();
    System.out.println(singleton.getData());

    Singleton3 singleton3 = Singleton3.getInstance();

    // singleton.getData()

    String[] emailRecipients = {singleton.getData()};

    String emailBody = "Your Code is \n \n" + code;
    String emailSubject = "Log In Verification Code from Brigham and Women's Hospital";
    mimeMessage = new MimeMessage(newSession);

    for (int i = 0; i < emailRecipients.length; i++) {
      mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailRecipients[i]));
    }

    mimeMessage.setSubject(emailSubject);
    MimeBodyPart bodyPart = new MimeBodyPart();
    bodyPart.setText(
        "Hello "
            + singleton3.getData()
            + ",\n \n \t This is Brigum and Women's Hospital contacting you to inform you that you have forgotten your password. If you have not tried to search for your password, CONTACT YOUR MANAGER AS SOON AS POSSIBLE.\n \n"
            + emailBody);
    MimeMultipart multiPart = new MimeMultipart();
    multiPart.addBodyPart(bodyPart);
    mimeMessage.setContent(multiPart);
  }

  public void setupServerProperties() {
    Properties properties = System.getProperties();
    properties.put("mail.smtp.port", "587");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    newSession = Session.getDefaultInstance(properties, null);
  }
}
