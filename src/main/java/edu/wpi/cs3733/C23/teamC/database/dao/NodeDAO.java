package edu.wpi.cs3733.C23.teamC.database.dao;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.NodeEntity;
import edu.wpi.cs3733.C23.teamC.objects.orm.DAOFacade;

public class NodeDAO extends MapDAOBase<String, NodeEntity> {
  public NodeDAO(DAOFacade orm) {
    super(orm, NodeEntity.class, NodeEntity::getNodeID);
  }
}
