package edu.wpi.cs3733.C23.teamC.objects.hibernate;

import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.objects.SubmissionAbs;
import edu.wpi.cs3733.C23.teamC.objects.submissions.SubmissionStatus;
import jakarta.persistence.*;
import java.sql.Date;
import java.util.Objects;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Entity
@Table(name = "securitysubmission", schema = "cdb", catalog = "teamcdb")
public class SecuritysubmissionEntity2 extends SubmissionAbs {
  @Column(name = "employeeid")
  private String employeeid;

  @Column(name = "location")
  private String location;

  @Column(name = "type")
  private String type;

  @Column(name = "notesupdate")
  private String notesupdate;

  @Enumerated(EnumType.STRING)
  @Column(name = "submissionstatus")
  private SubmissionStatus submissionstatus;

  @Column(name = "assignedid")
  private String assignedid;

  @Id
  @Column(name = "submissionid")
  private int submissionid;

  @Column(name = "urgency")
  private String urgency;

  @Basic
  @Column(name = "createdate")
  private Date createdate;

  @Basic
  @Column(name = "duedate")
  private Date duedate;

  public SecuritysubmissionEntity2() {}

  public SecuritysubmissionEntity2(
      int submissionid,
      String employeeid,
      String assignedid,
      String location,
      String type,
      String notesupdate,
      SubmissionStatus submissionstatus,
      String urgency,
      Date createdate,
      Date duedate) {
    this.submissionid = submissionid;
    this.employeeid = employeeid;
    this.assignedid = assignedid;
    this.location = location;
    this.type = type;
    this.notesupdate = notesupdate;
    this.submissionstatus = submissionstatus;
    this.urgency = urgency;
    this.createdate = createdate;
    this.duedate = duedate;
  }

  public String getEmployeeid() {
    return employeeid;
  }

  public void setEmployeeid(String employeeid) {
    Session session = Main.db.getSession();
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
    Session session = Main.db.getSession();
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
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getNotesupdate() {
    return notesupdate;
  }

  public void setNotesupdate(String notesupdate) {
    this.notesupdate = notesupdate;
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public SubmissionStatus getSubmissionstatus() {
    return submissionstatus;
  }

  public void setSubmissionstatus(SubmissionStatus submissionstatus) {

    this.submissionstatus = submissionstatus;
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getAssignedid() {
    return assignedid;
  }

  public void setAssignedid(String assignedid) {
    this.assignedid = assignedid;
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public int getSubmissionid() {
    return submissionid;
  }

  public void setSubmissionid(int submissionid) {
    this.submissionid = submissionid;
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getUrgency() {
    return urgency;
  }

  public void setUrgency(String urgency) {
    this.urgency = urgency;
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public Date getCreatedate() {
    return createdate;
  }

  public void setCreatedate(Date createdate) {
    this.createdate = createdate;
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public Date getDuedate() {
    return duedate;
  }

  public void setDuedate(Date duedate) {
    this.duedate = duedate;
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
    SecuritysubmissionEntity2 that = (SecuritysubmissionEntity2) o;
    return Objects.equals(employeeid, that.employeeid)
        && Objects.equals(location, that.location)
        && Objects.equals(type, that.type)
        && Objects.equals(submissionstatus, that.submissionstatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(employeeid, location, location, type);
  }

  @Override
  public String submissionType() {
    return "Security";
  }
}
