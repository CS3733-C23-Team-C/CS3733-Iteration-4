package edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui;

import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.elements.*;
import edu.wpi.cs3733.C23.teamC.objects.Floor;
import javafx.beans.property.ReadOnlySetProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

// facade for the map editor UI model
public interface UIModel {
  ReadOnlySetProperty<Element> elementsProperty();

  void add(Element element);

  void delete(Element element);

  ReadOnlySetProperty<Element>
      selectedProperty(); // managing selection state in the UI is delegated to Elements

  void select(Element element);

  void deselect(Element element);

  void deselectAll();

  boolean isShowLabels();

  SimpleBooleanProperty showLabelsProperty();

  void setShowLabels(boolean showLabels);

  Floor getShownFloor();

  SimpleObjectProperty<Floor> shownFloorProperty();

  void setShownFloor(Floor shownFloor);
}
