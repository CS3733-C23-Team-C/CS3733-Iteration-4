package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.objects.submissions.submissionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class TransportationsubmissionEntityPK implements Serializable {
  @Column(name = "employeeid")
  @Id
  private String employeeid;

  @Column(name = "currroomnum")
  @Id
  private String currroomnum;

  @Column(name = "destroomnum")
  @Id
  private String destroomnum;

  @Column(name = "reason")
  @Id
  private String reason;

  @Column(name = "status")
  @Id
  private submissionStatus status;

  public String getEmployeeid() {
    return employeeid;
  }

  public void setEmployeeid(String employeeid) {
    this.employeeid = employeeid;
    //    Session session = DatabaseConnect.getSession();
    //    Transaction tx = session.beginTransaction();
    //    session.merge(this);
    //    tx.commit();
    //    session.close();
  }

  public String getCurrroomnum() {
    return currroomnum;
  }

  public void setCurrroomnum(String currroomnum) {
    this.currroomnum = currroomnum;
    //    Session session = DatabaseConnect.getSession();
    //    Transaction tx = session.beginTransaction();
    //    session.merge(this);
    //    tx.commit();
    //    session.close();
  }

  public String getDestroomnum() {
    return destroomnum;
  }

  public void setDestroomnum(String destroomnum) {
    this.destroomnum = destroomnum;
    //    Session session = DatabaseConnect.getSession();
    //    Transaction tx = session.beginTransaction();
    //    session.merge(this);
    //    tx.commit();
    //    session.close();
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
    //    Session session = DatabaseConnect.getSession();
    //    Transaction tx = session.beginTransaction();
    //    session.merge(this);
    //    tx.commit();
    //    session.close();
  }

  public submissionStatus getStatus() {
    return status;
  }

  public void setStatus(submissionStatus status) {
    this.status = status;
    //    Session session = DatabaseConnect.getSession();
    //    Transaction tx = session.beginTransaction();
    //    session.merge(this);
    //    tx.commit();
    //    session.close();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TransportationsubmissionEntityPK that = (TransportationsubmissionEntityPK) o;
    return Objects.equals(employeeid, that.employeeid)
        && Objects.equals(currroomnum, that.currroomnum)
        && Objects.equals(destroomnum, that.destroomnum)
        && Objects.equals(reason, that.reason)
        && Objects.equals(status, that.status);
  }

  public TransportationsubmissionEntityPK(
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

  public TransportationsubmissionEntityPK() {}

  @Override
  public int hashCode() {
    return Objects.hash(employeeid, currroomnum, destroomnum, reason, status);
  }
}
