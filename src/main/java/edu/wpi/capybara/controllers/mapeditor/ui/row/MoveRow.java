package edu.wpi.capybara.controllers.mapeditor.ui.row;

import edu.wpi.capybara.objects.hibernate.MoveEntity;
import javafx.scene.control.TableView;

public class MoveRow extends RowBase<MoveEntity> {
  public MoveRow(TableView<MoveEntity> tableView, MoveEntity entity) {
    super(tableView, entity);
  }
}
