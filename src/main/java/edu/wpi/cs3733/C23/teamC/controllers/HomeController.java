package edu.wpi.cs3733.C23.teamC.controllers;

import edu.wpi.cs3733.C23.teamC.App;
import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.objects.MessageBox;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.MessagesEntity;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import java.util.ArrayList;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lombok.Getter;

public class HomeController {

  @FXML private Label welcomeTxt;
  @FXML private Text newMessageTxt;
  @FXML private MFXScrollPane scrollPane;
  @FXML private VBox vbox;
  @Getter static int unreadMessagesCount;

  final String SECRET_PASSWORD = "team coaching";

  private Map<Integer, MessagesEntity> messages;

  @Getter private MessageBox messageBox = new MessageBox();

  private ArrayList<Integer> keyList = new ArrayList<>();

  /** Initialize controller by FXML Loader. */
  @FXML
  public void initialize() {
    System.out.println("I am from HomeController.");

    if (welcomeTxt != null) {
      String text = "Welcome back, " + App.getUser().getFirstname() + "!";
      welcomeTxt.setText(text);
    }
    messages = Main.db.getMessages(App.getUser().getStaffid());

    for (Integer key : messages.keySet()) {
      keyList.add(key);
    }
    keyList.sort(null);
    showMessages();

    newMessageTxt.setText("You have " + messageBox.getUnreadMessages() + " new messages:");
    unreadMessagesCount = messageBox.getUnreadMessages();

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
    for (int i = (keyList.size() - 1); i >= 0; i--) {
      MessagesEntity message = messages.get(keyList.get(i));
      if (!message.getRead()) {
        HBox newMessage = messageBox.addHomeMessage(message);
        vbox.getChildren().add(newMessage);
      }
    }
  }
}
