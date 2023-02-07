package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.objects.submissions.submissionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class TransportationsubmissionEntityPK implements Serializable {
  @Column(name = "employeeid")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String employeeid;

  @Column(name = "currroomnum")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String currroomnum;

  @Column(name = "destroomnum")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String destroomnum;

  @Column(name = "reason")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String reason;

  @Column(name = "status")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private submissionStatus status;

  public String getEmployeeid() {
    return employeeid;
  }

  public void setEmployeeid(String employeeid) {
    this.employeeid = employeeid;
  }

  public String getCurrroomnum() {
    return currroomnum;
  }

  public void setCurrroomnum(String currroomnum) {
    this.currroomnum = currroomnum;
  }

  public String getDestroomnum() {
    return destroomnum;
  }

  public void setDestroomnum(String destroomnum) {
    this.destroomnum = destroomnum;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public submissionStatus getStatus() {
    return status;
  }

  public void setStatus(submissionStatus status) {
    this.status = status;
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

  @Override
  public int hashCode() {
    return Objects.hash(employeeid, currroomnum, destroomnum, reason, status);
  }
}
