package edu.wpi.cs3733.C23.teamC.controllers.mapeditor.dialogs;

import edu.wpi.cs3733.C23.teamC.App;
import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.AdapterRepository;
import javafx.stage.Window;

public class AddEdgeDialog extends FXMLDialog<Void> {
  public AddEdgeDialog(Window owner, AdapterRepository repository) {
    super(owner, App.class.getResource("views/EdgeDialog.fxml"));
    setTitle("Add Edge");
  }
}
