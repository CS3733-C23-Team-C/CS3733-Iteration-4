package edu.wpi.cs3733.C23.teamC.Home;

import edu.wpi.cs3733.C23.teamC.App;
import edu.wpi.cs3733.C23.teamC.Messages.SelectedMessage;
import edu.wpi.cs3733.C23.teamC.Pathfinding.KioskDialogController;
import edu.wpi.cs3733.C23.teamC.navigation.Navigation;
import edu.wpi.cs3733.C23.teamC.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

public class MenuController {

  @FXML private MenuButton userProfile;
  public String userMessage;
  @FXML private MFXButton homeButton;
  @FXML private MFXButton serviceRequestsButton;
  @FXML private MFXButton pathfindingButton;
  @FXML private MFXButton mapEditorButton;
  @FXML private MFXButton messagesButton;
  @FXML private MFXButton moveButton;
  @FXML private Circle newMessageCircle;
  @FXML private MFXButton employeeEditButton;
  @FXML private MFXButton kioskButton;
  @FXML private MFXButton graphsButton;
  private static MenuButton sUserProfile;
  private static Circle sNewMessageCircle;
  @Getter @Setter private static SelectedMessage selectedMessage;

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

    employeeEditButton.managedProperty().bind(employeeEditButton.visibleProperty());
    employeeEditButton.setVisible(App.getUser().getRole().equals("admin"));

    kioskButton.managedProperty().bind(kioskButton.visibleProperty());
    kioskButton.setVisible(App.getUser().getRole().equals("admin"));

    sNewMessageCircle = newMessageCircle;
    if (HomeController.getUnreadMessagesCount() != 0) {
      MenuController.messageNotiOn();
      System.out.println("Set Notif on Home Start Up");
    }
    selectedMessage = new SelectedMessage("", 0);
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

  public void goToEmployeeEdit(ActionEvent actionEvent) {
    Navigation.navigate(Screen.EMPLOYEE_EDIT);
  }

  public void showAssignedRequests(ActionEvent actionEvent) {
    Navigation.navigate(Screen.ASSIGNED_REQUESTS);
  }

  public void giveInfo(MouseEvent mouseEvent) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/InfoPage.fxml"));
      Parent root1 = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.setScene(new Scene(root1));
      stage.show();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

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

  public void goToMessages(ActionEvent actionEvent) {
    Navigation.navigate(Screen.MESSAGES);
  }

  public void goToCreditsPage(ActionEvent actionEvent) {
    Navigation.navigate(Screen.CREDITS_PAGE);
  }

  public void goToAboutPage(ActionEvent actionEvent) {
    Navigation.navigate(Screen.ABOUT_PAGE);
  }

  public void goToGraph(ActionEvent actionEvent) {
    Navigation.navigate(Screen.GRAPH);
  }

  public void goToMovePage(ActionEvent actionEvent) {
    Navigation.navigate(Screen.MOVES);
  }

  public void goToKiosk(ActionEvent actionEvent) {
    KioskDialogController.showDialog();
  }

  public static void messageNotiOn() {
    sNewMessageCircle.setVisible(true);
  }

  public static void messageNotiOff() {
    sNewMessageCircle.setVisible(false);
  }
}
