package edu.wpi.capybara.controllers.mapeditor.ui.elements;

import edu.wpi.capybara.controllers.mapeditor.adapters.LocationNameAdapter;
import edu.wpi.capybara.controllers.mapeditor.ui.gfx.LocationGFX;
import edu.wpi.capybara.controllers.mapeditor.ui.row.LocationRow;

public final class LocationElement extends ElementBase<LocationGFX, LocationRow, LocationNameAdapter> implements Element {
    public LocationElement(LocationGFX onMap, LocationRow inTable, LocationNameAdapter inRepo) {
        super(onMap, inTable, inRepo);
    }
}
