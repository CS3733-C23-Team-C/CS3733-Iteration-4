package edu.wpi.cs3733.C23.teamC.database.hibernate;

import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.database.CSVExportable;
import edu.wpi.cs3733.C23.teamC.database.CSVImporter;
import edu.wpi.cs3733.C23.teamC.database.orm.DAOFacade;
import edu.wpi.cs3733.C23.teamC.database.orm.Persistent;
import jakarta.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Entity
@Table(name = "alerts", schema = "cdb", catalog = "teamcdb")
public class AlertEntity implements Persistent, CSVExportable {
  // private final SimpleObjectProperty<UUID> messageID = new SimpleObjectProperty<>();

  private final SimpleIntegerProperty alertid = new SimpleIntegerProperty();
  private final SimpleObjectProperty<Date> date = new SimpleObjectProperty<>();
  private final SimpleStringProperty message = new SimpleStringProperty();

  private InvalidationListener listener;

  @Override
  public void enablePersistence(DAOFacade orm) {
    listener = evt -> orm.mergeOnlyWhenManual(this);
    alertid.addListener(listener);
    date.addListener(listener);
    message.addListener(listener);
  }

  @Override
  public void disablePersistence() {
    if (listener != null) {
      alertid.removeListener(listener);
      date.removeListener(listener);
      message.removeListener(listener);
      listener = null;
    }
  }

  public AlertEntity() {}

  public AlertEntity(int alertid, Date date, String message) {
    setAlertid(alertid);
    setDate(date);
    setMessage(message);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    else if (obj == this) return true;
    else if (obj instanceof AlertEntity that) {
      return Persistent.compareProperties(
          this, that, AlertEntity::getAlertid, AlertEntity::getDate, AlertEntity::getMessage);
    } else return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getAlertid(), getDate(), getAlertid());
  }

  @Id
  @Column(name = "alertid")
  public int getAlertid() {
    return alertid.get();
  }

  public SimpleIntegerProperty alertidProperty() {
    return alertid;
  }

  public void setAlertid(int alertid) {
    this.alertid.set(alertid);
  }

  @Column(name = "alertdate")
  public Date getDate() {
    return date.get();
  }

  public SimpleObjectProperty<Date> dateProperty() {
    return date;
  }

  public void setDate(Date date) {
    this.date.set(date);
  }

  @Column(name = "message")
  public String getMessage() {
    return message.get();
  }

  public SimpleStringProperty messageProperty() {
    return message;
  }

  public void setMessage(String message) {
    this.message.set(message);
  }

  public List<StaffEntity> allStaff() {
    Session session = Main.db.getSession();
    Transaction tx = null;
    List<StaffEntity> ret = new ArrayList<>();

    try {
      tx = session.beginTransaction();
      Query q =
          session.createNativeQuery(
              "SELECT s.staffid"
                  + " FROM cdb.staff AS s, cdb.alertstaff AS sa, cdb.alerts AS a "
                  + "WHERE s.staffid = sa.staff AND sa.alert = a.alertid AND a.alertid = :id");
      q.setParameter("id", getAlertid());
      List l = q.list();
      for (Iterator iterator = l.iterator(); iterator.hasNext(); ) {
        ret.add(Main.db.getStaff(iterator.next().toString()));
      }
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    return ret;
  }

  public List<StaffEntity> allNotReadStaff() {
    Session session = Main.db.getSession();
    Transaction tx = null;
    List<StaffEntity> ret = new ArrayList<>();

    try {
      tx = session.beginTransaction();
      Query q =
          session.createNativeQuery(
              "SELECT s.staffid, s.firstname, s.lastname, s.role, s.password, s.notes, s.picid"
                  + " FROM cdb.staff AS s, cdb.alertstaff AS sa, cdb.alerts AS a "
                  + "WHERE s.staffid = sa.staff AND sa.alert = a.alertid AND a.alertid = :id AND sa.seen = FALSE");
      q.setParameter("id", getAlertid());
      List l = q.list();
      for (Iterator iterator = l.iterator(); iterator.hasNext(); ) {
        ret.add(Main.db.getStaff(iterator.next().toString()));
      }
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    return ret;
  }

  public void addStaff(StaffEntity staff) {
    Session session = Main.db.getSession();
    Transaction tx = null;

    try {
      tx = session.beginTransaction();
      Query q =
          session.createNativeQuery(
              "INSERT INTO cdb.alertstaff "
                  + "(staff, alert, seen)"
                  + "VALUES (:staff, :alert, FALSE)");
      q.setParameter("staff", staff.getStaffid());
      q.setParameter("alert", getAlertid());
      q.executeUpdate();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
  }

  public void addStaff(List<StaffEntity> staff) {
    Session session = Main.db.getSession();
    Transaction tx = null;

    try {
      tx = session.beginTransaction();
      for (StaffEntity s : staff) {
        Query q =
            session.createNativeQuery(
                "INSERT INTO cdb.alertstaff "
                    + "(staff, alert, seen)"
                    + "VALUES (:staff, :alert, FALSE)");
        q.setParameter("staff", s.getStaffid());
        q.setParameter("alert", getAlertid());
        q.executeUpdate();
      }
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
  }

  public void markRead(StaffEntity staff) {
    Session session = Main.db.getSession();
    Transaction tx = null;

    try {
      tx = session.beginTransaction();
      Query q =
          session.createNativeQuery(
              "UPDATE cdb.alertstaff "
                  + "SET seen = TRUE "
                  + "WHERE staff = :staff AND alert = :alert");
      q.setParameter("staff", staff.getStaffid());
      q.setParameter("alert", getAlertid());
      q.executeUpdate();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
  }

  @Override
  public String[] toCSV() {
    return new String[] {Integer.toString(getAlertid()), getMessage(), getDate().toString()};
  }

  public static class Importer implements CSVImporter<AlertEntity> {
    @Override
    public AlertEntity fromCSV(String[] csv) {

      int alertid = Integer.parseInt(csv[0]);
      String message = csv[1];
      java.sql.Date alertdate;

      try {
        String startDate = csv[2];
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = sdf1.parse(startDate);
        alertdate = new java.sql.Date(date.getTime());
      } catch (ParseException e) {
        throw new IllegalArgumentException(e);
      }

      // Constructor order is wrong
      return new AlertEntity(alertid, alertdate, message);
    }
  }
}
