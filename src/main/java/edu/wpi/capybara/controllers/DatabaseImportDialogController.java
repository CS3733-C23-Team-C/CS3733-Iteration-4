package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DatabaseImportDialogController {

  @FXML private MFXTextField folderText;
  @FXML private MFXButton importData;
  @FXML private MFXButton exportData;

  public static void showDialog() {
    Stage dialog;
    dialog = new Stage();
    dialog.initModality(Modality.WINDOW_MODAL);
    dialog.initOwner(App.getPrimaryStage().getOwner());

    final FXMLLoader loader =
        new FXMLLoader(App.class.getResource("views/DatabaseImportDialog.fxml"));
    BorderPane root;
    try {
      root = loader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    dialog.setScene(new Scene(root));
    dialog.showAndWait();
  }

  public void initialize() {
    System.out.println("INITIALIZED");
    importData.setOnAction(this::importDataFunc);
    exportData.setOnAction(this::exportDataFunc);
  }

  public void importDataFunc(ActionEvent event) {
    System.out.println("import data to " + folderText.getText());
  }

  public void exportDataFunc(ActionEvent event) {
    System.out.println("export data to " + folderText.getText());
  }
}