package edu.wpi.capybara.controllers.mapeditor.ui.elements;

import edu.wpi.capybara.controllers.mapeditor.adapters.NodeAdapter;
import edu.wpi.capybara.controllers.mapeditor.ui.UIModel;
import edu.wpi.capybara.controllers.mapeditor.ui.gfx.NodeGFX;
import edu.wpi.capybara.controllers.mapeditor.ui.row.NodeRow;
import edu.wpi.capybara.objects.hibernate.NodeEntity;

public final class NodeElement extends ElementBase<NodeGFX, NodeRow, NodeEntity> implements Element {
    public NodeElement(UIModel model, NodeGFX onMap, NodeRow inTable, NodeEntity inRepo) {
        super(model, onMap, inTable, inRepo);

        inTable.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                model.select(this);
            } else {
                model.deselect(this);
            }
        });
    }
}
