package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.exceptions.FloorDoesNotExistException;
import edu.wpi.capybara.objects.NodeCircle;
import edu.wpi.capybara.objects.NodeCircleClickHandler;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import lombok.Getter;
import lombok.Setter;

public class MapViewController {

  private double mapX, mapY, mapW, mapH;
  private Collection<NodeEntity> allNodes;
  private Image currentFloorImage;
  private final Image G, L2, L1, F1, F2, F3;
  private final Canvas nodeDrawer;
  private final GraphicsContext gc;
  private AnchorPane ap;
  private Pane canvasPane;
  private List<NodeEntity> currentPath;
  private int lastX, lastY;
  private double canvasW, canvasH;
  private static final float SCROLL_SPEED = 1f;
  private static final int MOVE_SPEED = 30;
  private static final float DRAG_SPEED = 1f;
  private boolean isPath;
  private String currentFloor;
  private NodeCircleClickHandler onClick;
  @Setter @Getter private NodeEntity startNode, endNode;

  public MapViewController(
      Canvas nodeDrawer, AnchorPane ap, Pane canvasPane, NodeCircleClickHandler onClick) {
    this.nodeDrawer = nodeDrawer;
    this.ap = ap;
    this.canvasPane = canvasPane;
    this.gc = nodeDrawer.getGraphicsContext2D();
    this.allNodes = DatabaseConnect.getNodes().values();
    this.onClick = onClick;

    L1 = new Image(Objects.requireNonNull(App.class.getResourceAsStream("images/blankL1.png")));
    L2 =
        new Image(
            Objects.requireNonNull(App.class.getResourceAsStream("images/thelowerlevel2.png")));
    G =
        new Image(
            Objects.requireNonNull(App.class.getResourceAsStream("images/thegroundfloor.png")));
    F1 =
        new Image(
            Objects.requireNonNull(App.class.getResourceAsStream("images/thefirstfloor.png")));
    F2 =
        new Image(
            Objects.requireNonNull(App.class.getResourceAsStream("images/thesecondfloor.png")));
    F3 =
        new Image(
            Objects.requireNonNull(App.class.getResourceAsStream("images/thethirdfloor.png")));

    currentFloorImage = L1;
    currentPath = null;
    isPath = false;

    gc.setFill(Color.RED);
    gc.setLineWidth(2);
    gc.setStroke(Color.BLUE);

    mapX = 1441;
    mapY = 660;
    mapW = 1224;
    mapH = 1000;

    currentFloor = "L1";

    canvasW = nodeDrawer.getWidth();
    canvasH = nodeDrawer.getHeight();

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

    canvasPane.addEventHandler(MouseEvent.MOUSE_PRESSED, this::mapStartDrag);
    canvasPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::mapDrag);
    canvasPane.addEventHandler(MouseEvent.MOUSE_RELEASED, this::mapStopDrag);
    canvasPane.addEventHandler(ScrollEvent.SCROLL, this::mapScroll);
  }

  public void mapStartDrag(MouseEvent event) {
    lastX = (int) event.getX();
    lastY = (int) event.getY();

    nodeDrawer.setCursor(Cursor.CLOSED_HAND);
  }

  public void mapStopDrag(MouseEvent event) {
    double moveX = (lastX - event.getX()) * (mapW / canvasW);
    double moveY = (lastY - event.getY()) * (mapH / canvasH);

    if (moveX == 0 && moveY == 0) return;

    mapX += moveX;
    mapY += moveY;

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

    nodeDrawer.setCursor(Cursor.OPEN_HAND);
    drawNodes();
  }

  public void mapDrag(MouseEvent event) {
    double moveX = (lastX - event.getX()) * (mapW / canvasW);
    double moveY = (lastY - event.getY()) * (mapH / canvasH);

    if (moveX == 0 && moveY == 0) return;

    mapX += moveX;
    mapY += moveY;

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

  public void displayPath(List<NodeEntity> nodes) {
    currentPath = nodes;
    isPath = true;

    drawNodes();
    // todo
  }

  public void drawNodes() {
    // System.out.println("Redrawing Nodes");
    gc.clearRect(0, 0, canvasW, canvasH);
    gc.drawImage(currentFloorImage, mapX, mapY, mapW, mapH, 0, 0, canvasW, canvasH);
    ap.getChildren().removeIf(node -> node.getClass() == NodeCircle.class);

    // System.out.println("window: " + mapX + " x " + mapY + " size: " + mapW + " x " + mapH);

    if (isPath) {
      drawPath();
      drawNode(currentPath.get(0), Color.GREEN);
      drawNode(currentPath.get(currentPath.size() - 1), Color.RED);
    } else {
      if (startNode != null) {
        drawNode(startNode, Color.GREEN);
      }
      if (endNode != null) {
        drawNode(endNode, Color.RED);
      }

      gc.setFill(Color.BLUE);
      for (NodeEntity n : allNodes) {
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
    return n.getXcoord() > mapX + scale(4)
        && n.getXcoord() < mapX + mapW - scale(4)
        && n.getYcoord() > mapY + scale(4)
        && n.getYcoord() < mapY + mapH - scale(4)
        && n.getFloor().equals(currentFloor);
  }

  private void drawNode(NodeEntity node) {
    drawNode(node, Color.BLUE);
  }

  private void drawNode(NodeEntity node, Paint color) {
    if (node == null) {
      System.out.println("NULL NODE");
      return;
    }

    NodeCircle testCircle = new NodeCircle(scale(4), color, node);
    ap.getChildren().add(testCircle);
    testCircle.setCenterX(locToMapX(node.getXcoord()));
    testCircle.setCenterY(locToMapY(node.getYcoord()));
    testCircle.setOnMouseClicked(event -> onClick.handle(event, testCircle));
  }

  private void drawPath() {
    for (int i = 1; i < currentPath.size(); i++) {
      NodeEntity n1 = currentPath.get(i - 1), n2 = currentPath.get(i);
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

  public void zoom(float factor, double x, double y) {
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

  public void clearPath() {
    currentPath = null;
    isPath = false;

    drawNodes();
  }

  public void changeFloor(String floorID) throws FloorDoesNotExistException {
    if (floorID == null) throw new FloorDoesNotExistException("floorID is null");

    if (floorID.equals("L1")) {
      currentFloorImage = L1;
    } else if (floorID.equals("L2")) {
      currentFloorImage = L2;
    } else if (floorID.equals("G")) {
      currentFloorImage = G;
    } else if (floorID.equals("1")) {
      currentFloorImage = F1;
    } else if (floorID.equals("2")) {
      currentFloorImage = F2;
    } else if (floorID.equals("3")) {
      currentFloorImage = F3;
    } else {
      throw new FloorDoesNotExistException("floorID " + floorID + " does not exist");
    }

    currentFloor = floorID;
    drawNodes();
  }
}
