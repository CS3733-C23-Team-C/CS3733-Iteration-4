package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.Main;
import edu.wpi.capybara.objects.hibernate.*;
import edu.wpi.capybara.objects.submissions.SubmissionStatus;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class AssignedRequestController {

  @FXML TableView<CleaningsubmissionEntity> cleanRequests;
  @FXML TableView<TransportationsubmissionEntity> transportationRequests;
  @FXML TableView<SecuritysubmissionEntity> securityRequests;
  @FXML TableView<AudiosubmissionEntity> audioRequests;
  @FXML TableView<ComputersubmissionEntity> computerRequests;

  @FXML TableColumn<CleaningsubmissionEntity, String> cleanID;
  @FXML TableColumn<TransportationsubmissionEntity, String> transportationID;
  @FXML TableColumn<SecuritysubmissionEntity, String> securityID;
  @FXML TableColumn<CleaningsubmissionEntity, String> hazardLevel;
  @FXML TableColumn<CleaningsubmissionEntity, String> cleanDescription;
  @FXML TableColumn<TransportationsubmissionEntity, String> reason;
  @FXML TableColumn<SecuritysubmissionEntity, String> securityDescription;
  @FXML TableColumn<CleaningsubmissionEntity, String> cleanLocation;
  @FXML TableColumn<TransportationsubmissionEntity, String> transportationLocation;
  @FXML TableColumn<SecuritysubmissionEntity, String> securityLocation;
  @FXML TableColumn<CleaningsubmissionEntity, SubmissionStatus> cleanStatus;
  @FXML TableColumn<TransportationsubmissionEntity, SubmissionStatus> transportationStatus;
  @FXML TableColumn<SecuritysubmissionEntity, SubmissionStatus> securityStatus;
  @FXML TableColumn<TransportationsubmissionEntity, String> destination;
  @FXML TableColumn<SecuritysubmissionEntity, String> type;
  @FXML TableColumn<CleaningsubmissionEntity, String> cleanEmployeeAssigned;
  @FXML TableColumn<SecuritysubmissionEntity, String> securityEmployeeAssigned;
  @FXML TableColumn<TransportationsubmissionEntity, String> transportationEmployeeAssigned;
  @FXML TableColumn<AudiosubmissionEntity, String> audioID;
  @FXML TableColumn<AudiosubmissionEntity, String> audioLocation;
  @FXML TableColumn<AudiosubmissionEntity, String> audioItem;
  @FXML TableColumn<AudiosubmissionEntity, String> audioDescription;
  @FXML TableColumn<AudiosubmissionEntity, String> audioAssignedID;
  @FXML TableColumn<AudiosubmissionEntity, SubmissionStatus> audioStatus;
  @FXML TableColumn<ComputersubmissionEntity, String> computerID;
  @FXML TableColumn<ComputersubmissionEntity, String> computerLocation;
  @FXML TableColumn<ComputersubmissionEntity, String> computerItem;
  @FXML TableColumn<ComputersubmissionEntity, String> computerDescription;
  @FXML TableColumn<ComputersubmissionEntity, String> computerAssignedID;
  @FXML TableColumn<ComputersubmissionEntity, SubmissionStatus> computerStatus;
  @FXML TableColumn<ComputersubmissionEntity, String> computerELevel;
  @FXML TableColumn<CleaningsubmissionEntity, String> cleanELevel;
  @FXML TableColumn<TransportationsubmissionEntity, String> transportationELevel;
  @FXML TableColumn<SecuritysubmissionEntity, String> securityELevel;
  @FXML TableColumn<AudiosubmissionEntity, String> audioELevel;

  @FXML MFXComboBox<String> requestType;
  @FXML MFXButton fieldsEdit;
  @FXML MFXButton fieldsSave;

  ObservableList<CleaningsubmissionEntity> cleaningRequestsList =
      FXCollections.observableArrayList();
  ObservableList<TransportationsubmissionEntity> transportationRequestsList =
      FXCollections.observableArrayList();
  ObservableList<SecuritysubmissionEntity> securityRequestsList =
      FXCollections.observableArrayList();
  ObservableList<AudiosubmissionEntity> audioRequestsList = FXCollections.observableArrayList();
  ObservableList<ComputersubmissionEntity> computerRequestsList =
      FXCollections.observableArrayList();
  private final ObservableList<String> options = FXCollections.observableArrayList();

  /** When it switches to page, gets data from submission collector and creates tables */
  public void initialize() {
    cleanRequests.setEditable(true);
    transportationRequests.setEditable(true);
    securityRequests.setEditable(true);
    audioRequests.setEditable(true);
    computerRequests.setEditable(true);
    options.addAll("Transportation", "Cleaning", "Security", "Audio", "Computer");
    requestType.setItems(options);

    Map<Integer, CleaningsubmissionEntity> cleaningdata = Main.db.getCleaningSubs();
    Map<Integer, TransportationsubmissionEntity> transportationdata =
        Main.db.getTransportationSubs();
    Map<Integer, SecuritysubmissionEntity> securitydata = Main.db.getSecuritySubs();
    Map<Integer, AudiosubmissionEntity> audiodata = Main.db.getAudioSubs();
    Map<Integer, ComputersubmissionEntity> computerdata = Main.db.getComputerSubs();

    /*cleaning columns*/
    cleanEmployeeAssigned.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("assignedid"));
    cleanEmployeeAssigned.setCellFactory(TextFieldTableCell.forTableColumn());
    cleanID.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("memberid"));
    cleanID.setCellFactory(TextFieldTableCell.forTableColumn());
    hazardLevel.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("hazardlevel"));
    hazardLevel.setCellFactory(TextFieldTableCell.forTableColumn());

    cleanLocation.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("location"));
    cleanLocation.setCellFactory(TextFieldTableCell.forTableColumn());

    cleanStatus.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, SubmissionStatus>("submissionstatus"));
    cleanStatus.setCellFactory(ComboBoxTableCell.forTableColumn(SubmissionStatus.values()));
    cleanStatus.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<CleaningsubmissionEntity, SubmissionStatus>>() {
          @Override
          public void handle(
              TableColumn.CellEditEvent<CleaningsubmissionEntity, SubmissionStatus> event) {
            CleaningsubmissionEntity clean = event.getRowValue();
            clean.setSubmissionstatus(event.getNewValue());
            clean.setSubmissionstatus(event.getNewValue());
            if (!clean.getSubmissionstatus().equals(SubmissionStatus.BLANK)) {
              java.util.Date submissionDate = new java.util.Date();
              Timestamp time = new Timestamp(submissionDate.getTime());
              int messageID = Main.db.generateMessageID();
              MessagesEntity newMessage =
                  new MessagesEntity(
                      messageID,
                      "SYSTEM",
                      clean.getMemberid(),
                      time,
                      "Request #"
                          + clean.getSubmissionid()
                          + " has been marked as "
                          + clean.getSubmissionstatus().toString(),
                      false);
              Main.db.addMessage(newMessage);
            }
          }
        });
    cleanDescription.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("description"));
    cleanDescription.setCellFactory(TextFieldTableCell.forTableColumn());
    cleanELevel.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("urgency"));
    cleanELevel.setCellFactory(TextFieldTableCell.forTableColumn());
    for (CleaningsubmissionEntity sub : cleaningdata.values()) {
      if (Objects.equals(App.getUser().getStaffid(), sub.getAssignedid())) {
        cleaningRequestsList.add(sub);
      }
    }
    cleanRequests.setItems(cleaningRequestsList);

    /*transportation columns*/
    transportationEmployeeAssigned.setCellValueFactory(
        new PropertyValueFactory<TransportationsubmissionEntity, String>("assignedid"));
    transportationEmployeeAssigned.setCellFactory(TextFieldTableCell.forTableColumn());

    transportationID.setCellValueFactory(
        new PropertyValueFactory<TransportationsubmissionEntity, String>("employeeid"));
    transportationID.setCellFactory(TextFieldTableCell.forTableColumn());

    transportationLocation.setCellValueFactory(
        new PropertyValueFactory<TransportationsubmissionEntity, String>("currroomnum"));
    transportationLocation.setCellFactory(TextFieldTableCell.forTableColumn());

    destination.setCellValueFactory(
        new PropertyValueFactory<TransportationsubmissionEntity, String>("destroomnum"));
    destination.setCellFactory(TextFieldTableCell.forTableColumn());

    reason.setCellValueFactory(
        new PropertyValueFactory<TransportationsubmissionEntity, String>("reason"));
    reason.setCellFactory(TextFieldTableCell.forTableColumn());
    transportationStatus.setCellValueFactory(
        new PropertyValueFactory<TransportationsubmissionEntity, SubmissionStatus>("status"));
    transportationStatus.setCellFactory(
        ComboBoxTableCell.forTableColumn(SubmissionStatus.values()));
    transportationStatus.setOnEditCommit(
        new EventHandler<
            TableColumn.CellEditEvent<TransportationsubmissionEntity, SubmissionStatus>>() {
          @Override
          public void handle(
              TableColumn.CellEditEvent<TransportationsubmissionEntity, SubmissionStatus> event) {
            TransportationsubmissionEntity transport = event.getRowValue();
            transport.setStatus(event.getNewValue());
            transport.setStatus(event.getNewValue());
            if (!transport.getStatus().equals(SubmissionStatus.BLANK)) {
              java.util.Date submissionDate = new java.util.Date();
              Timestamp time = new Timestamp(submissionDate.getTime());
              int messageID = Main.db.generateMessageID();
              MessagesEntity newMessage =
                  new MessagesEntity(
                      messageID,
                      "SYSTEM",
                      transport.getEmployeeid(),
                      time,
                      "Request #"
                          + transport.getSubmissionid()
                          + " has been marked as "
                          + transport.getStatus().toString(),
                      false);
              Main.db.addMessage(newMessage);
            }
          }
        });
    transportationELevel.setCellValueFactory(
        new PropertyValueFactory<TransportationsubmissionEntity, String>("urgency"));
    transportationELevel.setCellFactory(TextFieldTableCell.forTableColumn());

    for (TransportationsubmissionEntity sub : transportationdata.values()) {
      if (Objects.equals(App.getUser().getStaffid(), sub.getAssignedid())) {
        transportationRequestsList.add(sub);
      }
    }
    transportationRequests.setItems(transportationRequestsList);

    /*security columns*/
    securityID.setCellValueFactory(
        new PropertyValueFactory<SecuritysubmissionEntity, String>("employeeid"));
    securityID.setCellFactory(TextFieldTableCell.forTableColumn());

    securityLocation.setCellValueFactory(
        new PropertyValueFactory<SecuritysubmissionEntity, String>("location"));
    securityLocation.setCellFactory(TextFieldTableCell.forTableColumn());
    securityDescription.setCellValueFactory(
        new PropertyValueFactory<SecuritysubmissionEntity, String>("notesupdate"));
    securityDescription.setCellFactory(TextFieldTableCell.forTableColumn());
    type.setCellValueFactory(new PropertyValueFactory<SecuritysubmissionEntity, String>("type"));
    type.setCellFactory(TextFieldTableCell.forTableColumn());
    securityStatus.setCellValueFactory(
        new PropertyValueFactory<SecuritysubmissionEntity, SubmissionStatus>("submissionstatus"));
    securityStatus.setCellFactory(ComboBoxTableCell.forTableColumn(SubmissionStatus.values()));
    securityStatus.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<SecuritysubmissionEntity, SubmissionStatus>>() {
          @Override
          public void handle(
              TableColumn.CellEditEvent<SecuritysubmissionEntity, SubmissionStatus> event) {
            SecuritysubmissionEntity security = event.getRowValue();
            security.setSubmissionstatus(event.getNewValue());
            security.setSubmissionstatus(event.getNewValue());
            if (!security.getSubmissionstatus().equals(SubmissionStatus.BLANK)) {
              java.util.Date submissionDate = new java.util.Date();
              Timestamp time = new Timestamp(submissionDate.getTime());
              int messageID = Main.db.generateMessageID();
              MessagesEntity newMessage =
                  new MessagesEntity(
                      messageID,
                      "SYSTEM",
                      security.getEmployeeid(),
                      time,
                      "Request #"
                          + security.getSubmissionid()
                          + " has been marked as "
                          + security.getSubmissionstatus().toString(),
                      false);
              Main.db.addMessage(newMessage);
            }
          }
        });

    securityEmployeeAssigned.setCellValueFactory(
        new PropertyValueFactory<SecuritysubmissionEntity, String>("assignedid"));
    securityEmployeeAssigned.setCellFactory(TextFieldTableCell.forTableColumn());
    securityELevel.setCellValueFactory(
        new PropertyValueFactory<SecuritysubmissionEntity, String>("urgency"));
    securityELevel.setCellFactory(TextFieldTableCell.forTableColumn());

    for (SecuritysubmissionEntity sub : securitydata.values()) {
      if (Objects.equals(App.getUser().getStaffid(), sub.getAssignedid())) {
        securityRequestsList.add(sub);
      }
    }
    securityRequests.setItems(securityRequestsList);

    /*audio columns*/

    audioID.setCellValueFactory(
        new PropertyValueFactory<AudiosubmissionEntity, String>("employeeid"));
    audioID.setCellFactory(TextFieldTableCell.forTableColumn());
    audioItem.setCellValueFactory(new PropertyValueFactory<AudiosubmissionEntity, String>("type"));
    audioItem.setCellFactory(TextFieldTableCell.forTableColumn());
    audioLocation.setCellValueFactory(
        new PropertyValueFactory<AudiosubmissionEntity, String>("location"));
    audioLocation.setCellFactory(TextFieldTableCell.forTableColumn());
    audioDescription.setCellValueFactory(
        new PropertyValueFactory<AudiosubmissionEntity, String>("notesupdate"));
    audioDescription.setCellFactory(TextFieldTableCell.forTableColumn());
    audioStatus.setCellValueFactory(
        new PropertyValueFactory<AudiosubmissionEntity, SubmissionStatus>("submissionstatus"));
    audioStatus.setCellFactory(ComboBoxTableCell.forTableColumn(SubmissionStatus.values()));
    audioStatus.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<AudiosubmissionEntity, SubmissionStatus>>() {
          @Override
          public void handle(
              TableColumn.CellEditEvent<AudiosubmissionEntity, SubmissionStatus> event) {
            AudiosubmissionEntity audio = event.getRowValue();
            audio.setSubmissionstatus(event.getNewValue());
            audio.setSubmissionstatus(event.getNewValue());
            if (!audio.getSubmissionstatus().equals(SubmissionStatus.BLANK)) {
              java.util.Date submissionDate = new java.util.Date();
              Timestamp time = new Timestamp(submissionDate.getTime());
              int messageID = Main.db.generateMessageID();
              MessagesEntity newMessage =
                  new MessagesEntity(
                      messageID,
                      "SYSTEM",
                      audio.getEmployeeid(),
                      time,
                      "Request #"
                          + audio.getSubmissionid()
                          + " has been marked as "
                          + audio.getSubmissionstatus().toString(),
                      false);
              Main.db.addMessage(newMessage);
            }
          }
        });
    audioAssignedID.setCellValueFactory(
        new PropertyValueFactory<AudiosubmissionEntity, String>("assignedid"));
    audioAssignedID.setCellFactory(TextFieldTableCell.forTableColumn());
    audioELevel.setCellValueFactory(
        new PropertyValueFactory<AudiosubmissionEntity, String>("urgency"));
    audioELevel.setCellFactory(TextFieldTableCell.forTableColumn());
    for (AudiosubmissionEntity sub : audiodata.values()) {
      if (Objects.equals(App.getUser().getStaffid(), sub.getAssignedid())) {
        audioRequestsList.add(sub);
      }
    }
    audioRequests.setItems(audioRequestsList);

    /*computer columns*/
    computerID.setCellValueFactory(
        new PropertyValueFactory<ComputersubmissionEntity, String>("employeeid"));
    computerID.setCellFactory(TextFieldTableCell.forTableColumn());
    computerItem.setCellValueFactory(
        new PropertyValueFactory<ComputersubmissionEntity, String>("type"));
    computerItem.setCellFactory(TextFieldTableCell.forTableColumn());
    computerLocation.setCellValueFactory(
        new PropertyValueFactory<ComputersubmissionEntity, String>("location"));
    computerLocation.setCellFactory(TextFieldTableCell.forTableColumn());
    computerDescription.setCellValueFactory(
        new PropertyValueFactory<ComputersubmissionEntity, String>("notesupdate"));
    computerDescription.setCellFactory(TextFieldTableCell.forTableColumn());
    computerStatus.setCellValueFactory(
        new PropertyValueFactory<ComputersubmissionEntity, SubmissionStatus>("submissionstatus"));
    computerStatus.setCellFactory(ComboBoxTableCell.forTableColumn(SubmissionStatus.values()));
    computerStatus.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<ComputersubmissionEntity, SubmissionStatus>>() {
          @Override
          public void handle(
              TableColumn.CellEditEvent<ComputersubmissionEntity, SubmissionStatus> event) {
            ComputersubmissionEntity computer = event.getRowValue();
            computer.setSubmissionstatus(event.getNewValue());
            computer.setSubmissionstatus(event.getNewValue());
            if (!computer.getSubmissionstatus().equals(SubmissionStatus.BLANK)) {
              java.util.Date submissionDate = new java.util.Date();
              Timestamp time = new Timestamp(submissionDate.getTime());
              int messageID = Main.db.generateMessageID();
              MessagesEntity newMessage =
                  new MessagesEntity(
                      messageID,
                      "SYSTEM",
                      computer.getEmployeeid(),
                      time,
                      "Request #"
                          + computer.getSubmissionid()
                          + " has been marked as "
                          + computer.getSubmissionstatus().toString(),
                      false);
              Main.db.addMessage(newMessage);
            }
          }
        });
    computerAssignedID.setCellValueFactory(
        new PropertyValueFactory<ComputersubmissionEntity, String>("assignedid"));
    computerAssignedID.setCellFactory(TextFieldTableCell.forTableColumn());
    computerELevel.setCellValueFactory(
        new PropertyValueFactory<ComputersubmissionEntity, String>("urgency"));
    computerELevel.setCellFactory(TextFieldTableCell.forTableColumn());
    for (ComputersubmissionEntity sub : computerdata.values()) {
      if (Objects.equals(App.getUser().getStaffid(), sub.getAssignedid())) {
        computerRequestsList.add(sub);
      }
    }
    computerRequests.setItems(computerRequestsList);

    transportationRequests.setVisible(false);
    securityRequests.setVisible(false);
    cleanRequests.setVisible(false);
    audioRequests.setVisible(false);
    computerRequests.setVisible(false);
  }

  public void updateTable() {
    String selection = requestType.getValue();
    transportationRequests.setVisible(false);
    securityRequests.setVisible(false);
    cleanRequests.setVisible(false);
    audioRequests.setVisible(false);
    computerRequests.setVisible(false);
    if (selection.equals("Transportation")) {
      transportationRequests.setVisible(true);
    } else if (selection.equals("Cleaning")) {
      cleanRequests.setVisible(true);
    } else if (selection.equals("Security")) {
      securityRequests.setVisible(true);
    } else if (selection.equals("Audio")) {
      audioRequests.setVisible(true);
    } else if (selection.equals("Computer")) {
      computerRequests.setVisible(true);
    }
  }

  public void edit(ActionEvent actionEvent) {}

  public void save(ActionEvent actionEvent) {}
}
