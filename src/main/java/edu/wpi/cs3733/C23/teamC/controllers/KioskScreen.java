package edu.wpi.cs3733.C23.teamC.controllers;

import edu.wpi.cs3733.C23.teamC.App;
import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.exceptions.FloorDoesNotExistException;
import edu.wpi.cs3733.C23.teamC.objects.*;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.EdgeEntity;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.NodeEntity;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class KioskScreen implements MapViewHolder {

  private static String title;
  private static NodeEntity loc;
  private static List<NodeEntity> path;

  @FXML private Text kioskTitle, leftNode, rightNode;
  @FXML private Canvas nodeDrawer;
  @FXML private AnchorPane nodeAnchorPane;
  @FXML private Pane canvasPane;
  @FXML private StackPane stackPane;
  private List<EdgeEntity> edges;
  private int currentEdge;
  private Floor currentFloor;
  private MapViewController mvc;

  public static void showKiosk(String titleStr, NodeEntity locNode, List<NodeEntity> pathList) {
    title = titleStr;
    loc = locNode;
    path = pathList;

    Stage dialog = new Stage();

    final FXMLLoader loader = new FXMLLoader(App.class.getResource("views/KioskScreen.fxml"));
    BorderPane root;
    try {
      root = loader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    dialog.setScene(new Scene(root));

    dialog.setOnCloseRequest((event -> dialog.close()));
    dialog.show();
    dialog.setFullScreen(true);

    dialog
        .fullScreenProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (!newValue) {
                dialog.close();
              }
            });
  }

  public void initialize() {
    kioskTitle.setText(title);

    edges =
        Main.getRepo().getEdges().stream()
            .filter((edge -> edge.getNode1().equals(loc) || edge.getNode2().equals(loc)))
            .toList();
    currentEdge = 0;

    currentFloor = loc.getFloor();

    mvc =
        new MapViewController(
            nodeDrawer,
            nodeAnchorPane,
            canvasPane,
            NodeCircleClickHandler.doNothing(),
            stackPane,
            this);
    mvc.setStartNode(loc);
    mvc.setEndNode(path.get(path.size() - 1));
    mvc.displayPath(path);

    int maxX = -1, minX = Integer.MAX_VALUE;
    int maxY = -1, minY = Integer.MAX_VALUE;

    for (NodeEntity node : path) {
      if (node.getXcoord() > maxX) maxX = node.getXcoord();
      if (node.getXcoord() < minX) minX = node.getXcoord();
      if (node.getYcoord() > maxY) maxY = node.getYcoord();
      if (node.getYcoord() < minY) minY = node.getYcoord();
    }

    double canvasRatio = mvc.getCanvasH() / mvc.getCanvasW();

    mvc.setMapX(minX - 50);
    mvc.setMapW(maxX - minX + 50);

    mvc.setMapY(minY - 50);
    mvc.setMapH(mvc.getMapW() * canvasRatio);

    if (mvc.getMapY() + mvc.getMapH() < maxY) {
      mvc.setMapH(maxY - minY + 50);
      mvc.setMapW(mvc.getMapH() * (1 / canvasRatio));
    }

    try {
      mvc.changeFloor(currentFloor.toString());
    } catch (FloorDoesNotExistException ignored) {
    }

    mvc.drawNodes();

    setSideText();
  }

  private void setSideText() {
    NodeEntity other = Main.getRepo().getNode(edges.get(currentEdge).getOtherNode(loc));

    if (other.getXcoord() < loc.getXcoord()) {
      leftNode.setText(other.getLongName());
      rightNode.setText(loc.getLongName());
    } else {
      leftNode.setText(loc.getLongName());
      rightNode.setText(other.getLongName());
    }
  }

  public void changeSides() {
    String temp = leftNode.getText();
    leftNode.setText(rightNode.getText());
    rightNode.setText(temp);
  }

  public void switchEdge() {
    currentEdge++;
    if (currentEdge >= edges.size()) {
      currentEdge = 0;
    }
    setSideText();
  }

  @Override
  public boolean displayLocationNames() {
    return true;
  }

  @Override
  public boolean serviceRequestSelected() {
    return false;
  }

  @Override
  public String getMapText() {
    return "";
  }

  @Override
  public Date getMoveDate() {
    return Date.valueOf(LocalDate.now());
  }

  @Override
  public PFPlace getPFPlace(NodeEntity node) {
    String lname = node.getLongName();
    if (lname == null || lname.equals("")) {
      return new PFNode(node);
    } else {
      return new PFLocation(Main.getRepo().getLocationname(lname));
    }
  }

  public void changeFloorNum(String floor) {
    currentFloor = Floor.fromString(floor);
  }

  @Override
  public void removeCurrentDialog() {
    // do nothing :-)
  }

  public void keyPress(KeyEvent event) {
    System.out.println("KEY EVENT " + event.getCharacter());
  }
}
