package edu.wpi.cs3733.C23.teamC.database.dao;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.AlertEntity;
import edu.wpi.cs3733.C23.teamC.objects.orm.DAOFacade;

public class AlertDAO extends MapDAOBase<Integer, AlertEntity> {
  public AlertDAO(DAOFacade orm) {
    super(orm, AlertEntity.class, AlertEntity::getAlertid);
  }
}
