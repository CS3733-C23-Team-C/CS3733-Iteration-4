package edu.wpi.capybara.controllers.mapeditor.ui;

import edu.wpi.capybara.controllers.mapeditor.ui.elements.Element;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.SetChangeListener;

public class UIModelImpl implements UIModel {
    private final ReadOnlyListWrapper<Element> elements = new ReadOnlyListWrapper<>(FXCollections.observableArrayList());
    private final ReadOnlySetWrapper<Element> selected = new ReadOnlySetWrapper<>(FXCollections.observableSet());

    public UIModelImpl() {
        // TODO: 2/18/23 add listeners to elements and selected
        selected.addListener((SetChangeListener<? super Element>) change -> {
            if (change.wasAdded()) change.getElementAdded().showAsSelected();
            if (change.wasRemoved()) change.getElementRemoved().showAsDeselected();
        });
    }

    @Override
    public ReadOnlyListProperty<Element> elementsProperty() {
        return elements.getReadOnlyProperty();
    }

    @Override
    public void add(Element element) {
        elements.add(element);
    }

    @Override
    public void delete(Element element) {
        elements.remove(element);
    }

    @Override
    public ReadOnlySetProperty<Element> selectedProperty() {
        return selected.getReadOnlyProperty();
    }

    @Override
    public void select(Element element) {
        selected.add(element);
    }

    @Override
    public void deselect(Element element) {
        selected.remove(element);
    }

    @Override
    public void deselectAll() {
        selected.clear();
    }
}
