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
import javafx.stage.Stage;

public class CleaningController {

  @FXML public MFXButton SubmitButton;

  @FXML public MFXButton ReturnButton;

  @FXML public TextField Location;
  @FXML public TextArea Description;
  @FXML public Stage primaryStage;

  @FXML public Button ClearButton;
  @FXML public TextField MemberID;
  @FXML public TextField hazardLevel;

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

  public void submit(ActionEvent actionEvent) {
    String locationInfo = Location.getText();
    String descriptionInfo = Description.getText();
    String memberID = MemberID.getText();
    String hazardLevelInfo = hazardLevel.getText();
    cleaningSubmission addSubmission =
        new cleaningSubmission(memberID, locationInfo, hazardLevelInfo, descriptionInfo);
    App.cleaningSubsTotal.newCleaningSub(addSubmission);
    System.out.println(App.cleaningSubsTotal.getCleaningData());
    clearRequest();
  }

  public void back(ActionEvent actionEvent) throws IOException {
    Navigation.navigate(Screen.HOME);
  }

  public void clearRequest() {
    Location.clear();
    Description.clear();
    MemberID.clear();
    hazardLevel.clear();
  }
}
