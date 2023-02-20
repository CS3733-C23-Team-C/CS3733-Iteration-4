package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.orm.DAOFacade;
import edu.wpi.capybara.objects.orm.Messages;

public class MessageDAO extends MapDAOBase<Integer, Messages> {
  public MessageDAO(DAOFacade orm) {
    super(orm, Messages.class, Messages::getMessageID);
  }
}
