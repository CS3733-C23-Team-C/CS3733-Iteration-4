package edu.wpi.capybara.controllers.mapeditor.ui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;

public interface Selectable {
    void showAsSelected();
    void showAsDeselected();
}
