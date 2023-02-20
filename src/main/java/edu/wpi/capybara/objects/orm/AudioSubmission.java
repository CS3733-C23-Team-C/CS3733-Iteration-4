package edu.wpi.capybara.objects.orm;

import edu.wpi.capybara.objects.submissions.SubmissionStatus;
import jakarta.persistence.*;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "audiosubmission", schema = "cdb", catalog = "teamcdb")
public class AudioSubmission implements Persistent {
    private final SimpleObjectProperty<UUID> submissionID = new SimpleObjectProperty<>();
    private final SimpleStringProperty employeeID = new SimpleStringProperty();
    private final SimpleStringProperty assignedID = new SimpleStringProperty();
    private final SimpleStringProperty location = new SimpleStringProperty();
    private final SimpleStringProperty type = new SimpleStringProperty();
    private final SimpleStringProperty notesUpdate = new SimpleStringProperty();
    private final SimpleObjectProperty<SubmissionStatus> submissionStatus = new SimpleObjectProperty<>();
    private final SimpleStringProperty urgency = new SimpleStringProperty();
    private final SimpleObjectProperty<Date> createDate = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Date> dueDate = new SimpleObjectProperty<>();

    public AudioSubmission() {}

    @Override
    public void enablePersistence(DAOFacade orm) {
        final InvalidationListener listener = evt -> orm.merge(this);
        submissionID.addListener(listener);
        employeeID.addListener(listener);
        assignedID.addListener(listener);
        location.addListener(listener);
        type.addListener(listener);
        notesUpdate.addListener(listener);
        submissionStatus.addListener(listener);
        urgency.addListener(listener);
        createDate.addListener(listener);
        dueDate.addListener(listener);
    }

    @Id
    @Column(name = "submissionid")
    public UUID getSubmissionID() {
        return submissionID.get();
    }

    public SimpleObjectProperty<UUID> submissionIDProperty() {
        return submissionID;
    }

    public void setSubmissionID(UUID submissionID) {
        this.submissionID.set(submissionID);
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
    public String getNotesUpdate() {
        return notesUpdate.get();
    }

    public SimpleStringProperty notesUpdateProperty() {
        return notesUpdate;
    }

    public void setNotesUpdate(String notesUpdate) {
        this.notesUpdate.set(notesUpdate);
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "submissionstatus")
    public SubmissionStatus getSubmissionStatus() {
        return submissionStatus.get();
    }

    public SimpleObjectProperty<SubmissionStatus> submissionStatusProperty() {
        return submissionStatus;
    }

    public void setSubmissionStatus(SubmissionStatus submissionStatus) {
        this.submissionStatus.set(submissionStatus);
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        else if (this == obj) return true;
        else if (obj instanceof AudioSubmission that) {
            return Persistent.compareProperties(this, that,
                    AudioSubmission::getSubmissionID,
                    AudioSubmission::getEmployeeID,
                    AudioSubmission::getAssignedID,
                    AudioSubmission::getLocation,
                    AudioSubmission::getType,
                    AudioSubmission::getNotesUpdate,
                    AudioSubmission::getSubmissionStatus,
                    AudioSubmission::getUrgency,
                    AudioSubmission::getCreateDate,
                    AudioSubmission::getDueDate);
        } else return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getSubmissionID(),
                getEmployeeID(),
                getAssignedID(),
                getLocation(),
                getType(),
                getNotesUpdate(),
                getSubmissionStatus(),
                getUrgency(),
                getCreateDate(),
                getDueDate()
        );
    }
}
