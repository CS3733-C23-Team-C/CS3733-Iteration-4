package edu.wpi.capybara.controllers.mapeditor.ui.row;

import edu.wpi.capybara.controllers.mapeditor.adapters.MoveAdapter;
import io.github.palexdev.materialfx.controls.MFXTableView;

public class MoveRow extends RowBase<MoveAdapter> {
    public MoveRow(MFXTableView<MoveAdapter> tableView, MoveAdapter data) {
        super(tableView, data);
    }
}
