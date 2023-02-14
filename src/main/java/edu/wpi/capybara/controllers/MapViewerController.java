package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.controllers.mapeditor.NodeAdapter;
import java.util.Objects;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ListChangeListener;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class MapViewerController {

  private final Canvas nodeDrawer;

  private final SimpleListProperty<NodeAdapter> nodesToDisplay;

  private final GraphicsContext gc;
  private final Image image;

  private double mapX, mapY, mapW, mapH;
  private double canvasW, canvasH;
  private int lastX, lastY;

  private static final float SCROLL_SPEED = 1f;
  private static final int MOVE_SPEED = 30;

  public MapViewerController(Pane pane, Canvas nodeDrawer, ListProperty<NodeAdapter> nodes) {
    // this dependency injection hurts my soul, but it will work for now
    this.nodeDrawer = nodeDrawer;

    image = new Image(Objects.requireNonNull(App.class.getResourceAsStream("images/blankL1.png")));
    gc = nodeDrawer.getGraphicsContext2D();
    gc.setFill(Color.RED);
    gc.setLineWidth(2);
    gc.setStroke(Color.BLUE);

    mapX = 1441;
    mapY = 660;
    mapW = 1224;
    mapH = 1000;

    canvasW = (int) nodeDrawer.getWidth();
    canvasH = (int) nodeDrawer.getHeight();

    nodesToDisplay = new SimpleListProperty<>(nodes);
    nodesToDisplay.addListener(
        (ListChangeListener<? super NodeAdapter>)
            (node) -> {
              // this could be made more efficient by only redrawing the changed node, but it will
              // work for now
              System.out.println("Redrawing nodes");
              drawNodes();
            });

    nodeDrawer.widthProperty().bind(pane.widthProperty());
    nodeDrawer.heightProperty().bind(pane.heightProperty());

    nodeDrawer
        .widthProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              canvasW = nodeDrawer.getWidth();

              mapW = mapH * (canvasW / canvasH);

              drawNodes();
            });
    nodeDrawer
        .heightProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              canvasH = nodeDrawer.getHeight();

              mapH = mapW * (canvasH / canvasW);

              drawNodes();
            });

    nodeDrawer.setOnMousePressed(this::mapStartDrag);
    nodeDrawer.setOnMouseDragged(this::mapDrag);
    nodeDrawer.setOnMouseReleased(this::mapStopDrag);
    nodeDrawer.setOnMouseExited(this::mapStopDrag);
    nodeDrawer.setOnScroll(this::mapScroll);

    nodeDrawer.setCursor(Cursor.OPEN_HAND);
  }

  public void mapStartDrag(MouseEvent event) {
    lastX = (int) event.getX();
    lastY = (int) event.getY();

    nodeDrawer.setCursor(Cursor.CLOSED_HAND);
  }

  public void mapStopDrag(MouseEvent event) {

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

  public void mapScroll(ScrollEvent event) {
    float amountZoom = 1f + (SCROLL_SPEED * ((float) event.getDeltaY() / 400));
    zoom(amountZoom, (int) event.getX(), (int) event.getY());
    drawNodes();
  }

  private void drawNodes() {
    gc.clearRect(0, 0, canvasW, canvasH);
    gc.drawImage(image, mapX, mapY, mapW, mapH, 0, 0, canvasW, canvasH);
    // System.out.println("window: " + mapX + " x " + mapY + " size: " + mapW + " x " + mapH);

    /*if (isPath) {
        drawPath();
        gc.setFill(Color.GREEN);
        drawNode(nodesToDisplay.get(0));
        gc.setFill(Color.RED);
        drawNode(nodesToDisplay.get(nodesToDisplay.size() - 1));
    } else {*/
    /*Node startNode = searchName(currRoom.getText());
    Node endNode = searchName(destRoom.getText());

    if (startNode != null) {
        gc.setFill(Color.GREEN);
        drawNode(startNode);
    }
    if (endNode != null) {
        gc.setFill(Color.RED);
        drawNode(endNode);
    }*/

    gc.setFill(Color.BLUE);
    for (NodeAdapter n : nodesToDisplay) {
      if (nodeInMapView(n)) {
        // if (n == startNode || n == endNode) continue;
        drawNode(n);
        gc.setFill(Color.BLUE);
        // System.out.println(n);
      }
    }
    // }
  }

  private boolean nodeInMapView(NodeAdapter n) {
    return n.getXCoord() > mapX
        && n.getXCoord() < mapX + mapW
        && n.getYCoord() > mapY
        && n.getYCoord() < mapY + mapH;
  }

  private void drawNode(NodeAdapter node) {
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

  private double locToMapX(int xCoord) {
    return (xCoord - mapX) * (canvasW) / (mapW);
  }

  private double locToMapY(int yCoord) {
    return (yCoord - mapY) * (canvasH) / (mapH);
  }

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
}
