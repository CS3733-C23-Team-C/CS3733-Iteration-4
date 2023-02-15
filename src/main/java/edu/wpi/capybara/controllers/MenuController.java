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
  public String userMessage;
  @FXML private MFXButton homeButton;
  @FXML private MFXButton serviceRequestsButton;
  @FXML private MFXButton pathfindingButton;
  @FXML private MFXButton mapEditorButton;
  private static MenuButton sUserProfile;

  public static void setUserProfile() {
    System.out.println(sUserProfile == null);
    System.out.println(App.getUser().getFirstname());
    System.out.println(App.getUser().getLastname());
    if (sUserProfile != null) {
      sUserProfile.setText(App.getUser().getFirstname() + " " + App.getUser().getLastname());
    }
  }

  @FXML
  public void initialize() {
    sUserProfile = userProfile;
    setUserProfile();
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

  public void showAssignedRequests(ActionEvent actionEvent) {
    Navigation.navigate(Screen.ASSIGNED_REQUESTS);
  }

  public void giveInfo(MouseEvent mouseEvent) {}

  public void quitApp(MouseEvent mouseEvent) {
    System.exit(0);
  }

  public void showProfile(ActionEvent actionEvent) {
    Navigation.navigate(Screen.USER_PROFILE);
  }

  public void showRequests(ActionEvent actionEvent) {
    Navigation.navigate(Screen.REQUESTS);
  }

  public void showLogOut(ActionEvent actionEvent) {
    Navigation.navigate(Screen.LOG_IN_PAGE);
  }
}
