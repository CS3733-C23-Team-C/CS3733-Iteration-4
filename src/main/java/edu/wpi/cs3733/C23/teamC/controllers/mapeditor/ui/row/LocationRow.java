package edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.row;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.LocationnameEntity;
import javafx.scene.control.TableView;

public class LocationRow extends RowBase<LocationnameEntity> {
  public LocationRow(TableView<LocationnameEntity> tableView, LocationnameEntity entity) {
    super(tableView, entity);
  }
}
