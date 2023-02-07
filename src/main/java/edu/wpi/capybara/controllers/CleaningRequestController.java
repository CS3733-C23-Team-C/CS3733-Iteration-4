package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import edu.wpi.capybara.objects.hibernate.CleaningsubmissionEntity;
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

  @FXML TableView<CleaningsubmissionEntity> allRequests;
  @FXML TableColumn memberIDNeeded;
  @FXML TableColumn hazardLevel;
  @FXML TableColumn description;
  @FXML TableColumn locationCleanup;
  @FXML TableColumn status;

  ObservableList<CleaningsubmissionEntity> totalRequests = FXCollections.observableArrayList();

  /** When it switches to page, gets data from submission collector and creates tables */
  public void initialize() {
    memberIDNeeded.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("memberID"));
    hazardLevel.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("hazardLevel"));
    locationCleanup.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("location"));
    description.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("description"));
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
