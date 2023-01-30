package edu.wpi.capybara.controllers;

import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CleaningController {

  @FXML public MFXButton SubmitButton;

  @FXML public MFXButton ReturnButton;

  @FXML public TextField Location;
  @FXML public TextArea Description;
  @FXML public Stage primaryStage;

  @FXML public Button ClearButton;
  @FXML public TextField MemberID;


  public void submit(ActionEvent actionEvent) {
    String locationInfo = Location.getText();
    String descriptionInfo = Description.getText();
    System.out.println("Location: " + locationInfo);
    System.out.println("Description: " + descriptionInfo);
  }

  public void back(ActionEvent actionEvent) throws IOException {
    Navigation.navigate(Screen.HOME);
  }

  public void clearRequest(ActionEvent actionEvent){
    Location.clear();
    Description.clear();
  }
}
