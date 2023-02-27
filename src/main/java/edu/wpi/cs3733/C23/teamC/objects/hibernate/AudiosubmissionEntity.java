package edu.wpi.cs3733.C23.teamC.objects.hibernate;

import edu.wpi.cs3733.C23.teamC.database.CSVExportable;
import edu.wpi.cs3733.C23.teamC.database.CSVImporter;
import edu.wpi.cs3733.C23.teamC.objects.SubmissionAbs;
import edu.wpi.cs3733.C23.teamC.objects.orm.DAOFacade;
import edu.wpi.cs3733.C23.teamC.objects.orm.Persistent;
import edu.wpi.cs3733.C23.teamC.objects.submissions.SubmissionStatus;
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
@Table(name = "audiosubmission", schema = "cdb", catalog = "teamcdb")
public class AudiosubmissionEntity extends SubmissionAbs implements Persistent, CSVExportable {
  // private final SimpleObjectProperty<UUID> submissionID = new SimpleObjectProperty<>();
  private final SimpleIntegerProperty submissionid = new SimpleIntegerProperty();
  private final SimpleStringProperty employeeid = new SimpleStringProperty();
  private final SimpleStringProperty assignedid = new SimpleStringProperty();
  private final SimpleStringProperty location = new SimpleStringProperty();
  private final SimpleStringProperty type = new SimpleStringProperty();
  private final SimpleStringProperty notesupdate = new SimpleStringProperty();
  private final SimpleObjectProperty<SubmissionStatus> submissionstatus =
      new SimpleObjectProperty<>();
  private final SimpleStringProperty urgency = new SimpleStringProperty();
  private final SimpleObjectProperty<Date> createdate = new SimpleObjectProperty<>();
  private final SimpleObjectProperty<Date> duedate = new SimpleObjectProperty<>();

  public AudiosubmissionEntity() {}

  public AudiosubmissionEntity(
      int submissionid,
      String employeeid,
      String assignedid,
      String location,
      String type,
      String notesupdate,
      SubmissionStatus submissionstatus,
      String urgency,
      Date createdate,
      Date duedate) {
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

  private InvalidationListener listener;

  @Override
  public void enablePersistence(DAOFacade orm) {
    listener = evt -> orm.mergeOnlyWhenManual(this);
    submissionid.addListener(listener);
    employeeid.addListener(listener);
    assignedid.addListener(listener);
    location.addListener(listener);
    type.addListener(listener);
    notesupdate.addListener(listener);
    submissionstatus.addListener(listener);
    urgency.addListener(listener);
    createdate.addListener(listener);
    duedate.addListener(listener);
  }

  @Override
  public void disablePersistence() {
    if (listener != null) {
      submissionid.removeListener(listener);
      employeeid.removeListener(listener);
      assignedid.removeListener(listener);
      location.removeListener(listener);
      type.removeListener(listener);
      notesupdate.removeListener(listener);
      submissionstatus.removeListener(listener);
      urgency.removeListener(listener);
      createdate.removeListener(listener);
      duedate.removeListener(listener);
      listener = null;
    }
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
  public boolean equals(Object obj) {
    if (obj == null) return false;
    else if (this == obj) return true;
    else if (obj instanceof AudiosubmissionEntity that) {
      return Persistent.compareProperties(
          this,
          that,
          AudiosubmissionEntity::getSubmissionid,
          AudiosubmissionEntity::getEmployeeid,
          AudiosubmissionEntity::getAssignedid,
          AudiosubmissionEntity::getLocation,
          AudiosubmissionEntity::getType,
          AudiosubmissionEntity::getNotesupdate,
          AudiosubmissionEntity::getSubmissionstatus,
          AudiosubmissionEntity::getUrgency,
          AudiosubmissionEntity::getCreatedate,
          AudiosubmissionEntity::getDuedate);
    } else return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getSubmissionid(),
        getEmployeeid(),
        getAssignedid(),
        getLocation(),
        getType(),
        getNotesupdate(),
        getSubmissionstatus(),
        getUrgency(),
        getCreatedate(),
        getDuedate());
  }

  @Override
  public String submissionType() {
    return "Audio";
  }

  @Override
  public String[] toCSV() {
    return new String[] {
      Integer.toString(getSubmissionid()),
      getEmployeeid(),
      getAssignedid(),
      getLocation(),
      getType(),
      getNotesupdate(),
      getSubmissionstatus().toString(),
      getUrgency(),
      getCreatedate().toString(),
      getDuedate().toString()
    };
  }

  public static class Importer implements CSVImporter<AudiosubmissionEntity> {
    @Override
    public AudiosubmissionEntity fromCSV(String[] csv) {
      int submissionid = Integer.parseInt(csv[0]);
      String employeeid = csv[1];
      String assignedid = csv[2];
      String location = csv[3];
      String type = csv[4];
      String notesupdate = csv[5];
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

      return new AudiosubmissionEntity(
          submissionid,
          employeeid,
          assignedid,
          location,
          type,
          notesupdate,
          submissionstatus,
          urgency,
          createdate,
          duedate);
    }
  }
}
