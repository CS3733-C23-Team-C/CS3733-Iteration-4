package edu.wpi.capybara.controllers.mapeditor.ui.elements;

import edu.wpi.capybara.controllers.mapeditor.adapters.NodeAdapter;
import edu.wpi.capybara.controllers.mapeditor.ui.gfx.NodeGFX;
import edu.wpi.capybara.controllers.mapeditor.ui.row.NodeRow;

public final class NodeElement extends ElementBase<NodeGFX, NodeRow, NodeAdapter> implements Element {
    public NodeElement(NodeGFX onMap, NodeRow inTable, NodeAdapter inRepo) {
        super(onMap, inTable, inRepo);
    }
}
