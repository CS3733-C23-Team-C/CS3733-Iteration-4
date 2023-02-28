package edu.wpi.cs3733.C23.teamC.objects.hibernate;

import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.database.CSVExportable;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class AlertStaff implements CSVExportable {
  private String staff;
  private int alertid;
  private boolean seen;

  public AlertStaff(String staff, int alertid, boolean seen) {
    this.staff = staff;
    this.alertid = alertid;
    this.seen = seen;
  }

  public AlertStaff(String a, String b, String c) {
    staff = a;
    alertid = Integer.valueOf(b);
    seen = Boolean.valueOf(c);
  }

  //Create this with the CSV information
  public AlertStaff(String[] a){
    staff = a[0];
    alertid = Integer.parseInt(a[1]);
    seen = Boolean.parseBoolean(a[2]);

    Session session = Main.db.getSession();
    Transaction tx = null;

    try {
      tx = session.beginTransaction();
        Query q =
                session.createNativeQuery(
                        "INSERT INTO cdb.alertstaff "
                                + "(staff, alert, seen)"
                                + "VALUES (:staff, :alert, :seen)");
        q.setParameter("staff", staff);
        q.setParameter("alert", alertid);
        q.setParameter("seen", seen);
        q.executeUpdate();

    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
  }

  public String getStaff() {
    return staff;
  }

  public void setStaff(String staff) {
    this.staff = staff;
  }

  public int getAlertid() {
    return alertid;
  }

  public void setAlertid(int alertid) {
    this.alertid = alertid;
  }

  public boolean isSeen() {
    return seen;
  }

  public void setSeen(boolean seen) {
    this.seen = seen;
  }

  @Override
  public String toString() {
    return "AlertStaff{"
        + "staff='"
        + staff
        + '\''
        + ", alertid="
        + alertid
        + ", seen="
        + seen
        + '}';
  }

  @Override
  public String[] toCSV() {
    return new String[]{
            staff, Integer.toString(alertid), Boolean.toString(seen)
    };
  }
}
