package edu.wpi.capybara.objects;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.controllers.HomeController;
import edu.wpi.capybara.controllers.MenuController;
import edu.wpi.capybara.controllers.MessagesController;
import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import edu.wpi.capybara.objects.hibernate.MessagesEntity;
import edu.wpi.capybara.objects.hibernate.StaffEntity;
import java.text.SimpleDateFormat;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MessageBox {

  public MessageBox() {}

  public HBox addHomeMessage(MessagesEntity message) {
    HBox newMessage = new HBox();
    newMessage.setId(Integer.toString(message.getMessageid()));
    newMessage.getStyleClass().add("unread");
    newMessage.getStylesheets().add("edu/wpi/capybara/styles/message.css");
    newMessage.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
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

  public VBox addMessageBox(MessagesEntity message) {
    VBox newMessage = new VBox();
    newMessage.setId(Integer.toString(message.getMessageid()));
    if (message.getRead()) newMessage.getStyleClass().add("read");
    else newMessage.getStyleClass().add("unread");
    newMessage.getStylesheets().add("edu/wpi/capybara/styles/message.css");
    newMessage.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            if (!message.getRead()) {
              message.setRead(true);
              HomeController.setNewMessageCount(HomeController.getNewMessageCount() - 1);
              MenuController.updateMessageNotif();
            }
            newMessage.getStyleClass().clear();
            newMessage.getStyleClass().add("selected");
            MessagesController.setSelectedMessage(message.getMessageid());
          }
        });
    HBox topBox = new HBox();
    HBox fromTextBox = new HBox();
    HBox spacer = new HBox();
    HBox dateBox = new HBox();
    Text fromText = new Text();
    fromText.setFont(Font.font(16));
    StaffEntity sender = Main.db.getStaff(message.getSenderid());
    if(sender.equals("SYSTEM")) fromText.setText("");
    else{
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
}
