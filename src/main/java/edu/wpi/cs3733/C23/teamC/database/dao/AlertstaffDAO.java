package edu.wpi.cs3733.C23.teamC.database.dao;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.AlertstaffEntity;
import edu.wpi.cs3733.C23.teamC.objects.orm.DAOFacade;

public class AlertstaffDAO extends MapDAOBase<Integer, AlertstaffEntity> {
  public AlertstaffDAO(DAOFacade orm) {
    super(orm, AlertstaffEntity.class, AlertstaffEntity::getId);
  }
}
