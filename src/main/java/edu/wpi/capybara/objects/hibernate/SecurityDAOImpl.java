package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.Main;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SecurityDAOImpl implements SecurityDAO {
  HashMap<Integer, SecuritysubmissionEntity> securitySubs = new HashMap();

  @Override
  public HashMap<Integer, SecuritysubmissionEntity> getSecuritySubs() {
    Session session = Main.db.getSession();
    Transaction tx = null;

    HashMap<Integer, SecuritysubmissionEntity> ret =
        new HashMap<Integer, SecuritysubmissionEntity>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM SecuritysubmissionEntity").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        SecuritysubmissionEntity temp = (SecuritysubmissionEntity) iterator.next();
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
  public SecuritysubmissionEntity getSecurity(int id) {
    return securitySubs.get(id);
  }

  @Override
  public void addSecurity(SecuritysubmissionEntity submission) {
    newDBConnect.insertNew(submission);
    this.securitySubs.put(submission.getSubmissionid(), submission);
  }

  public SecurityDAOImpl(HashMap<Integer, SecuritysubmissionEntity> securitySubs) {
    this.securitySubs = securitySubs;
  }

  @Override
  public void deleteSecurity(int id) {
    newDBConnect.delete(getSecurity(id));
    securitySubs.remove(id);
  }
}
