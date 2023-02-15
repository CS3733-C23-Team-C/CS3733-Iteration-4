package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.Main;
import jakarta.persistence.*;
import java.util.Objects;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Entity
@Table(name = "staff", schema = "cdb", catalog = "teamcdb")
public class StaffEntity {
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
      String staffid,
      String firstname,
      String lastname,
      String password,
      String role,
      String notes) {
    this.staffid = staffid;
    this.firstname = firstname;
    this.lastname = lastname;
    this.password = password;
    this.role = role;
    this.notes = notes;
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
}
