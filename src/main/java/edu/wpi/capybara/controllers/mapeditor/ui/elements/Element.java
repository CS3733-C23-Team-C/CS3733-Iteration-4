package edu.wpi.capybara.controllers.mapeditor.ui.elements;

import edu.wpi.capybara.controllers.mapeditor.ui.Selectable;
import javafx.scene.Node;

public sealed interface Element extends Selectable
    permits EdgeElement, LocationElement, MoveElement, NodeElement {
  Node getNode();
}
