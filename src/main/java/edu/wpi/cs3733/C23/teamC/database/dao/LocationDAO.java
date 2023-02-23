package edu.wpi.cs3733.C23.teamC.database.dao;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.LocationnameEntity;
import edu.wpi.cs3733.C23.teamC.objects.orm.DAOFacade;

public class LocationDAO extends MapDAOBase<String, LocationnameEntity> {
  public LocationDAO(DAOFacade orm) {
    super(orm, LocationnameEntity.class, LocationnameEntity::getLongname);
  }
}
