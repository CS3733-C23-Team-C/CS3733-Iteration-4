package edu.wpi.capybara.controllers.mapeditor.ui;

import edu.wpi.capybara.controllers.mapeditor.ui.elements.Element;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlySetProperty;

public class UIModelImpl implements UIModel {
    @Override
    public ReadOnlyListProperty<Element> elements() {
        return null;
    }

    @Override
    public void add(Element element) {

    }

    @Override
    public void delete(Element element) {

    }

    @Override
    public ReadOnlySetProperty<Element> selected() {
        return null;
    }

    @Override
    public void select(Element element) {

    }

    @Override
    public void deselect(Element element) {

    }
}
