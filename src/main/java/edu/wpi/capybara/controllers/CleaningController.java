package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import edu.wpi.capybara.objects.Node;
import edu.wpi.capybara.objects.NodeAlphabetComparator;
import edu.wpi.capybara.objects.submissions.cleaningSubmission;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CleaningController {

  @FXML public MFXButton SubmitButton;

  @FXML public MFXButton ReturnButton;

  @FXML public ChoiceBox Location;
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

  @FXML
  public void initialize() {
    System.out.println("I am from cleaningController");
    currentStatus = submissionStatus.BLANK;

    // Add different locations

    //    Location.getItems().add("Location");
    //    Location.getItems().add("Location1");

    TreeMap<String, Node> nodes = DatabaseConnect.getNodes();

    SortedSet<Node> sortedset = new TreeSet<Node>(new NodeAlphabetComparator());

    sortedset.addAll(nodes.values());

    Iterator<Node> iterator = sortedset.iterator();
    while (iterator.hasNext()) {
      Node n = iterator.next();
      System.out.println(n.getShortName());
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

    String locationInfo = "" + Location.getValue();
    String descriptionInfo = Description.getText();
    String memberID = MemberID.getText();
    String hazardLevelInfo = hazardLevel.getText();
    cleaningSubmission addSubmission =
        new cleaningSubmission(memberID, locationInfo, hazardLevelInfo, descriptionInfo);
    App.cleaningSubsTotal.newCleaningSub(addSubmission);
    System.out.println(App.cleaningSubsTotal.getCleaningData());
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
    MemberID.clear();
    hazardLevel.clear();
    currentStatus = submissionStatus.BLANK;
  }
}
