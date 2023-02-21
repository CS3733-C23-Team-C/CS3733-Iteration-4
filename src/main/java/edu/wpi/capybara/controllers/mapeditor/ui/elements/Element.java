package edu.wpi.capybara.controllers.mapeditor.ui.elements;

import edu.wpi.capybara.controllers.mapeditor.ui.Selectable;

public sealed interface Element extends Selectable permits EdgeElement, LocationElement, MoveElement, NodeElement {}
