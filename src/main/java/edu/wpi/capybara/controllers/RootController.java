package edu.wpi.capybara.controllers;

import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class RootController {

  @FXML private MenuItem exitButtonM;
  @FXML private MenuItem requestButton;

  @FXML
  public void initialize() {}

  public void exitApplication(ActionEvent actionEvent) {
    System.exit(0);
  }

  public void showTransportationPage(ActionEvent actionEvent) {
    Navigation.navigate(Screen.SERVICE_REQUEST_TRANSPORTATION);
  }

  public void showCleaningPage(ActionEvent actionEvent) {
    Navigation.navigate(Screen.SERVICE_REQUEST_CLEANING);
  }

  public void showSecurityPage(ActionEvent actionEvent) {
    Navigation.navigate(Screen.SERVICE_REQUEST_SECURITY);
  }

  public void showMapEditorPage(ActionEvent actionEvent) {
    Navigation.navigate(Screen.MAP_EDITOR);
  }

  public void showRequestPage(ActionEvent actionEvent) {
    Navigation.navigate(Screen.REQUESTS);
  }
}
