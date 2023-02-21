package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.objects.SubmissionAbs;
import edu.wpi.capybara.objects.orm.DAOFacade;
import edu.wpi.capybara.objects.orm.Persistent;

import edu.wpi.capybara.objects.submissions.SubmissionStatus;
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
@Table(name = "transportationsubmission", schema = "cdb", catalog = "teamcdb")

public class TransportationsubmissionEntity extends SubmissionAbs implements Persistent {
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

  @Transient
  @Override
  public String getLocation() {
    return getCurrroomnum();
  }

  @Override
  public String submissionType() {
    return "Security";
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
