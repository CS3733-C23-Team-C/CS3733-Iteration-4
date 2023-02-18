package edu.wpi.capybara.controllers.mapeditor.ui.row;

import edu.wpi.capybara.controllers.mapeditor.adapters.LocationNameAdapter;
import io.github.palexdev.materialfx.controls.MFXTableView;

public class LocationRow extends RowBase<LocationNameAdapter> {
    public LocationRow(MFXTableView<LocationNameAdapter> tableView, LocationNameAdapter data) {
        super(tableView, data);
    }
}
