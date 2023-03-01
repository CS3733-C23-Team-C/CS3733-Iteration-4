package edu.wpi.cs3733.C23.teamC.ServiceRequests;

import edu.wpi.cs3733.C23.teamC.navigation.Navigation;
import edu.wpi.cs3733.C23.teamC.navigation.Screen;
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
