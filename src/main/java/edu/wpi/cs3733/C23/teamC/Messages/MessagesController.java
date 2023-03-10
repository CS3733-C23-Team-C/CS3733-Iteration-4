package edu.wpi.cs3733.C23.teamC.Messages;

import edu.wpi.cs3733.C23.teamC.App;
import edu.wpi.cs3733.C23.teamC.Home.MenuController;
import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.database.hibernate.AlertEntity;
import edu.wpi.cs3733.C23.teamC.database.hibernate.MessagesEntity;
import edu.wpi.cs3733.C23.teamC.database.hibernate.StaffEntity;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.text.SimpleDateFormat;
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
  @FXML private MFXTextField Filter;
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
  @Getter @Setter private static int totalUnread;

  public void initialize() {
    System.out.println("I am from MessageController.");
    newAlertButton.managedProperty().bind(newAlertButton.visibleProperty());
    newAlertButton.setVisible(App.getUser().getRole().equals("admin"));

    alerts = App.getUser().allNotReadAlerts();
    messages = Main.db.getMessages(App.getUser().getStaffid());
    highestID = 0;
    totalUnread = 0;
    previousMessage = new SelectedMessage("", Integer.MAX_VALUE);
    selectedMessage = new SelectedMessage("", Integer.MAX_VALUE);
    for (Integer key : messages.keySet()) {
      keyList.add(key);
    }
    keyList.sort(null);
    displayAlerts();
    displayMessages();
    // System.out.println(MenuController.getSelectedHomeMessage());
    if (totalUnread != 0) {
      if (MenuController.getSelectedMessage().getType().equals("Message")) {
        selectedMessage = MenuController.getSelectedMessage();
        VBox selectedMessageBox = messageBoxes.get(selectedMessage.getSelectedID());
        selectedMessageBox.getStyleClass().clear();
        selectedMessageBox.getStyleClass().add("selected");
        messages.get(selectedMessage.getSelectedID()).setRead(true);
        totalUnread--;
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
    if (totalUnread > 0) MenuController.messageNotiOn();
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
              messages.remove(selectedMessage.getSelectedID());
            } else if (selectedMessage.getType().equals("Alert")) {
              AlertEntity alert;
              for (int i = 0; i < alerts.size(); i++) {
                if (selectedMessage.getSelectedID() == alerts.get(i).getAlertid()) {
                  alert = alerts.get(i);
                  alert.markRead(App.getUser());
                  alerts.remove(i);
                }
              }
              VBox deletedMessage = alertBoxes.get(selectedMessage.getSelectedID());
              vbox.getChildren().remove(deletedMessage);
              deleteButton.setDisable(true);
              replyButton.setDisable(true);
              deleteButton.setText("Delete");
              totalUnread--;
              selectedMessage = new SelectedMessage("", Integer.MAX_VALUE);
              if (totalUnread == 0) MenuController.messageNotiOff();
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

    newAlertButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            NewAlertDialogController.showAlertDialog();
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
            if (totalUnread != 0) MenuController.messageNotiOn();
            else MenuController.messageNotiOff();
            System.out.println("Unread: " + totalUnread);
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
  }

  public void displayAlerts() {
    for (int i = (alerts.size() - 1); i >= 0; i--) {
      AlertEntity alert = alerts.get(i);
      VBox newAlert = messageBox.addMessageAlert(alert);
      alertBoxes.put(alert.getAlertid(), newAlert);
      vbox.getChildren().add(newAlert);
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
    totalUnread = 0;
    displayAlerts();
    displayMessages();
  }

  public void enableFilter() {
    String filterText = Filter.getText(), filterTextLower = filterText.toLowerCase();
    vbox.getChildren().clear();
    messageBoxes = new HashMap<>();
    alertBoxes = new HashMap<>();
    previousMessage = new SelectedMessage("", Integer.MAX_VALUE);

    for (int i = (alerts.size() - 1); i >= 0; i--) {
      AlertEntity alert = alerts.get(i);
      if (alertFilterText(alert).contains(filterTextLower)) {
        VBox newAlert = messageBox.addMessageAlert(alert);
        alertBoxes.put(alert.getAlertid(), newAlert);
        vbox.getChildren().add(newAlert);
        totalUnread--;
        // System.out.println(alert.getMessage());
      }
    }

    for (int i = (keyList.size() - 1); i >= 0; i--) {
      MessagesEntity message = messages.get(keyList.get(i));
      if (messageFilterText(message).contains(filterTextLower)) {
        VBox newMessage = messageBox.addMessageBox(message);
        if (!message.getRead()) totalUnread--;
        int messageID = message.getMessageid();
        messageBoxes.put(messageID, newMessage);
        vbox.getChildren().add(newMessage);
        if (messageID > highestID) highestID = messageID;
        // System.out.println(message.getRead());
      }
    }
    System.out.println(totalUnread);
  }

  private String messageFilterText(MessagesEntity message) {
    StaffEntity sender = Main.db.getStaff(message.getSenderid());
    String s =
        message.getMessage()
            + (sender.getStaffid().equals("SYSTEM")
                ? ("Service Request System")
                : (sender.getFirstname() + " " + sender.getLastname()))
            + (new SimpleDateFormat("MM/dd/yy h:mm a").format(message.getDate()));
    // System.out.println("Testing : " + s);
    return s.toLowerCase();
  }

  private String alertFilterText(AlertEntity alert) {
    String s =
        alert.getMessage() + (new SimpleDateFormat("MM/dd/yy h:mm a").format(alert.getDate()));
    return s.toLowerCase();
  }
}
