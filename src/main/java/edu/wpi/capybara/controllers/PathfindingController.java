package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import edu.wpi.capybara.objects.hibernate.*;
import edu.wpi.capybara.pathfinding.Path;
import edu.wpi.capybara.pathfinding.Pathfinder;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PathfindingController {

  @FXML public Stage primaryStage;
  @FXML private MFXButton backButton;
  @FXML private MFXButton submitButton;
  @FXML private MFXButton clearButton;
  @FXML private MFXTextField idField;
  @FXML private MFXTextField currRoom;
  @FXML private MFXTextField destRoom;
  @FXML private MFXTextField dateField;
  @FXML private TableView<Path> pathTable;
  @FXML private TableColumn startCol;
  @FXML private TableColumn endCol;
  @FXML private VBox imageVBox;
  // @FXML private ImageView map;
  // @FXML private MFXComboBox<String> dropDown;
  private ObservableList<Path> paths = FXCollections.observableArrayList();

  /** Initialize controller by FXML Loader. */
  @FXML
  public void initialize() {
    System.out.println("I am from Pathfinder Controller.");
  }

  /**
   * FXML Injected Method that handles the submit button.
   *
   * @param actionEvent The event that triggered the method.
   */
  public void returnHome(ActionEvent actionEvent)
      throws IOException { // when back button is pressed
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

  /**
   * When submit button is clicked, calculates path using A* and prints data to table
   *
   * @param actionEvent
   * @throws IOException
   */
  public void submitForm(ActionEvent actionEvent) throws IOException {
    System.out.println("asdf");
    String outputID = idField.getText();
    String outputCurrRoom = currRoom.getText();
    String outputDestRoom = destRoom.getText();
    try {
      InputStream edgesFile = App.class.getResourceAsStream("csv/L1Edges.csv");
      InputStream nodesFile = App.class.getResourceAsStream("csv/L1Nodes.csv");
      Pathfinder pathfinder =
          new Pathfinder(DatabaseConnect.getNodes(), DatabaseConnect.getEdges());
      List<NodeEntity> path = pathfinder.findPath(outputCurrRoom, outputDestRoom);
      System.out.println("path successfully created");
      System.out.println("Number of Nodes : " + path.size());
      List<EdgeEntity> pathEdges = Pathfinder.getPathEdges(path);
      System.out.println("Number of Edges : " + pathEdges.size());
      pathTable.setVisible(true);

      System.out.println("asdf1");
      for (Map.Entry<Integer, NodeEntity> entry :
          pathfinder.getNodes().entrySet().stream().toList()) {
        System.out.println(entry.getKey());
      }
      System.out.println("asdf2");

      String outputForTable;
      for (int i = 0; i < path.size(); i++) {
        if (i == 0) {
          // System.out.println("Start at " + path.get(0).getShortName());
          outputForTable = "Start Node, End Node";
        } else {
          paths.add(new Path(path.get(i - 1).getShortName(), path.get(i).getShortName()));
          outputForTable = "\r\n " + path.get(i - 1) + "," + path.get(i);
        }
      }
      startCol.setCellValueFactory(new PropertyValueFactory<Path, String>("startNode"));
      endCol.setCellValueFactory(new PropertyValueFactory<Path, String>("endNode"));
      pathTable.setItems(paths);
      System.out.println("Path successfully created");
    } catch (Exception e) {
      System.out.println(e);
    }
    // System.out.println("Current Room: " + outputCurrRoom + " Destination Room: " +
    // outputDestRoom);
    clearFields();
  }

  public void clearFields() { // clears fields of text
    currRoom.setText("");
    destRoom.setText("");
    idField.setText("");
    dateField.setText("");
  }

  public void
      validateButton() { // ensures that information has been filled in before allowing submission
    boolean valid = false;
    if ((!currRoom.getText().equals("")
            && !destRoom.getText().equals("")
            && !idField.getText().equals(""))
        && !dateField.getText().equals("")) valid = true;
    submitButton.setDisable(!valid);
  }
}
