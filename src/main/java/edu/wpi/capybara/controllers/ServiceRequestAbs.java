package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.objects.NodeAlphabetComparator;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import edu.wpi.capybara.objects.submissions.ISubmission;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

public abstract class ServiceRequestAbs {
  @FXML private MFXTextField assignedStaffID;
  @FXML private MFXComboBox<String> location;
  @FXML private MFXComboBox<String> requestSpecific;
  @FXML private MFXComboBox<String> emergencyLevel;
  @FXML private MFXDatePicker date;
  @FXML private MFXTextField notes;
  @FXML private MFXButton clearButton;
  @FXML private MFXButton submitButton;
  @FXML private Text submissionRecieved;
  @FXML private Text missingFields;
  private ISubmission submission; // sets submission type

  @FXML
  public void inititalize() {
    TreeMap<String, NodeEntity> nodes = DatabaseConnect.getNodes();
    SortedSet<NodeEntity> sortedset = new TreeSet<NodeEntity>(new NodeAlphabetComparator());

    sortedset.addAll(nodes.values());

    Iterator<NodeEntity> iterator = sortedset.iterator();
    while (iterator.hasNext()) {
      NodeEntity n = iterator.next();
      // System.out.println(n.getShortName());
      location.getItems().add(n.getShortName());
    }
    // Set a default variable
    location.getSelectionModel().selectFirst();

    setRequestSpecific(); // sets dropdown options for requestSpecific field, overridden in specific
    // class

    ObservableList<String> eLevels = FXCollections.observableArrayList();
    eLevels.addAll("Urgent", "High", "Medium", "Low");
    emergencyLevel.setItems(eLevels);

    submitButton.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            if(submitButton.isDisabled()) missingFields.setVisible(true);
            else{
              newSubmission();
              clearFields();
              missingFields.setVisible(false);
              submissionRecieved.setVisible(true);
            }
          }
        });

    clearButton.setOnAction(
            new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent event) {clearFields();}
            });

    assignedStaffID.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        validateButton();
      }
    });

    location.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        validateButton();
      }
    });

    requestSpecific.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        validateButton();
      }
    });

    emergencyLevel.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        validateButton();
      }
    });

    date.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        validateButton();
      }
    });

    notes.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        validateButton();
      }
    });
  }

  public void clearFields() {
    assignedStaffID.clear();
    location.clearSelection();
    requestSpecific.clearSelection();
    emergencyLevel.clearSelection();
    date.clear();
    notes.clear();
  }

  public void
      validateButton() { // ensures that information has been filled in before allowing submission
    boolean valid = false;
    if (!assignedStaffID.getText().equals("")
        && !location.getValue().equals("")
        && !requestSpecific.getValue().equals("")
        && !emergencyLevel.getValue().equals("")
        && !date.getValue().equals(0)
        && !notes.getText().equals("")) valid = true;
    submitButton.setDisable(!valid);
    submissionRecieved.setVisible(false);
    missingFields.setVisible(false);
  }

  public void newSubmission() {
    String currStaffID = App.getUser().getStaffid();
    String outputAssignedStaffID = assignedStaffID.getText();
    String outputLocation = location.getValue();
    String outputRequestSpecific = requestSpecific.getValue();
    String outputELevel = emergencyLevel.getValue();
    LocalDate outputDate = date.getValue();
    String outputNotes = notes.getText();
    submission.newSubmission(
        currStaffID,
        outputAssignedStaffID,
        outputLocation,
        outputRequestSpecific,
        outputELevel,
        outputDate,
        outputNotes);
  }

  public void setRequestSpecific() {}
}
