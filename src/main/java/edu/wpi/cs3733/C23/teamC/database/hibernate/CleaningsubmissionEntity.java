package edu.wpi.cs3733.C23.teamC.database.hibernate;

import edu.wpi.cs3733.C23.teamC.ServiceRequests.SubmissionAbs;
import edu.wpi.cs3733.C23.teamC.ServiceRequests.submissions.SubmissionStatus;
import edu.wpi.cs3733.C23.teamC.database.CSVExportable;
import edu.wpi.cs3733.C23.teamC.database.CSVImporter;
import edu.wpi.cs3733.C23.teamC.database.orm.DAOFacade;
import edu.wpi.cs3733.C23.teamC.database.orm.Persistent;
import jakarta.persistence.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

@Entity
@Table(name = "cleaningsubmission", schema = "cdb", catalog = "teamcdb")
public class CleaningsubmissionEntity extends SubmissionAbs implements Persistent, CSVExportable {
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

  private InvalidationListener listener;

  @Override
  public void enablePersistence(DAOFacade orm) {
    listener = evt -> orm.mergeOnlyWhenManual(this);
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
  public void disablePersistence() {
    if (listener != null) {
      memberid.removeListener(listener);
      location.removeListener(listener);
      hazardlevel.removeListener(listener);
      description.removeListener(listener);
      submissionstatus.removeListener(listener);
      assignedid.removeListener(listener);
      submissionid.removeListener(listener);
      urgency.removeListener(listener);
      createdate.removeListener(listener);
      duedate.removeListener(listener);
      listener = null;
    }
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
