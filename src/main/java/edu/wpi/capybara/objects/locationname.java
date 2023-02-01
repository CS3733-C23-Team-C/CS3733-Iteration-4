package edu.wpi.capybara.objects;

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
