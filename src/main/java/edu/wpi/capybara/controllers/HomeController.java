package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class HomeController {

  @FXML private Text welcomeTxt;

  final String SECRET_PASSWORD = "team coaching";

  /** Initialize controller by FXML Loader. */
  @FXML
  public void initialize() {
    System.out.println("I am from HomeController.");

    if (welcomeTxt != null) {
      welcomeTxt.setText("Welcome back, " + App.getUser().getFirstname() + "!");
    }

    //    submit.setOnMouseClicked(event -> {});
  }

  /**
   * FXML Injected Method that handles the submit button.
   *
   * @param event The event that triggered the method.
   */
  @FXML
  private void handleButtonSubmit(ActionEvent event) {
    /*
     1. Get the text the user input
     2. Validate it against the correct information
     3. Display to the user whether their input was correct or not
    */

  }

  /**
   * Checks if a user's password is correct.
   *
   * @param input The user's password.
   * @return True if the password is correct.
   */
  private boolean validate(final String input) {
    return input.equals(SECRET_PASSWORD);
  }
}
