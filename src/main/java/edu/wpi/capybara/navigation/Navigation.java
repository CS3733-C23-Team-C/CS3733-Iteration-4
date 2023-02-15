package edu.wpi.capybara.navigation;

import edu.wpi.capybara.App;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

public class Navigation {

  public static void navigate(final Screen screen) {
    final String filename = screen.getFilename();

    try {
      final var resource = App.class.getResource(filename);
      final FXMLLoader loader = new FXMLLoader(resource);

      if (screen == Screen.LOG_IN_PAGE) {
        App.getRootPane().setLeft(null);
        App.getPrimaryStage().setMaximized(false);
      }

      App.getRootPane().setCenter(loader.load());
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
    }
  }

  public static void addMenu(final Screen screen) {
    final String filename = screen.getFilename();

    try {
      final var resource = App.class.getResource(filename);
      final FXMLLoader loader = new FXMLLoader(resource);

      App.getRootPane().setLeft(loader.load());
      App.getPrimaryStage().setMaximized(true);
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
    }
  }
}
