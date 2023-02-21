package edu.wpi.capybara.controllers.mapeditor.ui.elements;

import edu.wpi.capybara.controllers.mapeditor.ui.UIModel;
import edu.wpi.capybara.controllers.mapeditor.ui.gfx.MoveGFX;
import edu.wpi.capybara.controllers.mapeditor.ui.row.MoveRow;
import edu.wpi.capybara.objects.hibernate.MoveEntity;

public final class MoveElement extends ElementBase<MoveGFX, MoveRow, MoveEntity>
    implements Element {
  public MoveElement(UIModel model, MoveGFX onMap, MoveRow inTable, MoveEntity inRepo) {
    super(model, onMap, inTable, inRepo);

    inTable
        .selectedProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue) {
                model.select(this);
              } else {
                model.deselect(this);
              }
            });
  }
}
