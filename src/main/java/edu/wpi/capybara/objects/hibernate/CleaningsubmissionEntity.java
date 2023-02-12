package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.objects.submissions.SubmissionStatus;
import jakarta.persistence.*;
import java.sql.Date;
import java.util.Objects;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Entity
@Table(name = "cleaningsubmission", schema = "cdb", catalog = "teamcdb")
public class CleaningsubmissionEntity {
  // @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "memberid")
  private String memberid;

  // @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "location")
  private String location;

  // @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "hazardlevel")
  private String hazardlevel;

  // @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "description")
  private String description;

  // @GeneratedValue(strategy = GenerationType.IDENTITY)
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

  public String getMemberid() {
    return memberid;
  }

  public void setMemberid(String memberid) {

    Session session = DatabaseConnect.getSession();
    Transaction tx = session.beginTransaction();
    this.memberid = memberid;
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

  public String getHazardlevel() {
    return hazardlevel;
  }

  public void setHazardlevel(String hazardlevel) {

    this.hazardlevel = hazardlevel;
    Session session = DatabaseConnect.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getDescription() {
    return description;
  }

  public CleaningsubmissionEntity(
      String memberid,
      String location,
      String hazardlevel,
      String description,
      SubmissionStatus submissionstatus,
      String assignedid,
      int submissionid,
      String urgency,
      Date createdate,
      Date duedate) {
    this.memberid = memberid;
    this.location = location;
    this.hazardlevel = hazardlevel;
    this.description = description;
    this.submissionstatus = submissionstatus;
    this.assignedid = assignedid;
    this.submissionid = submissionid;
    this.urgency = urgency;
    this.createdate = createdate;
    this.duedate = duedate;
  }

  public CleaningsubmissionEntity() {}

  public void setDescription(String description) {

    this.description = description;
    Session session = DatabaseConnect.getSession();
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
    CleaningsubmissionEntity that = (CleaningsubmissionEntity) o;
    return Objects.equals(memberid, that.memberid)
        && Objects.equals(location, that.location)
        && Objects.equals(hazardlevel, that.hazardlevel)
        && Objects.equals(description, that.description)
        && Objects.equals(submissionstatus, that.submissionstatus);
  }

  public void create() {
    Session session = DatabaseConnect.getSession();
    Transaction tx = session.beginTransaction();
    session.persist(this);
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

  @Override
  public int hashCode() {
    return Objects.hash(memberid, location, hazardlevel, description, submissionstatus);
  }
}
