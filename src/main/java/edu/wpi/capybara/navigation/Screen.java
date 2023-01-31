package edu.wpi.capybara.navigation;

public enum Screen {
  ROOT("views/Root.fxml"),
  HOME("views/Home.fxml"),
  SERVICE_REQUEST("views/ServiceRequest.fxml"),
  SERVICE_REQUEST_TRANSPORTATION("views/Transportation.fxml"),
  SERVICE_REQUEST_CLEANING("views/Cleaning.fxml"),
  SERVICE_REQUEST_SECURITY("views/Security.fxml"),
  MAP_EDITOR("views/MapEditor.fxml"),
  REQUESTS("views/CleaningRequests.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
