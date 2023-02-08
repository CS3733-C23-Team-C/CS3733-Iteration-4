package edu.wpi.capybara.controllers.mapeditor.dialogs;

import edu.wpi.capybara.controllers.mapeditor.DBObjectRepository;
import javafx.stage.Window;

public class AddNodeDialog extends NodeDialog {

  private final DBObjectRepository repository;

  public AddNodeDialog(Window owner, DBObjectRepository repository) {
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
