package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.Main;
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
    Session session = Main.db.getSession();
    Transaction tx = null;

    HashMap<Integer, TransportationsubmissionEntity> ret =
        new HashMap<Integer, TransportationsubmissionEntity>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM TransportationsubmissionEntity").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        TransportationsubmissionEntity temp = (TransportationsubmissionEntity) iterator.next();
        ret.put(temp.getSubmissionid(), temp);
        if (Main.db.getID() < temp.getSubmissionid()) {
          Main.db.setID(temp.getSubmissionid());
        }
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
  public TransportationsubmissionEntity getTransportation(int id) {
    return transportationSubs.get(id);
  }

  @Override
  public void addTransportation(TransportationsubmissionEntity submission) {
    newDBConnect.insertNew(submission);
    this.transportationSubs.put(submission.getSubmissionid(), submission);
  }

  public TransportationDAOImpl(
      HashMap<Integer, TransportationsubmissionEntity> transportationSubs) {
    this.transportationSubs = transportationSubs;
  }

  @Override
  public void deleteTransportation(int id) {
    newDBConnect.delete(getTransportation(id));
    transportationSubs.remove(id);
  }
}
