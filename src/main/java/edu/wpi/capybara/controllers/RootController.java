package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class RootController {

  @FXML private MenuItem exitButtonM;
  @FXML private MenuBar rightMenu;
  @FXML private MenuBar leftMenu;
  private static MenuBar sLeftMenu;
  private static MenuBar sRightMenu;
  @FXML private MenuItem logOut;
  @FXML private Menu userProfile;
  private static Menu sUserProfile;

  @FXML
  public void initialize() {
    sLeftMenu = leftMenu;
    sRightMenu = rightMenu;
    sUserProfile = userProfile;
  }

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

  public void showPathfindingPage(ActionEvent actionEvent) {
    Navigation.navigate(Screen.PATHFINDING);
  }

  public void showRequestPage(ActionEvent actionEvent) {
    Navigation.navigate(Screen.REQUESTS);
  }

  public void showUserProfilePage(ActionEvent actionEvent) {
    Navigation.navigate(Screen.USER_PROFILE);
  }

  public static void updateUser() {
    // rightMenu.setVisible(true);
    if (sUserProfile != null) sUserProfile.setText("Hello " + App.getUser().getFirstname() + "!");
    else System.out.println("why");
  }

  public void logOut() {
    leftMenu.setVisible(false);
    rightMenu.setVisible(false);
    Navigation.navigate(Screen.LOG_IN_PAGE);
  }

  public static void showMenu() {
    sLeftMenu.setVisible(true);
    sRightMenu.setVisible(true);
  }
}
