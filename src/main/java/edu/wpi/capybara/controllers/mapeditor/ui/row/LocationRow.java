package edu.wpi.capybara.controllers.mapeditor.ui.row;

import edu.wpi.capybara.objects.hibernate.LocationnameEntity;
import javafx.scene.control.TableView;

public class LocationRow extends RowBase<LocationnameEntity> {
  public LocationRow(TableView<LocationnameEntity> tableView, LocationnameEntity entity) {
    super(tableView, entity);
  }
}
