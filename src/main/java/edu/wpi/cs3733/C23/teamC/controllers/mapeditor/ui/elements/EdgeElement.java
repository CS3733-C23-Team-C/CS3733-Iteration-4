package edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.elements;

import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.UIModel;
import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.gfx.EdgeGFX;
import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.row.EdgeRow;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.EdgeEntity;
import javafx.scene.input.MouseEvent;

public final class EdgeElement extends ElementBase<EdgeGFX, EdgeRow, EdgeEntity>
    implements Element {
  public EdgeElement(UIModel model, EdgeGFX onMap, EdgeRow inTable, EdgeEntity inRepo) {
    super(model, onMap, inTable, inRepo);

    // handle selection from the map
    onMap.addEventHandler(
        MouseEvent.MOUSE_PRESSED,
        event -> {
          // if (event.getButton() != MouseButton.PRIMARY) return;

          if (event.isShiftDown()) {
            model.select(this);
          } else {
            model.deselectAll();
            model.select(this);
          }

          event.consume();
        });

    // handle selection from the table
    /*inTable
    .selectedProperty()
    .addListener(
        (observable, oldValue, newValue) -> {
          if (newValue) {
            model.select(this);
          } else {
            model.deselect(this);
          }
        });*/
  }
}
