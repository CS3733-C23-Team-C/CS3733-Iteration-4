package edu.wpi.capybara.controllers.mapeditor.ui.row;

import edu.wpi.capybara.controllers.mapeditor.ui.Selectable;
import io.github.palexdev.materialfx.controls.MFXTableRow;
import io.github.palexdev.materialfx.controls.MFXTableView;

class RowBase<T> extends MFXTableRow<T> implements Selectable {
    public RowBase(MFXTableView<T> tableView, T data) {
        super(tableView, data);
    }

    @Override
    public void showAsSelected() {
        setSelected(true);
    }

    @Override
    public void showAsDeselected() {
        setSelected(false);
    }
}
