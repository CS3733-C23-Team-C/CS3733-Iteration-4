package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.database.newDBConnect;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class MessagesDAOImpl implements MessagesDAO {
  HashMap<Integer, MessagesEntity> messages = new HashMap();

  @Override
  public HashMap<Integer, MessagesEntity> getMessages() {
    return messages;
  }

  @Override
  public HashMap<Integer, MessagesEntity> getMessages(String staff) {
    Session session = Main.db.getSession();
    Transaction tx = null;

    HashMap<Integer, MessagesEntity> ret = new HashMap<>();

    try {
      tx = session.beginTransaction();
      Query query =
          session.createQuery(
              "FROM MessagesEntity WHERE receivingid = :staff " + "ORDER BY date DESC");
      query.setParameter("staff", staff);
      List n = query.list();
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
    return ret;
  }

  public HashMap<Integer, MessagesEntity> getMessages(String staff, int lastid) {
    Session session = Main.db.getSession();
    Transaction tx = null;

    HashMap<Integer, MessagesEntity> ret = new HashMap<>();

    try {
      tx = session.beginTransaction();
      Query query =
          session.createQuery(
              "FROM MessagesEntity WHERE receivingid = :staff AND messageid > :lastid "
                  + "ORDER BY date DESC");
      query.setParameter("staff", staff);
      query.setParameter("lastid", lastid);

      List n = query.list();
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
    return ret;
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

    HashMap<Integer, MessagesEntity> ret = new HashMap<>();

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
