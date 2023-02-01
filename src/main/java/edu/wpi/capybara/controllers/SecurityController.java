package edu.wpi.capybara.controllers;

import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SecurityController {

  @FXML public MFXButton SubmitButton;

  @FXML public MFXButton ReturnButton;

  @FXML public TextField Location;
  @FXML public TextArea Description;
  @FXML public Stage primaryStage;

  public void back(ActionEvent actionEvent) throws IOException {
    Navigation.navigate(Screen.HOME);
  }
}
