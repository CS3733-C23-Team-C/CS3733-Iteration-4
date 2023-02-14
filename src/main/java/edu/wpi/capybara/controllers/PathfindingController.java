package edu.wpi.capybara.controllers;

import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.exceptions.FloorDoesNotExistException;
import edu.wpi.capybara.objects.NodeCircle;
import edu.wpi.capybara.objects.PFNode;
import edu.wpi.capybara.objects.hibernate.*;
import edu.wpi.capybara.pathfinding.AstarPathfinder;
import edu.wpi.capybara.pathfinding.DFSPathfinder;
import edu.wpi.capybara.pathfinding.PathfindingAlgorithm;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PathfindingController {

  @FXML private MFXButton submitButton;
  @FXML private MFXComboBox<PFNode> currRoom;
  @FXML private MFXComboBox<PFNode> destRoom;
  @FXML private Pane canvasPane;
  @FXML private StackPane stackPane;
  @FXML private Canvas nodeDrawer;
  @FXML private AnchorPane nodeAnchorPane;
  @FXML private MFXComboBox<String> floorSelect;
  @FXML private MFXDatePicker dateField;
  @FXML private MFXComboBox<PathfindingAlgorithm> pathfindingAlgorithm;

  private MapViewController mvc;

  private List<PFNode> pfNodes;

  /** Initialize controller by FXML Loader. */
  @FXML
  public void initialize() {
    log.info("start");
    dateField.setValue(LocalDate.now());

    Collection<NodeEntity> nodes = DatabaseConnect.getNodes().values();

    log.info("1");
    mvc =
        new MapViewController(
            nodeDrawer, nodeAnchorPane, canvasPane, this::nodeClickedOnAction, stackPane, this);
    log.info("2");
    pfNodes = new ArrayList<>(nodes.stream().map((n) -> new PFNode(n, this)).toList());
    log.info("3");

    pfNodes.sort(Comparator.comparing(PFNode::toString));

    floorSelect.setItems(FXCollections.observableArrayList("L2", "L1", "1", "2", "3"));
    floorSelect.setText("L1");

    currRoom.setItems(FXCollections.observableArrayList(pfNodes));
    destRoom.setItems(FXCollections.observableArrayList(pfNodes));

    pathfindingAlgorithm.setItems(
        FXCollections.observableArrayList(
            new AstarPathfinder(DatabaseConnect.getNodes(), DatabaseConnect.getEdges()),
            new DFSPathfinder(DatabaseConnect.getNodes())));
    pathfindingAlgorithm.selectFirst();
    mvc.drawNodes();
    getMoveDate();
    log.info("done");
  }

  /*
  SUBMISSIONS FOR CURRENT/DESTINATION ROOM MUST BE VALID NODE
  use two of these nodes:L1X2255Y0849,L1X2665Y1043,L1X2445Y1245,L1X1980Y0844,L1X1845Y0844,L1X2310Y1043,L1X1732Y0924,
  L1X2445Y1043,L1X2445Y1284,L1X2770Y1070,L1X1750Y1284,L1X2130Y1284,L1X2130Y1045,L1X2215Y1045,L1X2220Y0904,L1X2265Y0904,
  L1X2360Y0849,L1X2130Y0904,L1X2130Y0844,L1X1845Y0924,L1X2300Y0849,L1X1750Y1090,L1X2290Y1284,L1X2320Y1284,L1X2770Y1284,
  L1X1732Y1019,L1X2065Y1284,L1X2300Y0879,L1X2770Y1160,L1X2185Y0904,L1X2490Y1043,L1X1637Y2116,L1X1702Y2260,L1X1702Y2167,
  L1X1688Y2167,L1X1666Y2167,L1X1688Y2131,L1X1665Y2116,L1X1720Y2131,L1X2715Y1070,L1X2360Y0799,L1X2220Y0974,L1X1785Y0924,
  L1X1820Y1284, L1X1965Y1284
   */

  public void submitForm() {
    NodeEntity currRoomNode = currRoom.getValue().getNode();
    NodeEntity destRoomNode = destRoom.getValue().getNode();

    List<NodeEntity> path = pathfindingAlgorithm.getValue().findPath(currRoomNode, destRoomNode);
    if (path == null) return;

    mvc.displayPath(path);

    clearFields(null);
  }

  public void clearFields(ActionEvent event) { // clears fields of text
    currRoom.clearSelection();
    destRoom.clearSelection();
    pathfindingAlgorithm.selectFirst();
    dateField.setValue(LocalDate.now());

    mvc.setEndNode(null);
    mvc.setStartNode(null);
    validateButton();

    if (event != null) {
      mvc.clearPath();
    }
  }

  public void validateButton() {
    // ensures that information has been filled in before allowing submission
    boolean valid =
        (!currRoom.getText().equals("")
            && !destRoom.getText().equals("")
            && !dateField.getText().equals("")
            && !pathfindingAlgorithm.getText().equals(""));

    submitButton.setDisable(!valid);

    PFNode currRoomNode = currRoom.getValue();
    PFNode destRoomNode = destRoom.getValue();
    if (currRoomNode != null) {
      currRoom.selectItem(currRoomNode);
      mvc.setStartNode(currRoom.getValue().getNode());
    }
    if (destRoomNode != null) {
      destRoom.selectItem(destRoomNode);
      mvc.setEndNode(destRoom.getValue().getNode());
    }

    mvc.drawNodes();
  }

  public void mapKeyPress(KeyEvent actionEvent) {
    mvc.mapKeyPress(actionEvent);
  }

  public void changeFloor() throws FloorDoesNotExistException {
    mvc.changeFloor(floorSelect.getSelectedItem());
  }

  public PFNode getPFNode(NodeEntity node) {
    for (PFNode pfNode : pfNodes) {
      if (pfNode.getNode().equals(node)) return pfNode;
    }
    return null;
  }

  private void nodeClickedOnAction(MouseEvent event, NodeCircle nodeCircle) {
    NodeEntity node = nodeCircle.getConnectedNode();
    PFNode pfNode = getPFNode(node);

    MFXGenericDialogBuilder dialogBuilder = new MFXGenericDialogBuilder();
    VBox textHolder;

    // text
    if (pfNode.hasRecentNode(getMoveDate())) {
      Text title = new Text(pfNode.getLongname(getMoveDate()));
      title.setFont(Font.font(16));
      Text shortName = new Text("Short Name: " + pfNode.getShortname(getMoveDate()));
      Text longName = new Text("Long Name: " + pfNode.getLongname(getMoveDate()));
      Text locationType = new Text("Location Type: " + pfNode.getLocationtype(getMoveDate()));
      textHolder = new VBox(title, shortName, longName, locationType);
    } else {
      textHolder =
          new VBox(
              new Text("Node " + node.getNodeid() + " has no moves before " + dateField.getText()));
    }

    // options
    MFXButton setStartNode = new MFXButton("Set as Current Location");
    setStartNode.setBackground(Background.fill(Color.color(0f / 256f, 156f / 256f, 166f / 256f)));
    MFXButton setEndNode = new MFXButton("Set as Destination Location");
    setEndNode.setBackground(Background.fill(Color.color(0f / 256f, 156f / 256f, 166f / 256f)));

    // dialogBuilder.setActionsOrientation(Orientation.VERTICAL);
    dialogBuilder.makeScrollable(true);
    dialogBuilder.setShowAlwaysOnTop(false);
    dialogBuilder.setHeaderText("Location Information");
    dialogBuilder.setShowMinimize(false);
    dialogBuilder.setContent(textHolder);
    dialogBuilder.addActions(setStartNode, setEndNode);

    MFXGenericDialog dialog = dialogBuilder.get();
    dialog.setPrefSize(200, 300);

    dialog.setOnClose((event1 -> stackPane.getChildren().removeAll(dialog)));
    dialog.setOnMouseClicked(event1 -> System.out.println("i was clicked"));
    setEndNode.setOnAction(
        (event1 -> {
          destRoom.selectItem(pfNode);
          validateButton();
          stackPane.getChildren().removeAll(dialog);
        }));
    setStartNode.setOnAction(
        (event1 -> {
          currRoom.selectItem(pfNode);
          validateButton();
          stackPane.getChildren().removeAll(dialog);
        }));

    stackPane.getChildren().add(dialog);
  }

  public Date getMoveDate() {
    return Date.valueOf(dateField.getValue());
  }

  public void changeFloorNum(String floor) {
    floorSelect.selectItem(floor);
  }
}
