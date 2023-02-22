package edu.wpi.capybara.controllers.mapeditor.dialogs;

import edu.wpi.capybara.App;
import edu.wpi.capybara.Main;
import edu.wpi.capybara.objects.hibernate.LocationnameEntity;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;

public class AddLocationNameDialog extends FXMLDialog<Void> {

  @FXML private MFXTextField longName;
  @FXML private MFXTextField shortName;
  @FXML private MFXTextField locationType;

  @FXML private ButtonType okButton;

  public AddLocationNameDialog(Window owner) {
    super(owner, App.class.getResource("views/LocationNameDialog.fxml"));

    setTitle("Add Location");

    longName.prefWidthProperty().bind(pane.widthProperty());
    shortName.prefWidthProperty().bind(pane.widthProperty());
    locationType.prefWidthProperty().bind(pane.widthProperty());

    final var ok = pane.lookupButton(okButton);
    ok.addEventHandler(
        ActionEvent.ACTION,
        event ->
            Main.getRepo()
                .addLocationName(
                    new LocationnameEntity(
                        longName.getText(), shortName.getText(), locationType.getText())));
    ok.disableProperty()
        .bind(
            Bindings.createBooleanBinding(
                this::isInputInvalid,
                longName.textProperty(),
                shortName.textProperty(),
                locationType.textProperty()));
  }

  private boolean isInputInvalid() {
    return longName.getText().isBlank()
        || shortName.getText().isBlank()
        || locationType.getText().isBlank();
  }
}
