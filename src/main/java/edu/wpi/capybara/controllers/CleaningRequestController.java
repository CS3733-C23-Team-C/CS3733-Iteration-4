package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.Main;
import edu.wpi.capybara.objects.hibernate.CleaningsubmissionEntity;
import edu.wpi.capybara.objects.hibernate.SecuritysubmissionEntity;
import edu.wpi.capybara.objects.hibernate.TransportationsubmissionEntity;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
  @FXML TableColumn<CleaningsubmissionEntity, String> cleanStatus;
  @FXML TableColumn<TransportationsubmissionEntity, String> transportationStatus;
  @FXML TableColumn<SecuritysubmissionEntity, String> securityStatus;
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
    hazardLevel.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("hazardlevel"));
    hazardLevel.setCellFactory(TextFieldTableCell.forTableColumn());
    cleanLocation.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("location"));
    cleanLocation.setCellFactory(TextFieldTableCell.forTableColumn());
    cleanStatus.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("description"));
    cleanStatus.setCellFactory(TextFieldTableCell.forTableColumn());
    cleanDescription.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("submissionstatus"));
    cleanDescription.setCellFactory(TextFieldTableCell.forTableColumn());
    for (CleaningsubmissionEntity sub : cleaningdata.values()) {
      if (sub.getMemberid() == App.getUser().getStaffid()) {
        cleaningRequestsList.add(sub);
      }
    }
    cleanRequests.setItems(cleaningRequestsList);

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
        new PropertyValueFactory<TransportationsubmissionEntity, String>("status"));
    transportationStatus.setCellFactory(TextFieldTableCell.forTableColumn());
    for (TransportationsubmissionEntity sub : transportationdata.values()) {
      if (sub.getEmployeeid() == App.getUser().getStaffid()) {
        transportationRequestsList.add(sub);
      }
    }
    transportationRequests.setItems(transportationRequestsList);

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
        new PropertyValueFactory<SecuritysubmissionEntity, String>("submissionstatus"));
    securityStatus.setCellFactory(TextFieldTableCell.forTableColumn());
    for (SecuritysubmissionEntity sub : securitydata.values()) {
      if (sub.getEmployeeid() == App.getUser().getStaffid()) {
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

  public void save(ActionEvent actionEvent) {
    String editCleanID = cleanID.getText();
  }
}
