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
@Table(name = "transportationsubmission", schema = "cdb", catalog = "teamcdb")
public class TransportationsubmissionEntity implements CSVExportable {
  @Column(name = "employeeid")
  private String employeeid;

  @Column(name = "currroomnum")
  private String currroomnum;

  @Column(name = "destroomnum")
  private String destroomnum;

  @Column(name = "reason")
  private String reason;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private SubmissionStatus status;

  @Basic
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

  public TransportationsubmissionEntity(
      int submissionid,
      String employeeid,
      String assignedid,
      String currroomnum,
      String destroomnum,
      String reason,
      SubmissionStatus status,
      String urgency,
      Date createdate,
      Date duedate) {
    this.submissionid = submissionid;
    this.employeeid = employeeid;
    this.assignedid = assignedid;
    this.currroomnum = currroomnum;
    this.destroomnum = destroomnum;
    this.reason = reason;
    this.status = status;
    this.assignedid = assignedid;
    this.submissionid = submissionid;
    System.out.println(submissionid + " entity");
    this.urgency = urgency;
    this.createdate = createdate;
    this.duedate = duedate;
  }

  public TransportationsubmissionEntity() {}

  public String getEmployeeid() {
    return employeeid;
  }

  public void setEmployeeid(String employeeid) {
    this.employeeid = employeeid;
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getCurrroomnum() {
    return currroomnum;
  }

  public void setCurrroomnum(String currroomnum) {
    this.currroomnum = currroomnum;
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getDestroomnum() {
    return destroomnum;
  }

  public void setDestroomnum(String destroomnum) {
    this.destroomnum = destroomnum;
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public SubmissionStatus getStatus() {
    return status;
  }

  public void setStatus(SubmissionStatus status) {
    this.status = status;
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
    TransportationsubmissionEntity that = (TransportationsubmissionEntity) o;
    return Objects.equals(employeeid, that.employeeid)
        && Objects.equals(currroomnum, that.currroomnum)
        && Objects.equals(destroomnum, that.destroomnum)
        && Objects.equals(reason, that.reason)
        && Objects.equals(status, that.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(employeeid, currroomnum, destroomnum, reason, status);
  }

  @Override
  public String[] toCSV() {
    return new String[] {
      Integer.toString(getSubmissionid()),
      getEmployeeid(),
      getAssignedid(),
      getCurrroomnum(),
      getDestroomnum(),
      getReason(),
      getStatus().toString(),
      getUrgency(),
      getCreatedate().toString(),
      getDuedate().toString()
    };
  }

  public static class Importer implements CSVImporter<TransportationsubmissionEntity> {
    @Override
    public TransportationsubmissionEntity fromCSV(String[] csv) {
      int submissionid = Integer.parseInt(csv[0]);
      String employeeid = csv[1];
      String assignedid = csv[2];
      String currroomnum = csv[3];
      String destroomnum = csv[4];
      String reason = csv[5];
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

      return new TransportationsubmissionEntity(
          submissionid,
          employeeid,
          assignedid,
          currroomnum,
          destroomnum,
          reason,
          submissionstatus,
          urgency,
          createdate,
          duedate);
    }
  }
}
