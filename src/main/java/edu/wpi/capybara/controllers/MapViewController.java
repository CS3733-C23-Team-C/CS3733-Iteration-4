package edu.wpi.capybara.controllers;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.exceptions.FloorDoesNotExistException;
import edu.wpi.capybara.objects.ImageLoader;
import edu.wpi.capybara.objects.NodeCircle;
import edu.wpi.capybara.objects.NodeCircleClickHandler;
import edu.wpi.capybara.objects.SubmissionAbs;
import edu.wpi.capybara.objects.hibernate.EdgeEntity;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MapViewController {

  private double mapX, mapY, mapW, mapH;
  private Collection<NodeEntity> allNodes;
  private Image currentFloorImage;
  private final Canvas nodeDrawer;
  private final GraphicsContext gc;
  private final StackPane stackPane;
  private AnchorPane ap;
  private Pane canvasPane;
  @Getter private List<NodeEntity> currentPath;
  private int lastX, lastY;
  private double canvasW, canvasH;
  private static final float SCROLL_SPEED = 1f;
  private static final int MOVE_SPEED = 30;
  private static final float DRAG_SPEED = 1f;
  @Getter private boolean isPath;
  private String currentFloor;
  private final PathfindingController controller;
  private final NodeCircleClickHandler onClick;
  @Setter @Getter private NodeEntity startNode, endNode, selectedNode;

  public MapViewController(
      Canvas nodeDrawer,
      AnchorPane ap,
      Pane canvasPane,
      NodeCircleClickHandler onClick,
      StackPane stackPane,
      PathfindingController controller) {
    this.nodeDrawer = nodeDrawer;
    this.ap = ap;
    this.canvasPane = canvasPane;
    this.stackPane = stackPane;
    this.gc = nodeDrawer.getGraphicsContext2D();
    this.allNodes = Main.db.getNodes().values();
    this.onClick = onClick;
    this.controller = controller;

    // log.info("start image 1");

    // log.info("get image 1");
    try {
      currentFloorImage = ImageLoader.getL1().get();
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException("Floor image did not load correctly!");
    }

    // log.info("start other threads");

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

    // ap.setCursor(Cursor.CLOSED_HAND);
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

    ap.setCursor(Cursor.OPEN_HAND);
    drawNodes();
  }

  public void mapDrag(MouseEvent event) {
    double moveX = (lastX - event.getX()) * (mapW / canvasW);
    double moveY = (lastY - event.getY()) * (mapH / canvasH);

    if (Math.abs(moveX) < 1 && Math.abs(moveY) < 1) return;

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
  }

  public void drawNodes() {
    // System.out.println("Redrawing Nodes");
    gc.clearRect(0, 0, canvasW, canvasH);
    gc.drawImage(currentFloorImage, mapX, mapY, mapW, mapH, 0, 0, canvasW, canvasH);
    ap.getChildren().removeIf(node -> node.getClass() == NodeCircle.class);
    ap.getChildren().removeIf(node -> node.getClass() == Text.class);

    // System.out.println("window: " + mapX + " x " + mapY + " size: " + mapW + " x " + mapH);

    if (isPath) {
      drawPath();
      if (nodeInMapView(currentPath.get(0))) drawNode(currentPath.get(0), Color.GREEN);
      if (nodeInMapView(currentPath.get(currentPath.size() - 1)))
        drawNode(currentPath.get(currentPath.size() - 1), Color.RED);

    } else {
      if (startNode != null && nodeInMapView(startNode)) {
        drawNode(startNode, Color.GREEN);
      }
      if (endNode != null && nodeInMapView(endNode)) {
        drawNode(endNode, Color.RED);
      }
      if (selectedNode != null && nodeInMapView(selectedNode)) {
        drawNode(selectedNode, Color.YELLOW);
      }

      gc.setFill(Color.BLUE);
      for (NodeEntity n : allNodes) {
        if (nodeInMapView(n)) {
          if (n == startNode || n == endNode || n == selectedNode) continue;
          Set<SubmissionAbs> requests = getServiceRequests(n);
          if (requests.size() > 0 && controller.getServiceRequest().isSelected()) {
            drawNode(n, requests);
          } else {
            drawNode(n);
          }
          gc.setFill(Color.BLUE);
          // System.out.println(n);
        }
      }

      // drawEdges();
    }
    drawPathText();
  }

  private boolean nodeInMapView(NodeEntity n) {
    return n.getXcoord() > mapX + scale(4)
        && n.getXcoord() < mapX + mapW - scale(4)
        && n.getYcoord() > mapY + scale(4)
        && n.getYcoord() < mapY + mapH - scale(4)
        && n.getFloor().toString().equals(currentFloor);
  }

  private void drawNode(NodeEntity node) {
    drawNode(node, Color.BLUE);
  }

  private void drawNode(NodeEntity node, Paint color) {
    if (node == null) {
      System.out.println("NULL NODE");
      return;
    }

    NodeCircle testCircle;
    testCircle = new NodeCircle(scale(4), color, node);
    ap.getChildren().add(testCircle);
    testCircle.setCenterX(locToMapX(node.getXcoord()));
    testCircle.setCenterY(locToMapY(node.getYcoord()));
    testCircle.setCursor(Cursor.HAND);
    testCircle.setPickOnBounds(true);
    testCircle.setOnMousePressed(event -> onClick.handle(event, testCircle));

    drawNodeText(node, color);
  }

  private void drawNode(NodeEntity node, Set<SubmissionAbs> subs) {
    if (node == null) {
      System.out.println("NULL NODE");
      return;
    }

    NodeCircle testCircle;
    testCircle = new NodeCircle(scale(4), Color.ORANGE, node, subs);
    ap.getChildren().add(testCircle);
    testCircle.setCenterX(locToMapX(node.getXcoord()));
    testCircle.setCenterY(locToMapY(node.getYcoord()));
    testCircle.setCursor(Cursor.HAND);
    testCircle.setPickOnBounds(true);
    testCircle.setOnMousePressed(event -> onClick.handle(event, testCircle));

    drawNodeText(node, Color.ORANGE);
  }

  private void drawNodeText(NodeEntity node, Paint color) {
    if (controller.getLocationNames().isSelected()) {
      Text locationNameText = new Text(node.getShortName());
      double x = locToMapX(node.getXcoord());
      double y = locToMapY(node.getYcoord()) - scale(5);
      System.out.println(
          "x- " + x + " w-" + locationNameText.getBoundsInLocal().getWidth() + " cw-" + canvasW);
      if (x < 0 || x + locationNameText.getBoundsInLocal().getWidth() > canvasW) return;
      if (y - locationNameText.getFont().getSize() < 0 || y > canvasH) return;
      locationNameText.setX(x);
      locationNameText.setY(y);
      locationNameText.setFill(color);
      ap.getChildren().add(locationNameText);
    }
  }

  private void drawNode(NodeEntity node, EventHandler<? super MouseEvent> eventHandler) {
    if (node == null) {
      System.out.println("NULL NODE");
      return;
    }

    NodeCircle testCircle;

    testCircle = new NodeCircle(scale(4), Color.PURPLE, node);
    ap.getChildren().add(testCircle);
    testCircle.setCenterX(locToMapX(node.getXcoord()));
    testCircle.setCenterY(locToMapY(node.getYcoord()));
    testCircle.setCursor(Cursor.HAND);
    testCircle.setPickOnBounds(true);
    testCircle.setOnMousePressed(eventHandler);

    drawNodeText(node, Color.PURPLE);
  }

  private void drawPathText() {
    if (controller.getMapText() == null || controller.getMapText().equals("")) return;

    Paint current = gc.getFill();
    gc.setFill(Color.BLACK);
    gc.setFont(Font.font(30));
    gc.fillText(controller.getMapText(), 10, 40, canvasW - 20);
    gc.setFill(current);
  }

  private Set<SubmissionAbs> getServiceRequests(NodeEntity node) {
    Set<SubmissionAbs> requests = new HashSet<>();

    for (SubmissionAbs sub : Main.db.getAudioSubs().values()) {
      NodeEntity ne = sub.getLocationNode(controller.getMoveDate());
      if (ne != null && ne.equals(node)) requests.add(sub);
    }

    for (SubmissionAbs sub : Main.db.getCleaningSubs().values()) {
      NodeEntity ne = sub.getLocationNode(controller.getMoveDate());
      if (ne != null && ne.equals(node)) requests.add(sub);
    }

    for (SubmissionAbs sub : Main.db.getComputerSubs().values()) {
      NodeEntity ne = sub.getLocationNode(controller.getMoveDate());
      if (ne != null && ne.equals(node)) requests.add(sub);
    }

    for (SubmissionAbs sub : Main.db.getSecuritySubs().values()) {
      NodeEntity ne = sub.getLocationNode(controller.getMoveDate());
      if (ne != null && ne.equals(node)) requests.add(sub);
    }

    for (SubmissionAbs sub : Main.db.getTransportationSubs().values()) {
      NodeEntity ne = sub.getLocationNode(controller.getMoveDate());
      if (ne != null && ne.equals(node)) requests.add(sub);
    }

    return requests;
  }

  private void drawEdges() {
    gc.setStroke(Color.RED);
    for (EdgeEntity edge : Main.db.getEdges()) {
      NodeEntity n1 = edge.getNode1();
      NodeEntity n2 = edge.getNode2();
      if (!n1.getFloor().toString().equals(currentFloor) || !n2.getFloor().toString().equals(currentFloor)) continue;

      gc.strokeLine(
          locToMapX(n1.getXcoord()),
          locToMapY(n1.getYcoord()),
          locToMapX(n2.getXcoord()),
          locToMapY(n2.getYcoord()));
    }
    gc.setStroke(Color.BLUE);
  }

  private void drawPath() {
    for (int i = 1; i < currentPath.size(); i++) {
      NodeEntity n1 = currentPath.get(i - 1), n2 = currentPath.get(i);
      if (n1.getFloor().toString().equals(currentFloor)
          && !n2.getFloor().toString().equals(currentFloor)
          && nodeInMapView(n1)) {
        drawNode(n1, (event -> alertNewFloor(n1, n1.getFloor().toString(), n2.getFloor().toString())));
      } else if (n2.getFloor().toString().equals(currentFloor)
          && !n1.getFloor().toString().equals(currentFloor)
          && nodeInMapView(n2)) {
        drawNode(n2, (event -> alertNewFloor(n2, n2.getFloor().toString(), n1.getFloor().toString())));
      } else if (!n1.getFloor().toString().equals(currentFloor) && !n2.getFloor().toString().equals(currentFloor)) {
        continue;
      } else if ((!nodeInMapView(n1)) && (!nodeInMapView(n2))) {
        continue;
      } else if (!controller.getPFPlace(n1).getLocationtype().equals("HALL")) {
        drawNodeText(n1, Color.BLUE);
      }

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

    try {
      if (floorID.equals("L1")) {
        currentFloorImage = ImageLoader.getL1().get();
      } else if (floorID.equals("L2")) {
        currentFloorImage = ImageLoader.getL2().get();
      } else if (floorID.equals("1")) {
        currentFloorImage = ImageLoader.getF1().get();
      } else if (floorID.equals("2")) {
        currentFloorImage = ImageLoader.getF2().get();
      } else if (floorID.equals("3")) {
        currentFloorImage = ImageLoader.getF3().get();
      } else {
        throw new FloorDoesNotExistException("floorID " + floorID + " does not exist");
      }
    } catch (FloorDoesNotExistException e) {
      throw new FloorDoesNotExistException(e);
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException("Floor image did not load correctly!");
    }

    currentFloor = floorID;
    drawNodes();
  }

  private void alertNewFloor(NodeEntity node, String fromFloor, String toFloor) {
    MFXGenericDialogBuilder dialogBuilder = new MFXGenericDialogBuilder();

    Text title = new Text("Take " + node.getShortName() + " from " + fromFloor + " to " + toFloor);
    MFXButton gotoFloor = new MFXButton("Go to " + toFloor);
    gotoFloor.setBackground(Background.fill(Color.PURPLE));

    dialogBuilder.makeScrollable(true);
    dialogBuilder.setShowAlwaysOnTop(false);
    dialogBuilder.setHeaderText("Location Information");
    dialogBuilder.setShowMinimize(false);
    dialogBuilder.setContent(title);
    dialogBuilder.addActions(gotoFloor);

    MFXGenericDialog dialog = dialogBuilder.get();
    gotoFloor.setOnAction(
        (event) -> {
          try {
            changeFloor(toFloor);
          } catch (FloorDoesNotExistException ignored) {
          }
          stackPane.getChildren().removeAll(dialog);
          controller.changeFloorNum(toFloor);
          drawNodes();
        });
    dialog.setOnClose((event1 -> stackPane.getChildren().removeAll(dialog)));

    controller.removeCurrentDialog();
    stackPane.getChildren().add(dialog);
  }
}
