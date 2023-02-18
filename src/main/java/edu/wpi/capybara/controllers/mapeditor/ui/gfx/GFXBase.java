package edu.wpi.capybara.controllers.mapeditor.ui.gfx;

import edu.wpi.capybara.controllers.mapeditor.ui.Selectable;
import javafx.css.PseudoClass;
import javafx.scene.Node;

class GFXBase extends Node implements Selectable {
    private static final PseudoClass PSEUDO_CLASS = PseudoClass.getPseudoClass("selected");

    // todo: should we expose the selection state to our subclasses? this would allow for styling beyond what CSS can do

    @Override
    public void showAsSelected() {
        pseudoClassStateChanged(PSEUDO_CLASS, true);
    }

    @Override
    public void showAsDeselected() {
        pseudoClassStateChanged(PSEUDO_CLASS, false);
    }
}
