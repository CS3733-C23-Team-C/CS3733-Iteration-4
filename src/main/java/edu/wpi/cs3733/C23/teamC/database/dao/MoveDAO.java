package edu.wpi.cs3733.C23.teamC.database.dao;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.MoveEntity;
import edu.wpi.cs3733.C23.teamC.objects.orm.DAOFacade;

public class MoveDAO extends ListDAOBase<MoveEntity> {
  public MoveDAO(DAOFacade orm) {
    super(orm, MoveEntity.class);
  }
}
