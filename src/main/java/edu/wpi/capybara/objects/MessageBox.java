package edu.wpi.capybara.objects;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import edu.wpi.capybara.objects.hibernate.MessagesEntity;
import edu.wpi.capybara.objects.hibernate.StaffEntity;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class MessageBox {

  public MessageBox() {}

  public HBox addHomeMessage(MessagesEntity message) {
    HBox newMessage = new HBox();
    newMessage.setId(Integer.toString(message.getMessageid()));
    newMessage.getStyleClass().add("box");
    newMessage.getStylesheets().add("edu/wpi/capybara/styles/unreadMessage.css");
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
      label.setText("New Message from " + firstName + " " + lastName);
    }
    HBox dateBox = new HBox();
    dateBox.setAlignment(Pos.CENTER_RIGHT);
    Text date = new Text();
    date.setText(message.getDate().toString());
    newMessage.getChildren().addAll(textBox, dateBox);
    return newMessage;
  }
}
