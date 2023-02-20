package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.Main;
import edu.wpi.capybara.objects.MessageBox;
import edu.wpi.capybara.objects.hibernate.MessagesEntity;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class MessagesController {
  @FXML private VBox vbox;
  @FXML private MFXButton refreshButton;
  @FXML private MFXButton replyButton;
  @FXML private MFXButton deleteButton;
  private static MFXButton sReplyButton;
  private static MFXButton sDeleteButton;
  @FXML private MFXButton newMessageButton;
  private HashMap<Integer, MessagesEntity> messages;
  private HashMap<Integer, VBox> messageBoxes = new HashMap<>();
  private MessageBox messageBox = new MessageBox();
  private static int selectedID;

  public void initialize() {
    System.out.println("I am from MessageController.");
    messages = Main.db.getMessages(App.getUser().getStaffid());
    for (MessagesEntity message : messages.values()) {
      VBox newMessage = messageBox.addMessageBox(message);
      messageBoxes.put(message.getMessageid(), newMessage);
      vbox.getChildren().add(newMessage);
    }
    sReplyButton = replyButton;
    sDeleteButton = deleteButton;

    replyButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            try {
              String recipientID = messages.get(selectedID).getSenderid();
              NewMessageDialogController.showMessageDialogReply(recipientID);
            } catch (Exception e) {
              System.out.println("it broke");
            }
          }
        });

    deleteButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            Main.db.deleteMessage(selectedID);
            VBox deletedMessage = messageBoxes.get(selectedID);
            vbox.getChildren().remove(deletedMessage);
          }
        });
    newMessageButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            NewMessageDialogController.showMessageDialog();
          }
        });
  }

  public static void setSelectedMessage(int messageID) {
    selectedID = messageID;
    sReplyButton.setDisable(false);
    sDeleteButton.setDisable(false);
  }
}
