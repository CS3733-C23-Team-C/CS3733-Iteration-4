package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.Main;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class LocationnameDAOImpl implements LocationnameDAO {
  HashMap<String, LocationnameEntity> locationnames = new HashMap();

  @Override
  public HashMap<String, LocationnameEntity> getLocationnames() {
    Session session = Main.db.getSession();
    Transaction tx = null;

    HashMap<String, LocationnameEntity> ret = new HashMap<String, LocationnameEntity>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM LocationnameEntity ").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        LocationnameEntity temp = (LocationnameEntity) iterator.next();
        ret.put(temp.getLongname(), temp);
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
  public LocationnameEntity getLocationname(String longname) {
    return locationnames.get(longname);
  }

  @Override
  public void addLocationname(LocationnameEntity submission) {
    newDBConnect.insertNew(submission);
    this.locationnames.put(submission.getLongname(), submission);
  }

  @Override
  public void deleteLocationname(String longname) {
    newDBConnect.delete(getLocationname(longname));
    locationnames.remove(longname);
  }

  public LocationnameDAOImpl(HashMap<String, LocationnameEntity> locationnames) {
    this.locationnames = locationnames;
  }
}
