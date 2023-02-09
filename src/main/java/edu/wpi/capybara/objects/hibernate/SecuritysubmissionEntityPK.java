package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.objects.submissions.submissionStatus;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SecuritysubmissionEntityPK implements Serializable {
  @Id
  @Column(name = "employeeid")
  private String employeeid;

  @Id
  @Column(name = "location")
  private String location;

  @Id
  @Column(name = "type")
  private String type;

  @Id
  @Column(name = "notesupdate")
  private String notesupdate;

  @Column(name = "submissionstatus")
  @Enumerated(EnumType.STRING)
  @Id
  // @GeneratedValue(strategy = GenerationType.IDENTITY)
  private submissionStatus submissionstatus;

  public String getEmployeeid() {
    return employeeid;
  }

  public void setEmployeeid(String employeeid) {
    //    Session session = DatabaseConnect.getSession();
    //    Transaction tx = session.beginTransaction();
    this.employeeid = employeeid;
    //    session.merge(this);
    //    tx.commit();
    //    session.close();
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
    //    Session session = DatabaseConnect.getSession();
    //    Transaction tx = session.beginTransaction();
    //    session.merge(this);
    //    tx.commit();
    //    session.close();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
    //    Session session = DatabaseConnect.getSession();
    //    Transaction tx = session.beginTransaction();
    //    session.merge(this);
    //    tx.commit();
    //    session.close();
  }

  public String getNotesupdate() {
    return notesupdate;
  }

  public SecuritysubmissionEntityPK() {}

  public void setNotesupdate(String notesupdate) {
    this.notesupdate = notesupdate;
    //    Session session = DatabaseConnect.getSession();
    //    Transaction tx = session.beginTransaction();
    //    session.merge(this);
    //    tx.commit();
    //    session.close();
  }

  public submissionStatus getSubmissionstatus() {
    return submissionstatus;
  }

  public void setSubmissionstatus(submissionStatus submissionstatus) {
    this.submissionstatus = submissionstatus;
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
    SecuritysubmissionEntityPK that = (SecuritysubmissionEntityPK) o;
    return Objects.equals(employeeid, that.employeeid)
        && Objects.equals(location, that.location)
        && Objects.equals(type, that.type)
        && Objects.equals(submissionstatus, that.submissionstatus);
  }

  public SecuritysubmissionEntityPK(
      String employeeid,
      String location,
      String type,
      String notesupdate,
      submissionStatus submissionstatus) {
    this.employeeid = employeeid;
    this.location = location;
    this.type = type;
    this.notesupdate = notesupdate;
    this.submissionstatus = submissionstatus;
  }

  @Override
  public int hashCode() {
    return Objects.hash(employeeid, location, location, type);
  }
}
