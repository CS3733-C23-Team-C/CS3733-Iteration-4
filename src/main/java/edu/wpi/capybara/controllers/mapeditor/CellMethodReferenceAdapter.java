package edu.wpi.capybara.controllers.mapeditor;

import java.util.function.BiConsumer;
import java.util.function.Function;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class CellMethodReferenceAdapter<S, T>
    implements Callback<TableColumn.CellDataFeatures<S, T>, ObservableValue<T>>,
        EventHandler<TableColumn.CellEditEvent<S, T>> {

  private final Function<S, T> getter;
  private final BiConsumer<S, T> setter;

  public CellMethodReferenceAdapter(Function<S, T> getter, BiConsumer<S, T> setter) {
    this.getter = getter;
    this.setter = setter;
  }

  @Override
  public ObservableValue<T> call(TableColumn.CellDataFeatures<S, T> param) {
    var instance = param.getValue();
    return new ReadOnlyObjectWrapper<>(getter.apply(instance));
  }

  @Override
  public void handle(TableColumn.CellEditEvent<S, T> event) {
    var instance = event.getRowValue();
    var newValue = event.getNewValue();
    setter.accept(instance, newValue);
  }
}
