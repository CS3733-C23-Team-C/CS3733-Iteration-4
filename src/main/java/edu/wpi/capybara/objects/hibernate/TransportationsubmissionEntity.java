package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.objects.submissions.submissionStatus;
import jakarta.persistence.*;
import java.util.Objects;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Entity
@Table(name = "transportationsubmission", schema = "cdb", catalog = "teamcdb")
@IdClass(TransportationsubmissionEntityPK.class)
public class TransportationsubmissionEntity {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "employeeid")
  private String employeeid;

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "currroomnum")
  private String currroomnum;

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "destroomnum")
  private String destroomnum;

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "reason")
  private String reason;

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "status")
  private submissionStatus status;

  public TransportationsubmissionEntity(
      String employeeid,
      String currroomnum,
      String destroomnum,
      String reason,
      submissionStatus status) {
    this.employeeid = employeeid;
    this.currroomnum = currroomnum;
    this.destroomnum = destroomnum;
    this.reason = reason;
    this.status = status;
  }

  public TransportationsubmissionEntity() {}

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

  public String getCurrroomnum() {
    return currroomnum;
  }

  public void setCurrroomnum(String currroomnum) {
    this.currroomnum = currroomnum;
    Session session = DatabaseConnect.getSession();
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
    Session session = DatabaseConnect.getSession();
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
    Session session = DatabaseConnect.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public submissionStatus getStatus() {
    return status;
  }

  public void setStatus(submissionStatus status) {
    this.status = status;
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
}
