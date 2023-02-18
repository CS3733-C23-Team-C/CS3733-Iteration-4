package edu.wpi.capybara.controllers.mapeditor.ui.elements;

import edu.wpi.capybara.controllers.mapeditor.adapters.EdgeAdapter;
import edu.wpi.capybara.controllers.mapeditor.ui.gfx.EdgeGFX;
import edu.wpi.capybara.controllers.mapeditor.ui.row.EdgeRow;

public final class EdgeElement extends ElementBase<EdgeGFX, EdgeRow, EdgeAdapter> implements Element {
    public EdgeElement(EdgeGFX onMap, EdgeRow inTable, EdgeAdapter inRepo) {
        super(onMap, inTable, inRepo);
    }
}
