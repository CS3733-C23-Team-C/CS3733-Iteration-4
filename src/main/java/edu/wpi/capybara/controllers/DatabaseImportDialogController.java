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
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static edu.wpi.capybara.database.DBcsv.*;

public class DatabaseImportDialogController {

  @FXML private MFXTextField folderText;
  @FXML private MFXButton importData;
  @FXML private MFXButton exportData;
  @FXML private Text errorText;

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
    importAlldbcsv();
    try {
      importDatabase(folderText.getText());
    } catch (Exception e) {
      errorText.setText("Unable to import data");
    }
  }

  public void exportDataFunc(ActionEvent event) {
    try {
      exportDatabase();
    } catch (Exception e) {
      errorText.setText("Unable to export data");
    }
  }
}
