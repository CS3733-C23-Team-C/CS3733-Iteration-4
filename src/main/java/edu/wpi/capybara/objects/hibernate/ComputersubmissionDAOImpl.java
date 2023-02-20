package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.database.newDBConnect;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import edu.wpi.capybara.objects.orm.ComputerSubmission;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ComputersubmissionDAOImpl implements ComputersubmissionDAO {
  HashMap<Integer, ComputerSubmission> computerSubs = new HashMap();

  @Override
  public HashMap<Integer, ComputerSubmission> getComputerSubs() {
    return computerSubs;
  }

  @Override
  public ComputerSubmission getComputer(int id) {
    return computerSubs.get(id);
  }

  @Override
  public void addComputer(ComputerSubmission submission) {
    newDBConnect.insertNew(submission);
    this.computerSubs.put(submission.getSubmissionid(), submission);
  }

  @Override
  public void deleteComputer(int id) {
    newDBConnect.delete(getComputer(id));
    computerSubs.remove(id);
  }

  public ComputersubmissionDAOImpl() {
    Session session = Main.db.getSession();
    Transaction tx = null;

    HashMap<Integer, ComputerSubmission> ret =
        new HashMap<>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM ComputersubmissionEntity").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        ComputerSubmission temp = (ComputersubmissionEntity) iterator.next();
        ret.put(temp.getSubmissionid(), temp);
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    computerSubs = ret;
  }
}
