package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import edu.wpi.capybara.objects.hibernate.CleaningsubmissionEntity;
import edu.wpi.capybara.objects.hibernate.SecuritysubmissionEntity;
import edu.wpi.capybara.objects.hibernate.TransportationsubmissionEntity;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import java.io.IOException;
import java.util.LinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CleaningRequestController {

  @FXML TableView allRequests;

  @FXML TableColumn staffID;
  @FXML TableColumn hazardLevel;
  @FXML TableColumn description;
  @FXML TableColumn location;
  @FXML TableColumn status;
  @FXML TableColumn destination;
  @FXML TableColumn type;
  @FXML MFXComboBox<String> requestType;

  ObservableList<CleaningsubmissionEntity> cleaningRequests = FXCollections.observableArrayList();
  ObservableList<TransportationsubmissionEntity> transportationRequests =
      FXCollections.observableArrayList();
  ObservableList<SecuritysubmissionEntity> securityRequests = FXCollections.observableArrayList();
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
    for (CleaningsubmissionEntity sub : cleaningdata) {
      cleaningRequests.add(sub);
    }
    allRequests.setItems(cleaningRequests);
    for (TransportationsubmissionEntity sub : transportationdata) {
      transportationRequests.add(sub);
    }
    allRequests.setItems(transportationRequests);
    for (SecuritysubmissionEntity sub : securitydata) {
      securityRequests.add(sub);
    }
  }

  public void updateTable() {
    staffID.setVisible(false);
    location.setVisible(false);
    destination.setVisible(false);
    type.setVisible(false);
    hazardLevel.setVisible(false);
    description.setVisible(false);
    status.setVisible(false);
    String selection = requestType.getValue();
    if (selection.equals("Transportation")) {
      allRequests = new TableView<TransportationsubmissionEntity>();
      staffID = new TableColumn<TransportationsubmissionEntity, String>();
      location = new TableColumn<TransportationsubmissionEntity, String>();
      destination = new TableColumn<TransportationsubmissionEntity, String>();
      status = new TableColumn<TransportationsubmissionEntity, String>();
      staffID.setCellValueFactory(
          new PropertyValueFactory<TransportationsubmissionEntity, String>("employeeid"));
      location.setCellValueFactory(
          new PropertyValueFactory<TransportationsubmissionEntity, String>("currroomnum"));
      destination.setCellValueFactory(
          new PropertyValueFactory<TransportationsubmissionEntity, String>("destroomnum"));
      description.setCellValueFactory(
              new PropertyValueFactory<TransportationsubmissionEntity, String>("reason"));
      status.setCellValueFactory(
          new PropertyValueFactory<TransportationsubmissionEntity, String>("submissionstatus"));
      staffID.setVisible(true);
      location.setVisible(true);
      destination.setVisible(true);
      status.setVisible(true);
      allRequests.setItems(transportationRequests);
    } else if (selection.equals("Cleaning")) {
      allRequests = new TableView<CleaningsubmissionEntity>();
      staffID = new TableColumn<CleaningsubmissionEntity, String>();
      location = new TableColumn<CleaningsubmissionEntity, String>();
      hazardLevel = new TableColumn<CleaningsubmissionEntity, String>();
      description = new TableColumn<CleaningsubmissionEntity, String>();
      status = new TableColumn<TransportationsubmissionEntity, String>();
      staffID.setCellValueFactory(
          new PropertyValueFactory<CleaningsubmissionEntity, String>("memberid"));
      hazardLevel.setCellValueFactory(
          new PropertyValueFactory<CleaningsubmissionEntity, String>("hazardlevel"));
      location.setCellValueFactory(
          new PropertyValueFactory<CleaningsubmissionEntity, String>("location"));
      description.setCellValueFactory(
          new PropertyValueFactory<CleaningsubmissionEntity, String>("description"));
      status.setCellValueFactory(
          new PropertyValueFactory<CleaningsubmissionEntity, String>("submissionstatus"));
      staffID.setVisible(true);
      location.setVisible(true);
      hazardLevel.setVisible(true);
      description.setVisible(true);
      status.setVisible(true);
      allRequests.setItems(cleaningRequests);
    } else if (selection.equals("Security")) {
      staffID = new TableColumn<SecuritysubmissionEntity, String>();
      location = new TableColumn<SecuritysubmissionEntity, String>();
      destination = new TableColumn<SecuritysubmissionEntity, String>();
      status = new TableColumn<SecuritysubmissionEntity, String>();
      type = new TableColumn<SecuritysubmissionEntity, String>();
      allRequests = new TableView<SecuritysubmissionEntity>();
      staffID.setCellValueFactory(
          new PropertyValueFactory<SecuritysubmissionEntity, String>("employeeid"));
      location.setCellValueFactory(
          new PropertyValueFactory<SecuritysubmissionEntity, String>("location"));
      description.setCellValueFactory(
          new PropertyValueFactory<SecuritysubmissionEntity, String>("notesupdate"));
      type.setCellValueFactory(new PropertyValueFactory<SecuritysubmissionEntity, String>("type"));
      status.setCellValueFactory(
          new PropertyValueFactory<SecuritysubmissionEntity, String>("submissionstatus"));
      staffID.setVisible(true);
      location.setVisible(true);
      description.setVisible(true);
      status.setVisible(true);
      type.setVisible(true);
      allRequests.setItems(securityRequests);
    }
  }

  public void back(ActionEvent actionEvent) throws IOException {
    Navigation.navigate(Screen.HOME);
  }
}
