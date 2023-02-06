package edu.wpi.capybara.objects.hibernate;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "staff", schema = "cdb", catalog = "teamcdb")
public class StaffEntity {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "staffid")
  private String staffid;

  @Basic
  @Column(name = "firstname")
  private String firstname;

  @Basic
  @Column(name = "lastname")
  private String lastname;

  @Basic
  @Column(name = "password")
  private String password;

  public String getStaffid() {
    return staffid;
  }

  public void setStaffid(String staffid) {
    this.staffid = staffid;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    StaffEntity that = (StaffEntity) o;
    return Objects.equals(staffid, that.staffid)
        && Objects.equals(firstname, that.firstname)
        && Objects.equals(lastname, that.lastname)
        && Objects.equals(password, that.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(staffid, firstname, lastname, password);
  }
}
