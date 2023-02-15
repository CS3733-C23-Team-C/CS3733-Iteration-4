package edu.wpi.capybara.controllers.mapeditor.ui;

import javafx.beans.value.ChangeListener;
import javafx.css.PseudoClass;
import javafx.scene.Node;

public final class PseudoClassSelectionHandler {
  private static final PseudoClass SELECTED = PseudoClass.getPseudoClass("selected");

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

  public static ChangeListener<Boolean> create(Node node) {
    // the more "traditional" way of doing this in Java would be to make PseudoClassSelectionHandler
    // implement
    // ChangeListener<Boolean>, and take a reference to the node in the constructor. I personally
    // prefer using
    // lambdas instead, as it has much less boilerplate. however, due to Java's lack of a proper
    // namespace
    // construct, the closest thing I can do to a free function is a static function in a
    // private-constructor
    // class. this adds enough boilerplate to make the lambda approach about as boilerplate-y as the
    // traditional
    // approach. thus, I think it just comes down to personal preference.
    // I *could* move this to the Selectable interface, but Selectable is intended to be independent
    // of any UI
    // implementation details. this function specifically deals with the minutia of JavaFX CSS, and
    // thus should
    // be in its own namespace.
    // of course, this also comes with the possible downside of introducing inconsistency to client
    // code. not all
    // code will use this kind of lambda approach. this could lead to a situation where
    return (observable, oldValue, newValue) -> node.pseudoClassStateChanged(SELECTED, newValue);
  }
}
