package edu.wpi.capybara.controllers;

import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import edu.wpi.capybara.objects.Node;
import edu.wpi.capybara.objects.NodeAlphabetComparator;
import edu.wpi.capybara.objects.submissions.securitySubmission;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.io.IOException;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.swing.*;

public class SecurityController {

  @FXML private MFXButton backButton;
  @FXML private MFXButton SubmitButton;
  @FXML private MFXButton clear;

  @FXML private TextArea notesUpdate;

  @FXML private MFXComboBox<String> Type;

  @FXML private TextField employeeID;

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
    employeeID.clear();
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

  private void printf(String s) {}
  // end

  // entered room number

  public void submit(ActionEvent actionEvent) {
    String outputID = employeeID.getText();
    String outputLocation = "" + Location.getValue();
    String outputType = "" + Type.getValue();
    String outputNotes = notesUpdate.getText();
    securitySubmission addSubmission =
        new securitySubmission(outputNotes, outputID, outputLocation, outputType);

    clearFields();
  }

  public void
      validateButton() { // ensures that information has been filled in before allowing submission
    boolean valid = false;
    if (!employeeID.getText().equals("") && !notesUpdate.getText().equals("")) valid = true;
    SubmitButton.setDisable(!valid);
  }
}
