package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.Main;
import edu.wpi.capybara.objects.hibernate.CleaningsubmissionEntity;
import edu.wpi.capybara.objects.hibernate.SecuritysubmissionEntity;
import edu.wpi.capybara.objects.hibernate.TransportationsubmissionEntity;
import edu.wpi.capybara.objects.submissions.SubmissionStatus;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.utils.EnumStringConverter;
import java.util.HashMap;
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class CleaningRequestController {

  @FXML TableView<CleaningsubmissionEntity> cleanRequests;
  @FXML TableView<TransportationsubmissionEntity> transportationRequests;
  @FXML TableView<SecuritysubmissionEntity> securityRequests;

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
  @FXML MFXComboBox<String> requestType;
  @FXML MFXButton fieldsEdit;
  @FXML MFXButton fieldsSave;

  ObservableList<CleaningsubmissionEntity> cleaningRequestsList =
      FXCollections.observableArrayList();
  ObservableList<TransportationsubmissionEntity> transportationRequestsList =
      FXCollections.observableArrayList();
  ObservableList<SecuritysubmissionEntity> securityRequestsList =
      FXCollections.observableArrayList();
  private ObservableList<String> options = FXCollections.observableArrayList();

  /** When it switches to page, gets data from submission collector and creates tables */
  public void initialize() {
    cleanRequests.setEditable(true);
    transportationRequests.setEditable(true);
    securityRequests.setEditable(true);
    options.addAll("Transportation", "Cleaning", "Security");
    requestType.setItems(options);

    HashMap<Integer, CleaningsubmissionEntity> cleaningdata = Main.db.getCleaningSubs();
    HashMap<Integer, TransportationsubmissionEntity> transportationdata =
        Main.db.getTransportationSubs();
    HashMap<Integer, SecuritysubmissionEntity> securitydata = Main.db.getSecuritySubs();

    cleanID.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("memberid"));
    cleanID.setCellFactory(TextFieldTableCell.forTableColumn());
    cleanID.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<CleaningsubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<CleaningsubmissionEntity, String> event) {
            CleaningsubmissionEntity clean = event.getRowValue();
            clean.setMemberid(event.getNewValue());
          }
        });
    hazardLevel.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("hazardlevel"));
    hazardLevel.setCellFactory(TextFieldTableCell.forTableColumn());
    hazardLevel.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<CleaningsubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<CleaningsubmissionEntity, String> event) {
            CleaningsubmissionEntity clean = event.getRowValue();
            clean.setHazardlevel(event.getNewValue());
          }
        });
    cleanLocation.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("location"));
    cleanLocation.setCellFactory(TextFieldTableCell.forTableColumn());
    cleanLocation.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<CleaningsubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<CleaningsubmissionEntity, String> event) {
            CleaningsubmissionEntity clean = event.getRowValue();
            clean.setLocation(event.getNewValue());
          }
        });
    cleanStatus.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, SubmissionStatus>("submissionStatus"));
    cleanStatus.setCellFactory(
        TextFieldTableCell.forTableColumn(new EnumStringConverter<>(SubmissionStatus.class)));
    cleanStatus.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<CleaningsubmissionEntity, SubmissionStatus>>() {
          @Override
          public void handle(
              TableColumn.CellEditEvent<CleaningsubmissionEntity, SubmissionStatus> event) {
            CleaningsubmissionEntity clean = event.getRowValue();
            clean.setSubmissionstatus(event.getNewValue());
          }
        });
    new PropertyValueFactory<CleaningsubmissionEntity, SubmissionStatus>("submissionStatus");
    cleanDescription.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("description"));
    cleanDescription.setCellFactory(TextFieldTableCell.forTableColumn());
    cleanDescription.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<CleaningsubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<CleaningsubmissionEntity, String> event) {}
        });
    for (CleaningsubmissionEntity sub : cleaningdata.values()) {
      if (sub.getMemberid() == App.getUser().getStaffid()) {
        cleaningRequestsList.add(sub);
      }
    }
    cleanRequests.setItems(cleaningRequestsList);

    transportationID.setCellValueFactory(
        new PropertyValueFactory<TransportationsubmissionEntity, String>("employeeid"));
    transportationID.setCellFactory(TextFieldTableCell.forTableColumn());
    transportationID.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<TransportationsubmissionEntity, String>>() {
          @Override
          public void handle(
              TableColumn.CellEditEvent<TransportationsubmissionEntity, String> event) {
            TransportationsubmissionEntity transport = event.getRowValue();
            transport.setEmployeeid(event.getNewValue());
          }
        });
    transportationLocation.setCellValueFactory(
        new PropertyValueFactory<TransportationsubmissionEntity, String>("currroomnum"));
    transportationLocation.setCellFactory(TextFieldTableCell.forTableColumn());
    transportationLocation.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<TransportationsubmissionEntity, String>>() {
          @Override
          public void handle(
              TableColumn.CellEditEvent<TransportationsubmissionEntity, String> event) {
            TransportationsubmissionEntity transport = event.getRowValue();
            transport.setCurrroomnum(event.getNewValue());
          }
        });
    destination.setCellValueFactory(
        new PropertyValueFactory<TransportationsubmissionEntity, String>("destroomnum"));
    destination.setCellFactory(TextFieldTableCell.forTableColumn());
    destination.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<TransportationsubmissionEntity, String>>() {
          @Override
          public void handle(
              TableColumn.CellEditEvent<TransportationsubmissionEntity, String> event) {
            TransportationsubmissionEntity transport = event.getRowValue();
            transport.setDestroomnum(event.getNewValue());
          }
        });
    reason.setCellValueFactory(
        new PropertyValueFactory<TransportationsubmissionEntity, String>("reason"));
    reason.setCellFactory(TextFieldTableCell.forTableColumn());
    reason.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<TransportationsubmissionEntity, String>>() {
          @Override
          public void handle(
              TableColumn.CellEditEvent<TransportationsubmissionEntity, String> event) {
            TransportationsubmissionEntity transport = event.getRowValue();
            transport.setReason(event.getNewValue());
          }
        });
    transportationStatus.setCellValueFactory(
        new PropertyValueFactory<TransportationsubmissionEntity, SubmissionStatus>("status"));
    transportationStatus.setCellFactory(
        TextFieldTableCell.forTableColumn(new EnumStringConverter<>(SubmissionStatus.class)));
    transportationStatus.setOnEditCommit(
        new EventHandler<
            TableColumn.CellEditEvent<TransportationsubmissionEntity, SubmissionStatus>>() {
          @Override
          public void handle(
              TableColumn.CellEditEvent<TransportationsubmissionEntity, SubmissionStatus> event) {
            TransportationsubmissionEntity transport = event.getRowValue();
            transport.setStatus(event.getNewValue());
          }
        });

    for (TransportationsubmissionEntity sub : transportationdata.values()) {
      if (Objects.equals(sub.getEmployeeid(), App.getUser().getStaffid())) {
        transportationRequestsList.add(sub);
      }
    }
    transportationRequests.setItems(transportationRequestsList);

    securityID.setCellValueFactory(
        new PropertyValueFactory<SecuritysubmissionEntity, String>("employeeid"));
    securityID.setCellFactory(TextFieldTableCell.forTableColumn());
    securityID.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<SecuritysubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<SecuritysubmissionEntity, String> event) {
            SecuritysubmissionEntity security = event.getRowValue();
            security.setEmployeeid(event.getNewValue());
          }
        });
    securityLocation.setCellValueFactory(
        new PropertyValueFactory<SecuritysubmissionEntity, String>("location"));
    securityLocation.setCellFactory(TextFieldTableCell.forTableColumn());
    securityLocation.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<SecuritysubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<SecuritysubmissionEntity, String> event) {
            SecuritysubmissionEntity security = event.getRowValue();
            security.setLocation(event.getNewValue());
          }
        });
    securityDescription.setCellValueFactory(
        new PropertyValueFactory<SecuritysubmissionEntity, String>("notesupdate"));
    securityDescription.setCellFactory(TextFieldTableCell.forTableColumn());
    securityDescription.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<SecuritysubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<SecuritysubmissionEntity, String> event) {
            SecuritysubmissionEntity security = event.getRowValue();
            security.setNotesupdate(event.getNewValue());
          }
        });
    type.setCellValueFactory(new PropertyValueFactory<SecuritysubmissionEntity, String>("type"));
    type.setCellFactory(TextFieldTableCell.forTableColumn());
    type.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<SecuritysubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<SecuritysubmissionEntity, String> event) {
            SecuritysubmissionEntity security = event.getRowValue();
            security.setType(event.getNewValue());
          }
        });
    securityStatus.setCellValueFactory(
        new PropertyValueFactory<SecuritysubmissionEntity, SubmissionStatus>("submissionstatus"));
    securityStatus.setCellFactory(
        TextFieldTableCell.forTableColumn(new EnumStringConverter<>(SubmissionStatus.class)));
    securityStatus.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<SecuritysubmissionEntity, SubmissionStatus>>() {
          @Override
          public void handle(
              TableColumn.CellEditEvent<SecuritysubmissionEntity, SubmissionStatus> event) {
            SecuritysubmissionEntity security = event.getRowValue();
            security.setSubmissionstatus(event.getNewValue());
          }
        });

    for (SecuritysubmissionEntity sub : securitydata.values()) {
      if (Objects.equals(sub.getEmployeeid(), App.getUser().getStaffid())) {
        securityRequestsList.add(sub);
      }
    }
    securityRequests.setItems(securityRequestsList);
    transportationRequests.setVisible(false);
    securityRequests.setVisible(false);
    cleanRequests.setVisible(false);
  }

  public void updateTable() {
    String selection = requestType.getValue();
    transportationRequests.setVisible(false);
    securityRequests.setVisible(false);
    cleanRequests.setVisible(false);
    if (selection.equals("Transportation")) {
      transportationRequests.setVisible(true);
    } else if (selection.equals("Cleaning")) {
      cleanRequests.setVisible(true);
    } else if (selection.equals("Security")) {
      securityRequests.setVisible(true);
    }
  }

  public void edit(ActionEvent actionEvent) {}

  public void save(ActionEvent actionEvent) {}
}
