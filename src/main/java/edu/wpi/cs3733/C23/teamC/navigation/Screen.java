package edu.wpi.cs3733.C23.teamC.navigation;

public enum Screen {
  ROOT("views/Root.fxml"),
  EMPTY_ROOT("views/EmptyRoot.fxml"),
  ABOUT_PAGE("views/AboutPage.fxml"),
  CREDITS_PAGE("views/CreditsPage.fxml"),
  MENU("views/Menu.fxml"),
  SERVICE_REQUEST_COMPUTER("views/Computer.fxml"),
  SERVICE_REQUEST_AUDIO("views/Audio.fxml"),
  SERVICE_REQUESTS("views/ServiceRequests.fxml"),
  HOME("views/Home.fxml"),

  FORGOT_PASSWORD("views/ForgotPassword.fxml"),

  FORGOT_PASSWORD_SECOND_SCREEN("views/ForgotPasswordSecondScreen.fxml"),

  SERVICE_REQUEST_TRANSPORTATION("views/Transportation.fxml"),
  SERVICE_REQUEST_CLEANING("views/Cleaning.fxml"),
  SERVICE_REQUEST_SECURITY("views/Security.fxml"),
  MAP_EDITOR("views/MapEditor.fxml"),

  EMPLOYEE_EDIT("views/EmployeeEdit.fxml"),

  LOG_IN_PAGE("views/LogInPage.fxml"),

  SIGN_UP_PAGE("views/SignUpPage.fxml"),

  PATHFINDING("views/Pathfinding.fxml"),

  REQUESTS("views/MyRequests.fxml"),

  USER_PROFILE("views/UserProfile.fxml"),

  ASSIGNED_REQUESTS("views/AssignedRequests.fxml"),

  GRAPH("views/Graphing.fxml"),

  MESSAGES("views/Messages.fxml"),

  MOVES("views/Move.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
