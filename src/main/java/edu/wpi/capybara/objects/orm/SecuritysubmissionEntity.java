package edu.wpi.capybara.objects.orm;

import edu.wpi.capybara.objects.submissions.SubmissionStatus;
import jakarta.persistence.*;
import java.util.Date;
import java.util.Objects;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

@Entity
@Table(name = "securitysubmission", schema = "cdb", catalog = "teamcdb")
public class SecuritysubmissionEntity implements Persistent {
  private final SimpleStringProperty employeeid = new SimpleStringProperty();
  private final SimpleStringProperty location = new SimpleStringProperty();
  private final SimpleStringProperty type = new SimpleStringProperty();
  private final SimpleStringProperty notesupdate = new SimpleStringProperty();
  private final SimpleObjectProperty<SubmissionStatus> submissionstatus =
      new SimpleObjectProperty<>();
  private final SimpleStringProperty assignedid = new SimpleStringProperty();
  // private final SimpleObjectProperty<UUID> submissionID = new SimpleObjectProperty<>();
  private final SimpleIntegerProperty submissionid = new SimpleIntegerProperty();
  private final SimpleStringProperty urgency = new SimpleStringProperty();
  private final SimpleObjectProperty<Date> createdate = new SimpleObjectProperty<>();
  private final SimpleObjectProperty<Date> duedate = new SimpleObjectProperty<>();

  public SecuritysubmissionEntity() {}

  public SecuritysubmissionEntity(
      int submissionid,
      String employeeid,
      String assignedid,
      String location,
      String type,
      String notesupdate,
      SubmissionStatus submissionstatus,
      String urgency,
      java.sql.Date createdate,
      java.sql.Date duedate) {
    setSubmissionid(submissionid);
    setEmployeeid(employeeid);
    setAssignedid(assignedid);
    setLocation(location);
    setType(type);
    setNotesupdate(notesupdate);
    setSubmissionstatus(submissionstatus);
    setUrgency(urgency);
    setCreatedate(createdate);
    setDuedate(duedate);
  }

  @Override
  public void enablePersistence(DAOFacade orm) {
    final InvalidationListener listener = evt -> orm.merge(this);
    employeeid.addListener(listener);
    location.addListener(listener);
    type.addListener(listener);
    notesupdate.addListener(listener);
    submissionstatus.addListener(listener);
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
    else if (obj instanceof SecuritysubmissionEntity that) {
      return Persistent.compareProperties(
          this,
          that,
          SecuritysubmissionEntity::getEmployeeid,
          SecuritysubmissionEntity::getLocation,
          SecuritysubmissionEntity::getType,
          SecuritysubmissionEntity::getNotesupdate,
          SecuritysubmissionEntity::getAssignedid,
          SecuritysubmissionEntity::getSubmissionid,
          SecuritysubmissionEntity::getUrgency,
          SecuritysubmissionEntity::getCreatedate,
          SecuritysubmissionEntity::getDuedate);
    } else return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getEmployeeid(),
        getLocation(),
        getType(),
        getNotesupdate(),
        getSubmissionstatus(),
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

  @Column(name = "location")
  public String getLocation() {
    return location.get();
  }

  public SimpleStringProperty locationProperty() {
    return location;
  }

  public void setLocation(String location) {
    this.location.set(location);
  }

  @Column(name = "type")
  public String getType() {
    return type.get();
  }

  public SimpleStringProperty typeProperty() {
    return type;
  }

  public void setType(String type) {
    this.type.set(type);
  }

  @Column(name = "notesupdate")
  public String getNotesupdate() {
    return notesupdate.get();
  }

  public SimpleStringProperty notesupdateProperty() {
    return notesupdate;
  }

  public void setNotesupdate(String notesupdate) {
    this.notesupdate.set(notesupdate);
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "submissionstatus")
  public SubmissionStatus getSubmissionstatus() {
    return submissionstatus.get();
  }

  public SimpleObjectProperty<SubmissionStatus> submissionstatusProperty() {
    return submissionstatus;
  }

  public void setSubmissionstatus(SubmissionStatus submissionstatus) {
    this.submissionstatus.set(submissionstatus);
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
