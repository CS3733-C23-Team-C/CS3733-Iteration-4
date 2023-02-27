package edu.wpi.cs3733.C23.teamC.objects.hibernate;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class StaffAlertsEntityPK implements Serializable {
  @Column(name = "alertid")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int alertid;

  @Column(name = "staffid")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String staffid;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    StaffAlertsEntityPK that = (StaffAlertsEntityPK) o;
    return alertid == that.alertid && Objects.equals(staffid, that.staffid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(alertid, staffid);
  }
}
