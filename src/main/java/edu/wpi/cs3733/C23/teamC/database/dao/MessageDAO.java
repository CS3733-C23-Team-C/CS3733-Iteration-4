package edu.wpi.cs3733.C23.teamC.database.dao;

import edu.wpi.cs3733.C23.teamC.database.hibernate.MessagesEntity;
import edu.wpi.cs3733.C23.teamC.database.orm.DAOFacade;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class MessageDAO extends MapDAOBase<Integer, MessagesEntity> {
  public MessageDAO(DAOFacade orm) {
    super(orm, MessagesEntity.class, MessagesEntity::getMessageid);
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

  public HashMap<Integer, MessagesEntity> getMessages(String staff) {
    final var map = new HashMap<Integer, MessagesEntity>();
    getAll().entrySet().stream()
        .filter(entry -> entry.getValue().getReceivingid().equals(staff))
        .sorted(Comparator.comparing(a -> a.getValue().getDate()))
        .forEach(entry -> map.put(entry.getKey(), entry.getValue()));
    return map;
  }

  public HashMap<Integer, MessagesEntity> getMessages(String staff, int lastid) {
    final var map = new HashMap<Integer, MessagesEntity>();
    getAll().entrySet().stream()
        .filter(
            entry ->
                entry.getValue().getReceivingid().equals(staff)
                    && entry.getValue().getMessageid() > lastid)
        .sorted(Comparator.comparing(a -> a.getValue().getDate()))
        .forEach(entry -> map.put(entry.getKey(), entry.getValue()));
    return map;
  }
}
