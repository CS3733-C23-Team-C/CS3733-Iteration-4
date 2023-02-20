package edu.wpi.capybara.objects.orm;

import jakarta.persistence.*;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;

@Entity
@Table(name = "locationname", schema = "cdb", catalog = "teamcdb")
public class Location implements Persistent {

  private final SimpleStringProperty longName = new SimpleStringProperty();
  private final SimpleStringProperty shortName = new SimpleStringProperty();
  private final SimpleStringProperty locationType = new SimpleStringProperty();

  public Location() {}

  public Location(String longname, String shortname, String locationtype) {
    setLongName(longname);
    setShortName(shortname);
    setLocationType(locationtype);
  }

  @Override
  public void enablePersistence(DAOFacade orm) {
    final InvalidationListener listener = evt -> orm.merge(this);
    longName.addListener(listener);
    shortName.addListener(listener);
    locationType.addListener(listener);
  }

  @Id
  @Column(name = "longname")
  public String getLongName() {
    return longName.get();
  }

  public void setLongName(String longName) {
    this.longName.set(longName);
  }

  public SimpleStringProperty longNameProperty() {
    return longName;
  }

  @Basic
  @Column(name = "shortname")
  public String getShortName() {
    return shortName.get();
  }

  public void setShortName(String shortName) {
    this.shortName.set(shortName);
  }

  public SimpleStringProperty shortNameProperty() {
    return shortName;
  }

  @Basic
  @Column(name = "locationtype")
  public String getLocationType() {
    return locationType.get();
  }

  public void setLocationType(String locationType) {
    this.locationType.set(locationType);
  }

  public SimpleStringProperty locationTypeProperty() {
    return locationType;
  }
}
