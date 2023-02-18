package edu.wpi.capybara.controllers.mapeditor.ui.elements;

import edu.wpi.capybara.controllers.mapeditor.adapters.MoveAdapter;
import edu.wpi.capybara.controllers.mapeditor.ui.gfx.MoveGFX;
import edu.wpi.capybara.controllers.mapeditor.ui.row.MoveRow;

public final class MoveElement extends ElementBase<MoveGFX, MoveRow, MoveAdapter> implements Element {
    public MoveElement(MoveGFX onMap, MoveRow inTable, MoveAdapter inRepo) {
        super(onMap, inTable, inRepo);
    }
}
