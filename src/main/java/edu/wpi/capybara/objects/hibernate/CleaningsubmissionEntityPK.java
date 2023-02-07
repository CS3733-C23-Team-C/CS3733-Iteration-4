package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.objects.submissions.submissionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.util.Objects;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CleaningsubmissionEntityPK implements Serializable {
  @Column(name = "memberid")
  @Id
  // @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String memberid;

  @Column(name = "location")
  @Id
  // @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String location;

  @Column(name = "hazardlevel")
  @Id
  // @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String hazardlevel;

  @Column(name = "description")
  @Id
  // @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String description;

  @Column(name = "submissionstatus")
  @Enumerated(EnumType.STRING)
  @Id
  // @GeneratedValue(strategy = GenerationType.IDENTITY)
  private submissionStatus submissionstatus;

  public String getMemberid() {
    return memberid;
  }

  public void setMemberid(String memberid) {
    Session session = DatabaseConnect.getSession();
    Transaction tx = session.beginTransaction();
    this.memberid = memberid;
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
    Session session = DatabaseConnect.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getHazardlevel() {
    return hazardlevel;
  }

  public void setHazardlevel(String hazardlevel) {
    this.hazardlevel = hazardlevel;
    Session session = DatabaseConnect.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
    Session session = DatabaseConnect.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public submissionStatus getSubmissionstatus() {
    return submissionstatus;
  }

  public void setSubmissionstatus(submissionStatus submissionstatus) {
    this.submissionstatus = submissionstatus;
    Session session = DatabaseConnect.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
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
