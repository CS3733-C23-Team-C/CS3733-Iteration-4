package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.database.newDBConnect;
import edu.wpi.capybara.objects.orm.ComputersubmissionEntity;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ComputersubmissionDAOImpl implements ComputersubmissionDAO {
  HashMap<Integer, ComputersubmissionEntity> computerSubs = new HashMap();

  @Override
  public HashMap<Integer, ComputersubmissionEntity> getComputerSubs() {
    return computerSubs;
  }

  @Override
  public ComputersubmissionEntity getComputer(int id) {
    return computerSubs.get(id);
  }

  @Override
  public void addComputer(ComputersubmissionEntity submission) {
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

    HashMap<Integer, ComputersubmissionEntity> ret = new HashMap<>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM ComputersubmissionEntity").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        ComputersubmissionEntity temp = (ComputersubmissionEntity) iterator.next();
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
