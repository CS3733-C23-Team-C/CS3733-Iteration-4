package edu.wpi.cs3733.C23.teamC.controllers;

import edu.wpi.cs3733.C23.teamC.App;
import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.objects.MessageBox;
import edu.wpi.cs3733.C23.teamC.objects.SelectedMessage;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.AlertEntity;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.MessagesEntity;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  @FXML private MFXButton newAlertButton;
  private Map<Integer, MessagesEntity> messages;
  private List<AlertEntity> alerts;
  private Map<Integer, VBox> messageBoxes = new HashMap<>();
  private Map<Integer, VBox> alertBoxes = new HashMap<>();
  private MessageBox messageBox = new MessageBox();
  private int highestID;
  private ArrayList<Integer> keyList = new ArrayList<>();
  @Getter private static SelectedMessage selectedMessage;
  @Getter @Setter private static SelectedMessage previousMessage;

  public void initialize() {
    System.out.println("I am from MessageController.");
    newAlertButton.managedProperty().bind(newAlertButton.visibleProperty());
    newAlertButton.setVisible(App.getUser().getRole().equals("admin"));

    alerts = App.getUser().allNotReadAlerts();
    messages = Main.db.getMessages(App.getUser().getStaffid());
    highestID = 0;
    previousMessage = new SelectedMessage("", Integer.MAX_VALUE);
    selectedMessage = new SelectedMessage("", Integer.MAX_VALUE);
    for (Integer key : messages.keySet()) {
      keyList.add(key);
    }
    keyList.sort(null);
    displayAlerts();
    displayMessages();
    // System.out.println(MenuController.getSelectedHomeMessage());
    if (MenuController.getSelectedMessage().getSelectedID() != 0) {
      if (MenuController.getSelectedMessage().getType().equals("Message")) {
        selectedMessage = MenuController.getSelectedMessage();
        VBox selectedMessageBox = messageBoxes.get(selectedMessage.getSelectedID());
        selectedMessageBox.getStyleClass().clear();
        selectedMessageBox.getStyleClass().add("selected");
        messages.get(selectedMessage.getSelectedID()).setRead(true);
        messageBox.setUnreadMessages(messageBox.getUnreadMessages() - 1);
        replyButton.setDisable(false);
      } else if (MenuController.getSelectedMessage().getType().equals("Alert")) {
        selectedMessage = MenuController.getSelectedMessage();
        VBox selectedAlertBox = alertBoxes.get(selectedMessage.getSelectedID());
        selectedAlertBox.getStyleClass().clear();
        selectedAlertBox.getStyleClass().add("selected");
        replyButton.setDisable(true);
        deleteButton.setText("Dismiss");
      }
      deleteButton.setDisable(false);
      previousMessage = selectedMessage;
      MenuController.setSelectedMessage(new SelectedMessage("", 0));
    }
    if (messageBox.getUnreadMessages() > 0) MenuController.messageNotiOn();
    else MenuController.messageNotiOff();
    sReplyButton = replyButton;
    sDeleteButton = deleteButton;

    replyButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            try {
              int selectedID = 0;
              String recipientID = "";
              if (selectedMessage.getType().equals("Message")) {
                selectedID = selectedMessage.getSelectedID();
                recipientID = messages.get(selectedID).getSenderid();
              } else if (selectedMessage.getType().equals("Alert")) {

              }
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
            if (selectedMessage.getType().equals("Message")) {
              Main.db.deleteMessage(selectedMessage.getSelectedID());
              VBox deletedMessage = messageBoxes.get(selectedMessage.getSelectedID());
              vbox.getChildren().remove(deletedMessage);
              deleteButton.setText("Delete");
              deleteButton.setDisable(true);
              replyButton.setDisable(true);
            } else if (selectedMessage.getType().equals("Alert")) {
              // TODO
              AlertEntity alert;
              for (int i = 0; i < alerts.size(); i++) {
                if (selectedMessage.getSelectedID() == alerts.get(i).getAlertid()) {
                  alert = alerts.get(i);
                  alert.markRead(App.getUser());
                }
              }
              VBox deletedMessage = alertBoxes.get(selectedMessage.getSelectedID());
              vbox.getChildren().remove(deletedMessage);
              deleteButton.setDisable(true);
              replyButton.setDisable(true);
              deleteButton.setText("Delete");
              messageBox.setUnreadMessages(messageBox.getUnreadMessages() - 1);
            }
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
            if ((!previousMessage.getType().equals(selectedMessage.getType())
                    && previousMessage.getSelectedID() != Integer.MAX_VALUE)
                || (previousMessage.getType().equals(selectedMessage.getType())
                    && previousMessage.getSelectedID() != selectedMessage.getSelectedID())) {
              if (previousMessage.getType().equals("Alert")) {
                VBox oldBox = alertBoxes.get(previousMessage.getSelectedID());
                oldBox.getStyleClass().clear();
                oldBox.getStyleClass().add("alert");
              } else if (previousMessage.getType().equals("Message")) {
                VBox oldBox = messageBoxes.get(previousMessage.getSelectedID());
                oldBox.getStyleClass().clear();
                oldBox.getStyleClass().add("read");
              }
            }

            previousMessage = selectedMessage;
            if (messageBox.getUnreadMessages() != 0) MenuController.messageNotiOn();
            else MenuController.messageNotiOff();
            System.out.println(messageBox.getUnreadMessages());
          }
        });
  }

  public void displayMessages() {
    for (int i = (keyList.size() - 1); i >= 0; i--) {
      MessagesEntity message = messages.get(keyList.get(i));
      VBox newMessage = messageBox.addMessageBox(message);
      int messageID = message.getMessageid();
      messageBoxes.put(messageID, newMessage);
      vbox.getChildren().add(newMessage);
      if (messageID > highestID) highestID = messageID;
      // System.out.println(message.getRead());
    }
    System.out.println(messageBox.getUnreadMessages());
  }

  public void displayAlerts() {
    for (int i = (alerts.size() - 1); i >= 0; i--) {
      AlertEntity alert = alerts.get(i);
      VBox newAlert = messageBox.addMessageAlert(alert);
      alertBoxes.put(alert.getAlertid(), newAlert);
      vbox.getChildren().add(newAlert);
      System.out.println(alert.getMessage());
    }
  }

  public static void setSelectedMessage(SelectedMessage message) {
    selectedMessage = message;
    // System.out.println(selectedID);
    if (message.getType().equals("Message")) sReplyButton.setDisable(false);
    else if (message.getType().equals("Alert")) {
      sReplyButton.setDisable(true);
      sDeleteButton.setText("Dismiss");
    }
    sDeleteButton.setDisable(false);
  }

  public void refreshMap() {
    Main.db.refreshMessages();
    Map<Integer, MessagesEntity> updatedMap =
        Main.db.getMessages(App.getUser().getStaffid(), highestID);
    for (MessagesEntity message : updatedMap.values()) {
      messages.put(message.getMessageid(), message);
      keyList.add(message.getMessageid());
    }
    keyList.sort(null);
    vbox.getChildren().clear();
    alerts = App.getUser().allNotReadAlerts();
    displayAlerts();
    displayMessages();
  }
}
