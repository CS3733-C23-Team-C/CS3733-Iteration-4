package edu.wpi.capybara.objects;

import java.sql.Date;

import edu.wpi.capybara.database.DBUpdate;
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

  public void setNodeid(String nodeid) {
    this.nodeid = nodeid;
    DBUpdate.update("move", this.nodeid, this.longname, String.valueOf(this.movedate), "'nodeid'", nodeid, "nodeid", "longname", "movedate");
  }

  public void setLongname(String longname) {
    this.longname = longname;
    DBUpdate.update("move", this.nodeid, this.longname, String.valueOf(this.movedate), "'longname'", longname, "nodeid", "longname", "movedate");
  }

  public void setMovedate(String movedate) {
    this.movedate = Date.valueOf(movedate);
    DBUpdate.update("move", this.nodeid, this.longname, String.valueOf(this.movedate), "'movedate'", movedate, "nodeid", "longname", "movedate");
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
