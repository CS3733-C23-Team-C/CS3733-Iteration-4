package edu.wpi.capybara.objects;

import edu.wpi.capybara.database.DBUpdate;
import lombok.Getter;

public class locationname {
  @Getter private String longname;

  @Getter private String shortname;
  @Getter private String locationtype;

  public locationname(String longname, String shortname, String locationtype) {
    this.longname = longname;
    this.shortname = shortname;
    this.locationtype = locationtype;
  }

  public void setLongname(String longname) {
    this.longname = longname;
    DBUpdate.update(
        "locationname",
        this.longname,
        this.shortname,
        this.locationtype,
        "'longname'",
        longname,
        "longname",
        "shortname",
        "locationtype");
  }

  public void setShortname(String shortname) {
    this.shortname = shortname;
    DBUpdate.update(
        "shortname",
        this.longname,
        this.shortname,
        this.locationtype,
        "'shortname'",

        shortname,
        "longname",
        "shortname",
        "locationtype");
  }

  public void setLocationtype(String locationtype) {
    this.locationtype = locationtype;
    DBUpdate.update(
        "locationtype",
        this.longname,
        this.shortname,
        this.locationtype,
        "'locationtype'",

        locationtype,
        "longname",
        "shortname",
        "locationtype");
  }

  @Override
  public String toString() {
    return "Long name : "
        + this.longname
        + "\n"
        + "Short name : "
        + this.shortname
        + "\n"
        + "Location type : "
        + this.locationtype
        + "\n";
  }
}
