package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.database.CSVExportable;
import edu.wpi.capybara.database.CSVImporter;
import jakarta.persistence.*;
import java.util.Objects;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Entity
@Table(name = "staff", schema = "cdb", catalog = "teamcdb")
public class StaffEntity implements CSVExportable {
  @Id
  @Column(name = "staffid")
  private String staffid;

  @Basic
  @Column(name = "firstname")
  private String firstname;

  @Basic
  @Column(name = "lastname")
  private String lastname;

  @Basic
  @Column(name = "password")
  private String password;

  @Basic
  @Column(name = "role")
  private String role;

  @Basic
  @Column(name = "notes")
  private String notes;

  public StaffEntity() {}

  public StaffEntity(
      String staffid, String firstname, String lastname, String role, String password) {
    this.staffid = staffid;
    this.firstname = firstname;
    this.lastname = lastname;
    this.role = role;
    this.password = password;
  }

  public String getStaffid() {
    return staffid;
  }

  public void setStaffid(String staffid) {
    this.staffid = staffid;
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    StaffEntity that = (StaffEntity) o;
    return Objects.equals(staffid, that.staffid)
        && Objects.equals(firstname, that.firstname)
        && Objects.equals(lastname, that.lastname)
        && Objects.equals(password, that.password)
        && Objects.equals(notes, that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(staffid, firstname, lastname, password, notes);
  }

  public void delete() {
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.remove(this);
    tx.commit();
    session.close();
  }

  @Override
  public String[] toCSV() {
    return new String[] {
      getStaffid(), getFirstname(), getLastname(), getRole(), getPassword(), getNotes()
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
      // String notes = csv[5];

      return new StaffEntity(staffid, firstname, lastname, role, password);
    }
  }
}
