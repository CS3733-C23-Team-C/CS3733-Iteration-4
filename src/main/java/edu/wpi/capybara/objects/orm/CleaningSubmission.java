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
@Table(name = "cleaningsubmission", schema = "cdb", catalog = "teamcdb")
public class CleaningSubmission implements Persistent {
    private final SimpleStringProperty memberID = new SimpleStringProperty();
    private final SimpleStringProperty location = new SimpleStringProperty();
    private final SimpleStringProperty hazardLevel = new SimpleStringProperty();
    private final SimpleStringProperty description = new SimpleStringProperty();
    private final SimpleObjectProperty<SubmissionStatus> submissionStatus = new SimpleObjectProperty<>();
    private final SimpleStringProperty assignedID = new SimpleStringProperty();
    private final SimpleObjectProperty<UUID> submissionID = new SimpleObjectProperty<>();
    private final SimpleStringProperty urgency = new SimpleStringProperty();
    private final SimpleObjectProperty<Date> createDate = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Date> dueDate = new SimpleObjectProperty<>();

    protected CleaningSubmission() {}

    @Override
    public void enablePersistence(DAOFacade orm) {
        final InvalidationListener listener = evt -> orm.merge(this);
        memberID.addListener(listener);
        location.addListener(listener);
        hazardLevel.addListener(listener);
        description.addListener(listener);
        submissionStatus.addListener(listener);
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
        else if (obj instanceof CleaningSubmission that) {
            return Persistent.compareProperties(this, that,
                    CleaningSubmission::getMemberID,
                    CleaningSubmission::getLocation,
                    CleaningSubmission::getHazardLevel,
                    CleaningSubmission::getDescription,
                    CleaningSubmission::getSubmissionStatus,
                    CleaningSubmission::getAssignedID,
                    CleaningSubmission::getUrgency,
                    CleaningSubmission::getCreateDate,
                    CleaningSubmission::getDueDate);
        } else return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getMemberID(),
                getLocation(),
                getHazardLevel(),
                getDescription(),
                getSubmissionStatus(),
                getAssignedID(),
                getUrgency(),
                getCreateDate(),
                getDueDate()
        );
    }

    @Column(name = "memberid")
    public String getMemberID() {
        return memberID.get();
    }

    public SimpleStringProperty memberIDProperty() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID.set(memberID);
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
    public String getHazardLevel() {
        return hazardLevel.get();
    }

    public SimpleStringProperty hazardLevelProperty() {
        return hazardLevel;
    }

    public void setHazardLevel(String hazardLevel) {
        this.hazardLevel.set(hazardLevel);
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
    public SubmissionStatus getSubmissionStatus() {
        return submissionStatus.get();
    }

    public SimpleObjectProperty<SubmissionStatus> submissionStatusProperty() {
        return submissionStatus;
    }

    public void setSubmissionStatus(SubmissionStatus submissionStatus) {
        this.submissionStatus.set(submissionStatus);
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
    public UUID getSubmissionID() {
        return submissionID.get();
    }

    public SimpleObjectProperty<UUID> submissionIDProperty() {
        return submissionID;
    }

    public void setSubmissionID(UUID submissionID) {
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
