package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.orm.DAOFacade;
import edu.wpi.capybara.objects.orm.Move;

public class MoveDAO extends ListDAOBase<Move> {
  public MoveDAO(DAOFacade orm) {
    super(orm, Move.class);
  }
}
