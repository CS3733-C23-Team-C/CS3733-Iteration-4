package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import edu.wpi.capybara.objects.hibernate.CleaningsubmissionEntity;
import java.io.IOException;
import java.util.LinkedList;

import edu.wpi.capybara.objects.hibernate.SecuritysubmissionEntity;
import edu.wpi.capybara.objects.hibernate.TransportationsubmissionEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CleaningRequestController {

  @FXML TableView<CleaningsubmissionEntity> allRequests;

  @FXML TableView<TransportationsubmissionEntity> transportationRequests;
  @FXML TableView<SecuritysubmissionEntity> securityRequests;
  @FXML TableColumn staffid;
  @FXML TableColumn hazardLevel;
  @FXML TableColumn description;
  @FXML TableColumn location;
  @FXML TableColumn status;
  @FXML TableColumn destinationroom;
  @FXML TableColumn type;


  ObservableList<CleaningsubmissionEntity> totalRequests = FXCollections.observableArrayList();


  /** When it switches to page, gets data from submission collector and creates tables */
  public void initialize() {
    staffid.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("memberid"));
    hazardLevel.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("hazardlevel"));
    location.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("location"));
    description.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("description"));
    status.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("submissionstatus"));
    destinationroom.setCellValueFactory(
            new PropertyValueFactory<TransportationsubmissionEntity, String>("DestRoomnum"));
    LinkedList<CleaningsubmissionEntity> data = App.getTotalSubmissions().getCleaningSubmissions();
    for (CleaningsubmissionEntity sub : data) {
      totalRequests.add(sub);
    }
    allRequests.setItems(totalRequests);
  }

  public void back(ActionEvent actionEvent) throws IOException {
    Navigation.navigate(Screen.HOME);
  }
}
