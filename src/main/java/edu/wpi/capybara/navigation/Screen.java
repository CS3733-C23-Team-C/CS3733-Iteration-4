package edu.wpi.capybara.navigation;

public enum Screen {
  ROOT("views/Root.fxml"),
  HOME("views/NewHome.fxml"),
  SERVICE_REQUEST("views/ServiceRequest.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
