package edu.wpi.cs3733.C23.teamC.database.hibernate;

import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.database.CSVExportable;
import edu.wpi.cs3733.C23.teamC.database.CSVImporter;
import edu.wpi.cs3733.C23.teamC.database.orm.DAOFacade;
import edu.wpi.cs3733.C23.teamC.database.orm.Persistent;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Entity
@Table(name = "staff", schema = "cdb", catalog = "teamcdb")
public class StaffEntity implements Persistent, CSVExportable {

  private final SimpleStringProperty staffid = new SimpleStringProperty();
  private final SimpleStringProperty firstname = new SimpleStringProperty();
  private final SimpleStringProperty lastname = new SimpleStringProperty();
  private final SimpleStringProperty password = new SimpleStringProperty();
  private final SimpleStringProperty role = new SimpleStringProperty();
  private final SimpleStringProperty notes = new SimpleStringProperty();
  private final SimpleIntegerProperty picid = new SimpleIntegerProperty();

  public StaffEntity() {}

  public StaffEntity(
      String staffid, String firstname, String lastname, String role, String password) {
    setStaffid(staffid);
    setFirstname(firstname);
    setLastname(lastname);
    setRole(role);
    setPassword(password);
  }

  public StaffEntity(
      String staffid,
      String firstname,
      String lastname,
      String role,
      String password,
      String notes,
      int picid) {
    setStaffid(staffid);
    setFirstname(firstname);
    setLastname(lastname);
    setRole(role);
    setPassword(password);
    setNotes(notes);
    setPicid(picid);
  }

  private InvalidationListener listener;

  @Override
  public void enablePersistence(DAOFacade orm) {
    listener = evt -> orm.mergeOnlyWhenManual(this);
    staffid.addListener(listener);
    firstname.addListener(listener);
    lastname.addListener(listener);
    password.addListener(listener);
    role.addListener(listener);
    notes.addListener(listener);
    picid.addListener(listener);
  }

  @Override
  public void disablePersistence() {
    if (listener != null) {
      staffid.removeListener(listener);
      firstname.removeListener(listener);
      lastname.removeListener(listener);
      password.removeListener(listener);
      role.removeListener(listener);
      notes.removeListener(listener);
      listener = null;
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    else if (obj == this) return true;
    else if (obj instanceof StaffEntity that) {
      return Persistent.compareProperties(
          this,
          that,
          StaffEntity::getStaffid,
          StaffEntity::getFirstname,
          StaffEntity::getLastname,
          StaffEntity::getPassword,
          StaffEntity::getRole,
          StaffEntity::getNotes,
          StaffEntity::getPicid);
    } else return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getStaffid(), getFirstname(), getLastname(), getPassword(), getRole(), getNotes());
  }

  @Id
  @Column(name = "staffid")
  public String getStaffid() {
    return staffid.get();
  }

  public SimpleStringProperty staffidProperty() {
    return staffid;
  }

  public void setStaffid(String staffid) {
    this.staffid.set(staffid);
  }

  @Column(name = "firstname")
  public String getFirstname() {
    return firstname.get();
  }

  public SimpleStringProperty firstnameProperty() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname.set(firstname);
  }

  @Column(name = "lastname")
  public String getLastname() {
    return lastname.get();
  }

  public SimpleStringProperty lastnameProperty() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname.set(lastname);
  }

  @Column(name = "password")
  public String getPassword() {
    return password.get();
  }

  public SimpleStringProperty passwordProperty() {
    return password;
  }

  public void setPassword(String password) {
    this.password.set(password);
  }

  @Column(name = "role")
  public String getRole() {
    return role.get();
  }

  public SimpleStringProperty roleProperty() {
    return role;
  }

  public void setRole(String role) {
    this.role.set(role);
  }

  @Column(name = "notes")
  public String getNotes() {
    return notes.get();
  }

  public SimpleStringProperty notesProperty() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes.set(notes);
  }

  @Column(name = "picid")
  public int getPicid() {
    return picid.get();
  }

  public SimpleIntegerProperty picidProperty() {
    return picid;
  }

  public void setPicid(int picid) {
    this.picid.set(picid);
  }

  public List<AlertEntity> allAlerts() {
    Session session = Main.db.getSession();
    Transaction tx = null;
    List<AlertEntity> ret = new ArrayList<>();

    try {
      tx = session.beginTransaction();
      Query q =
          session.createNativeQuery(
              "SELECT a.alertid"
                  + " FROM cdb.staff AS s, cdb.alertstaff AS sa, cdb.alerts AS a "
                  + "WHERE s.staffid = sa.staff AND sa.alert = a.alertid AND s.staffid = :id");
      q.setParameter("id", getStaffid());
      List l = q.list();
      for (Iterator iterator = l.iterator(); iterator.hasNext(); ) {
        ret.add(Main.db.getAlert((int) iterator.next()));
      }
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    return ret;
  }

  public List<AlertEntity> allNotReadAlerts() {
    Session session = Main.db.getSession();
    Transaction tx = null;
    List<AlertEntity> ret = new ArrayList<>();

    try {
      tx = session.beginTransaction();
      Query q =
          session.createNativeQuery(
              "SELECT a.alertid"
                  + " FROM cdb.staff AS s, cdb.alertstaff AS sa, cdb.alerts AS a "
                  + "WHERE s.staffid = sa.staff AND sa.alert = a.alertid AND s.staffid = :id AND sa.seen = FALSE");
      q.setParameter("id", getStaffid());
      List l = q.list();
      for (Iterator iterator = l.iterator(); iterator.hasNext(); ) {
        ret.add(Main.db.getAlert((int) iterator.next()));
      }
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    return ret;
  }

  @Override
  public String[] toCSV() {
    return new String[] {
      getStaffid(),
      getFirstname(),
      getLastname(),
      getRole(),
      getPassword(),
      getNotes(),
      Integer.toString(getPicid())
    };
  }

  public static class Importer implements CSVImporter<StaffEntity> {
    @Override
    public StaffEntity fromCSV(String[] csv) {

      String staffid = csv[0];
      String firstname = csv[1];
      String lastname = csv[2];
      String role = csv[3];
      String password = csv[4];
      String notes = csv[5];
      int picid = Integer.parseInt(csv[6]);

      return new StaffEntity(staffid, firstname, lastname, role, password, notes, picid);
    }
  }
}
