package edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.row;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.EdgeEntity;
import javafx.scene.control.TableView;

public class EdgeRow extends RowBase<EdgeEntity> {
  public EdgeRow(TableView<EdgeEntity> tableView, EdgeEntity entity) {
    super(tableView, entity);
  }
}
