package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.Main;
import edu.wpi.capybara.objects.PFPlace;
import edu.wpi.capybara.objects.SubmissionAbs;
import edu.wpi.capybara.objects.hibernate.LocationnameEntity;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import edu.wpi.capybara.pathfinding.AstarPathfinder;
import io.github.palexdev.materialfx.controls.*;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PathfindingSearchDialogController {

  @FXML private HBox question3;
  @FXML private HBox question4;
  @FXML private MFXButton findNodeButton;
  @FXML private Text outputText;
  @FXML private MFXButton resetButton;
  @FXML private MFXButton cancelButton;
  @FXML private MFXButton setStartNodeButton;
  @FXML private MFXButton setEndNodeButton;
  @FXML private MFXTextField requestField;
  @FXML private MFXComboBox<String> typeField;
  @FXML private MFXFilterComboBox<PFPlace> startNode;
  @FXML private MFXComboBox<String> targetField;
  @FXML private Text typeText;
  private ObservableList<String> nodeTypes;
  private ObservableList<String> requestTypes;
  private PFPlace foundNode;
  private static PathfindingController controller;
  public static Stage dialog;

  public static void showDialog(PathfindingController pcontroller) {
    controller = pcontroller;

    dialog = new Stage();
    dialog.initModality(Modality.WINDOW_MODAL);
    dialog.initOwner(App.getPrimaryStage().getOwner());

    final FXMLLoader loader =
        new FXMLLoader(App.class.getResource("views/PathfindingSearchDialog.fxml"));
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
    nodeTypes = FXCollections.observableArrayList();
    for (LocationnameEntity lne : Main.db.getLocationnames().values()) {
      if (!nodeTypes.contains(lne.getLocationtype())) {
        nodeTypes.add(lne.getLocationtype());
      }
    }

    requestTypes =
        FXCollections.observableArrayList(
            "Audio", "Cleaning", "Computer", "Security", "Transportation");

    targetField.setItems(FXCollections.observableArrayList("Node Type", "Service Request"));
    startNode.setItems(FXCollections.observableArrayList(controller.getPfLocations()));
    typeField.setItems(FXCollections.emptyObservableList());

    requestField.textProperty().addListener((a, b, c) -> validateFindNode());
    reset();
  }

  public void reset() {
    question3.setVisible(false);
    question4.setVisible(false);
    findNodeButton.setVisible(false);
    findNodeButton.setDisable(true);
    outputText.setVisible(false);
    setStartNodeButton.setVisible(false);
    setEndNodeButton.setVisible(false);

    requestField.setText("");
    typeField.clearSelection();
    typeField.setItems(FXCollections.emptyObservableList());
    startNode.clearSelection();
    targetField.clearSelection();
  }

  public void validateFindNode() {
    if (!notEmpty(targetField.getValue())) {
      findNodeButton.setDisable(true);
      return;
    }

    if (targetField.getValue().equals("Service Request")) {
      findNodeButton.setDisable(
          !(notEmpty(typeField.getValue()) && notEmpty(requestField.getText()))
              || !requestField.getText().matches("\\d*"));
    } else {
      findNodeButton.setDisable(!notEmpty(typeField.getValue()) || startNode.getValue() == null);
    }
  }

  private boolean notEmpty(String str) {
    return str != null && !str.equals("");
  }

  public void onTargetSelection() {
    if (targetField.getValue() == null) {
      reset();
    } else if (targetField.getValue().equals("Node Type")) {
      typeText.setText("Which type of Node?");
      typeField.clearSelection();
      typeField.setPromptText("Node Type");
      typeField.setItems(nodeTypes);
      question3.setVisible(true);
      findNodeButton.setVisible(true);
    } else if (targetField.getValue().equals("Service Request")) {
      typeText.setText("Which type of Request?");
      typeField.clearSelection();
      requestField.setText("");
      typeField.setPromptText("Request Type");
      typeField.setItems(requestTypes);
      question3.setVisible(true);
      question4.setVisible(true);
      findNodeButton.setVisible(true);
    } else {
      reset();
    }
    validateFindNode();
  }

  public void findNode() {
    outputText.setVisible(true);
    if (targetField.getValue().equals("Service Request")) {
      SubmissionAbs submission = null;
      System.out.println(requestField.getText());
      int id = Integer.parseInt(requestField.getText());

      if (typeField.getValue().equals("Audio")) {
        submission = Main.db.getAudio(id);
      } else if (typeField.getValue().equals("Cleaning")) {
        submission = Main.db.getCleaning(id);
      } else if (typeField.getValue().equals("Computer")) {
        submission = Main.db.getComputer(id);
      } else if (typeField.getValue().equals("Security")) {
        submission = Main.db.getSecurity(id);
      } else if (typeField.getValue().equals("Transportation")) {
        submission = Main.db.getTransportation(id);
      }

      if (submission == null) {
        outputText.setText("Unable to find Submission");
      } else {
        LocationnameEntity lne = submission.getLocationEntity();
        if (lne == null) {
          outputText.setText("Unable to find location on Submission");
        } else {
          PFPlace place = controller.getPFPlace(lne);
          foundNode = place;
          outputText.setText("Found: " + place.getLongname());
          setEndNodeButton.setVisible(true);
          setStartNodeButton.setVisible(true);
        }
      }
    } else if (targetField.getValue().equals("Node Type")) {
      NodeEntity start = startNode.getValue().getNode(controller.getMoveDate());
      AstarPathfinder pf = new AstarPathfinder(Main.db.getNodes(), Main.db.getEdges(), controller);
      List<NodeEntity> path = pf.findPath(start, (node) -> isOfType(node, typeField.getValue()));
      if (path == null) {
        outputText.setText("Unable to find that location type");
      } else {
        NodeEntity found = path.get(path.size() - 1);
        PFPlace place = controller.getPFPlace(found);
        foundNode = place;
        outputText.setText("Found " + place.getLongname());
        setEndNodeButton.setVisible(true);
        setStartNodeButton.setVisible(true);
      }
    }
  }

  public void closeDialog() {
    Stage oldDialog = dialog;
    dialog = null;
    oldDialog.close();
  }

  public void onSetStartNode() {
    controller.setCurrRoom(foundNode);
    controller.validateButton();
    closeDialog();
  }

  public void onSetEndNode() {
    controller.setDestRoom(foundNode);
    controller.validateButton();
    closeDialog();
  }

  public void onGetStartNode() {
    PFPlace place = controller.getCurrRoom();
    if (place != null) startNode.selectItem(place);
  }

  private boolean isOfType(NodeEntity ne, String type) {
    return controller.getPFPlace(ne).getLocationtype().equals(type);
  }
}
