package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import edu.wpi.capybara.objects.NodeAlphabetComparator;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import edu.wpi.capybara.objects.hibernate.TransportationsubmissionEntity;
import edu.wpi.capybara.objects.submissions.submissionStatus;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javax.swing.*;

public class TransportationController {

  public TransportationController() {}

  @FXML public Stage primaryStage;
  @FXML private MFXButton backButton;
  @FXML private MFXButton submitButton;
  @FXML private MFXButton clearButton;
  @FXML private MFXTextField idField;
  @FXML MFXTextField currRoom;
  @FXML private MFXTextField destRoom;
  @FXML private MFXTextField reasonField;
  @FXML private MFXComboBox<String> Location;
  @FXML private VBox imageVBox;
  @FXML private AnchorPane mapPane;
  // @FXML private ImageView map;
  @FXML private MFXComboBox<String> dropDown;

  private ObservableList<String> options = FXCollections.observableArrayList();
  /** Initialize controller by FXML Loader. */
  @FXML
  public void initialize() {
    options.addAll(
        "Lower Level 2",
        "Lower Level 1",
        "Ground Floor",
        "First Floor",
        "Second Floor",
        "Third Floor");
    dropDown.setItems(options);
    backgroundMap("edu/wpi/capybara/images/thelowerlevel1.png");
    System.out.println("I am from TransportationController.");

    // Add different locations

    //    Location.getItems().add("Location");
    //    Location.getItems().add("Location1");

    TreeMap<Integer, NodeEntity> nodes = DatabaseConnect.getNodes();

    SortedSet<NodeEntity> sortedset = new TreeSet<NodeEntity>(new NodeAlphabetComparator());

    sortedset.addAll(nodes.values());

    Iterator<NodeEntity> iterator = sortedset.iterator();
    while (iterator.hasNext()) {
      NodeEntity n = iterator.next();
      // System.out.println(n.getShortName());
      Location.getItems().add(n.getShortName());
    }

    //    ObservableList<String> locationList =
    //        FXCollections.observableArrayList("Location", "Another location");
    //    Location.setItems(locationList);

    // Set a default variable
    Location.getSelectionModel().selectFirst();
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
    String outputCurrRoom =
        currRoom.getText(); // then creates an object to store them, clears fields
    String outputDestRoom = destRoom.getText();
    String outputReason = reasonField.getText();
    // System.out.println("Current Room: " + outputCurrRoom + " Destination Room: " +
    // outputDestRoom);
    TransportationsubmissionEntity newSubmission =
        new TransportationsubmissionEntity(
            App.getUser().getStaffid(),
            outputCurrRoom,
            outputDestRoom,
            outputDestRoom,
            submissionStatus.BLANK);
    //    newSubmission.setCurrroomnum(outputCurrRoom);
    //    newSubmission.setDestroomnum(outputDestRoom);
    //    newSubmission.setReason(outputReason);
    App.getTotalSubmissions().newTransportationSubmission(newSubmission);
    System.out.println(App.getTotalSubmissions().getTransportationData());
    clearFields();
  }

  public void clearFields() { // clears fields of text
    dropDown.getSelectionModel().selectFirst();
    Location.getSelectionModel().selectFirst();
    currRoom.setText("");
    destRoom.setText("");
    reasonField.setText("");
    submitButton.setDisable(true);
  }

  public void
      validateButton() { // ensures that information has been filled in before allowing submission
    boolean valid = false;
    if (!currRoom.getText().equals("")
        && !destRoom.getText().equals("")
        && !reasonField.getText().equals("")) valid = true;
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
