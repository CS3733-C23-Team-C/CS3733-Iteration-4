package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
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
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Pair;

public class PathfindingController {

  @FXML private MFXButton submitButton;
  @FXML private MFXTextField idField;
  @FXML private MFXComboBox<String> currRoom;
  @FXML private MFXComboBox<String> destRoom;
  @FXML private MFXTextField dateField;
  @FXML private Pane canvasPane;
  @FXML private Canvas nodeDrawer;

  private double mapX, mapY, mapW, mapH;

  private List<NodeEntity> nodesToDisplay;

  boolean isPath;

  private GraphicsContext gc;

  private Image image;

  private double canvasW, canvasH;
  private int lastX, lastY;

  private List<Pair<String, NodeEntity>> shortNames;

  // Variables to change
  private static final float SCROLL_SPEED = 1f;
  private static final int MOVE_SPEED = 30;
  private static final float DRAG_SPEED = 1f;

  /** Initialize controller by FXML Loader. */
  @FXML
  public void initialize() {
    System.out.println("I am from Pathfinder Controller.");

    image = new Image(Objects.requireNonNull(App.class.getResourceAsStream("images/blankL1.png")));
    gc = nodeDrawer.getGraphicsContext2D();
    gc.setFill(Color.RED);
    gc.setLineWidth(2);
    gc.setStroke(Color.BLUE);

    nodesToDisplay = new ArrayList<>();
    shortNames = new ArrayList<>();

    Collection<NodeEntity> nodes = DatabaseConnect.getNodes().values();
    for (NodeEntity n : nodes) {
      if (n.getFloor().equals("L1")) {
        nodesToDisplay.add(n);
        shortNames.add(new Pair<>(n.getShortName(), n));
      }
    }
    shortNames.sort(Comparator.comparing(Pair::getKey));

    mapX = 1441;
    mapY = 660;
    mapW = 1224;
    mapH = 1000;

    canvasW = (int) nodeDrawer.getWidth();
    canvasH = (int) nodeDrawer.getHeight();

    isPath = false;

    canvasPane
        .widthProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              nodeDrawer.setWidth(canvasPane.getWidth());
              canvasW = nodeDrawer.getWidth();

              mapW = mapH * (canvasW / canvasH);

              zoom(1f, mapX + (mapW / 2), mapY + (mapH / 2));

              drawNodes();
            });
    canvasPane
        .heightProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              nodeDrawer.setHeight(canvasPane.getHeight());
              canvasH = nodeDrawer.getHeight();

              mapH = mapW * (canvasH / canvasW);

              zoom(1f, mapX + (mapW / 2), mapY + (mapH / 2));

              drawNodes();
            });

    currRoom.setItems(
        FXCollections.observableArrayList(shortNames.stream().map(Pair::getKey).toList()));
    destRoom.setItems(
        FXCollections.observableArrayList(shortNames.stream().map(Pair::getKey).toList()));
    drawNodes();
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
    nodesToDisplay = path;

    isPath = true;
    drawNodes();

    clearFields(null);
  }

  public void mapStartDrag(MouseEvent event) {
    lastX = (int) event.getX();
    lastY = (int) event.getY();

    nodeDrawer.setCursor(Cursor.CLOSED_HAND);
  }

  public void mapStopDrag() {

    nodeDrawer.setCursor(Cursor.OPEN_HAND);
    drawNodes();
  }

  public void mapDrag(MouseEvent event) {

    mapX += (lastX - event.getX()) * (mapW / canvasW);
    mapY += (lastY - event.getY()) * (mapH / canvasH);

    if (mapX + mapW > 5000) {
      mapX = 5000 - mapW;
    }
    if (mapX < 0) {
      mapX = 0;
    }
    if (mapY + mapH > 3400) {
      mapY = 3400 - mapH;
    }
    if (mapY < 0) {
      mapY = 0;
    }

    lastX = (int) (event.getX());
    lastY = (int) (event.getY());

    drawNodes();
  }

  public void mapKeyPress(KeyEvent actionEvent) {
    if (!actionEvent.isShiftDown()
        || actionEvent.getText() == null
        || actionEvent.getText().length() == 0) return;

    char c = actionEvent.getText().charAt(0);

    if (c == 'w') {
      if (mapY - inverseScale(MOVE_SPEED) < 0) {
        mapY = 0;
      } else {
        mapY -= inverseScale(MOVE_SPEED);
      }
    } else if (c == 's') {
      if (mapY + mapH + inverseScale(MOVE_SPEED) > 3399) {
        mapY = 3400 - mapH;
      } else {
        mapY += inverseScale(MOVE_SPEED);
      }
    } else if (c == 'a') {
      if (mapX - inverseScale(MOVE_SPEED) < 0) {
        mapX = 0;
      } else {
        mapX -= inverseScale(MOVE_SPEED);
      }
    } else if (c == 'd') {
      if (mapX + mapW + inverseScale(MOVE_SPEED) > 4999) {
        mapX = 5000 - mapW;
      } else {
        mapX += inverseScale(MOVE_SPEED);
      }
    } else if (c == 'r') {
      zoom(1.1f, canvasW / 2, canvasH / 2);
    } else if (c == 'f') {
      zoom(0.9f, canvasW / 2, canvasH / 2);
    } else {
      return;
    }

    drawNodes();
  }

  public void mapScroll(ScrollEvent event) {
    float amountZoom = 1f + (SCROLL_SPEED * ((float) event.getDeltaY() / 400));
    zoom(amountZoom, (int) event.getX(), (int) event.getY());
    drawNodes();
  }

  public void clearFields(ActionEvent event) { // clears fields of text
    currRoom.setText("");
    destRoom.setText("");
    idField.setText("");
    dateField.setText("");

    if (event != null) {
      nodesToDisplay = DatabaseConnect.getNodes().values().stream().toList();
      isPath = false;
      drawNodes();
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
    drawNodes();
  }

  private void drawNodes() {
    gc.clearRect(0, 0, canvasW, canvasH);
    gc.drawImage(image, mapX, mapY, mapW, mapH, 0, 0, canvasW, canvasH);
    // System.out.println("window: " + mapX + " x " + mapY + " size: " + mapW + " x " + mapH);

    if (isPath) {
      drawPath();
      gc.setFill(Color.GREEN);
      drawNode(nodesToDisplay.get(0));
      gc.setFill(Color.RED);
      drawNode(nodesToDisplay.get(nodesToDisplay.size() - 1));
    } else {
      NodeEntity startNode = searchName(currRoom.getText());
      NodeEntity endNode = searchName(destRoom.getText());

      if (startNode != null) {
        gc.setFill(Color.GREEN);
        drawNode(startNode);
      }
      if (endNode != null) {
        gc.setFill(Color.RED);
        drawNode(endNode);
      }

      gc.setFill(Color.BLUE);
      for (NodeEntity n : nodesToDisplay) {
        if (nodeInMapView(n)) {
          if (n == startNode || n == endNode) continue;
          drawNode(n);
          gc.setFill(Color.BLUE);
          // System.out.println(n);
        }
      }
    }
  }

  private boolean nodeInMapView(NodeEntity n) {
    return n.getXcoord() > mapX
        && n.getXcoord() < mapX + mapW
        && n.getYcoord() > mapY
        && n.getYcoord() < mapY + mapH;
  }

  private void drawNode(NodeEntity node) {
    if (node == null) {
      System.out.println("NULL NODE");
      return;
    }

    gc.fillOval(
        locToMapX(node.getXcoord()) - scale(4),
        locToMapY(node.getYcoord()) - scale(4),
        scale(8),
        scale(8));
  }

  private void drawPath() {
    for (int i = 1; i < nodesToDisplay.size(); i++) {
      NodeEntity n1 = nodesToDisplay.get(i - 1), n2 = nodesToDisplay.get(i);
      if ((!nodeInMapView(n1)) && (!nodeInMapView(n2))) continue;

      gc.strokeLine(
          locToMapX(n1.getXcoord()),
          locToMapY(n1.getYcoord()),
          locToMapX(n2.getXcoord()),
          locToMapY(n2.getYcoord()));
    }
  }

  private double locToMapX(int xCoord) {
    return (xCoord - mapX) * (canvasW) / (mapW);
  }

  private double locToMapY(int yCoord) {
    return (yCoord - mapY) * (canvasH) / (mapH);
  }

  // if you want the number to get bigger as the map zooms in
  private float scale(float baseValue) {
    float mapMultiplier = (1000f) / ((float) mapH);
    float canvasMultiplier = ((float) canvasH) / (300f);

    return Math.round((baseValue) * mapMultiplier * canvasMultiplier);
  }

  // if you want the number to get smaller as the map zooms in
  private float inverseScale(float baseValue) {
    float multiplier = ((float) mapH) / (1000f);

    return (baseValue) * multiplier;
  }

  private void zoom(float factor, double x, double y) {
    float multiplier = factor - 1;

    long moveX = Math.round((x / canvasW) * mapW * multiplier);
    long moveY = Math.round((y / canvasH) * mapH * multiplier);

    mapH /= factor;
    mapW /= factor;

    mapX += moveX;
    mapY += moveY;

    if (mapW > 5000) {
      float diff = 5000 / (float) mapW;

      mapW = 5000;
      mapH *= diff;
    }

    if (mapH > 3400) {
      float diff = 3400 / (float) mapH;

      mapH = 3400;
      mapW *= diff;
    }

    if (mapX < 0) {
      mapX = 0;
    }
    if (mapX + mapW > 5000) {
      mapX = 5000 - mapW;
    }

    if (mapY < 0) {
      mapY = 0;
    }
    if (mapY + mapH > 3400) {
      mapY = 3400 - mapH;
    }
  }

  private NodeEntity searchName(String name) {
    for (Pair<String, NodeEntity> pair : shortNames) {
      if (name.equals(pair.getKey())) return pair.getValue();
    }
    return null;
  }
}
