package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.net.URL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.*;

public class TransportationController {

  @FXML public Stage primaryStage;
  @FXML private MFXButton backButton;
  @FXML private MFXButton submitButton;
  @FXML private MFXTextField currRoom;
  @FXML private MFXTextField destRoom;
  @FXML private VBox imageVBox;
  @FXML private ImageView map;
  @FXML private MFXComboBox<String> dropDown;

  private ObservableList<String> options = FXCollections.observableArrayList();
  /** Initialize controller by FXML Loader. */
  @FXML
  public void initialize() {
    System.out.println("I am from TransportationController.");

    options.add("Lower Level 2");
    options.add("Lower Level 1");
    options.add("Ground Floor");
    options.add("First Floor");
    options.add("Second Floor");
    options.add("Third Floor");
    dropDown.setItems(options);

    //    if (imageVBox.getWidth() / imageVBox.getHeight() <= 1.45) {
    //      map.setFitWidth(imageVBox.getWidth() / 1000);
    //      map.setFitHeight(imageVBox.getWidth() / 1450);
    //    } else {
    //      map.setFitHeight(imageVBox.getHeight() / 1000);
    //      map.setFitWidth(imageVBox.getHeight() * 1.45 / 1000);
    //    }

    //    submit.setOnMouseClicked(event -> {});
  }

  /**
   * FXML Injected Method that handles the submit button.
   *
   * @param actionEvent The event that triggered the method.
   */
  public void returnHome(ActionEvent actionEvent) throws IOException {
    final URL path = App.class.getResource("views/Home.fxml");
    final FXMLLoader loader = new FXMLLoader(path);
    primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    final BorderPane mainScene = loader.load();
    final Scene scene = new Scene(mainScene);

    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public void submitForm(ActionEvent actionEvent) throws IOException {
    String outputCurrRoom = currRoom.getText();
    String outputDestRoom = destRoom.getText();
    System.out.println("Current Room: " + outputCurrRoom + " Destination Room: " + outputDestRoom);
  }

  public void resizeImg(ActionEvent actionEvent) throws IOException {
    if (imageVBox.getWidth() / imageVBox.getHeight() <= 1.45) {
      map.setFitWidth(imageVBox.getWidth());
      map.setFitHeight(imageVBox.getWidth() / 1.45);
    } else {
      map.setFitHeight(imageVBox.getHeight());
      map.setFitWidth(imageVBox.getHeight() * 1.45);
    }
  }

  public void updateMap(ActionEvent actionEvent) throws IOException {
    String selection = dropDown.getValue();
    if (selection.equals("Lower Level 2")) {
      Image newImage =
          new Image(
              "C:\\Users\\Johnk\\Documents\\CS\\Soft Eng\\CS3733-C23-Team-C-Prototype-1\\src\\main\\resources\\edu\\wpi\\capybara\\images\\thelowerlevel2.png");
      map.setImage(newImage);
    } else if (selection.equals("Lower Level 1")) {
      Image newImage =
          new Image(
              "C:\\Users\\Johnk\\Documents\\CS\\Soft Eng\\CS3733-C23-Team-C-Prototype-1\\src\\main\\resources\\edu\\wpi\\capybara\\images\\thelowerlevel1.png");
      map.setImage(newImage);
    } else if (selection.equals("Ground Floor")) {
      Image newImage =
          new Image(
              "C:\\Users\\Johnk\\Documents\\CS\\Soft Eng\\CS3733-C23-Team-C-Prototype-1\\src\\main\\resources\\edu\\wpi\\capybara\\images\\thegroundfloor.png");
      map.setImage(newImage);
    } else if (selection.equals("First Floor")) {
      Image newImage =
          new Image(
              "C:\\Users\\Johnk\\Documents\\CS\\Soft Eng\\CS3733-C23-Team-C-Prototype-1\\src\\main\\resources\\edu\\wpi\\capybara\\images\\thefirstfloor.png");
      map.setImage(newImage);
    } else if (selection.equals("Second Floor")) {
      Image newImage =
          new Image(
              "C:\\Users\\Johnk\\Documents\\CS\\Soft Eng\\CS3733-C23-Team-C-Prototype-1\\src\\main\\resources\\edu\\wpi\\capybara\\images\\thesecondfloor.png");
      map.setImage(newImage);
    } else {
      Image newImage =
          new Image(
              "C:\\Users\\Johnk\\Documents\\CS\\Soft Eng\\CS3733-C23-Team-C-Prototype-1\\src\\main\\resources\\edu\\wpi\\capybara\\images\\thethirdfloor.png");
      map.setImage(newImage);
    }
  }
}
