package edu.wpi.cs3733.C23.teamC.database.dao;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.EdgeEntity;
import edu.wpi.cs3733.C23.teamC.objects.orm.DAOFacade;

public class EdgeDAO extends ListDAOBase<EdgeEntity> {
  public EdgeDAO(DAOFacade orm) {
    super(orm, EdgeEntity.class);
  }
}
