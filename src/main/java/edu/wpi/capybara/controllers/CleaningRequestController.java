package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.objects.hibernate.CleaningsubmissionEntity;
import edu.wpi.capybara.objects.hibernate.SecuritysubmissionEntity;
import edu.wpi.capybara.objects.hibernate.TransportationsubmissionEntity;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
  @FXML MFXComboBox<String> requestType;

  ObservableList<CleaningsubmissionEntity> cleaningRequestsList =
      FXCollections.observableArrayList();
  ObservableList<TransportationsubmissionEntity> transportationRequestsList =
      FXCollections.observableArrayList();
  ObservableList<SecuritysubmissionEntity> securityRequestsList =
      FXCollections.observableArrayList();
  private ObservableList<String> options = FXCollections.observableArrayList();

  /** When it switches to page, gets data from submission collector and creates tables */
  public void initialize() {
    options.addAll("Transportation", "Cleaning", "Security");
    requestType.setItems(options);

    LinkedList<CleaningsubmissionEntity> cleaningdata =
        App.getTotalSubmissions().getCleaningSubmissions();
    LinkedList<TransportationsubmissionEntity> transportationdata =
        App.getTotalSubmissions().getTransportationSubs();
    LinkedList<SecuritysubmissionEntity> securitydata = App.getTotalSubmissions().getSecuritySubs();

    cleanID.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("memberid"));
    hazardLevel.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("hazardlevel"));
    cleanLocation.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("location"));
    cleanStatus.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("description"));
    cleanDescription.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("submissionstatus"));
    for (CleaningsubmissionEntity sub : cleaningdata) {
      cleaningRequestsList.add(sub);
    }
    cleanRequests.setItems(cleaningRequestsList);

    transportationID.setCellValueFactory(
        new PropertyValueFactory<TransportationsubmissionEntity, String>("employeeid"));
    transportationLocation.setCellValueFactory(
        new PropertyValueFactory<TransportationsubmissionEntity, String>("currroomnum"));
    destination.setCellValueFactory(
        new PropertyValueFactory<TransportationsubmissionEntity, String>("destroomnum"));
    reason.setCellValueFactory(
        new PropertyValueFactory<TransportationsubmissionEntity, String>("reason"));
    transportationStatus.setCellValueFactory(
        new PropertyValueFactory<TransportationsubmissionEntity, String>("status"));
    for (TransportationsubmissionEntity sub : transportationdata) {
      transportationRequestsList.add(sub);
    }
    transportationRequests.setItems(transportationRequestsList);

    securityID.setCellValueFactory(
        new PropertyValueFactory<SecuritysubmissionEntity, String>("employeeid"));
    securityLocation.setCellValueFactory(
        new PropertyValueFactory<SecuritysubmissionEntity, String>("location"));
    securityDescription.setCellValueFactory(
        new PropertyValueFactory<SecuritysubmissionEntity, String>("notesupdate"));
    type.setCellValueFactory(new PropertyValueFactory<SecuritysubmissionEntity, String>("type"));
    securityStatus.setCellValueFactory(
        new PropertyValueFactory<SecuritysubmissionEntity, String>("submissionstatus"));
    for (SecuritysubmissionEntity sub : securitydata) {
      securityRequestsList.add(sub);
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
