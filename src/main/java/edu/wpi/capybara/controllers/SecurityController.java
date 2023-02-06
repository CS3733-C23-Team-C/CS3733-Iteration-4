package edu.wpi.capybara.controllers;

import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import edu.wpi.capybara.objects.Node;
import edu.wpi.capybara.objects.NodeAlphabetComparator;
import edu.wpi.capybara.objects.submissions.securitySubmission;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.*;

public class SecurityController {

  @FXML private MFXButton backButton;
  @FXML private MFXButton SubmitButton;
  @FXML private MFXButton clear;

  @FXML private MFXTextField notesUpdate;

  @FXML private MFXButton Police;

  @FXML private MFXButton Fire;

  @FXML private TextField employeeID;

  @FXML private ChoiceBox Location;
  @FXML private TextArea Description;
  @FXML private Stage primaryStage;

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
    employeeID.setText("");
    Location.getSelectionModel().selectFirst();
    notesUpdate.setText("");
  }

  @FXML
  public void initialize() {
    System.out.println("I am from cleaningController");

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

  public void back(ActionEvent actionEvent) throws IOException {
    Navigation.navigate(Screen.HOME);
  }

  // clicking a department
  public void clickedPolice(ActionEvent actionEvent) throws IOException {
    printf("The Police Were Notified and was selected");
  }

  public void clickedFireDepartment(ActionEvent actionEvent) throws IOException {
    printf("The Fire Department was selected");
  }

  public void clickedDoctor(ActionEvent actionEvent) throws IOException {
    printf("The Doctor was selected");
  }

  private void printf(String s) {}
  // end

  // entered room number

  public void submit(ActionEvent actionEvent) {
    String outputeID = employeeID.getText();
    String outputroomNumber1 = "" + Location.getValue();
    String outputnotes = notesUpdate.getText();
    securitySubmission addSubmission =
        new securitySubmission(outputnotes, outputeID, outputroomNumber1);

    clearFields();
  }

  public void clearRequest() {
    Location.setValue("Location");
    notesUpdate.clear();
    employeeID.clear();
  }

  // entered notes/update

  // entered floor number

}
