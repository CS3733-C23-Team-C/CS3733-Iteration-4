package edu.wpi.cs3733.C23.teamC.controllers.mapeditor.dialogs;

import java.util.Arrays;
import java.util.stream.Collectors;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ErrorMessage {
  private ErrorMessage() {}

  public static void displayError(Throwable e, String format, Object... args) {
    final var alert =
        new Alert(
            Alert.AlertType.ERROR,
            String.format(
                "%s: %s\n%s",
                e.getClass().getName(),
                e.getMessage(),
                Arrays.stream(e.getStackTrace())
                    .map(StackTraceElement::toString)
                    .collect(Collectors.joining("\n\t"))),
            ButtonType.OK);
    alert.setTitle("Error");
    alert.setHeaderText(String.format(format, args));
    alert.setResizable(true);
    alert.show();
  }

  public static void handleUncaughtException(Thread thread, Throwable exception) {
    exception.printStackTrace();
    displayError(exception, "Error on thread [%s]", thread.getName());
  }
}
