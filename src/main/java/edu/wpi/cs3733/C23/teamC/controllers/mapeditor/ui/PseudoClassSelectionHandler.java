package edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui;

import javafx.css.PseudoClass;
import javafx.scene.Node;

/**
 * Utility class that handles toggling the :selected CSS pseudoclass. Useful for things with
 * toggle-like behavior.
 */
public final class PseudoClassSelectionHandler {
  private static final PseudoClass SELECTED = PseudoClass.getPseudoClass("selected");

  /** The JavaFX node that this object manipulates. */
  private final Node node;

  PseudoClassSelectionHandler(Node node) {
    this.node = node;
  }

  public void onSelected() {
    node.pseudoClassStateChanged(SELECTED, true);
  }

  public void onDeselected() {
    node.pseudoClassStateChanged(SELECTED, false);
  }
}
