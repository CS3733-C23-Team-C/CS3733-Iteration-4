package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import edu.wpi.capybara.objects.submissions.cleaningSubmission;
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

  @FXML TableView<cleaningSubmission> allRequests;
  @FXML TableColumn memberIDNeeded;
  @FXML TableColumn hazardLevel;
  @FXML TableColumn description;
  @FXML TableColumn locationCleanup;
  @FXML TableColumn status;

  ObservableList<cleaningSubmission> totalRequests = FXCollections.observableArrayList();

  /** When it switches to page, gets data from submission collector and creates tables */
  public void initialize() {
    memberIDNeeded.setCellValueFactory(
        new PropertyValueFactory<cleaningSubmission, String>("memberID"));
    hazardLevel.setCellValueFactory(
        new PropertyValueFactory<cleaningSubmission, String>("hazardLevel"));
    locationCleanup.setCellValueFactory(
        new PropertyValueFactory<cleaningSubmission, String>("location"));
    description.setCellValueFactory(
        new PropertyValueFactory<cleaningSubmission, String>("description"));
    LinkedList<cleaningSubmission> data = App.cleaningSubsTotal.getCleaningSubmissions();
    for (cleaningSubmission sub : data) {
      totalRequests.add(sub);
    }
    allRequests.setItems(totalRequests);
  }

  public void back(ActionEvent actionEvent) throws IOException {
    Navigation.navigate(Screen.HOME);
  }
}
