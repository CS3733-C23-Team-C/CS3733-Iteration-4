package edu.wpi.capybara.controllers.mapeditor.dialogs;

import edu.wpi.capybara.App;
import edu.wpi.capybara.Main;
import edu.wpi.capybara.objects.hibernate.MoveEntity;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.sql.Date;
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
        });
    ok.disableProperty()
        .bind(
            Bindings.createBooleanBinding(
                this::isInputInvalid, nodeField.textProperty(), locationField.textProperty()));
  }

  private boolean isInputInvalid() {
    return !Main.getRepo().getNodes().containsKey(nodeField.getText())
        || !Main.getRepo().getLocationNames().containsKey(locationField.getText());
  }
}
