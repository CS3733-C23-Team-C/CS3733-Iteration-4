package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.hibernate.MoveEntity;
import edu.wpi.capybara.objects.orm.DAOFacade;

public class MoveDAO extends ListDAOBase<MoveEntity> {
  public MoveDAO(DAOFacade orm) {
    super(orm, MoveEntity.class);
  }
}
