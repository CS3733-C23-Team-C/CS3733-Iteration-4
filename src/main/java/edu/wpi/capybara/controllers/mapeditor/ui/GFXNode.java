package edu.wpi.capybara.controllers.mapeditor.ui;

import edu.wpi.capybara.controllers.mapeditor.adapters.NodeAdapter;
import edu.wpi.capybara.objects.Floor;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

public class GFXNode extends Circle implements Selectable {

    private final MapEditorPane root;
    private final NodeAdapter node;
    private final PseudoClassSelectionHandler pseudoClassHandler;

    GFXNode(MapEditorPane root, NodeAdapter node) {
        this.root = root;
        this.node = node;
        pseudoClassHandler = new PseudoClassSelectionHandler(this);

        setRadius(10);
        centerXProperty().bind(node.xCoordProperty());
        centerYProperty().bind(node.yCoordProperty());
        getStyleClass().add("selectable");

        final var floor = Floor.fromString(node.getFloor());
        if (floor != null) {
            visibleProperty().bind(root.lookupFloorImage(floor).visibleProperty());
        }

        addEventFilter(
                MouseEvent.MOUSE_PRESSED,
                event -> {
                    if (!event.isPrimaryButtonDown()) return;
                    if (!event.isShiftDown()) {
                        // remove all other selections
                        root.deselectAll();
                    }
                    select();
                    // don't let the click propagate back to the map
                    event.consume();
                });
        addEventFilter(MouseEvent.MOUSE_RELEASED, MouseEvent::consume);
    }

    @Override
    public void select() {
        root.addSelection(this);
        pseudoClassHandler.onSelected();
    }

    @Override
    public void deselect() {
        root.removeSelection(this);
        pseudoClassHandler.onDeselected();
    }

    @Override
    public Object getSelectedObject() {
        return node;
    }
}
