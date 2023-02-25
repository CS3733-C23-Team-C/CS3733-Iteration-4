package edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.row;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.MoveEntity;
import javafx.scene.control.TableView;

public class MoveRow extends RowBase<MoveEntity> {
  public MoveRow(TableView<MoveEntity> tableView, MoveEntity entity) {
    super(tableView, entity);
  }
}
