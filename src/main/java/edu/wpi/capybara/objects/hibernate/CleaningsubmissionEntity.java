package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.objects.SubmissionAbs;
import edu.wpi.capybara.objects.orm.DAOFacade;
import edu.wpi.capybara.objects.orm.Persistent;
import edu.wpi.capybara.objects.submissions.SubmissionStatus;
import jakarta.persistence.*;
import java.sql.Date;
import java.util.Objects;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

@Entity
@Table(name = "cleaningsubmission", schema = "cdb", catalog = "teamcdb")
public class CleaningsubmissionEntity extends SubmissionAbs implements Persistent {
  private final SimpleStringProperty memberid = new SimpleStringProperty();
  private final SimpleStringProperty location = new SimpleStringProperty();
  private final SimpleStringProperty hazardlevel = new SimpleStringProperty();
  private final SimpleStringProperty description = new SimpleStringProperty();
  private final SimpleObjectProperty<SubmissionStatus> submissionstatus =
      new SimpleObjectProperty<>();
  private final SimpleStringProperty assignedid = new SimpleStringProperty();
  // private final SimpleObjectProperty<UUID> submissionID = new SimpleObjectProperty<>();
  private final SimpleIntegerProperty submissionid = new SimpleIntegerProperty();
  private final SimpleStringProperty urgency = new SimpleStringProperty();
  private final SimpleObjectProperty<Date> createdate = new SimpleObjectProperty<>();
  private final SimpleObjectProperty<Date> duedate = new SimpleObjectProperty<>();

  public CleaningsubmissionEntity() {}

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
    setSubmissionid(submissionid);
    setMemberid(memberid);
    setAssignedid(assignedid);
    setLocation(location);
    setHazardlevel(hazardlevel);
    setDescription(description);
    setSubmissionstatus(submissionstatus);
    setUrgency(urgency);
    setCreatedate(createdate);
    setDuedate(duedate);
  }

  @Override
  public void enablePersistence(DAOFacade orm) {
    final InvalidationListener listener = evt -> orm.merge(this);
    memberid.addListener(listener);
    location.addListener(listener);
    hazardlevel.addListener(listener);
    description.addListener(listener);
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
    else if (obj instanceof CleaningsubmissionEntity that) {
      return Persistent.compareProperties(
          this,
          that,
          CleaningsubmissionEntity::getMemberid,
          CleaningsubmissionEntity::getLocation,
          CleaningsubmissionEntity::getHazardlevel,
          CleaningsubmissionEntity::getDescription,
          CleaningsubmissionEntity::getSubmissionstatus,
          CleaningsubmissionEntity::getAssignedid,
          CleaningsubmissionEntity::getSubmissionid,
          CleaningsubmissionEntity::getUrgency,
          CleaningsubmissionEntity::getCreatedate,
          CleaningsubmissionEntity::getDuedate);
    } else return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getMemberid(),
        getLocation(),
        getHazardlevel(),
        getDescription(),
        getSubmissionstatus(),
        getAssignedid(),
        getSubmissionid(),
        getUrgency(),
        getCreatedate(),
        getDuedate());
  }

  @Column(name = "memberid")
  public String getMemberid() {
    return memberid.get();
  }

  public SimpleStringProperty memberidProperty() {
    return memberid;
  }

  public void setMemberid(String memberid) {
    this.memberid.set(memberid);
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

  @Column(name = "hazardlevel")
  public String getHazardlevel() {
    return hazardlevel.get();
  }

  public SimpleStringProperty hazardlevelProperty() {
    return hazardlevel;
  }

  public void setHazardlevel(String hazardlevel) {
    this.hazardlevel.set(hazardlevel);
  }

  @Column(name = "description")
  public String getDescription() {
    return description.get();
  }

  public SimpleStringProperty descriptionProperty() {
    return description;
  }

  public void setDescription(String description) {
    this.description.set(description);
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

  @Override
  public String submissionType() {
    return "Cleaning";
  }
}
