package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.objects.submissions.submissionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class CleaningsubmissionEntityPK implements Serializable {
  @Column(name = "memberid")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String memberid;

  @Column(name = "location")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String location;

  @Column(name = "hazardlevel")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String hazardlevel;

  @Column(name = "description")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String description;

  @Column(name = "submissionstatus")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private submissionStatus submissionstatus;

  public String getMemberid() {
    return memberid;
  }

  public void setMemberid(String memberid) {
    this.memberid = memberid;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getHazardlevel() {
    return hazardlevel;
  }

  public void setHazardlevel(String hazardlevel) {
    this.hazardlevel = hazardlevel;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public submissionStatus getSubmissionstatus() {
    return submissionstatus;
  }

  public void setSubmissionstatus(submissionStatus submissionstatus) {
    this.submissionstatus = submissionstatus;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CleaningsubmissionEntityPK that = (CleaningsubmissionEntityPK) o;
    return Objects.equals(memberid, that.memberid)
        && Objects.equals(location, that.location)
        && Objects.equals(hazardlevel, that.hazardlevel)
        && Objects.equals(description, that.description)
        && Objects.equals(submissionstatus, that.submissionstatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(memberid, location, hazardlevel, description, submissionstatus);
  }
}
