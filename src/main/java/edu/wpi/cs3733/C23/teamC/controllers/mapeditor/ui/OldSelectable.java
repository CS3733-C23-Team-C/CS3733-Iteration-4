package edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui;

// i miss c++'s composition and multiple-inheritance
public interface OldSelectable {
  void select();

  void deselect();

  Object getSelectedObject();

  /*default boolean isSelected() { return selectedProperty().get(); }
  default void setSelected(boolean selected) { selectedProperty().set(selected); }
  BooleanProperty selectedProperty();*/
}
