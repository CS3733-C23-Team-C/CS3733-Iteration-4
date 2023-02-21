package edu.wpi.capybara.controllers.mapeditor.ui.elements;

import edu.wpi.capybara.controllers.mapeditor.adapters.EdgeAdapter;
import edu.wpi.capybara.controllers.mapeditor.ui.UIModel;
import edu.wpi.capybara.controllers.mapeditor.ui.gfx.EdgeGFX;
import edu.wpi.capybara.controllers.mapeditor.ui.row.EdgeRow;
import edu.wpi.capybara.objects.hibernate.EdgeEntity;

public final class EdgeElement extends ElementBase<EdgeGFX, EdgeRow, EdgeEntity> implements Element {
    public EdgeElement(UIModel model, EdgeGFX onMap, EdgeRow inTable, EdgeEntity inRepo) {
        super(model, onMap, inTable, inRepo);

        // handle selection from the map

        // handle selection from the table
        inTable.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                model.select(this);
            } else {
                model.deselect(this);
            }
        });
    }
}
