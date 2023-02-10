package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.database.DatabaseConnect;
import jakarta.persistence.*;
import java.util.Objects;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Entity
@Table(name = "locationname", schema = "cdb", catalog = "teamcdb")
public class LocationnameEntity {
  @Id
  @Column(name = "longname")
  private String longname;

  @Basic
  @Column(name = "shortname")
  private String shortname;

  @Basic
  @Column(name = "locationtype")
  private String locationtype;

  public LocationnameEntity() {}

  public LocationnameEntity(String longname, String shortname, String locationtype) {
    this.longname = longname;
    this.shortname = shortname;
    this.locationtype = locationtype;
  }

  public String getLongname() {
    return longname;
  }

  public void setLongname(String longname) {
    this.longname = longname;
    Session session = DatabaseConnect.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getShortname() {
    return shortname;
  }

  public void setShortname(String shortname) {
    this.shortname = shortname;
    Session session = DatabaseConnect.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getLocationtype() {
    return locationtype;
  }

  public void setLocationtype(String locationtype) {
    this.locationtype = locationtype;
    Session session = DatabaseConnect.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public void delete() {
    Session session = DatabaseConnect.getSession();
    Transaction tx = session.beginTransaction();
    session.remove(this);
    tx.commit();
    session.close();
    DatabaseConnect.importLocations();
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
