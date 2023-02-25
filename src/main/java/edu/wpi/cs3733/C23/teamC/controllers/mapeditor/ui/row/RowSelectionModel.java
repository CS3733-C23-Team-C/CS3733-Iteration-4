package edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.row;

import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.UIModel;
import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.elements.Element;
import io.github.palexdev.materialfx.selection.base.IMultipleSelectionModel;
import java.util.List;
import javafx.beans.property.MapProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class RowSelectionModel<T extends Element> implements IMultipleSelectionModel<T> {
  private final UIModel model;
  private final ObservableList<T> items;

  public RowSelectionModel(UIModel model, ObservableList<T> items) {
    this.model = model;
    this.items = items;
  }

  @Override
  public void clearSelection() {
    model.deselectAll();
  }

  @Override
  public void deselectIndex(int index) {
    deselectItem(items.get(index));
  }

  @Override
  public void deselectItem(T item) {
    model.deselect(item);
  }

  @Override
  public void deselectIndexes(int... indexes) {
    for (int index : indexes) {
      deselectIndex(index);
    }
  }

  @SafeVarargs
  @Override
  public final void deselectItems(T... items) {
    for (T item : items) {
      deselectItem(item);
    }
  }

  @Override
  public void selectIndex(int index) {
    selectItem(items.get(index));
  }

  @Override
  public void selectItem(T item) {
    model.select(item);
  }

  @Override
  public void selectIndexes(List<Integer> indexes) {
    indexes.forEach(this::selectIndex);
  }

  @Override
  public void selectItems(List<T> items) {
    items.forEach(this::selectItem);
  }

  @Override
  public void expandSelection(int index) {}

  @Override
  public void replaceSelection(Integer... indexes) {}

  @SafeVarargs
  @Override
  public final void replaceSelection(T... items) {}

  @Override
  public ObservableMap<Integer, T> getSelection() {
    return null;
  }

  @Override
  public MapProperty<Integer, T> selectionProperty() {
    return null;
  }

  @Override
  public void setSelection(ObservableMap<Integer, T> newSelection) {}

  @Override
  public List<T> getSelectedValues() {
    return List.of();
  }

  @Override
  public boolean allowsMultipleSelection() {
    return true;
  }

  @Override
  public void setAllowsMultipleSelection(boolean allowsMultipleSelection) {}
}
