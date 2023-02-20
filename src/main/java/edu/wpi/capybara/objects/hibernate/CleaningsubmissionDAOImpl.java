package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.database.newDBConnect;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import edu.wpi.capybara.objects.orm.CleaningSubmission;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CleaningsubmissionDAOImpl implements CleaningsubmissionDAO {
  HashMap<Integer, CleaningSubmission> cleaningSubs = new HashMap();

  @Override
  public HashMap<Integer, CleaningSubmission> getCleaningSubs() {
    return cleaningSubs;
  }

  @Override
  public CleaningSubmission getCleaning(int id) {
    return cleaningSubs.get(id);
  }

  @Override
  public void addCleaning(CleaningSubmission submission) {
    newDBConnect.insertNew(submission);
    this.cleaningSubs.put(submission.getSubmissionid(), submission);
  }

  @Override
  public void deleteCleaning(int id) {
    newDBConnect.delete(getCleaning(id));
    cleaningSubs.remove(id);
  }

  public CleaningsubmissionDAOImpl() {
    Session session = Main.db.getSession();
    Transaction tx = null;

    HashMap<Integer, CleaningSubmission> ret =
        new HashMap<>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM CleaningsubmissionEntity").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        CleaningSubmission temp = (CleaningsubmissionEntity) iterator.next();
        ret.put(temp.getSubmissionid(), temp);
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    cleaningSubs = ret;
  }
}
