package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.orm.DAOFacade;
import edu.wpi.capybara.objects.orm.LocationnameEntity;

public class LocationDAO extends MapDAOBase<String, LocationnameEntity> {
  public LocationDAO(DAOFacade orm) {
    super(orm, LocationnameEntity.class, LocationnameEntity::getLongname);
  }
}
