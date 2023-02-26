package edu.wpi.cs3733.C23.teamC.controllers.mapeditor.dialogs;

import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.AdapterRepository;
import javafx.stage.Window;

public class AddNodeDialog extends NodeDialog {

  private final AdapterRepository repository;

  public AddNodeDialog(Window owner, AdapterRepository repository) {
    super(owner);
    this.repository = repository;
    setTitle("Add Node");
  }

  @Override
  protected void onOKPressed() {
    repository.createNode(
        nodeID.getText(),
        Integer.parseInt(xCoord.getText()),
        Integer.parseInt(yCoord.getText()),
        floor.getText(),
        building.getText());
  }
}