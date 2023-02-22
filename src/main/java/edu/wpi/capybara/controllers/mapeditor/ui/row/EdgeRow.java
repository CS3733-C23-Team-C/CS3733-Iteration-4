package edu.wpi.capybara.controllers.mapeditor.ui.row;

import edu.wpi.capybara.objects.hibernate.EdgeEntity;
import javafx.scene.control.TableView;

public class EdgeRow extends RowBase<EdgeEntity> {
  public EdgeRow(TableView<EdgeEntity> tableView, EdgeEntity entity) {
    super(tableView, entity);
  }
}
