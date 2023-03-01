package edu.wpi.cs3733.C23.teamC.database.dao;

import edu.wpi.cs3733.C23.teamC.database.hibernate.LocationnameEntity;
import edu.wpi.cs3733.C23.teamC.database.orm.DAOFacade;

public final class LocationDAO extends MapDAOBase<String, LocationnameEntity> {
  private static LocationDAO instance;

  public static LocationDAO initialize(DAOFacade orm) {
    if (instance == null) instance = new LocationDAO(orm);
    return instance;
  }

  private LocationDAO(DAOFacade orm) {
    super(orm, LocationnameEntity.class, LocationnameEntity::getLongname);
  }
}
