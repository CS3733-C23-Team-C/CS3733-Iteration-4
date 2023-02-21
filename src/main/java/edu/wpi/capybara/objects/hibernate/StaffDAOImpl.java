package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.database.newDBConnect;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import edu.wpi.capybara.objects.orm.StaffEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class StaffDAOImpl implements StaffDAO {
  HashMap<String, StaffEntity> staff = new HashMap();

  int curID;

  @Override
  public HashMap<String, StaffEntity> getStaff() {
    return staff;
  }

  @Override
  public StaffEntity getStaff(String nodeid) {
    return staff.get(nodeid);
  }

  @Override
  public void addStaff(StaffEntity submission) {
    newDBConnect.insertNew(submission);
    this.staff.put(submission.getStaffid(), submission);
  }

  public StaffDAOImpl() {
    Session session = Main.db.getSession();
    Transaction tx = null;

    HashMap<String, StaffEntity> ret = new HashMap<>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM StaffEntity").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        StaffEntity temp = (StaffEntity) iterator.next();
        ret.put(temp.getStaffid(), temp);
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    staff = ret;
  }

  @Override
  public void deleteStaff(String id) {
    newDBConnect.delete(getStaff(id));
    staff.remove(id);
  }

  public StaffEntity getStaff(String Staffid, String password) {
    for (StaffEntity s : staff.values()) {
      if (s.getStaffid().equals(Staffid) && s.getPassword().equals(password)) {
        return s;
      }
    }
    return null;
  }

  public StaffEntity getStaff2(String firstName, String lastName) {
    for (StaffEntity s : staff.values()) {
      if (s.getFirstname().equals(firstName) && s.getLastname().equals(lastName)) {
        return s;
      }
    }
    return null;
  }
}
