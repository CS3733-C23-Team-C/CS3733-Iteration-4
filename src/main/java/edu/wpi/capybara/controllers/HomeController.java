package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HomeController {

  final String SECRET_PASSWORD = "team coaching";

  /** Initialize controller by FXML Loader. */
  @FXML
  public void initialize() {
    System.out.println("I am from HomeController.");

    //    submit.setOnMouseClicked(event -> {});
  }

  /**
   * FXML Injected Method that handles the submit button.
   *
   * @param event The event that triggered the method.
   */
  @FXML
  private void handleButtonSubmit(ActionEvent event) {
    /*
     1. Get the text the user input
     2. Validate it against the correct information
     3. Display to the user whether their input was correct or not
    */

  }

  /**
   * Checks if a user's password is correct.
   *
   * @param input The user's password.
   * @return True if the password is correct.
   */
  private boolean validate(final String input) {
    return input.equals(SECRET_PASSWORD);
  }

  @FXML public Button exitButton;
  @FXML public Stage primaryStage;

  public void showTransportationPage(ActionEvent actionEvent) throws IOException {
    final URL path = App.class.getResource("views/Transportation.fxml");
    final FXMLLoader loader = new FXMLLoader(path);
    primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    final BorderPane mainScene = loader.load();
    final Scene scene = new Scene(mainScene);

    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public void showCleaningPage(ActionEvent actionEvent) throws IOException {
    final URL path = App.class.getResource("views/Cleaning.fxml");
    final FXMLLoader loader = new FXMLLoader(path);
    primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    final BorderPane mainScene = loader.load();
    final Scene scene = new Scene(mainScene);

    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public void exitApplication(ActionEvent actionEvent) {
    Stage stage = (Stage) exitButton.getScene().getWindow();
    stage.close();
  }
}
