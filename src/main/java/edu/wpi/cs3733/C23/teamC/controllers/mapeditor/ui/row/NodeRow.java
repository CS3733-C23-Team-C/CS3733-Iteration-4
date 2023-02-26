package edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.row;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.NodeEntity;
import javafx.scene.control.TableView;

public class NodeRow extends RowBase<NodeEntity> {
  public NodeRow(TableView<NodeEntity> tableView, NodeEntity entity) {
    super(tableView, entity);
  }
}
