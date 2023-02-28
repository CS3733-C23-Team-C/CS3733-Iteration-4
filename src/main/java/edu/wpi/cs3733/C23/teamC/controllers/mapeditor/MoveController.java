package edu.wpi.cs3733.C23.teamC.controllers.mapeditor;

import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.objects.PFLocation;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.AlertEntity;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.LocationnameEntity;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.MoveEntity;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.StaffEntity;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
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
  // @FXML MFXTextField newLocation;
  @FXML MFXButton clear;
  @FXML MFXButton submit;

  @FXML protected Text dateError;
  @FXML protected Text moveAdded;
  private final ObservableList<String> locations = FXCollections.observableArrayList();

  @FXML
  public void initialize() {

    //moveAdded.setVisible(false);

    for (LocationnameEntity allLocations : Main.db.getLocationnames().values()) {
      currentLocation.getItems().add(allLocations.getLongname());
    }

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
    /*newLocation.setOnKeyReleased(
    new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        validateButton();
      }
    });*/

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
    if (currentLocation.getValue() != null && moveDate.getValue() != null) valid = true;
    submit.setDisable(!valid);
  }

  public void clear() {
    currentLocation.clear();
    // newLocation.clear();
    moveDate.clear();
    moveAdded.setVisible(false);
  }

  public void submission() {
    Date outputDate = Date.valueOf(moveDate.getValue());
    String currLocation = currentLocation.getValue();
    // String movedLocation = newLocation.getText();
    String nodeID = "";
    for (LocationnameEntity names : Main.db.getLocationnames().values()) {
      if (currLocation.equals(names.toString())) {
        PFLocation location = new PFLocation(names);
        nodeID = location.getNodeId(outputDate);
      }
    }

    // System.out.println("nodeid: " + movedLocation);
    MoveEntity newMove = new MoveEntity(nodeID, currLocation, outputDate);
    clear();
    Main.db.addMove(newMove);
<<<<<<< HEAD
    moveAdded.setVisible(true);
=======
    java.util.Date date = new java.util.Date();
    int alertID = Main.db.getNewAlertID();
    AlertEntity newAlert =
        new AlertEntity(
            alertID,
            new java.sql.Date(date.getTime()),
            currLocation + " is moving to " + nodeID + " on " + outputDate.toString());
    Main.db.addAlert(newAlert);
    Map<String, StaffEntity> staffMap = Main.db.getStaff();
    List<StaffEntity> staffList = new ArrayList<>();
    for (StaffEntity staff : staffMap.values()) {
      staffList.add(staff);
    }
    newAlert.addStaff(staffList);
>>>>>>> main
  }
}
