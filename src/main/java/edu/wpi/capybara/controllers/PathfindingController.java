package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import edu.wpi.capybara.objects.Node;
import edu.wpi.capybara.pathfinding.Pathfinder;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.paint.Color;

public class PathfindingController {

  @FXML private MFXButton submitButton;
  @FXML private MFXTextField idField;
  @FXML private MFXTextField currRoom;
  @FXML private MFXTextField destRoom;
  @FXML private MFXTextField dateField;

  @FXML private Canvas nodeDrawer;

  private int mapX, mapY, mapW, mapH;

  private List<Node> nodesToDisplay;

  boolean isPath;

  private GraphicsContext gc;

  private Image image;

  private int canvasW, canvasH;
  private int lastX, lastY;

  // Variables to change
  private static final float SCROLL_SPEED = 1f;
  private static final int MOVE_SPEED = 30;
  private static final float DRAG_SPEED = 2f;

  /** Initialize controller by FXML Loader. */
  @FXML
  public void initialize() {
    System.out.println("I am from Pathfinder Controller.");

    image =
        new Image(
            Objects.requireNonNull(App.class.getResourceAsStream("images/thelowerlevel1.png")));
    gc = nodeDrawer.getGraphicsContext2D();
    gc.setFill(Color.RED);
    gc.setLineWidth(2);
    gc.setStroke(Color.BLUE);

    nodesToDisplay = DatabaseConnect.getNodes().values().stream().toList();

    mapX = 1441;
    mapY = 660;
    mapW = 1224;
    mapH = 1000;
    canvasW = (int) nodeDrawer.getWidth();
    canvasH = (int) nodeDrawer.getHeight();

    isPath = false;

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
    String outputCurrRoom = currRoom.getText();
    String outputDestRoom = destRoom.getText();

    Pathfinder pathfinder = new Pathfinder(DatabaseConnect.getNodes(), DatabaseConnect.getEdges());
    nodesToDisplay = pathfinder.findPath(outputCurrRoom, outputDestRoom);
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

    mapX += (lastX - event.getX()) * inverseScale(DRAG_SPEED);
    mapY += (lastY - event.getY()) * inverseScale(DRAG_SPEED) * 1.5f;

    if (mapX + mapW > 3999) {
      mapX = 3999 - mapW;
    }
    if (mapX < 0) {
      mapX = 0;
    }
    if (mapY + mapH > 4999) {
      mapY = 4999 - mapH;
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
        mapY = 3399 - mapH;
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
        mapX = 4999 - mapW;
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
      gc.setFill(Color.BLUE);
      for (Node n : nodesToDisplay) {
        if (nodeInMapView(n)) {
          if (n.getNodeID().equals(currRoom.getText())) {
            gc.setFill(Color.GREEN);
          }
          if (n.getNodeID().equals(destRoom.getText())) {
            gc.setFill(Color.RED);
          }

          drawNode(n);
          gc.setFill(Color.BLUE);
          // System.out.println(n);
        }
      }
    }
  }

  private boolean nodeInMapView(Node n) {
    return n.getXCoord() > mapX
        && n.getXCoord() < mapX + mapW
        && n.getYCoord() > mapY
        && n.getYCoord() < mapY + mapH;
  }

  private void drawNode(Node node) {
    if (node == null) {
      System.out.println("NULL NODE");
      return;
    }

    gc.fillOval(
        locToMapX(node.getXCoord()) - scale(4),
        locToMapY(node.getYCoord()) - scale(4),
        scale(8),
        scale(8));
  }

  private void drawPath() {
    for (int i = 1; i < nodesToDisplay.size(); i++) {
      Node n1 = nodesToDisplay.get(i - 1), n2 = nodesToDisplay.get(i);
      if ((!nodeInMapView(n1)) && (!nodeInMapView(n2))) continue;

      gc.strokeLine(
          locToMapX(n1.getXCoord()),
          locToMapY(n1.getYCoord()),
          locToMapX(n2.getXCoord()),
          locToMapY(n2.getYCoord()));
    }
  }

  private int locToMapX(int xCoord) {
    return (xCoord - mapX) * (canvasW) / (mapW);
  }

  private int locToMapY(int yCoord) {
    return (yCoord - mapY) * (canvasH) / (mapH);
  }

  // if you want the number to get bigger as the map zooms in
  private float scale(float baseValue) {
    float multiplier = (1000f) / ((float) mapH);

    return Math.round((baseValue) * multiplier);
  }

  // if you want the number to get smaller as the map zooms in
  private float inverseScale(float baseValue) {
    float multiplier = ((float) mapH) / (1000f);

    return (baseValue) * multiplier;
  }

  private void zoom(float factor, int x, int y) {
    float multiplier = factor - 1;

    int moveX = Math.round(((float) x / canvasW) * mapW * multiplier);
    int moveY = Math.round(((float) y / canvasH) * mapH * multiplier);

    mapH /= factor;
    mapW /= factor;

    mapX += moveX;
    mapY += moveY;
  }
}
