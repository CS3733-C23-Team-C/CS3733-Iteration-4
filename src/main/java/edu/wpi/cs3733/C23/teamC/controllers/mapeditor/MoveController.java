package edu.wpi.cs3733.C23.teamC.controllers.mapeditor;

import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.LocationnameEntity;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.sql.Date;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class MoveController {
  @FXML MFXDatePicker moveDate;
  @FXML MFXFilterComboBox<String> currentLocation;
  @FXML MFXFilterComboBox<String> newLocation;
  @FXML MFXButton clear;
  @FXML MFXButton submit;

  @FXML protected Text dateError;
  private final ObservableList<String> locations = FXCollections.observableArrayList();

  @FXML
  public void initialize() {

    for (LocationnameEntity allLocations : Main.db.getLocationnames().values()) {
      locations.add(allLocations.getLongname());
    }

    Map<String, LocationnameEntity> nodes = Main.db.getLocationnames();

    currentLocation.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            validateButton();
          }
        });

    currentLocation.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            currentLocation.show();
          }
        });
    newLocation.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            validateButton();
          }
        });

    newLocation.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            newLocation.show();
          }
        });
    moveDate.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            java.util.Date someDate = new java.util.Date();
            Date currDate = new java.sql.Date(someDate.getTime());
            dateError.setVisible(false);
            if (currDate.compareTo(Date.valueOf(moveDate.getValue())) > 0)
              dateError.setVisible(true);
            else validateButton();
          }
        });
  }

  public void
      validateButton() { // ensures that information has been filled in before allowing submission
    boolean valid = false;
    if (newLocation.getValue() != null
        && currentLocation.getValue() != null
        && moveDate.getValue() != null) valid = true;
    submit.setDisable(!valid);
  }

  public void clear() {
    currentLocation.clear();
    newLocation.clear();
    moveDate.clear();
  }
}
