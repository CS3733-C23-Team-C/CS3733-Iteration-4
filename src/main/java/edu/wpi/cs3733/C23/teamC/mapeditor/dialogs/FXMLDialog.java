package edu.wpi.cs3733.C23.teamC.mapeditor.dialogs;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
import javafx.stage.Window;

public class FXMLDialog<R> extends Dialog<R> {

  protected DialogPane pane;

  public FXMLDialog(Window owner, URL fxmlLocation) {
    try {
      final var loader = new FXMLLoader();
      loader.setLocation(fxmlLocation);
      loader.setController(this);

      pane = loader.load();

      initOwner(owner);
      initModality(Modality.WINDOW_MODAL);
      setDialogPane(pane);
    } catch (IOException e) {
      // todo do something else here
      throw new RuntimeException(e);
    }
  }
}
