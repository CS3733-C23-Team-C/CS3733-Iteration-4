package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.orm.DAOFacade;
import edu.wpi.capybara.objects.orm.NodeEntity;

public class NodeDAO extends MapDAOBase<String, NodeEntity> {
  public NodeDAO(DAOFacade orm) {
    super(orm, NodeEntity.class, NodeEntity::getNodeID);
  }
}
