package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.navigation.Navigation;
import edu.wpi.capybara.navigation.Screen;
import edu.wpi.capybara.objects.Edge;
import edu.wpi.capybara.objects.Node;
import edu.wpi.capybara.pathfinding.Path;
import edu.wpi.capybara.pathfinding.Pathfinder;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
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

  public void submitForm(ActionEvent actionEvent) throws IOException { // submissions for Curr
    System.out.println("asdf");
    String outputID = idField.getText();
    String outputCurrRoom = currRoom.getText();
    String outputDestRoom = destRoom.getText();
    try {
      InputStream edgesFile = App.class.getResourceAsStream("csv/L1Edges.csv");
      InputStream nodesFile = App.class.getResourceAsStream("csv/L1Nodes.csv");
      Pathfinder pathfinder =
          new Pathfinder(DatabaseConnect.getNodes(), DatabaseConnect.getEdges());
      List<Node> path = pathfinder.findPath(outputCurrRoom, outputDestRoom);
      System.out.println("path successfully created");
      System.out.println("Number of Nodes : " + path.size());
      List<Edge> pathEdges = Pathfinder.getPathEdges(path);
      System.out.println("Number of Edges : " + pathEdges.size());
      pathTable.setVisible(true);
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
