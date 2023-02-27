package edu.wpi.cs3733.C23.teamC.controllers;

import edu.wpi.cs3733.C23.teamC.App;
import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.LocationnameEntity;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.MoveEntity;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.sql.Date;
import java.util.Locale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class KioskDialogController {

  public static Stage dialog;

  @FXML private MFXComboBox<String> targetField;
  @FXML private Text question2Text, question3Text, question4Text;
  @FXML private HBox question2, question3, question4;
  @FXML private MFXButton createKioskButton;
  private MFXFilterComboBox<LocationnameEntity> locationBox;
  private MFXDatePicker datePicker;
  private ObservableList<LocationnameEntity> locations;

  public static void showDialog() {
    dialog = new Stage();
    dialog.initModality(Modality.WINDOW_MODAL);
    dialog.initOwner(App.getPrimaryStage().getOwner());

    final FXMLLoader loader = new FXMLLoader(App.class.getResource("views/KioskDialog.fxml"));
    BorderPane root;
    try {
      root = loader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    dialog.setScene(new Scene(root));

    dialog.setOnCloseRequest(
        (event -> {
          Stage oldDialog = dialog;
          dialog = null;
          oldDialog.close();
        }));
    dialog.showAndWait();
  }

  public void initialize() {
    targetField.setItems(FXCollections.observableArrayList("Move", "Custom"));

    locations = FXCollections.observableArrayList(Main.getRepo().getLocationNames().values());
    locations.sort(Comparator.comparing(LocationnameEntity::toString));

    reset();
  }

  public void onTargetSelection() {
    if (targetField.getValue() == null) return;
    if (targetField.getValue().equals("Move")) {
      question2Text.setText("Which location?");
      question2Text.setVisible(true);

      locationBox = new MFXFilterComboBox<>(locations);
      locationBox.setPrefSize(137, 50);
      locationBox.setOnAction(this::validate);

      question3Text.setText("What date is the move?");
      question3Text.setVisible(true);

      datePicker = new MFXDatePicker(Locale.US, YearMonth.now());
      datePicker.setPrefSize(137, 50);
      datePicker.setOnAction(this::validate);

      question2.getChildren().add(locationBox);
      question3.getChildren().add(datePicker);
    } else if (targetField.getValue().equals("Custom")) {

    }
  }

  public void reset() {
    question2Text.setVisible(false);
    question3Text.setVisible(false);
    question4Text.setVisible(false);

    question2.getChildren().remove(locationBox);
    question3.getChildren().remove(datePicker);

    targetField.clearSelection();

    createKioskButton.setDisable(true);
  }

  public void validate(ActionEvent event) {
    if (targetField.getValue() == null) {
      createKioskButton.setDisable(true);
      return;
    }
    if (targetField.getValue().equals("Move")) {
      LocationnameEntity location = locationBox.getValue();
      LocalDate locationDate = datePicker.getValue();

      if (location == null || locationDate == null) {
        createKioskButton.setDisable(true);
        return;
      }

      Date date = Date.valueOf(locationDate);

      MoveEntity found = null;
      for (MoveEntity move: Main.getRepo().getMoves()) {
        if (move.getLocationName().equals(location) && move.getMovedate().equals(date)) {
          found = move;
          break;
        }
      }

      if (found == null) {
        question4Text.setText("Unable to find move at " + location.getLongname() + " on " + locationDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
      }
    }
  }

  public void closeDialog() {
    Stage oldDialog = dialog;
    dialog = null;
    oldDialog.close();
  }

  public void createKiosk() {
    //todo set vals for kiosk
    //todo navigate to kiosk
    closeDialog();
  }
}
