package edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.row;

import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.Selectable;
import javafx.scene.control.TableView;

class RowBase<T> implements Selectable {
  private final TableView<T> tableView;
  private final TableView.TableViewSelectionModel<T> selectionModel;
  private final T entity;

  public RowBase(TableView<T> tableView, T entity) {
    this.tableView = tableView;
    this.selectionModel = tableView.getSelectionModel();
    this.entity = entity;
  }

  @Override
  public void showAsSelected() {
    // selectionModel.select(entity);
    // tableView.scrollTo(entity);
    System.out.println("Row selected");
  }

  @Override
  public void showAsDeselected() {
    // selectionModel.clearSelection(tableView.getItems().indexOf(entity));
    System.out.println("Row deselected");
  }
}
