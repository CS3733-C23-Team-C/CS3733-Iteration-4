package edu.wpi.capybara.controllers.mapeditor.ui.row;

import edu.wpi.capybara.controllers.mapeditor.adapters.EdgeAdapter;
import io.github.palexdev.materialfx.controls.MFXTableView;

public class EdgeRow extends RowBase<EdgeAdapter> {
    public EdgeRow(MFXTableView<EdgeAdapter> tableView, EdgeAdapter data) {
        super(tableView, data);
    }
}
