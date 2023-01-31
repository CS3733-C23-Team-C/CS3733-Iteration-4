package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import edu.wpi.capybara.objects.submissions.transportationSubmission;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javax.swing.*;

public class TransportationController {

  @FXML public Stage primaryStage;
  @FXML private MFXButton backButton;
  @FXML private MFXButton submitButton;
  @FXML private MFXButton clearButton;
  @FXML private MFXTextField idField;
  @FXML private MFXTextField currRoom;
  @FXML private MFXTextField destRoom;
  @FXML private MFXTextField reason;
  @FXML private VBox imageVBox;
  @FXML private AnchorPane mapPane;
  // @FXML private ImageView map;
  @FXML private MFXComboBox<String> dropDown;

  private ObservableList<String> options = FXCollections.observableArrayList();
  /** Initialize controller by FXML Loader. */
  @FXML
  public void initialize() {

    options.add("Lower Level 2");
    options.add("Lower Level 1");
    options.add("Ground Floor");
    options.add("First Floor");
    options.add("Second Floor");
    options.add("Third Floor");
    dropDown.setItems(options);
    backgroundMap("edu/wpi/capybara/images/thelowerlevel1.png");
    System.out.println("I am from TransportationController.");
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
    String outputReason = reason.getText();
    // System.out.println("Current Room: " + outputCurrRoom + " Destination Room: " +
    // outputDestRoom);
    transportationSubmission newSubmission =
        new transportationSubmission(outputID, outputCurrRoom, outputDestRoom, outputReason);
    App.totalSubmissions.newTransportationSubmission(newSubmission);
    System.out.println(App.totalSubmissions.getTransportationData());
    clearFields();
  }

  public void clearFields() { // clears fields of text
    currRoom.setText("");
    destRoom.setText("");
    idField.setText("");
    reason.setText("");
  }

  public void
      validateButton() { // ensures that information has been filled in before allowing submission
    boolean valid = false;
    if (!currRoom.getText().equals("")
        && !destRoom.getText().equals("")
        && !idField.getText().equals("")
        && !reason.getText().equals("")) valid = true;
    submitButton.setDisable(!valid);
  }


  public void backgroundMap(String path) {
    BackgroundSize backgroundSize =
        new BackgroundSize(mapPane.getWidth(), mapPane.getHeight(), false, false, true, false);
    BackgroundRepeat repeatX = BackgroundRepeat.NO_REPEAT;
    BackgroundRepeat repeatY = BackgroundRepeat.NO_REPEAT;
    BackgroundPosition position = BackgroundPosition.CENTER;
    Image imageToSet = new Image(path);
    BackgroundImage bri =
        new BackgroundImage(imageToSet, repeatX, repeatY, position, backgroundSize);
    mapPane.setBackground(new Background(bri));
  }

  public void updateMap() { // updates map depending on combobox
    String selection = dropDown.getValue();
    if (selection.equals("Lower Level 2")) {
      backgroundMap("edu/wpi/capybara/images/thelowerlevel2.png");
    } else if (selection.equals("Ground Floor")) {
      backgroundMap("edu/wpi/capybara/images/thegroundfloor.png");
    } else if (selection.equals("First Floor")) {
      backgroundMap("edu/wpi/capybara/images/thefirstfloor.png");
    } else if (selection.equals("Second Floor")) {
      backgroundMap("edu/wpi/capybara/images/thesecondfloor.png");
    } else if (selection.equals("Third Floor")) {
      backgroundMap("edu/wpi/capybara/images/thethirdfloor.png");
    } else {
      backgroundMap("edu/wpi/capybara/images/thelowerlevel1.png");
    }
  }
}
