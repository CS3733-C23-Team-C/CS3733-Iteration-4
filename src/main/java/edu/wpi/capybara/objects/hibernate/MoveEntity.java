package edu.wpi.capybara.objects.hibernate;

import jakarta.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "move", schema = "cdb", catalog = "teamcdb")
@IdClass(MoveEntityPK.class)
public class MoveEntity {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "nodeid")
  private String nodeid;

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "longname")
  private String longname;

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "movedate")
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
    MoveEntity that = (MoveEntity) o;
    return Objects.equals(nodeid, that.nodeid)
        && Objects.equals(longname, that.longname)
        && Objects.equals(movedate, that.movedate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nodeid, longname, movedate);
  }
}
