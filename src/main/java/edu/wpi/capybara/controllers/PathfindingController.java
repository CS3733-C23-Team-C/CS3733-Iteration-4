package edu.wpi.capybara.controllers;

import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import edu.wpi.capybara.objects.hibernate.*;
import edu.wpi.capybara.pathfinding.Pathfinder;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

public class PathfindingController {

  @FXML private MFXButton submitButton;
  @FXML private MFXTextField idField;
  @FXML private MFXComboBox<String> currRoom;
  @FXML private MFXComboBox<String> destRoom;
  @FXML private MFXTextField dateField;
  @FXML private Pane canvasPane;
  @FXML private Canvas nodeDrawer;
  @FXML private AnchorPane nodeAnchorPane;

  private MapViewController mvc;

  private List<Pair<String, NodeEntity>> shortNames;

  /** Initialize controller by FXML Loader. */
  @FXML
  public void initialize() {
    System.out.println("I am from Pathfinder Controller.");

    Collection<NodeEntity> nodes = DatabaseConnect.getNodes().values();

    mvc = new MapViewController(nodeDrawer, nodeAnchorPane, canvasPane);
    shortNames = nodes.stream().map((n) -> new Pair<>(n.getShortName(), n)).toList();

    shortNames.sort(Comparator.comparing(Pair::getKey));

    currRoom.setItems(
        FXCollections.observableArrayList(shortNames.stream().map(Pair::getKey).toList()));
    destRoom.setItems(
        FXCollections.observableArrayList(shortNames.stream().map(Pair::getKey).toList()));
    mvc.drawNodes();
  }

  public void returnHome() { // when back button is pressed
    Navigation.navigate(Screen.HOME);
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
    NodeEntity currRoomNode = searchName(currRoom.getText());
    NodeEntity destRoomNode = searchName(destRoom.getText());

    Pathfinder pathfinder = new Pathfinder(DatabaseConnect.getNodes(), DatabaseConnect.getEdges());
    List<NodeEntity> path = pathfinder.findPath(currRoomNode, destRoomNode);
    if (path == null) return;

    mvc.displayPath(path);

    clearFields(null);
  }

  public void clearFields(ActionEvent event) { // clears fields of text
    currRoom.setText("");
    destRoom.setText("");
    idField.setText("");
    dateField.setText("");

    if (event != null) {
      mvc.clearPath();
    }
  }

  public void validateButton() {
    // ensures that information has been filled in before allowing submission
    boolean valid =
        (!currRoom.getText().equals("")
                && !destRoom.getText().equals("")
                && !idField.getText().equals(""))
            && !dateField.getText().equals("");

    submitButton.setDisable(!valid);

    mvc.setStartNode(searchName(currRoom.getText()));
    mvc.setEndNode(searchName(destRoom.getText()));

    mvc.drawNodes();
  }

  private NodeEntity searchName(String name) {
    for (Pair<String, NodeEntity> pair : shortNames) {
      if (name.equals(pair.getKey())) return pair.getValue();
    }
    return null;
  }

  public void mapKeyPress(KeyEvent actionEvent) {
    mvc.mapKeyPress(actionEvent);
  }
}
