package edu.wpi.capybara.controllers;

import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class ServiceRequestController {

  @FXML MFXButton backButton;

  @FXML
  public void initialize() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }
}
