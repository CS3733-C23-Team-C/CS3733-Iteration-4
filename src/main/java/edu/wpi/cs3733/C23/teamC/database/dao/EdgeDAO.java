package edu.wpi.cs3733.C23.teamC.database.dao;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.EdgeEntity;
import edu.wpi.cs3733.C23.teamC.objects.orm.DAOFacade;

public final class EdgeDAO extends ListDAOBase<EdgeEntity> {
  private static EdgeDAO instance;

  public static EdgeDAO initialize(DAOFacade orm) {
    if (instance == null) instance = new EdgeDAO(orm);
    return instance;
  }

  private EdgeDAO(DAOFacade orm) {
    super(orm, EdgeEntity.class);
  }
}
