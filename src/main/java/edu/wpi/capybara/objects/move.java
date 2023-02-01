package edu.wpi.capybara.objects;

import java.sql.Date;
import lombok.Getter;

public class move {
  @Getter private String nodeid;
  @Getter private String longname;
  @Getter private Date movedate;

  public move(String nodeid, String longname, Date movedate) {
    this.nodeid = nodeid;
    this.longname = longname;
    this.movedate = movedate;
  }

  @Override
  public String toString() {
    return "Node id : "
        + this.nodeid
        + "\n"
        + "Long name : "
        + this.longname
        + "\n"
        + "Move date : "
        + this.movedate.toString()
        + "\n";
  }
}
