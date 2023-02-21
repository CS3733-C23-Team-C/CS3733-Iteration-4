package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.database.CSVExportable;
import edu.wpi.capybara.database.CSVImporter;
import edu.wpi.capybara.objects.submissions.SubmissionStatus;
import jakarta.persistence.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Entity
@Table(name = "cleaningsubmission", schema = "cdb", catalog = "teamcdb")
public class CleaningsubmissionEntity implements CSVExportable {
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

    Session session = Main.db.getSession();
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
    Session session = Main.db.getSession();
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
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getDescription() {
    return description;
  }

  public CleaningsubmissionEntity(
      int submissionid,
      String memberid,
      String assignedid,
      String location,
      String hazardlevel,
      String description,
      SubmissionStatus submissionstatus,
      String urgency,
      Date createdate,
      Date duedate) {
    this.submissionid = submissionid;
    this.memberid = memberid;
    this.assignedid = assignedid;
    this.location = location;
    this.hazardlevel = hazardlevel;
    this.description = description;
    this.submissionstatus = submissionstatus;
    this.urgency = urgency;
    this.createdate = createdate;
    this.duedate = duedate;
  }

  public CleaningsubmissionEntity() {}

  public void setDescription(String description) {

    this.description = description;
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
  public int hashCode() {
    return Objects.hash(memberid, location, hazardlevel, description, submissionstatus);
  }

  @Override
  public String[] toCSV() {
    return new String[] {
      Integer.toString(getSubmissionid()),
      getMemberid(),
      getAssignedid(),
      getLocation(),
      getHazardlevel(),
      getDescription(),
      getSubmissionstatus().toString(),
      getUrgency(),
      getCreatedate().toString(),
      getDuedate().toString()
    };
  }

  public static class Importer implements CSVImporter<CleaningsubmissionEntity> {
    @Override
    public CleaningsubmissionEntity fromCSV(String[] csv) {
      int submissionid = Integer.parseInt(csv[0]);
      String employeeid = csv[1];
      String assignedid = csv[2];
      String location = csv[3];
      String hazardlevel = csv[4];
      String description = csv[5];
      SubmissionStatus submissionstatus = SubmissionStatus.valueOf(csv[6]);
      String urgency = csv[7];

      java.sql.Date createdate;
      java.sql.Date duedate;
      try {
        String startDate = csv[8];
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = sdf1.parse(startDate);
        createdate = new Date(date.getTime());

        String startDate2 = csv[9];
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date2 = sdf2.parse(startDate2);
        duedate = new Date(date2.getTime());
      } catch (ParseException e) {
        throw new IllegalArgumentException(e);
      }

      return new CleaningsubmissionEntity(
          submissionid,
          employeeid,
          assignedid,
          location,
          hazardlevel,
          description,
          submissionstatus,
          urgency,
          createdate,
          duedate);
    }
  }
}
