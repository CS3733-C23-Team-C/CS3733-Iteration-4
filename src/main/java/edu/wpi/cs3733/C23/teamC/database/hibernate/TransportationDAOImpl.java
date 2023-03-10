package edu.wpi.cs3733.C23.teamC.database.hibernate;

import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.database.newDBConnect;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TransportationDAOImpl implements TransportationDAO {
  HashMap<Integer, TransportationsubmissionEntity> transportationSubs = new HashMap();

  @Override
  public HashMap<Integer, TransportationsubmissionEntity> getTransportationSubs() {
    return transportationSubs;
  }

  @Override
  public TransportationsubmissionEntity getTransportation(int id) {
    return transportationSubs.get(id);
  }

  @Override
  public void addTransportation(TransportationsubmissionEntity submission) {
    newDBConnect.insertNew(submission);
    this.transportationSubs.put(submission.getSubmissionid(), submission);
  }

  public TransportationDAOImpl() {
    Session session = Main.db.getSession();
    Transaction tx = null;

    HashMap<Integer, TransportationsubmissionEntity> ret = new HashMap<>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM TransportationsubmissionEntity").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        TransportationsubmissionEntity temp = (TransportationsubmissionEntity) iterator.next();
        ret.put(temp.getSubmissionid(), temp);
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    transportationSubs = ret;
  }

  @Override
  public void deleteTransportation(int id) {
    newDBConnect.delete(getTransportation(id));
    transportationSubs.remove(id);
  }
}
