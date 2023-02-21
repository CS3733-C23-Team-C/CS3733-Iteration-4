package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.orm.NodeEntity;
import edu.wpi.capybara.objects.orm.DAOFacade;

public class NodeDAO extends MapDAOBase<String, NodeEntity> {
  public NodeDAO(DAOFacade orm) {
    super(orm, NodeEntity.class, NodeEntity::getNodeID);
  }
}
