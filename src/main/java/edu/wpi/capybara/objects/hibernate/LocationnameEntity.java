package edu.wpi.capybara.objects.hibernate;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "locationname", schema = "cdb", catalog = "teamcdb")
public class LocationnameEntity {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "longname")
  private String longname;

  @Basic
  @Column(name = "shortname")
  private String shortname;

  @Basic
  @Column(name = "locationtype")
  private String locationtype;

  public String getLongname() {
    return longname;
  }

  public void setLongname(String longname) {
    this.longname = longname;
  }

  public String getShortname() {
    return shortname;
  }

  public void setShortname(String shortname) {
    this.shortname = shortname;
  }

  public String getLocationtype() {
    return locationtype;
  }

  public void setLocationtype(String locationtype) {
    this.locationtype = locationtype;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    LocationnameEntity that = (LocationnameEntity) o;
    return Objects.equals(longname, that.longname)
        && Objects.equals(shortname, that.shortname)
        && Objects.equals(locationtype, that.locationtype);
  }

  @Override
  public int hashCode() {
    return Objects.hash(longname, shortname, locationtype);
  }
}
