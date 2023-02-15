package edu.wpi.capybara.controllers.mapeditor.dialogs;

import edu.wpi.capybara.App;
import edu.wpi.capybara.controllers.mapeditor.AdapterRepository;
import javafx.stage.Window;

public class AddEdgeDialog extends FXMLDialog<Void> {
  public AddEdgeDialog(Window owner, AdapterRepository repository) {
    super(owner, App.class.getResource("views/EdgeDialog.fxml"));
    setTitle("Add Edge");
  }
}
