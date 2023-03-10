package edu.wpi.cs3733.C23.teamC.Messages;

import edu.wpi.cs3733.C23.teamC.Home.MenuController;
import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.database.hibernate.AlertEntity;
import edu.wpi.cs3733.C23.teamC.database.hibernate.MessagesEntity;
import edu.wpi.cs3733.C23.teamC.database.hibernate.StaffEntity;
import edu.wpi.cs3733.C23.teamC.navigation.Navigation;
import edu.wpi.cs3733.C23.teamC.navigation.Screen;
import java.text.SimpleDateFormat;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

public class MessageBox {

  @Getter @Setter private int unreadMessages = 0;

  public MessageBox() {}

  public HBox addHomeMessage(MessagesEntity message) {
    HBox newMessage = new HBox();
    newMessage.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
    newMessage.setId(Integer.toString(message.getMessageid()));
    newMessage.getStyleClass().add("unread");
    unreadMessages++;
    newMessage.getStylesheets().add("edu/wpi/cs3733/C23/teamC/styles/message.css");
    newMessage.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            MenuController.setSelectedMessage(
                new SelectedMessage("Message", message.getMessageid()));
            Navigation.navigate(Screen.MESSAGES);
          }
        });
    HBox textBox = new HBox();
    textBox.setAlignment(Pos.CENTER_LEFT);
    Label label = new Label();
    if (message.getSenderid().equals("SYSTEM")) label.setText(message.getMessage());
    else {
      StaffEntity sender = Main.db.getStaff(message.getSenderid());
      String firstName = sender.getFirstname();
      String lastName = sender.getLastname();
      String messagetxt = "New Message from " + firstName + " " + lastName;
      label.setText(messagetxt);
    }
    textBox.getChildren().add(label);
    HBox dateBox = new HBox();
    dateBox.setAlignment(Pos.CENTER_RIGHT);
    Text date = new Text();
    date.setText(new SimpleDateFormat("MM/dd/yy h:mm a").format(message.getDate()));
    dateBox.getChildren().add(date);
    HBox spacerBox = new HBox();
    HBox.setHgrow(spacerBox, Priority.ALWAYS);
    newMessage.getChildren().addAll(textBox, spacerBox, dateBox);
    return newMessage;
  }

  public HBox addHomeAlert(AlertEntity alert) {
    HBox newAlert = new HBox();
    newAlert.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
    newAlert.setId(Integer.toString(alert.getAlertid()));
    newAlert.getStyleClass().add("alert");
    unreadMessages++;
    newAlert.getStylesheets().add("edu/wpi/cs3733/C23/teamC/styles/message.css");
    newAlert.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            MenuController.setSelectedMessage(new SelectedMessage("Alert", alert.getAlertid()));
            Navigation.navigate(Screen.MESSAGES);
          }
        });
    HBox textBox = new HBox();
    textBox.setAlignment(Pos.CENTER_LEFT);
    Label label = new Label();
    label.setText("ALERT: " + alert.getMessage());
    textBox.getChildren().add(label);
    HBox dateBox = new HBox();
    dateBox.setAlignment(Pos.CENTER_RIGHT);
    Text date = new Text();
    date.setText(new SimpleDateFormat("MM/dd/yy").format(alert.getDate()));
    dateBox.getChildren().add(date);
    HBox spacerBox = new HBox();
    HBox.setHgrow(spacerBox, Priority.ALWAYS);
    newAlert.getChildren().addAll(textBox, spacerBox, dateBox);
    return newAlert;
  }

  public VBox addMessageBox(MessagesEntity message) {
    VBox newMessage = new VBox();
    newMessage.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
    newMessage.setId(Integer.toString(message.getMessageid()));
    if (message.getRead()) {
      newMessage.getStyleClass().add("read");
    } else {
      newMessage.getStyleClass().add("unread");
      MessagesController.setTotalUnread(MessagesController.getTotalUnread() + 1);
    }
    newMessage.getStylesheets().add("edu/wpi/cs3733/C23/teamC/styles/message.css");
    newMessage.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            if (!message.getRead()) {
              message.setRead(true);
              MessagesController.setTotalUnread(MessagesController.getTotalUnread() - 1);
            }
            newMessage.getStyleClass().clear();
            newMessage.getStyleClass().add("selected");
            MessagesController.setSelectedMessage(
                new SelectedMessage("Message", message.getMessageid()));
          }
        });
    HBox topBox = new HBox();
    HBox fromTextBox = new HBox();
    HBox spacer = new HBox();
    HBox dateBox = new HBox();
    Text fromText = new Text();
    fromText.setFont(Font.font(16));
    StaffEntity sender = Main.db.getStaff(message.getSenderid());
    if (sender.getStaffid().equals("SYSTEM")) {
      fromText.setText("From: Service Request System");
    } else {
      String firstName = sender.getFirstname();
      String lastName = sender.getLastname();
      fromText.setText("From: " + firstName + " " + lastName);
    }
    fromTextBox.getChildren().add(fromText);
    Text date = new Text();
    date.setFont(Font.font(16));
    date.setText(new SimpleDateFormat("MM/dd/yy h:mm a").format(message.getDate()));
    dateBox.getChildren().add(date);
    HBox.setHgrow(spacer, Priority.ALWAYS);
    topBox.getChildren().addAll(fromTextBox, spacer, dateBox);
    Label messageTxt = new Label();
    messageTxt.setText(message.getMessage());
    messageTxt.setWrapText(true);
    Insets insets = new Insets(10, 5, 10, 5);
    messageTxt.setPadding(insets);
    newMessage.getChildren().addAll(topBox, messageTxt);
    return newMessage;
  }

  public VBox addMessageAlert(AlertEntity alert) {
    VBox newAlert = new VBox();
    newAlert.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
    newAlert.setId(Integer.toString(alert.getAlertid()));
    newAlert.getStyleClass().add("alert");
    newAlert.getStylesheets().add("edu/wpi/cs3733/C23/teamC/styles/message.css");
    newAlert.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            newAlert.getStyleClass().clear();
            newAlert.getStyleClass().add("selected");
            MessagesController.setSelectedMessage(new SelectedMessage("Alert", alert.getAlertid()));
          }
        });
    HBox topBox = new HBox();
    HBox fromTextBox = new HBox();
    HBox spacer = new HBox();
    HBox dateBox = new HBox();
    Text fromText = new Text();
    fromText.setFont(Font.font(16));
    fromText.setText("New Alert:");
    fromTextBox.getChildren().add(fromText);
    Text date = new Text();
    date.setFont(Font.font(16));
    date.setText(new SimpleDateFormat("MM/dd/yy").format(alert.getDate()));
    dateBox.getChildren().add(date);
    HBox.setHgrow(spacer, Priority.ALWAYS);
    topBox.getChildren().addAll(fromTextBox, spacer, dateBox);
    Label messageTxt = new Label();
    messageTxt.setText(alert.getMessage());
    messageTxt.setWrapText(true);
    Insets insets = new Insets(10, 5, 10, 5);
    messageTxt.setPadding(insets);
    newAlert.getChildren().addAll(topBox, messageTxt);
    MessagesController.setTotalUnread(MessagesController.getTotalUnread() + 1);
    return newAlert;
  }
}
