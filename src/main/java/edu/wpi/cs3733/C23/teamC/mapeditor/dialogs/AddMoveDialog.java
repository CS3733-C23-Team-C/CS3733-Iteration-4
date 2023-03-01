package edu.wpi.cs3733.C23.teamC.mapeditor.dialogs;

import edu.wpi.cs3733.C23.teamC.App;
import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.database.hibernate.AlertEntity;
import edu.wpi.cs3733.C23.teamC.database.hibernate.MoveEntity;
import edu.wpi.cs3733.C23.teamC.database.hibernate.StaffEntity;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;

public class AddMoveDialog extends FXMLDialog<Void> {

  @FXML private MFXTextField nodeField;
  @FXML private MFXTextField locationField;
  @FXML private MFXDatePicker datePicker;

  @FXML private ButtonType okButton;

  public AddMoveDialog(Window owner) {
    super(owner, App.class.getResource("views/MoveDialog.fxml"));

    setTitle("Add Move");

    nodeField.prefWidthProperty().bind(pane.widthProperty());
    locationField.prefWidthProperty().bind(pane.widthProperty());
    datePicker.prefWidthProperty().bind(pane.widthProperty());

    final var ok = pane.lookupButton(okButton);
    ok.addEventHandler(
        ActionEvent.ACTION,
        event -> {
          final var move =
              new MoveEntity(
                  Main.getRepo().getNode(nodeField.getText()),
                  Main.getRepo().getLocationname(locationField.getText()),
                  new Date(
                      datePicker.getValue().toEpochDay()
                          * 24
                          * 60
                          * 60
                          * 1000) // days to milliseconds
                  );
          Main.getRepo().addMove(move);
          java.util.Date date = new java.util.Date();
          int alertID = Main.db.getNewAlertID();
          AlertEntity newAlert =
              new AlertEntity(
                  alertID,
                  new java.sql.Date(date.getTime()),
                  locationField.getText()
                      + " is moving to "
                      + nodeField.getText()
                      + " on "
                      + datePicker.getValue().toString());
          Main.db.addAlert(newAlert);
          Map<String, StaffEntity> staffMap = Main.db.getStaff();
          List<StaffEntity> staffList = new ArrayList<>();
          for (StaffEntity staff : staffMap.values()) {
            staffList.add(staff);
          }
          newAlert.addStaff(staffList);
        });
    ok.disableProperty()
        .bind(
            Bindings.createBooleanBinding(
                this::isInputInvalid, nodeField.textProperty(), locationField.textProperty()));
  }

  private boolean isInputInvalid() {
    return !Main.getRepo().getNodes().containsKey(nodeField.getText())
        || !Main.getRepo().getLocationNames().containsKey(locationField.getText())
        || datePicker.getValue() == null;
  }
}
