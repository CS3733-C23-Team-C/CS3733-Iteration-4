package edu.wpi.capybara.controllers;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.exceptions.FloorDoesNotExistException;
import edu.wpi.capybara.objects.*;
import edu.wpi.capybara.objects.NodeCircle;
import edu.wpi.capybara.objects.PFNode;
import edu.wpi.capybara.objects.hibernate.LocationnameEntity;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import edu.wpi.capybara.pathfinding.AstarPathfinder;
import edu.wpi.capybara.pathfinding.BFSPathfinder;
import edu.wpi.capybara.pathfinding.DFSPathfinder;
import edu.wpi.capybara.pathfinding.PathfindingAlgorithm;
import edu.wpi.capybara.pathfinding.costs.ElevatorCost;
import edu.wpi.capybara.pathfinding.costs.LegDayCost;
import edu.wpi.capybara.pathfinding.costs.PathfindingCost;
import edu.wpi.capybara.pathfinding.costs.StairsCost;
import edu.wpi.capybara.pathfinding.skips.AvoidStairsSkip;
import edu.wpi.capybara.pathfinding.skips.NodeSkip;
import edu.wpi.capybara.pathfinding.skips.PathfindingSkip;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PathfindingController {

  @FXML private MFXButton submitButton;
  @FXML private MFXComboBox<PFPlace> currRoom;
  @FXML private MFXComboBox<PFPlace> destRoom;
  @FXML private Pane canvasPane;
  @FXML private StackPane stackPane;
  @FXML private Canvas nodeDrawer;
  @FXML private AnchorPane nodeAnchorPane;
  @FXML private MFXComboBox<String> floorSelect;
  @FXML private MFXDatePicker dateField;
  @FXML private MFXComboBox<PathfindingAlgorithm> pathfindingAlgorithm;
  @FXML private MFXButton directionsButton;
  @FXML @Getter private MFXToggleButton serviceRequest;
  @FXML @Getter private MFXToggleButton locationNames;
  @FXML private MFXButton settingsButton;
  @FXML private Text infoText;
  @Getter @Setter private List<PFPlace> placesToAvoid;
  private MapViewController mvc;
  @Getter private List<PFPlace> pfLocations;
  @Getter @Setter private Set<PathfindingCost> costs;
  @Getter private Set<PathfindingSkip> skips;
  @Getter @Setter private MFXGenericDialog currentDialog;
  @Getter @Setter private boolean avoidStairs, preferStairs;
  @Getter private String mapText;

  /** Initialize controller by FXML Loader. */
  @FXML
  public void initialize() {
    // log.info("start");
    dateField.setValue(LocalDate.now());
    stackPane.setPickOnBounds(true);
    canvasPane.setPickOnBounds(true);
    nodeDrawer.setPickOnBounds(true);
    nodeAnchorPane.setPickOnBounds(true);

    costs = new HashSet<>();
    costs.add(new ElevatorCost());
    costs.add(new StairsCost());

    skips = new HashSet<>();

    // Collection<NodeEntity> nodes = Main.db.getNodes().values();
    Collection<LocationnameEntity> locations = Main.db.getLocationnames().values();

    // log.info("1");
    mvc =
        new MapViewController(
            nodeDrawer, nodeAnchorPane, canvasPane, this::nodeClickedOnAction, stackPane, this);
    // log.info("2");
    pfLocations = new ArrayList<>(locations.stream().map((n) -> new PFLocation(n, this)).toList());
    // log.info("3");
    dateField.setPopupOffsetX(-70);

    pfLocations.sort(Comparator.comparing(Object::toString));

    floorSelect.setItems(FXCollections.observableArrayList("L2", "L1", "1", "2", "3"));
    floorSelect.setText("L1");

    currRoom.setItems(FXCollections.observableArrayList(pfLocations));
    destRoom.setItems(FXCollections.observableArrayList(pfLocations));

    pathfindingAlgorithm.setItems(
        FXCollections.observableArrayList(
            new AstarPathfinder(Main.db.getNodes(), Main.db.getEdges(), this),
            new DFSPathfinder(Main.db.getNodes()),
            new BFSPathfinder(Main.db.getNodes(), Main.db.getEdges())));
    pathfindingAlgorithm.selectFirst();
    mvc.drawNodes();
    getMoveDate();

    placesToAvoid = new ArrayList<>();
    preferStairs = false;
    avoidStairs = false;
    mapText = "";
    // log.info("done");
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

  public void submitForm() throws FloorDoesNotExistException {
    NodeEntity currRoomNode = currRoom.getValue().getNode(getMoveDate());
    NodeEntity destRoomNode = destRoom.getValue().getNode(getMoveDate());

    costs = new HashSet<>();

    skips = new HashSet<>();
    for (PFPlace place : placesToAvoid) {
      skips.add(new NodeSkip(place.getNode(getMoveDate())));
    }

    if (avoidStairs) skips.add(new AvoidStairsSkip());

    if (preferStairs) {
      costs.add(new LegDayCost());
      System.out.println("preferring stairs");
    } else costs.add(new StairsCost());

    costs.add(new ElevatorCost());

    List<NodeEntity> path = pathfindingAlgorithm.getValue().findPath(currRoomNode, destRoomNode);
    if (path == null) {
      infoText.setText("Unable to find a path with the current settings");
      return;
    }
    // else System.out.println(path);

    mvc.displayPath(path);
    mvc.changeFloor(currRoomNode.getFloor().toString());
    changeFloorNum(currRoomNode.getFloor().toString());
    directionsButton.setVisible(true);

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
      directionsButton.setVisible(false);
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

    PFPlace currRoomNode = currRoom.getValue();
    PFPlace destRoomNode = destRoom.getValue();
    if (currRoomNode != null) {
      currRoom.selectItem(currRoomNode);
      NodeEntity node = currRoom.getValue().getNode(getMoveDate());
      if (node != null) {
        mvc.setStartNode(node);
      } else {
        infoText.setText(
            currRoom.getValue().getLongname() + " doesn't have a Node at " + getMoveDate());
        mvc.setStartNode(null);
      }
    }
    if (destRoomNode != null) {
      destRoom.selectItem(destRoomNode);
      mvc.setEndNode(destRoom.getValue().getNode(getMoveDate()));
    }

    mvc.drawNodes();
  }

  public void mapKeyPress(KeyEvent actionEvent) {
    mvc.mapKeyPress(actionEvent);
  }

  public void changeFloor() throws FloorDoesNotExistException {
    mvc.changeFloor(floorSelect.getSelectedItem());
  }

  public PFPlace getPFPlace(NodeEntity node) {
    for (PFPlace location : pfLocations) {
      if (location.getNode(getMoveDate()).equals(node)) return location;
    }
    return new PFNode(node);
  }

  public PFPlace getPFPlace(LocationnameEntity locationname) {
    for (PFPlace location : pfLocations) {
      if (location.getLongname().equals(locationname.getLongname())) return location;
    }
    return new PFLocation(locationname, this);
  }

  private void nodeClickedOnAction(MouseEvent event, NodeCircle nodeCircle) {
    NodeEntity node = nodeCircle.getConnectedNode();
    PFPlace pfPlace = getPFPlace(node);

    MFXGenericDialogBuilder dialogBuilder = new MFXGenericDialogBuilder();
    VBox textHolder;

    // text
    if (pfPlace.hasRecentNode(getMoveDate())) {
      Text title = new Text(pfPlace.getLongname());
      title.setFont(Font.font(16));
      Text shortName = new Text("Short Name: " + pfPlace.getShortname());
      Text longName = new Text("Long Name: " + pfPlace.getLongname());
      Text locationType = new Text("Location Type: " + pfPlace.getLocationtype());
      textHolder = new VBox(title, shortName, longName, locationType);
    } else {
      textHolder =
          new VBox(
              new Text("Node " + node.getNodeID() + " has no moves before " + dateField.getText()));
    }

    if (placesToAvoid.contains(pfPlace)) {
      textHolder.getChildren().add(new Text("This node will be avoided when pathfinding!"));
    }

    if (nodeCircle.getServiceRequests().size() > 0) {
      Text submissionTitle = new Text("Current Submissions");
      submissionTitle.setUnderline(true);

      textHolder.getChildren().add(submissionTitle);

      for (SubmissionAbs sub : nodeCircle.getServiceRequests()) {
        textHolder
            .getChildren()
            .add(
                new Text(
                    sub.submissionType() + " Service Request Number " + sub.getSubmissionid()));
      }
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
          destRoom.selectItem(pfPlace);
          validateButton();
          stackPane.getChildren().removeAll(dialog);
        }));
    setStartNode.setOnAction(
        (event1 -> {
          currRoom.selectItem(pfPlace);
          validateButton();
          stackPane.getChildren().removeAll(dialog);
        }));

    removeCurrentDialog();
    stackPane.getChildren().add(dialog);
  }

  public Date getMoveDate() {
    return Date.valueOf(dateField.getValue());
  }

  public void changeFloorNum(String floor) {
    floorSelect.selectItem(floor);
  }

  public void showDirections() {
    if (!mvc.isPath()) throw new RuntimeException("No path exists!");
    List<PFPlace> path = mvc.getCurrentPath().stream().map(this::getPFPlace).toList();

    MFXGenericDialogBuilder dialogBuilder = new MFXGenericDialogBuilder();

    Text firstDirection = new Text("First, start at " + path.get(0).getLongname());

    VBox textHolder = new VBox(firstDirection);

    for (int i = 1; i < path.size() - 1; i++) {
      Text nextDirection;

      if (path.get(i).getLocationtype().equals("ELEV")
          && path.get(i + 1).getLocationtype().equals("ELEV")) {
        nextDirection =
            new Text(
                "Then, take "
                    + path.get(i).getLongname()
                    + " from "
                    + path.get(i).getNode(getMoveDate()).getFloor()
                    + " to "
                    + path.get(i + 1).getNode(getMoveDate()).getFloor());
        i++;
      } else if (path.get(i).getLocationtype().equals("STAI")
          && path.get(i + 1).getLocationtype().equals("STAI")) {
        nextDirection =
            new Text(
                "Then, take "
                    + path.get(i).getLongname()
                    + " from "
                    + path.get(i).getNode(getMoveDate()).getFloor()
                    + " to "
                    + path.get(i + 1).getNode(getMoveDate()).getFloor());
        i++;
      } else {
        nextDirection = new Text("Then, walk to " + path.get(i).getLongname());
      }
      textHolder.getChildren().add(nextDirection);
    }

    Text lastDirection = new Text("Finally, walk to " + path.get(path.size() - 1).getLongname());
    Text lastText = new Text("and you will arrive at your destination!");
    textHolder.getChildren().addAll(lastDirection, lastText);

    // dialogBuilder.setActionsOrientation(Orientation.VERTICAL);
    dialogBuilder.makeScrollable(true);
    dialogBuilder.setShowAlwaysOnTop(false);
    dialogBuilder.setHeaderText("Directions");
    dialogBuilder.setShowMinimize(false);
    dialogBuilder.setContent(textHolder);

    MFXGenericDialog dialog = dialogBuilder.get();
    dialog.setPrefSize(200, 300);

    dialog.setOnClose((event1 -> stackPane.getChildren().removeAll(dialog)));

    removeCurrentDialog();
    stackPane.getChildren().add(dialog);
  }

  public void removeCurrentDialog() {
    Set<Node> toRemove = new HashSet<>();
    for (Node child : stackPane.getChildren()) {
      if (child.getClass() == MFXGenericDialog.class) toRemove.add(child);
    }
    stackPane.getChildren().removeAll(toRemove);
  }

  public void openSettings(ActionEvent event) {
    PathfindingDialogController.showDialog(this);
  }

  public void setMapText(String mapText) {
    this.mapText = mapText;
    mvc.drawNodes();
  }
}
