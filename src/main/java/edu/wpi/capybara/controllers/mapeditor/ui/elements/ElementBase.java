package edu.wpi.capybara.controllers.mapeditor.ui.elements;

import edu.wpi.capybara.controllers.mapeditor.ui.Selectable;

abstract class ElementBase<MAP extends Selectable, TABLE extends Selectable, REPO> {
    protected final MAP onMap; // graphical representation on the map view
    protected final TABLE inTable; // graphical representation in the table view
    protected final REPO inRepo; // entity stored in the repository

    // dependencies are injected from the view and/or controller
    protected ElementBase(MAP onMap, TABLE inTable, REPO inRepo) {
        this.onMap = onMap;
        this.inTable = inTable;
        this.inRepo = inRepo;
    }

    // updates the UI to show this element as selected
    public void showAsSelected() {
        // delegate
        onMap.showAsSelected();
        inTable.showAsSelected();
    }
    // updates the UI to show this element as unselected
    public void showAsDeselected() {
        // delegate
        onMap.showAsDeselected();
        inTable.showAsDeselected();
    }
}
