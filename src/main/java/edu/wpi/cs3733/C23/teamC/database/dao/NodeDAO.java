package edu.wpi.cs3733.C23.teamC.database.dao;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.NodeEntity;
import edu.wpi.cs3733.C23.teamC.objects.orm.DAOFacade;

public final class NodeDAO extends MapDAOBase<String, NodeEntity> {
  private static NodeDAO instance;

  public static NodeDAO initialize(DAOFacade orm) {
    if (instance == null) instance = new NodeDAO(orm);
    return instance;
  }

  private NodeDAO(DAOFacade orm) {
    super(orm, NodeEntity.class, NodeEntity::getNodeID);
  }
}
