package edu.wpi.capybara.controllers.mapeditor.ui.elements;

import edu.wpi.capybara.controllers.mapeditor.ui.Selectable;
import edu.wpi.capybara.controllers.mapeditor.ui.UIModel;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import lombok.Getter;

abstract class ElementBase<MAP extends Node & Selectable, TABLE extends Selectable, REPO> {
  protected final UIModel model;
  @Getter protected final MAP onMap; // graphical representation on the map view
  @Getter protected final TABLE inTable; // graphical representation in the table view
  @Getter protected final REPO inRepo; // entity stored in the repository

  protected final SimpleBooleanProperty selected = new SimpleBooleanProperty();

  // dependencies are injected from the view and/or controller
  protected ElementBase(UIModel model, MAP onMap, TABLE inTable, REPO inRepo) {
    this.model = model;
    this.onMap = onMap;
    this.inTable = inTable;
    this.inRepo = inRepo;

    // selection logic lives in the subclasses
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

  public ReadOnlyBooleanProperty selectedProperty() {
    return selected;
  }

  public Node getNode() {
    return onMap;
  }
}
