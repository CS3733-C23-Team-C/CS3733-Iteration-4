package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.hibernate.MessagesEntity;
import edu.wpi.capybara.objects.orm.DAOFacade;

public class MessageDAO extends MapDAOBase<Integer, MessagesEntity> {
    public MessageDAO(DAOFacade orm) {
        super(orm, MessagesEntity.class, MessagesEntity::getMessageid);
    }
}
