package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.orm.LocationnameEntity;
import edu.wpi.capybara.objects.orm.DAOFacade;

public class LocationDAO extends MapDAOBase<String, LocationnameEntity> {
  public LocationDAO(DAOFacade orm) {
    super(orm, LocationnameEntity.class, LocationnameEntity::getLongname);
  }
}
