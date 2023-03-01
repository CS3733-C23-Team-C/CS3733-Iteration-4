package edu.wpi.cs3733.C23.teamC.Pathfinding;

import edu.wpi.cs3733.C23.teamC.App;
import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.Pathfinding.Algorithms.AstarPathfinder;
import edu.wpi.cs3733.C23.teamC.database.hibernate.LocationnameEntity;
import edu.wpi.cs3733.C23.teamC.database.hibernate.MoveEntity;
import edu.wpi.cs3733.C23.teamC.database.hibernate.NodeEntity;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class KioskDialogController {

  public static Stage dialog;

  @FXML private MFXComboBox<String> targetField;
  @FXML private Text question2Text, question3Text, question4Text;
  @FXML private HBox question2, question3, question4;
  @FXML private MFXButton createKioskButton;
  private MFXFilterComboBox<LocationnameEntity> locationBox;
  private MFXFilterComboBox<PFPlace> startBox, endBox;
  private MFXDatePicker datePicker;
  private ObservableList<LocationnameEntity> locations;
  private ObservableList<PFPlace> places;

  public static void showDialog() {
    dialog = new Stage();
    dialog.initModality(Modality.WINDOW_MODAL);
    dialog.initOwner(App.getPrimaryStage().getOwner());

    final FXMLLoader loader = new FXMLLoader(App.class.getResource("views/KioskDialog.fxml"));
    BorderPane root;
    try {
      root = loader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    dialog.setScene(new Scene(root));

    dialog.setOnCloseRequest(
        (event -> {
          Stage oldDialog = dialog;
          dialog = null;
          oldDialog.close();
        }));
    dialog.showAndWait();
  }

  public void initialize() {
    targetField.setItems(FXCollections.observableArrayList("Move", "Custom"));

    locations = FXCollections.observableArrayList(Main.getRepo().getLocationNames().values());
    locations.sort(Comparator.comparing(LocationnameEntity::toString));

    List<? extends PFPlace> placesList =
        Main.db.getLocationnames().values().stream().map(PFLocation::new).sorted().toList();
    places = FXCollections.observableArrayList(placesList);

    reset(new ActionEvent());
  }

  public void onTargetSelection() {
    if (targetField.getValue() == null) return;
    reset(null);
    if (targetField.getValue().equals("Move")) {
      question2Text.setText("Which location?");
      question2Text.setVisible(true);

      locationBox = new MFXFilterComboBox<>(locations);
      locationBox.setPrefSize(137, 50);
      locationBox.setOnAction(this::validate);

      question3Text.setText("What date is the move?");
      question3Text.setVisible(true);

      datePicker = new MFXDatePicker(Locale.US, YearMonth.now());
      datePicker.setPrefSize(137, 50);
      datePicker.setOnAction(this::validate);

      question2.getChildren().add(locationBox);
      question3.getChildren().add(datePicker);
    } else if (targetField.getValue().equals("Custom")) {
      question2Text.setText("Start Location");
      question2Text.setVisible(true);

      startBox = new MFXFilterComboBox<>(places);
      startBox.setPrefSize(137, 50);
      startBox.setOnAction(this::validate);

      question3Text.setText("End Location");
      question3Text.setVisible(true);

      endBox = new MFXFilterComboBox<>(places);
      endBox.setPrefSize(137, 50);
      endBox.setOnAction(this::validate);

      question2.getChildren().add(startBox);
      question3.getChildren().add(endBox);
    }
  }

  public void reset(ActionEvent event) {
    question2Text.setVisible(false);
    question3Text.setVisible(false);
    question4Text.setVisible(false);

    if (locationBox != null) question2.getChildren().remove(locationBox);
    if (datePicker != null) question3.getChildren().remove(datePicker);

    if (startBox != null) question2.getChildren().remove(startBox);
    if (endBox != null) question3.getChildren().remove(endBox);

    locationBox = null;
    datePicker = null;
    startBox = null;
    endBox = null;

    if (event != null) targetField.clearSelection();

    createKioskButton.setDisable(true);
  }

  public void validate(ActionEvent event) {
    if (targetField.getValue() == null) {
      createKioskButton.setDisable(true);
      return;
    }
    if (targetField.getValue().equals("Move")) {
      LocationnameEntity location = locationBox.getValue();
      LocalDate locationDate = datePicker.getValue();

      if (location == null || locationDate == null) {
        createKioskButton.setDisable(true);
        return;
      }

      Date date = Date.valueOf(locationDate);

      if (date.before(Date.valueOf(LocalDate.of(2023, 1, 2)))) {
        question4Text.setText("Cannot use moves on or before 1/1/2023");
        question4Text.setVisible(true);
        createKioskButton.setDisable(true);
        return;
      }

      MoveEntity found = null;
      for (MoveEntity move : Main.getRepo().getMoves()) {
        if (move.getLocationName().equals(location) && move.getMovedate().equals(date)) {
          found = move;
          break;
        }
      }

      if (found == null) {
        question4Text.setText(
            "Unable to find move at "
                + location.getLongname()
                + " on "
                + locationDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
        question4Text.setVisible(true);
        createKioskButton.setDisable(true);
      } else {
        question4Text.setText("Found Move!");
        question4Text.setVisible(true);
        createKioskButton.setDisable(false);
      }
    } else if (targetField.getValue().equals("Custom")) {
      createKioskButton.setDisable(!(startBox.getValue() != null && endBox.getValue() != null));
    }
  }

  public void closeDialog() {
    Stage oldDialog = dialog;
    dialog = null;
    oldDialog.close();
  }

  public void createKiosk() {
    NodeEntity location1, location2;

    if (targetField.getValue() == null) {
      return;
    } else if (targetField.getValue().equals("Move")) {
      PFLocation location = new PFLocation(locationBox.getValue());
      location2 = location.getNode(Date.valueOf(datePicker.getValue()));
      location1 = location.getNode(Date.valueOf(datePicker.getValue().minus(1, ChronoUnit.DAYS)));
    } else if (targetField.getValue().equals("Custom")) {
      location1 = startBox.getValue().getNode(Date.valueOf(LocalDate.now()));
      location2 = endBox.getValue().getNode(Date.valueOf(LocalDate.now()));
    } else {
      return;
    }

    AstarPathfinder pathfinder = new AstarPathfinder(Main.db.getNodes(), Main.db.getEdges());
    List<NodeEntity> path = pathfinder.findPath(location1, location2);

    String sb = "Move of " + location1.getLongName();

    KioskScreen.showKiosk(sb, location1, path);

    closeDialog();
  }
}
