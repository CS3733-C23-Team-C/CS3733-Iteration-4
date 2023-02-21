package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.hibernate.EdgeEntity;
import edu.wpi.capybara.objects.orm.DAOFacade;

public class EdgeDAO extends ListDAOBase<EdgeEntity> {
  public EdgeDAO(DAOFacade orm) {
    super(orm, EdgeEntity.class);
  }
}
