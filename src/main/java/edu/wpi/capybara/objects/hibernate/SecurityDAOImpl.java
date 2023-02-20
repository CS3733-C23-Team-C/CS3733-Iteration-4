package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.database.newDBConnect;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import edu.wpi.capybara.objects.orm.SecuritySubmission;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SecurityDAOImpl implements SecurityDAO {
  HashMap<Integer, SecuritySubmission> securitySubs = new HashMap();

  @Override
  public HashMap<Integer, SecuritySubmission> getSecuritySubs() {
    return securitySubs;
  }

  @Override
  public SecuritySubmission getSecurity(int id) {
    return securitySubs.get(id);
  }

  @Override
  public void addSecurity(SecuritySubmission submission) {
    newDBConnect.insertNew(submission);
    this.securitySubs.put(submission.getSubmissionid(), submission);
  }

  public SecurityDAOImpl() {
    Session session = Main.db.getSession();
    Transaction tx = null;

    HashMap<Integer, SecuritySubmission> ret =
        new HashMap<>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM SecuritysubmissionEntity").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        SecuritySubmission temp = (SecuritysubmissionEntity) iterator.next();
        ret.put(temp.getSubmissionid(), temp);
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    securitySubs = ret;
  }

  @Override
  public void deleteSecurity(int id) {
    newDBConnect.delete(getSecurity(id));
    securitySubs.remove(id);
  }
}
