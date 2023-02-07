package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.objects.submissions.submissionStatus;
import jakarta.persistence.*;
import java.util.Objects;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Entity
@Table(name = "cleaningsubmission", schema = "cdb", catalog = "teamcdb")
@IdClass(CleaningsubmissionEntityPK.class)
public class CleaningsubmissionEntity {
  // @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "memberid")
  private String memberid;

  // @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "location")
  private String location;

  // @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "hazardlevel")
  private String hazardlevel;

  // @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "description")
  private String description;

  // @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Enumerated(EnumType.STRING)
  @Column(name = "submissionstatus")
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

  public CleaningsubmissionEntity(
      String memberid,
      String location,
      String hazardlevel,
      String description,
      submissionStatus submissionstatus) {
    this.memberid = memberid;
    this.location = location;
    this.hazardlevel = hazardlevel;
    this.description = description;
    this.submissionstatus = submissionstatus;
  }

  public CleaningsubmissionEntity() {}

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
    CleaningsubmissionEntity that = (CleaningsubmissionEntity) o;
    return Objects.equals(memberid, that.memberid)
        && Objects.equals(location, that.location)
        && Objects.equals(hazardlevel, that.hazardlevel)
        && Objects.equals(description, that.description)
        && Objects.equals(submissionstatus, that.submissionstatus);
  }

  public void create() {
    Session session = DatabaseConnect.getSession();
    Transaction tx = session.beginTransaction();
    session.persist(this);
    tx.commit();
    session.close();
  }

  @Override
  public int hashCode() {
    return Objects.hash(memberid, location, hazardlevel, description, submissionstatus);
  }
}
