package edu.wpi.capybara.controllers.mapeditor.ui;

import edu.wpi.capybara.controllers.mapeditor.ui.elements.Element;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlySetProperty;

public interface UIModel {
    ReadOnlyListProperty<Element> elements();
    void add(Element element);
    void delete(Element element);

    ReadOnlySetProperty<Element> selected();
    void select(Element element);
    void deselect(Element element);
}
