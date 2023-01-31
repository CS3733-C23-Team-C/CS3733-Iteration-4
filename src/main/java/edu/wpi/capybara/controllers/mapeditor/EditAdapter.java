package edu.wpi.capybara.controllers.mapeditor;

import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;

public class EditAdapter<S, T> implements EventHandler<TableColumn.CellEditEvent<S, T>> {

  @Override
  public void handle(TableColumn.CellEditEvent<S, T> event) {}
}
