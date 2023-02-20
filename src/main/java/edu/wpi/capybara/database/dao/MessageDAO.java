package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.objects.orm.DAOFacade;
import edu.wpi.capybara.objects.orm.Messages;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class MessageDAO extends MapDAOBase<Integer, Messages> {
  public MessageDAO(DAOFacade orm) {
    super(orm, Messages.class, Messages::getMessageID);
  }

  public int generateMessageID() {
    Session session = orm.getSession();
    Transaction tx = null;

    int id = 0;

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("SELECT MAX(messageid) FROM MessagesEntity").list();
      if (n != null) {
        id = (int) n.get(0);
        id++;
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    return id;
  }
}
