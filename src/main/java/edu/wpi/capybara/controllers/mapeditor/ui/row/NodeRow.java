package edu.wpi.capybara.controllers.mapeditor.ui.row;

import edu.wpi.capybara.objects.hibernate.NodeEntity;
import javafx.scene.control.TableView;

public class NodeRow extends RowBase<NodeEntity> {
  public NodeRow(TableView<NodeEntity> tableView, NodeEntity entity) {
    super(tableView, entity);
  }
}
