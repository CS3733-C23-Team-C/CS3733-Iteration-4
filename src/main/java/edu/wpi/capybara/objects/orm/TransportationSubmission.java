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
public class TransportationSubmission implements Persistent {
  private final SimpleStringProperty employeeID = new SimpleStringProperty();
  private final SimpleStringProperty currRoomNum = new SimpleStringProperty();
  private final SimpleStringProperty destRoomNum = new SimpleStringProperty();
  private final SimpleStringProperty reason = new SimpleStringProperty();
  private final SimpleObjectProperty<SubmissionStatus> status = new SimpleObjectProperty<>();
  private final SimpleStringProperty assignedID = new SimpleStringProperty();
  // private final SimpleObjectProperty<UUID> submissionID = new SimpleObjectProperty<>();
  private final SimpleIntegerProperty submissionID = new SimpleIntegerProperty();
  private final SimpleStringProperty urgency = new SimpleStringProperty();
  private final SimpleObjectProperty<Date> createDate = new SimpleObjectProperty<>();
  private final SimpleObjectProperty<Date> dueDate = new SimpleObjectProperty<>();

  public TransportationSubmission() {}

  public TransportationSubmission(int submissionid,
                                  String employeeid,
                                  String assignedid,
                                  String currroomnum,
                                  String destroomnum,
                                  String reason,
                                  SubmissionStatus status,
                                  String urgency,
                                  Date createdate,
                                  Date duedate) {
    setSubmissionID(submissionid);
    setEmployeeID(employeeid);
    setAssignedID(assignedid);
    setCurrRoomNum(currroomnum);
    setDestRoomNum(destroomnum);
    setReason(reason);
    setStatus(status);
    setUrgency(urgency);
    setCreateDate(createdate);
    setDueDate(duedate);
  }

  @Override
  public void enablePersistence(DAOFacade orm) {
    final InvalidationListener listener = evt -> orm.merge(this);
    employeeID.addListener(listener);
    currRoomNum.addListener(listener);
    destRoomNum.addListener(listener);
    reason.addListener(listener);
    status.addListener(listener);
    assignedID.addListener(listener);
    submissionID.addListener(listener);
    urgency.addListener(listener);
    createDate.addListener(listener);
    dueDate.addListener(listener);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    else if (obj == this) return true;
    else if (obj instanceof TransportationSubmission that) {
      return Persistent.compareProperties(
          this,
          that,
          TransportationSubmission::getEmployeeID,
          TransportationSubmission::getCurrRoomNum,
          TransportationSubmission::getDestRoomNum,
          TransportationSubmission::getReason,
          TransportationSubmission::getStatus,
          TransportationSubmission::getAssignedID,
          TransportationSubmission::getSubmissionID,
          TransportationSubmission::getUrgency,
          TransportationSubmission::getCreateDate,
          TransportationSubmission::getDueDate);
    } else return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getEmployeeID(),
        getCurrRoomNum(),
        getDestRoomNum(),
        getReason(),
        getStatus(),
        getAssignedID(),
        getSubmissionID(),
        getUrgency(),
        getCreateDate(),
        getDueDate());
  }

  @Column(name = "employeeid")
  public String getEmployeeID() {
    return employeeID.get();
  }

  public SimpleStringProperty employeeIDProperty() {
    return employeeID;
  }

  public void setEmployeeID(String employeeID) {
    this.employeeID.set(employeeID);
  }

  @Column(name = "currroomnum")
  public String getCurrRoomNum() {
    return currRoomNum.get();
  }

  public SimpleStringProperty currRoomNumProperty() {
    return currRoomNum;
  }

  public void setCurrRoomNum(String currRoomNum) {
    this.currRoomNum.set(currRoomNum);
  }

  @Column(name = "destroomnum")
  public String getDestRoomNum() {
    return destRoomNum.get();
  }

  public SimpleStringProperty destRoomNumProperty() {
    return destRoomNum;
  }

  public void setDestRoomNum(String destRoomNum) {
    this.destRoomNum.set(destRoomNum);
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
  public String getAssignedID() {
    return assignedID.get();
  }

  public SimpleStringProperty assignedIDProperty() {
    return assignedID;
  }

  public void setAssignedID(String assignedID) {
    this.assignedID.set(assignedID);
  }

  @Id
  @Column(name = "submissionid")
  public int getSubmissionID() {
    return submissionID.get();
  }

  public SimpleIntegerProperty submissionIDProperty() {
    return submissionID;
  }

  public void setSubmissionID(int submissionID) {
    this.submissionID.set(submissionID);
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
  public Date getCreateDate() {
    return createDate.get();
  }

  public SimpleObjectProperty<Date> createDateProperty() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate.set(createDate);
  }

  @Column(name = "duedate")
  public Date getDueDate() {
    return dueDate.get();
  }

  public SimpleObjectProperty<Date> dueDateProperty() {
    return dueDate;
  }

  public void setDueDate(Date dueDate) {
    this.dueDate.set(dueDate);
  }
}
