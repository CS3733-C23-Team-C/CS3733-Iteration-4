package edu.wpi.capybara.controllers.mapeditor;

import javafx.beans.property.Property;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;

public class PropertyEditAdapter<T, P> implements EventHandler<TableColumn.CellEditEvent<T, P>> {

  private final Property<P> property;

  public PropertyEditAdapter(Property<P> property) {
    this.property = property;
  }

  @Override
  public void handle(TableColumn.CellEditEvent<T, P> event) {}
}
