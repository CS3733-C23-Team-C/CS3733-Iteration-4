package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.orm.DAOFacade;
import edu.wpi.capybara.objects.orm.MoveEntity;

public class MoveDAO extends ListDAOBase<MoveEntity> {
  public MoveDAO(DAOFacade orm) {
    super(orm, MoveEntity.class);
  }
}
