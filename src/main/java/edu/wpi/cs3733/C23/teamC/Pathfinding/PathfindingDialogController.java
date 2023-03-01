package edu.wpi.cs3733.C23.teamC.Pathfinding;

import edu.wpi.cs3733.C23.teamC.App;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import io.github.palexdev.materialfx.controls.cell.MFXListCell;
import java.io.IOException;
import java.util.function.Function;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PathfindingDialogController {

  @FXML private MFXListView<PFPlace> listView;
  @FXML private MFXToggleButton avoidStairs;
  @FXML private MFXToggleButton preferStairs;
  @FXML private MFXFilterComboBox<PFPlace> locationDropdown;
  @FXML private MFXTextField mapText;
  private static PathfindingController controller;
  private ObservableList<PFPlace> items;
  public static Stage dialog;

  public static void showDialog(PathfindingController pcontroller) {
    controller = pcontroller;

    dialog = new Stage();
    dialog.initModality(Modality.WINDOW_MODAL);
    dialog.initOwner(App.getPrimaryStage().getOwner());

    final FXMLLoader loader =
        new FXMLLoader(App.class.getResource("views/PathfindingSettingsDialog.fxml"));
    BorderPane root;
    try {
      root = loader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    dialog.setScene(new Scene(root));

    dialog.setOnCloseRequest(
        (event -> {
          Stage oldDialog = dialog;
          dialog = null;
          oldDialog.close();
        }));
    dialog.showAndWait();
  }

  public void initialize() {
    items = FXCollections.observableArrayList();
    listView.setItems(items);

    avoidStairs.setSelected(controller.isAvoidStairs());
    preferStairs.setSelected(controller.isPreferStairs());
    locationDropdown.setItems(FXCollections.observableArrayList(controller.getPfLocations()));

    Function<PFPlace, MFXListCell<PFPlace>> func = listView.getCellFactory();
    listView.setCellFactory(
        (place) -> {
          MFXListCell<PFPlace> cell = func.apply(place);
          cell.setOnContextMenuRequested((event -> cellContextMenu(event, cell, place)));
          return cell;
        });

    items.addAll(controller.getPlacesToAvoid());

    mapText.setText(controller.getMapText());
    mapText.setDisable(!App.getUser().getRole().equals("admin"));
  }

  private void cellContextMenu(ContextMenuEvent event, Node anchor, PFPlace value) {
    MenuItem delete = new MenuItem("Delete");
    delete.setOnAction((event1 -> items.remove(value)));

    ContextMenu menu = new ContextMenu(delete);

    menu.show(anchor, event.getScreenX(), event.getScreenY());
  }

  public void applyChanges() {
    controller.setPlacesToAvoid(items.stream().toList());
    controller.setAvoidStairs(avoidStairs.isSelected());
    controller.setPreferStairs(preferStairs.isSelected());
    controller.setMapText(mapText.getText());
    // System.out.println("applying changes");
    // System.out.println(controller.getPlacesToAvoid());

    Stage oldDialog = dialog;
    dialog = null;
    oldDialog.close();
  }

  public void cancelChanges() {
    // System.out.println("cancelling changes");

    Stage oldDialog = dialog;
    dialog = null;
    oldDialog.close();
  }

  public void validatePreferStairs() {
    if (preferStairs.isSelected()) avoidStairs.setSelected(false);
    validateSliders();
  }

  public void validateSliders() {
    if (avoidStairs.isSelected()) preferStairs.setSelected(false);
  }

  public void selected() {
    if (!items.contains(locationDropdown.getSelectedItem()))
      items.add(locationDropdown.getSelectedItem());
    locationDropdown.clearSelection();
  }
}
