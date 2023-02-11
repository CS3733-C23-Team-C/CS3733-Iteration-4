package edu.wpi.capybara.controllers;

import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import javafx.event.ActionEvent;

public class ServiceRequestsController {
  public void goToTransportation(ActionEvent actionEvent) {
    Navigation.navigate(Screen.SERVICE_REQUEST_TRANSPORTATION);
  }

  public void goToSanitation(ActionEvent actionEvent) {
    Navigation.navigate(Screen.SERVICE_REQUEST_CLEANING);
  }

  public void goToSecurity(ActionEvent actionEvent) {
    Navigation.navigate(Screen.SERVICE_REQUEST_SECURITY);
  }

  public void goToComputer(ActionEvent actionEvent) {
    Navigation.navigate(Screen.SERVICE_REQUEST_COMPUTER);
  }

  public void goToAudio(ActionEvent actionEvent) {
    Navigation.navigate(Screen.SERVICE_REQUEST_AUDIO);
  }
}
