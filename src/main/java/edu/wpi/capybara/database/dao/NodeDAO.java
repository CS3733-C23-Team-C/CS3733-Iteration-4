package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.orm.DAOFacade;
import edu.wpi.capybara.objects.orm.Node;

public class NodeDAO extends MapDAOBase<String, Node> {
  public NodeDAO(DAOFacade orm) {
    super(orm, Node.class, Node::getId);
  }
}
