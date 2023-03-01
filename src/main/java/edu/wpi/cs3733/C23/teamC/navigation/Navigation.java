package edu.wpi.cs3733.C23.teamC.navigation;

import edu.wpi.cs3733.C23.teamC.App;
import edu.wpi.cs3733.C23.teamC.Home.ScreenSaver;
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
      App.getRootPane()
          .getCenter()
          .setOnMouseMoved(
              (event -> {
                ScreenSaver.setMoved(true);
              }));
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
