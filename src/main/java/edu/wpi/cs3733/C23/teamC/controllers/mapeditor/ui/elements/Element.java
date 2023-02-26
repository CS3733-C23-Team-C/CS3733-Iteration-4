package edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.elements;

import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.Selectable;
import javafx.scene.Node;

public sealed interface Element extends Selectable
    permits EdgeElement, LocationElement, MoveElement, NodeElement {
  Node getNode();
}
