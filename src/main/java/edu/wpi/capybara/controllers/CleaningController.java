package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import edu.wpi.capybara.objects.submissions.cleaningSubmission;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CleaningController {

  @FXML public MFXButton SubmitButton;

  @FXML public MFXButton ReturnButton;

  @FXML public TextField Location;
  @FXML public TextArea Description;

  @FXML public Button ClearButton;
  @FXML public TextField MemberID;
  @FXML public TextField hazardLevel;

  public CleaningRequestController forRequests;

  /** enumeration for status of request */
  private enum submissionStatus {
    BLANK,
    PROCESSING,
    DONE
  }

  private submissionStatus currentStatus;

  public void initializer() {
    System.out.println("I am from cleaningController");
    currentStatus = submissionStatus.BLANK;
  }

  /**
   * submit that creates a cleaning request object and then displays all of the previously created
   * request
   *
   * @param actionEvent
   */
  public void submit(ActionEvent actionEvent) {
    String locationInfo = Location.getText();
    String descriptionInfo = Description.getText();
    String memberID = MemberID.getText();
    String hazardLevelInfo = hazardLevel.getText();
    cleaningSubmission addSubmission =
        new cleaningSubmission(locationInfo, hazardLevelInfo, descriptionInfo);
    App.getTotalSubmissions().newCleaningSub(addSubmission);
    System.out.println(App.getTotalSubmissions().getCleaningData());
    clearRequest();
  }

  /**
   * navigates back to home screen
   *
   * @param actionEvent
   * @throws IOException
   */
  public void back(ActionEvent actionEvent) throws IOException {
    Navigation.navigate(Screen.HOME);
  }

  /** clears all areas on the submission form */
  public void clearRequest() {
    Location.clear();
    Description.clear();
    MemberID.clear();
    hazardLevel.clear();
    currentStatus = submissionStatus.BLANK;
  }
}
