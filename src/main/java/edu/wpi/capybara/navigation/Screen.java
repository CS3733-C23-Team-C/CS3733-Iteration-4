package edu.wpi.capybara.navigation;

public enum Screen {
  ROOT("views/Root.fxml"),
  HOME("views/Home.fxml"),
  SERVICE_REQUEST_TRANSPORTATION("views/Transportation.fxml"),
  SERVICE_REQUEST_CLEANING("views/Cleaning.fxml"),
  SERVICE_REQUEST_SECURITY("views/Security.fxml"),
  MAP_EDITOR("views/MapEditor.fxml"),

  LOG_IN_PAGE("views/LogInPage.fxml"),

  PATHFINDING("views/Pathfinding.fxml"),

  REQUESTS("views/CleaningRequests.fxml"),

  USER_PROFILE("views/UserProfile.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
