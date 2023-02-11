package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import edu.wpi.capybara.objects.NodeAlphabetComparator;
import edu.wpi.capybara.objects.hibernate.CleaningsubmissionEntity;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import edu.wpi.capybara.objects.submissions.Urgency;
import edu.wpi.capybara.objects.submissions.submissionStatus;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.io.IOException;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CleaningController {

  @FXML private MFXButton SubmitButton;

  @FXML private MFXButton ReturnButton;

  @FXML public MFXComboBox<String> Location;
  @FXML public TextArea Description;

  @FXML private Button ClearButton;

  @FXML private TextField hazardLevel;

  public CleaningRequestController forRequests;

  /** enumeration for status of request */
  private submissionStatus currentStatus;

  @FXML
  public void initialize() {
    System.out.println("I am from cleaningController");
    currentStatus = submissionStatus.BLANK;

    // Add different locations

    //    Location.getItems().add("Location");
    //    Location.getItems().add("Location1");

    TreeMap<String, NodeEntity> nodes = DatabaseConnect.getNodes();

    SortedSet<NodeEntity> sortedset = new TreeSet<NodeEntity>(new NodeAlphabetComparator());

    sortedset.addAll(nodes.values());

    Iterator<NodeEntity> iterator = sortedset.iterator();
    while (iterator.hasNext()) {
      NodeEntity n = iterator.next();
      // System.out.println(n.getShortName());
      Location.getItems().add(n.getShortName());
    }

    //    ObservableList<String> locationList =
    //        FXCollections.observableArrayList("Location", "Another location");
    //    Location.setItems(locationList);

    // Set a default variable
    Location.getSelectionModel().selectFirst();
  }

  /**
   * submit that creates a cleaning request object and then displays all of the previously created
   * request
   *
   * @param actionEvent
   */
  public void submit(ActionEvent actionEvent) {
    java.util.Date date = new java.util.Date();
    String locationInfo = "" + Location.getValue();
    String descriptionInfo = Description.getText();
    String hazardLevelInfo = hazardLevel.getText();
    CleaningsubmissionEntity addSubmission =
        new CleaningsubmissionEntity(
            App.getUser().getStaffid(),
            locationInfo,
            hazardLevelInfo,
            descriptionInfo,
            submissionStatus.BLANK,
            null,
            (int) (Math.random() * 100000),
            Urgency.BLANK,
            new java.sql.Date(date.getTime()),
            new java.sql.Date(date.getTime() + 86400000));
    // locationInfo, hazardLevelInfo, descriptionInfo
    //    addSubmission.setLocation(locationInfo);
    //    addSubmission.setHazardlevel(hazardLevelInfo);
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
    Location.getSelectionModel().selectFirst();
    Description.clear();
    hazardLevel.clear();
    currentStatus = submissionStatus.BLANK;
    SubmitButton.setDisable(true);
  }

  public void
      validateButton() { // ensures that information has been filled in before allowing submission
    boolean valid = false;
    if (!hazardLevel.getText().equals("") && !Description.getText().equals("")) valid = true;
    SubmitButton.setDisable(!valid);
  }
}
