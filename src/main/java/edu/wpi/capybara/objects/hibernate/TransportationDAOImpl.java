package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.database.newDBConnect;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import edu.wpi.capybara.objects.orm.TransportationSubmission;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TransportationDAOImpl implements TransportationDAO {
  HashMap<Integer, TransportationSubmission> transportationSubs = new HashMap();

  @Override
  public HashMap<Integer, TransportationSubmission> getTransportationSubs() {
    return transportationSubs;
  }

  @Override
  public TransportationSubmission getTransportation(int id) {
    return transportationSubs.get(id);
  }

  @Override
  public void addTransportation(TransportationSubmission submission) {
    newDBConnect.insertNew(submission);
    this.transportationSubs.put(submission.getSubmissionid(), submission);
  }

  public TransportationDAOImpl() {
    Session session = Main.db.getSession();
    Transaction tx = null;

    HashMap<Integer, TransportationSubmission> ret =
        new HashMap<>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM TransportationsubmissionEntity").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        TransportationSubmission temp = (TransportationsubmissionEntity) iterator.next();
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
