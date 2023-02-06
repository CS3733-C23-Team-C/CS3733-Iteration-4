package edu.wpi.capybara.objects.hibernate;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class MoveEntityPK implements Serializable {
  @Column(name = "nodeid")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String nodeid;

  @Column(name = "longname")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String longname;

  @Column(name = "movedate")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Date movedate;

  public String getNodeid() {
    return nodeid;
  }

  public void setNodeid(String nodeid) {
    this.nodeid = nodeid;
  }

  public String getLongname() {
    return longname;
  }

  public void setLongname(String longname) {
    this.longname = longname;
  }

  public Date getMovedate() {
    return movedate;
  }

  public void setMovedate(Date movedate) {
    this.movedate = movedate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MoveEntityPK that = (MoveEntityPK) o;
    return Objects.equals(nodeid, that.nodeid)
        && Objects.equals(longname, that.longname)
        && Objects.equals(movedate, that.movedate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nodeid, longname, movedate);
  }
}
