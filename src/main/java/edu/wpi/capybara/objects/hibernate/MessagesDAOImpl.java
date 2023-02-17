package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.database.newDBConnect;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class MessagesDAOImpl implements MessagesDAO {
  HashMap<Integer, MessagesEntity> messages = new HashMap();

  @Override
  public HashMap<Integer, MessagesEntity> getMessages() {
    return messages;
  }

  @Override
  public MessagesEntity getMessage(int id) {
    return messages.get(id);
  }

  @Override
  public void addMessage(MessagesEntity message) {
    newDBConnect.insertNew(message);
    this.messages.put(message.getMessageid(), message);
  }

  @Override
  public void deleteMessage(int id) {
    newDBConnect.delete(getMessage(id));
    messages.remove(id);
  }

  @Override
  public int generateMessageID() {
    Session session = Main.db.getSession();
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

  public MessagesDAOImpl() {
    Session session = Main.db.getSession();
    Transaction tx = null;

    HashMap<Integer, MessagesEntity> ret = new HashMap<Integer, MessagesEntity>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM MessagesEntity").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        MessagesEntity temp = (MessagesEntity) iterator.next();
        ret.put(temp.getMessageid(), temp);
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    messages = ret;
  }
}
