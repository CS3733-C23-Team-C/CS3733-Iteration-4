package edu.wpi.capybara.controllers;

import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PathfindingController {

  @FXML public Stage primaryStage;
  @FXML private MFXButton backButton;
  @FXML private MFXButton submitButton;
  @FXML private MFXButton clearButton;
  @FXML private MFXTextField idField;
  @FXML private MFXTextField currRoom;
  @FXML private MFXTextField destRoom;
  @FXML private MFXTextField dateField;
  @FXML private MFXTableView pathTable;
  @FXML private VBox imageVBox;
  // @FXML private ImageView map;
  // @FXML private MFXComboBox<String> dropDown;

  // private ObservableList<String> options = FXCollections.observableArrayList();
  /** Initialize controller by FXML Loader. */
  @FXML
  public void initialize() {
    System.out.println("I am from Pathfinder Controller.");
  }

  /**
   * FXML Injected Method that handles the submit button.
   *
   * @param actionEvent The event that triggered the method.
   */
  public void returnHome(ActionEvent actionEvent)
      throws IOException { // when back button is pressed
    Navigation.navigate(Screen.HOME);
  }

  public void submitForm(ActionEvent actionEvent)
      throws IOException { // when submit button is pressed, collects text fields
    String outputID = idField.getText(); // then creates an object to store them, clears fields
    String outputCurrRoom = currRoom.getText();
    String outputDestRoom = destRoom.getText();
    String outputDate = dateField.getText();
    // System.out.println("Current Room: " + outputCurrRoom + " Destination Room: " +
    // outputDestRoom);
    clearFields();
  }

  public void clearFields() { // clears fields of text
    currRoom.setText("");
    destRoom.setText("");
    idField.setText("");
    dateField.setText("");
  }

  public void
      validateButton() { // ensures that information has been filled in before allowing submission
    boolean valid = false;
    if ((!currRoom.getText().equals("")
            && !destRoom.getText().equals("")
            && !idField.getText().equals(""))
        && !dateField.getText().equals("")) valid = true;
    submitButton.setDisable(!valid);
  }
}
