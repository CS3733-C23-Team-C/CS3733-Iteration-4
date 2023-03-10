package edu.wpi.cs3733.C23.teamC.database.hibernate;

import edu.wpi.cs3733.C23.teamC.database.CSVExportable;
import edu.wpi.cs3733.C23.teamC.database.CSVImporter;
import edu.wpi.cs3733.C23.teamC.database.orm.DAOFacade;
import edu.wpi.cs3733.C23.teamC.database.orm.Persistent;
import jakarta.persistence.*;
import java.util.Objects;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;

@Entity
@Table(name = "locationname", schema = "cdb", catalog = "teamcdb")
public class LocationnameEntity implements Persistent, CSVExportable {

  private final SimpleStringProperty longname = new SimpleStringProperty();
  private final SimpleStringProperty shortname = new SimpleStringProperty();
  private final SimpleStringProperty locationtype = new SimpleStringProperty();

  public LocationnameEntity() {}

  public LocationnameEntity(String longname, String shortname, String locationtype) {
    setLongname(longname);
    setShortname(shortname);
    setLocationtype(locationtype);
  }

  private InvalidationListener listener;

  @Override
  public void enablePersistence(DAOFacade orm) {
    listener = evt -> orm.mergeOnlyWhenManual(this);
    longname.addListener(listener);
    shortname.addListener(listener);
    locationtype.addListener(listener);
  }

  @Override
  public void disablePersistence() {
    if (listener != null) {
      longname.removeListener(listener);
      shortname.removeListener(listener);
      locationtype.removeListener(listener);
      listener = null;
    }
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

  @Override
  public String[] toCSV() {
    return new String[] {getLongname(), getShortname(), getLocationtype()};
  }

  public String toString() {
    return getLongname();
  }

  public static class Importer implements CSVImporter<LocationnameEntity> {
    @Override
    public LocationnameEntity fromCSV(String[] csv) {

      String longname = csv[0];
      String shortname = csv[1];
      String locationtype = csv[2];

      return new LocationnameEntity(longname, shortname, locationtype);
    }
  }
}
