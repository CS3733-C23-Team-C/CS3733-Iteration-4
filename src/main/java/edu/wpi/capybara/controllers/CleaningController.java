package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CleaningController {

  @FXML public MFXButton SubmitButton;

  @FXML public MFXButton ReturnButton;

  @FXML public TextField Location;
  @FXML public TextArea Description;
  @FXML public Stage primaryStage;

  public void submit(ActionEvent actionEvent) {
    String loacationInfo = Location.getText();
    String descriptionInfo = Description.getText();
    System.out.println("Loacation: " + loacationInfo);
    System.out.println("Description: " + descriptionInfo);
  }

  public void back(ActionEvent actionEvent) throws IOException {
    final URL path = App.class.getResource("views/Home.fxml");
    final FXMLLoader loader = new FXMLLoader(path);
    primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    final BorderPane mainScene = loader.load();
    final Scene scene = new Scene(mainScene);

    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
