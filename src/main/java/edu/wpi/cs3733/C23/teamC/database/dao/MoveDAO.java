package edu.wpi.cs3733.C23.teamC.database.dao;

import edu.wpi.cs3733.C23.teamC.database.hibernate.MoveEntity;
import edu.wpi.cs3733.C23.teamC.database.orm.DAOFacade;

public final class MoveDAO extends ListDAOBase<MoveEntity> {
  private static MoveDAO instance;

  public static MoveDAO initialize(DAOFacade orm) {
    if (instance == null) instance = new MoveDAO(orm);
    return instance;
  }

  private MoveDAO(DAOFacade orm) {
    super(orm, MoveEntity.class);
  }
}
