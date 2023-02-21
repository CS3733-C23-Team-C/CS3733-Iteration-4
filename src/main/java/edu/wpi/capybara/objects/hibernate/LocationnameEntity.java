package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.objects.orm.DAOFacade;
import edu.wpi.capybara.objects.orm.Persistent;
import jakarta.persistence.*;
import java.util.Objects;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;

@Entity
@Table(name = "locationname", schema = "cdb", catalog = "teamcdb")
public class LocationnameEntity implements Persistent {

  private final SimpleStringProperty longname = new SimpleStringProperty();
  private final SimpleStringProperty shortname = new SimpleStringProperty();
  private final SimpleStringProperty locationtype = new SimpleStringProperty();

  public LocationnameEntity() {}

  public LocationnameEntity(String longname, String shortname, String locationtype) {
    setLongname(longname);
    setShortname(shortname);
    setLocationtype(locationtype);
  }

  @Override
  public void enablePersistence(DAOFacade orm) {
    final InvalidationListener listener = evt -> orm.merge(this);
    longname.addListener(listener);
    shortname.addListener(listener);
    locationtype.addListener(listener);
  }

  @Id
  @Column(name = "longname")
  public String getLongname() {
    return longname.get();
  }

  public void setLongname(String longname) {
    this.longname.set(longname);
  }

  public SimpleStringProperty longnameProperty() {
    return longname;
  }

  @Basic
  @Column(name = "shortname")
  public String getShortname() {
    return shortname.get();
  }

  public void setShortname(String shortname) {
    this.shortname.set(shortname);
  }

  public SimpleStringProperty shortnameProperty() {
    return shortname;
  }

  @Basic
  @Column(name = "locationtype")
  public String getLocationtype() {
    return locationtype.get();
  }

  public void setLocationtype(String locationtype) {
    this.locationtype.set(locationtype);
  }

  public SimpleStringProperty locationtypeProperty() {
    return locationtype;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    else if (obj == this) return true;
    else if (obj instanceof LocationnameEntity that) {
      return Persistent.compareProperties(
          this,
          that,
          LocationnameEntity::getLongname,
          LocationnameEntity::getShortname,
          LocationnameEntity::getLocationtype);
    } else return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getLongname(), getShortname(), getLocationtype());
  }
}
