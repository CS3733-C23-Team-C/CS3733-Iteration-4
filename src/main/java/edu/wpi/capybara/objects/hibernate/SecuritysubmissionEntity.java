package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.database.DatabaseConnect;
import jakarta.persistence.*;
import java.util.Objects;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Entity
@Table(name = "securitysubmission", schema = "cdb", catalog = "teamcdb")
@IdClass(SecuritysubmissionEntityPK.class)
public class SecuritysubmissionEntity {
  @Id
  @Column(name = "employeeid")
  private String employeeid;

  @Id
  @Column(name = "location")
  private String location;

  @Id
  @Column(name = "type")
  private String type;

  public SecuritysubmissionEntity(
      String employeeid, String location, String type, String notesupdate) {
    this.employeeid = employeeid;
    this.location = location;
    this.type = type;
    this.notesupdate = notesupdate;
  }

  @Id
  @Column(name = "notesupdate")
  private String notesupdate;

  public String getEmployeeid() {
    return employeeid;
  }

  public void setEmployeeid(String employeeid) {
    Session session = DatabaseConnect.getSession();
    Transaction tx = session.beginTransaction();
    this.employeeid = employeeid;
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
    Session session = DatabaseConnect.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
    Session session = DatabaseConnect.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getNotesupdate() {
    return notesupdate;
  }

  public SecuritysubmissionEntity() {}

  public void setNotesupdate(String notesupdate) {
    this.notesupdate = notesupdate;
    Session session = DatabaseConnect.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SecuritysubmissionEntity that = (SecuritysubmissionEntity) o;
    return Objects.equals(employeeid, that.employeeid)
        && Objects.equals(location, that.location)
        && Objects.equals(type, that.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(employeeid, location, location, type);
  }
}