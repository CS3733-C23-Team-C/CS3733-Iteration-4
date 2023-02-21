package edu.wpi.capybara.objects.orm;

import edu.wpi.capybara.objects.submissions.SubmissionStatus;
import jakarta.persistence.*;
import java.sql.Date;
import java.util.Objects;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

@Entity
@Table(name = "transportationsubmission", schema = "cdb", catalog = "teamcdb")
public class TransportationsubmissionEntity implements Persistent {
  private final SimpleStringProperty employeeid = new SimpleStringProperty();
  private final SimpleStringProperty currroomnum = new SimpleStringProperty();
  private final SimpleStringProperty destroomnum = new SimpleStringProperty();
  private final SimpleStringProperty reason = new SimpleStringProperty();
  private final SimpleObjectProperty<SubmissionStatus> status = new SimpleObjectProperty<>();
  private final SimpleStringProperty assignedid = new SimpleStringProperty();
  // private final SimpleObjectProperty<UUID> submissionID = new SimpleObjectProperty<>();
  private final SimpleIntegerProperty submissionid = new SimpleIntegerProperty();
  private final SimpleStringProperty urgency = new SimpleStringProperty();
  private final SimpleObjectProperty<Date> createdate = new SimpleObjectProperty<>();
  private final SimpleObjectProperty<Date> duedate = new SimpleObjectProperty<>();

  public TransportationsubmissionEntity() {}

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
    setSubmissionid(submissionid);
    setEmployeeid(employeeid);
    setAssignedid(assignedid);
    setCurrroomnum(currroomnum);
    setDestroomnum(destroomnum);
    setReason(reason);
    setStatus(status);
    setUrgency(urgency);
    setCreatedate(createdate);
    setDuedate(duedate);
  }

  @Override
  public void enablePersistence(DAOFacade orm) {
    final InvalidationListener listener = evt -> orm.merge(this);
    employeeid.addListener(listener);
    currroomnum.addListener(listener);
    destroomnum.addListener(listener);
    reason.addListener(listener);
    status.addListener(listener);
    assignedid.addListener(listener);
    submissionid.addListener(listener);
    urgency.addListener(listener);
    createdate.addListener(listener);
    duedate.addListener(listener);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    else if (obj == this) return true;
    else if (obj instanceof TransportationsubmissionEntity that) {
      return Persistent.compareProperties(
          this,
          that,
          TransportationsubmissionEntity::getEmployeeid,
          TransportationsubmissionEntity::getCurrroomnum,
          TransportationsubmissionEntity::getDestroomnum,
          TransportationsubmissionEntity::getReason,
          TransportationsubmissionEntity::getStatus,
          TransportationsubmissionEntity::getAssignedid,
          TransportationsubmissionEntity::getSubmissionid,
          TransportationsubmissionEntity::getUrgency,
          TransportationsubmissionEntity::getCreatedate,
          TransportationsubmissionEntity::getDuedate);
    } else return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getEmployeeid(),
        getCurrroomnum(),
        getDestroomnum(),
        getReason(),
        getStatus(),
        getAssignedid(),
        getSubmissionid(),
        getUrgency(),
        getCreatedate(),
        getDuedate());
  }

  @Column(name = "employeeid")
  public String getEmployeeid() {
    return employeeid.get();
  }

  public SimpleStringProperty employeeidProperty() {
    return employeeid;
  }

  public void setEmployeeid(String employeeid) {
    this.employeeid.set(employeeid);
  }

  @Column(name = "currroomnum")
  public String getCurrroomnum() {
    return currroomnum.get();
  }

  public SimpleStringProperty currroomnumProperty() {
    return currroomnum;
  }

  public void setCurrroomnum(String currroomnum) {
    this.currroomnum.set(currroomnum);
  }

  @Column(name = "destroomnum")
  public String getDestroomnum() {
    return destroomnum.get();
  }

  public SimpleStringProperty destroomnumProperty() {
    return destroomnum;
  }

  public void setDestroomnum(String destroomnum) {
    this.destroomnum.set(destroomnum);
  }

  @Column(name = "reason")
  public String getReason() {
    return reason.get();
  }

  public SimpleStringProperty reasonProperty() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason.set(reason);
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  public SubmissionStatus getStatus() {
    return status.get();
  }

  public SimpleObjectProperty<SubmissionStatus> statusProperty() {
    return status;
  }

  public void setStatus(SubmissionStatus status) {
    this.status.set(status);
  }

  @Column(name = "assignedid")
  public String getAssignedid() {
    return assignedid.get();
  }

  public SimpleStringProperty assignedidProperty() {
    return assignedid;
  }

  public void setAssignedid(String assignedid) {
    this.assignedid.set(assignedid);
  }

  @Id
  @Column(name = "submissionid")
  public int getSubmissionid() {
    return submissionid.get();
  }

  public SimpleIntegerProperty submissionidProperty() {
    return submissionid;
  }

  public void setSubmissionid(int submissionid) {
    this.submissionid.set(submissionid);
  }

  @Column(name = "urgency")
  public String getUrgency() {
    return urgency.get();
  }

  public SimpleStringProperty urgencyProperty() {
    return urgency;
  }

  public void setUrgency(String urgency) {
    this.urgency.set(urgency);
  }

  @Column(name = "createdate")
  public Date getCreatedate() {
    return createdate.get();
  }

  public SimpleObjectProperty<Date> createdateProperty() {
    return createdate;
  }

  public void setCreatedate(Date createdate) {
    this.createdate.set(createdate);
  }

  @Column(name = "duedate")
  public Date getDuedate() {
    return duedate.get();
  }

  public SimpleObjectProperty<Date> duedateProperty() {
    return duedate;
  }

  public void setDuedate(Date duedate) {
    this.duedate.set(duedate);
  }
}
