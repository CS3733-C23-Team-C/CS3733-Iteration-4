package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.objects.submissions.submissionStatus;
import jakarta.persistence.*;
import java.sql.Date;
import java.util.Objects;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Entity
@Table(name = "audiosubmission", schema = "cdb", catalog = "teamcdb")
public class AudiosubmissionEntity {
  @Id
  @Column(name = "submissionid")
  private int submissionid;

  @Column(name = "employeeid")
  private String employeeid;

  @Column(name = "assignedid")
  private String assignedid;

  @Column(name = "location")
  private String location;

  @Column(name = "type")
  private String type;

  @Column(name = "notesupdate")
  private String notesupdate;

  @Enumerated(EnumType.STRING)
  @Column(name = "submissionstatus")
  private submissionStatus submissionstatus;

  @Column(name = "urgency")
  private String urgency;

  @Column(name = "createdate")
  private Date createdate;

  @Column(name = "duedate")
  private Date duedate;

  public int getSubmissionid() {
    return submissionid;
  }

  public void setSubmissionid(int submissionid) {
    this.submissionid = submissionid;
    Session session = DatabaseConnect.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getEmployeeid() {
    return employeeid;
  }

  public void setEmployeeid(String employeeid) {
    this.employeeid = employeeid;
    Session session = DatabaseConnect.getSession();
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
    Session session = DatabaseConnect.getSession();
    Transaction tx = session.beginTransaction();
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

  public void setNotesupdate(String notesupdate) {
    this.notesupdate = notesupdate;
    Session session = DatabaseConnect.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
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

  public String getUrgency() {
    return urgency;
  }

  public void setUrgency(String urgency) {
    this.urgency = urgency;
    Session session = DatabaseConnect.getSession();
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
    Session session = DatabaseConnect.getSession();
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
    Session session = DatabaseConnect.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public AudiosubmissionEntity() {}

  public AudiosubmissionEntity(
      int submissionid,
      String employeeid,
      String assignedid,
      String location,
      String type,
      String notesupdate,
      submissionStatus submissionstatus,
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AudiosubmissionEntity that = (AudiosubmissionEntity) o;
    return Objects.equals(submissionid, that.submissionid)
        && Objects.equals(employeeid, that.employeeid)
        && Objects.equals(assignedid, that.assignedid)
        && Objects.equals(location, that.location)
        && Objects.equals(type, that.type)
        && Objects.equals(notesupdate, that.notesupdate)
        && Objects.equals(submissionstatus, that.submissionstatus)
        && Objects.equals(urgency, that.urgency)
        && Objects.equals(createdate, that.createdate)
        && Objects.equals(duedate, that.duedate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        submissionid,
        employeeid,
        assignedid,
        location,
        type,
        notesupdate,
        submissionstatus,
        urgency,
        createdate,
        duedate);
  }
}
