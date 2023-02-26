package edu.wpi.cs3733.C23.teamC.controllers.mapeditor.dialogs;

import edu.wpi.cs3733.C23.teamC.App;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;
import org.apache.commons.lang3.StringUtils;

public abstract class NodeDialog extends FXMLDialog<Void> {

  @FXML protected MFXTextField nodeID;
  @FXML protected MFXTextField xCoord;
  @FXML protected MFXTextField yCoord;
  @FXML protected MFXTextField floor;
  @FXML protected MFXTextField building;

  @FXML private ButtonType okButton;

  public NodeDialog(Window owner) {
    super(owner, App.class.getResource("views/NodeDialog.fxml"));

    /*nodeID
        .getValidator()
        .constraint(Severity.ERROR, "Please enter a Node ID", nodeID.textProperty().isNotEmpty());
    xCoord
        .getValidator()
        .constraint(
            Severity.ERROR, "Please enter an X coordinate", xCoord.textProperty().isNotEmpty());*/

    // ideally these would be set in the FXML, but I can't get that to work properly :/
    nodeID.prefWidthProperty().bind(pane.widthProperty());
    xCoord.prefWidthProperty().bind(pane.widthProperty());
    yCoord.prefWidthProperty().bind(pane.widthProperty());
    floor.prefWidthProperty().bind(pane.widthProperty());
    building.prefWidthProperty().bind(pane.widthProperty());

    nodeID
        .textProperty()
        .bind(
            Bindings.createStringBinding(
                () ->
                    String.format("%sX%sY%s", floor.getText(), xCoord.getText(), yCoord.getText()),
                xCoord.textProperty(),
                yCoord.textProperty(),
                floor.textProperty()));
    nodeID.setEditable(false);
    nodeID.setDisable(true);

    final var ok = pane.lookupButton(okButton);
    ok.addEventHandler(ActionEvent.ACTION, event -> onOKPressed());
    ok.disableProperty()
        .bind(
            Bindings.createBooleanBinding(
                this::isInputInvalid,
                nodeID.textProperty(),
                xCoord.textProperty(),
                yCoord.textProperty(),
                floor.textProperty(),
                building.textProperty()));
  }

  private boolean isInputInvalid() {
    return nodeID.getText().isBlank()
        || !StringUtils.isNumeric(xCoord.getText())
        || !StringUtils.isNumeric(yCoord.getText())
        || floor.getText().isBlank()
        || building.getText().isBlank();
  }

  protected abstract void onOKPressed();
}
