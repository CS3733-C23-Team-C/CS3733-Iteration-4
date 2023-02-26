package edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.elements;

import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.UIModel;
import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.gfx.MoveGFX;
import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.row.MoveRow;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.MoveEntity;

public final class MoveElement extends ElementBase<MoveGFX, MoveRow, MoveEntity>
    implements Element {
  public MoveElement(UIModel model, MoveGFX onMap, MoveRow inTable, MoveEntity inRepo) {
    super(model, onMap, inTable, inRepo);

    //    inTable
    //        .selectedProperty()
    //        .addListener(
    //            (observable, oldValue, newValue) -> {
    //              if (newValue) {
    //                model.select(this);
    //              } else {
    //                model.deselect(this);
    //              }
    //            });
  }
}
