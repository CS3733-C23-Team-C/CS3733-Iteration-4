package edu.wpi.capybara.controllers.mapeditor.ui.elements;

import edu.wpi.capybara.controllers.mapeditor.ui.UIModel;
import edu.wpi.capybara.controllers.mapeditor.ui.gfx.LocationGFX;
import edu.wpi.capybara.controllers.mapeditor.ui.row.LocationRow;
import edu.wpi.capybara.objects.hibernate.LocationnameEntity;

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
