package edu.wpi.cs3733.C23.teamC.controllers;

import edu.wpi.cs3733.C23.teamC.App;
import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.MessagesEntity;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NewMessageDialogController {
  @FXML private MFXTextField receivingID;
  private static MFXTextField sReceivingID;
  @FXML private MFXTextField message;
  @FXML private MFXButton cancelButton;
  @FXML private MFXButton sendMessageButton;
  @FXML private Text sentTxt;
  @FXML private VBox vbox;

  public void initialize() {
    receivingID.setOnKeyReleased(
        new EventHandler<KeyEvent>() {
          @Override
          public void handle(KeyEvent event) {
            validateButton();
          }
        });

    message.setOnKeyReleased(
        new EventHandler<KeyEvent>() {
          @Override
          public void handle(KeyEvent event) {
            validateButton();
            if (event.getCode().equals(KeyCode.ENTER) && !sendMessageButton.isDisabled())
              sendMessage();
          }
        });

    cancelButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            closeMessageDialog();
          }
        });

    sendMessageButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            sendMessage();
          }
        });
    sReceivingID = receivingID;
  }

  public static void showMessageDialogReply(String recipientID) {
    Stage dialog;
    dialog = new Stage();
    dialog.initModality(Modality.WINDOW_MODAL);
    dialog.initOwner(App.getPrimaryStage().getOwner());

    final FXMLLoader loader = new FXMLLoader(App.class.getResource("views/NewMessageDialog.fxml"));
    BorderPane root;
    try {
      root = loader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    sReceivingID.setText(recipientID);
    System.out.println(sReceivingID.getText());
    dialog.setScene(new Scene(root));
    dialog.showAndWait();
  }

  public static void showMessageDialog() {
    Stage dialog;
    dialog = new Stage();
    dialog.initModality(Modality.WINDOW_MODAL);
    dialog.initOwner(App.getPrimaryStage().getOwner());

    final FXMLLoader loader = new FXMLLoader(App.class.getResource("views/NewMessageDialog.fxml"));
    BorderPane root;
    try {
      root = loader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    dialog.setScene(new Scene(root));
    dialog.showAndWait();
  }

  public void closeMessageDialog() {
    Stage dialog;
    dialog = (Stage) cancelButton.getScene().getWindow();
    dialog.close();
  }

  public void validateButton() {
    if (!receivingID.getText().equals("") && !message.getText().equals(""))
      sendMessageButton.setDisable(false);
  }

  public void sendMessage() {
    Date date = new java.util.Date();
    Timestamp time = new Timestamp(date.getTime());
    int messageID = Main.db.generateMessageID();
    String senderID = App.getUser().getStaffid();
    MessagesEntity newMessage =
        new MessagesEntity(
            messageID, senderID, receivingID.getText(), time, message.getText(), false);
    Main.db.addMessage(newMessage);
    vbox.setVisible(false);
    sentTxt.setVisible(true);
  }
}
