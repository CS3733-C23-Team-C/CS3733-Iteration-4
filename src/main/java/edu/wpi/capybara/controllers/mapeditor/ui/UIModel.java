package edu.wpi.capybara.controllers.mapeditor.ui;

import edu.wpi.capybara.controllers.mapeditor.ui.elements.Element;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlySetProperty;

// facade for the map editor UI model
public interface UIModel {
    ReadOnlyListProperty<Element> elementsProperty();
    void add(Element element);
    void delete(Element element);

    ReadOnlySetProperty<Element> selectedProperty(); // managing selection state in the UI is delegated to Elements
    void select(Element element);
    void deselect(Element element);
    void deselectAll();
}
