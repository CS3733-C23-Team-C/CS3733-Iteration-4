package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.database.newDBConnect;
import edu.wpi.capybara.objects.orm.SecuritysubmissionEntity;
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
    return securitySubs;
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

  public SecurityDAOImpl() {
    Session session = Main.db.getSession();
    Transaction tx = null;

    HashMap<Integer, SecuritysubmissionEntity> ret = new HashMap<>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM SecuritysubmissionEntity").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        SecuritysubmissionEntity temp = (SecuritysubmissionEntity) iterator.next();
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
