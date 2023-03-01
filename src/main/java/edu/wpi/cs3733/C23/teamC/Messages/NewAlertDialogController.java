package edu.wpi.cs3733.C23.teamC.Messages;

import edu.wpi.cs3733.C23.teamC.App;
import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.database.hibernate.AlertEntity;
import edu.wpi.cs3733.C23.teamC.database.hibernate.StaffEntity;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

public class NewAlertDialogController {
  private static MFXTextField sReceivingID;
  @FXML private MFXTextField message;
  @FXML private MFXButton cancelButton;
  @FXML private MFXButton sendAlertButton;
  @FXML private Text sentTxt;
  @FXML private VBox vbox;

  public void initialize() {

    message.setOnKeyReleased(
        new EventHandler<KeyEvent>() {
          @Override
          public void handle(KeyEvent event) {
            validateButton();
            if (event.getCode().equals(KeyCode.ENTER) && !sendAlertButton.isDisabled()) sendAlert();
          }
        });

    cancelButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            closeMessageDialog();
          }
        });

    sendAlertButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            sendAlert();
          }
        });
  }

  public static void showAlertDialog() {
    Stage dialog;
    dialog = new Stage();
    dialog.initModality(Modality.WINDOW_MODAL);
    dialog.initOwner(App.getPrimaryStage().getOwner());

    final FXMLLoader loader = new FXMLLoader(App.class.getResource("views/NewAlertDialog.fxml"));
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
    if (!message.getText().equals("")) sendAlertButton.setDisable(false);
  }

  public void sendAlert() {
    java.util.Date date = new java.util.Date();
    int alertID = Main.db.getNewAlertID();
    AlertEntity newAlert =
        new AlertEntity(alertID, new java.sql.Date(date.getTime()), message.getText());
    Main.db.addAlert(newAlert);
    Map<String, StaffEntity> staffMap = Main.db.getStaff();
    List<StaffEntity> staffList = new ArrayList<>();
    for (StaffEntity staff : staffMap.values()) {
      staffList.add(staff);
    }
    newAlert.addStaff(staffList);
    vbox.setVisible(false);
    sentTxt.setVisible(true);
  }
}
