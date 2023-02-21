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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

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
  @Getter private static int selectedID;
  @Setter @Getter private static int previousID;
  private int highestID;

  public void initialize() {
    System.out.println("I am from MessageController.");
    messages = Main.db.getMessages(App.getUser().getStaffid());
    highestID = 0;
    previousID = Integer.MAX_VALUE;
    for (MessagesEntity message : messages.values()) {
      VBox newMessage = messageBox.addMessageBox(message);
      int messageID = message.getMessageid();
      messageBoxes.put(messageID, newMessage);
      vbox.getChildren().add(newMessage);
      if (messageID > highestID) highestID = messageID;
    }
    System.out.println(MenuController.getSelectedHomeMessage());
    if (MenuController.getSelectedHomeMessage() != 0) {
      System.out.println("got here");
      selectedID = MenuController.getSelectedHomeMessage();
      VBox selectedMessage = messageBoxes.get(selectedID);
      selectedMessage.getStyleClass().clear();
      selectedMessage.getStyleClass().add("selected");
      messages.get(selectedID).setRead(true);
      MenuController.setSelectedHomeMessage(0);
      replyButton.setDisable(false);
      deleteButton.setDisable(false);
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
            deleteButton.setDisable(true);
            replyButton.setDisable(true);
          }
        });
    newMessageButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            NewMessageDialogController.showMessageDialog();
          }
        });
    refreshButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            refreshMap();
          }
        });
    vbox.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            if (previousID != selectedID && previousID != Integer.MAX_VALUE) {
              VBox oldBox = messageBoxes.get(previousID);
              oldBox.getStyleClass().clear();
              oldBox.getStyleClass().add("read");
            }
            previousID = selectedID;
          }
        });
  }

  public static void setSelectedMessage(int messageID) {
    selectedID = messageID;
    System.out.println(selectedID);
    sReplyButton.setDisable(false);
    sDeleteButton.setDisable(false);
  }

  public void refreshMap() {
    HashMap<Integer, MessagesEntity> updatedMap =
        Main.db.getMessages(App.getUser().getStaffid(), highestID);
    for (MessagesEntity message : updatedMap.values()) {
      messages.put(message.getMessageid(), message);
      VBox newMessage = messageBox.addMessageBox(message);
      messageBoxes.put(message.getMessageid(), newMessage);
      vbox.getChildren().add(newMessage);
    }
  }
}
