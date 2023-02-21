package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.Main;
import edu.wpi.capybara.objects.MessageBox;
import edu.wpi.capybara.objects.hibernate.MessagesEntity;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import java.awt.*;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

public class HomeController {

  @FXML private Label welcomeTxt;
  @FXML private Text newMessageTxt;
  @FXML private MFXScrollPane scrollPane;
  @FXML private VBox vbox;

  final String SECRET_PASSWORD = "team coaching";

  private HashMap<Integer, MessagesEntity> messages;

  private MessageBox messageBox = new MessageBox();
  @Getter @Setter private static int newMessageCount;

  /** Initialize controller by FXML Loader. */
  @FXML
  public void initialize() {
    System.out.println("I am from HomeController.");

    if (welcomeTxt != null) {
      String text = "Welcome back, " + App.getUser().getFirstname() + "!";
      welcomeTxt.setText(text);
    }
    newMessageCount = 0;
    messages = Main.db.getMessages(App.getUser().getStaffid());
    showMessages();
    newMessageTxt.setText("You Have " + newMessageCount + " New Messages:");

    //    submit.setOnMouseClicked(event -> {});
  }

  /**
   * FXML Injected Method that handles the submit button.
   *
   * @param event The event that triggered the method.
   */
  @FXML
  private void handleButtonSubmit(ActionEvent event) {
    /*
     1. Get the text the user input
     2. Validate it against the correct information
     3. Display to the user whether their input was correct or not
    */

  }

  /**
   * Checks if a user's password is correct.
   *
   * @param input The user's password.
   * @return True if the password is correct.
   */
  private boolean validate(final String input) {
    return input.equals(SECRET_PASSWORD);
  }

  public void showMessages() {
    for (MessagesEntity message : messages.values()) {
      System.out.println(message.getMessage());
      if (!message.getRead()) {
        newMessageCount++;
        HBox newMessage = messageBox.addHomeMessage(message);
        vbox.getChildren().add(newMessage);
      }
    }
  }
}
