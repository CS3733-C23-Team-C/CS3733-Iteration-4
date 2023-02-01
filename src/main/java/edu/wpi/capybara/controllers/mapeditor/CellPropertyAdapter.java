package edu.wpi.capybara.controllers.mapeditor;

import java.util.function.Function;
import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class CellPropertyAdapter<S, T>
    implements Callback<TableColumn.CellDataFeatures<S, T>, ObservableValue<T>>,
        EventHandler<TableColumn.CellEditEvent<S, T>> {

  private final Function<S, ? extends Property<T>> propertyGetter;

  public CellPropertyAdapter(Function<S, ? extends Property<T>> propertyGetter) {
    this.propertyGetter = propertyGetter;
  }

  @Override
  public ObservableValue<T> call(TableColumn.CellDataFeatures<S, T> param) {
    return propertyGetter.apply(param.getValue());
  }

  @Override
  public void handle(TableColumn.CellEditEvent<S, T> event) {
    var property = propertyGetter.apply(event.getRowValue());
    property.setValue(event.getNewValue());
  }
}
