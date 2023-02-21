package edu.wpi.capybara.controllers.mapeditor.ui.row;

import edu.wpi.capybara.controllers.mapeditor.ui.Selectable;
import javafx.scene.control.TableRow;

class RowBase<T> extends TableRow<T> implements Selectable {
  public RowBase() {}

  @Override
  public void showAsSelected() {}

  @Override
  public void showAsDeselected() {}
}
