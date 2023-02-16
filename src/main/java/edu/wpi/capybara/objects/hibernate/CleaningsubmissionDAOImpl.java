package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.Main;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CleaningsubmissionDAOImpl implements CleaningsubmissionDAO {
  HashMap<Integer, CleaningsubmissionEntity> cleaningSubs = new HashMap();

  @Override
  public HashMap<Integer, CleaningsubmissionEntity> getCleaningSubs() {
    Session session = Main.db.getSession();
    Transaction tx = null;

    HashMap<Integer, CleaningsubmissionEntity> ret =
        new HashMap<Integer, CleaningsubmissionEntity>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM CleaningsubmissionEntity").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        CleaningsubmissionEntity temp = (CleaningsubmissionEntity) iterator.next();
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
  public CleaningsubmissionEntity getCleaning(int id) {
    return cleaningSubs.get(id);
  }

  @Override
  public void addCleaning(CleaningsubmissionEntity submission) {
    newDBConnect.insertNew(submission);
    this.cleaningSubs.put(submission.getSubmissionid(), submission);
  }

  @Override
  public void deleteCleaning(int id) {
    newDBConnect.delete(getCleaning(id));
    cleaningSubs.remove(id);
  }

  public CleaningsubmissionDAOImpl(HashMap<Integer, CleaningsubmissionEntity> cleaningSubs) {
    this.cleaningSubs = cleaningSubs;
  }
}
