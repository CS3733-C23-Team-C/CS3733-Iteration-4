package edu.wpi.capybara.controllers.mapeditor.ui.row;

import edu.wpi.capybara.controllers.mapeditor.adapters.NodeAdapter;
import io.github.palexdev.materialfx.controls.MFXTableView;

public class NodeRow extends RowBase<NodeAdapter> {
    public NodeRow(MFXTableView<NodeAdapter> tableView, NodeAdapter data) {
        super(tableView, data);
    }
}
