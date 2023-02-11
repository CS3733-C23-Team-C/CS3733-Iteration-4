package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;

public class MenuController {

  @FXML private MenuButton userProfile;
  @FXML private MFXButton homeButton;
  @FXML private MFXButton serviceRequestsButton;
  @FXML private MFXButton pathfindingButton;
  @FXML private MFXButton mapEditorButton;

  @FXML
  public void initialize() {
    userProfile.setText("I give up");
    userProfile.setText(App.getUser().getFirstname() + " " + App.getUser().getLastname());
  }

  public void goToHome(ActionEvent actionEvent) {
    Navigation.navigate(Screen.HOME);
  }

  public void goToServiceRequests(ActionEvent actionEvent) {
    Navigation.navigate(Screen.SERVICE_REQUESTS);
  }

  public void goToPathfinding(ActionEvent actionEvent) {
    Navigation.navigate(Screen.PATHFINDING);
  }

  public void goToMapEditor(ActionEvent actionEvent) {
    Navigation.navigate(Screen.MAP_EDITOR);
  }

  public void giveInfo(MouseEvent mouseEvent) {}

  public void quitApp(MouseEvent mouseEvent) {
    System.exit(0);
  }

  public void showProfile(ActionEvent actionEvent) {}

  public void showRequests(ActionEvent actionEvent) {}

  public void showLogOut(ActionEvent actionEvent) {

    Navigation.navigate(Screen.LOG_IN_PAGE);
  }
}
