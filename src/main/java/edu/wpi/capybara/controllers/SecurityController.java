package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import edu.wpi.capybara.objects.NodeAlphabetComparator;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import edu.wpi.capybara.objects.hibernate.SecuritysubmissionEntity;
import edu.wpi.capybara.objects.submissions.Urgency;
import edu.wpi.capybara.objects.submissions.submissionStatus;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.io.IOException;
import java.sql.Date;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javax.swing.*;

public class SecurityController {

  @FXML private MFXButton backButton;
  @FXML private MFXButton SubmitButton;
  @FXML private MFXButton clear;

  @FXML private TextArea notesUpdate;

  @FXML private MFXComboBox<String> Type;

  @FXML public TextArea Description;
  @FXML public Stage primaryStage;

  @FXML public MFXComboBox<String> Location;

  public void returnHome(ActionEvent actionEvent) throws IOException {
    Navigation.navigate(Screen.HOME);
  }

  //  public void submitForm(ActionEvent actionEvent) throws IOException {
  //    String outputRoomNumber = room.getText();
  //    String outputFloorNumber = floorNumber.getText();
  //    String outputNotes = notes.getText();
  //    // System.out.println("Current Room: " + outputCurrRoom + " Destination Room: " +
  //    // outputDestRoom);
  //    securtySubmission newSubmission =
  //            new securtySubmission(outputRoomNumber, outputFloorNumber, outputNotes);
  //    App.totalSubmissions.newTransportationSubmission(newSubmission);
  //    System.out.println(App.totalSubmissions.getTransportationData());
  //    clearFields();
  //  }

  public void clearFields() {
    Location.getSelectionModel().selectFirst();
    Type.getSelectionModel().selectFirst();
    notesUpdate.clear();
    SubmitButton.setDisable(true);
  }

  @FXML
  public void initialize() {
    System.out.println("I am from cleaningController");

    Type.getItems().add("Police Department");
    Type.getItems().add("Fire Department");
    Type.getItems().add("Health Department");

    Type.getSelectionModel().selectFirst();

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

  private void printf(String s) {}
  // end

  // entered room number

  public void submit(ActionEvent actionEvent) {
    String outputLocation = "" + Location.getValue();
    String outputType = "" + Type.getValue();
    String outputNotes = notesUpdate.getText();
    SecuritysubmissionEntity addSubmission =
        new SecuritysubmissionEntity(
            App.getUser().getStaffid(),
            outputLocation,
            outputType,
            outputNotes,
            submissionStatus.BLANK,
            "1",
            (int) (Math.random() * 100000),
            Urgency.BLANK,
            new Date(2000, 1, 1),
            new Date(2000, 1, 2));
    App.getTotalSubmissions().newSecuritySubmission(addSubmission);
    clearFields();
  }

  public void
      validateButton() { // ensures that information has been filled in before allowing submission
    boolean valid = false;
    if (!notesUpdate.getText().equals("")) valid = true;
    SubmitButton.setDisable(!valid);
  }
}
