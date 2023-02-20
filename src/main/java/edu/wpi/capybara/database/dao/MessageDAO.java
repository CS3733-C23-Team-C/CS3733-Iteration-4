package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.hibernate.MessagesEntity;
import edu.wpi.capybara.objects.orm.DAOFacade;
import edu.wpi.capybara.objects.orm.Messages;

import java.util.UUID;

public class MessageDAO extends MapDAOBase<UUID, Messages> {
    public MessageDAO(DAOFacade orm) {
        super(orm, Messages.class, Messages::getMessageID);
    }
}
