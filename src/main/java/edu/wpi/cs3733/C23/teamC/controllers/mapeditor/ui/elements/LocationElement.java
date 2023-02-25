package edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.elements;

import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.UIModel;
import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.gfx.LocationGFX;
import edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.row.LocationRow;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.LocationnameEntity;

public final class LocationElement extends ElementBase<LocationGFX, LocationRow, LocationnameEntity>
    implements Element {
  public LocationElement(
      UIModel model, LocationGFX onMap, LocationRow inTable, LocationnameEntity inRepo) {
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
