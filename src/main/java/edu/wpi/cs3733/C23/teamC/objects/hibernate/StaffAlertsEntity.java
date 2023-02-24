package edu.wpi.cs3733.C23.teamC.objects.hibernate;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "staff_alerts", schema = "cdb", catalog = "teamcdb")
@IdClass(StaffAlertsEntityPK.class)
public class StaffAlertsEntity {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "alertid")
  private int alertid;

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "staffid")
  private String staffid;

  @Basic
  @Column(name = "seen")
  private Boolean seen;

  public int getAlertid() {
    return alertid;
  }

  public void setAlertid(int alertid) {
    this.alertid = alertid;
  }

  public String getStaffid() {
    return staffid;
  }

  public void setStaffid(String staffid) {
    this.staffid = staffid;
  }

  public Boolean getSeen() {
    return seen;
  }

  public void setSeen(Boolean seen) {
    this.seen = seen;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    StaffAlertsEntity that = (StaffAlertsEntity) o;
    return alertid == that.alertid
        && Objects.equals(staffid, that.staffid)
        && Objects.equals(seen, that.seen);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alertid, staffid, seen);
  }
}
