package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.Main;
import jakarta.persistence.*;
import java.sql.Date;
import java.util.Objects;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Entity
@Table(name = "move", schema = "cdb", catalog = "teamcdb")
@IdClass(MoveEntityPK.class)
public class MoveEntity {
  @Id
  @Column(name = "nodeid")
  private String nodeid;

  @Id
  @Column(name = "longname")
  private String longname;

  @Id
  @Column(name = "movedate")
  private Date movedate;

  public String getNodeid() {
    return nodeid;
  }

  public MoveEntity() {}

  public MoveEntity(String nodeid, String longname, Date movedate) {
    this.nodeid = nodeid;
    this.longname = longname;
    this.movedate = movedate;
  }

  public void setNodeid(String nodeid) {
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    this.nodeid = nodeid;
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getLongname() {
    return longname;
  }

  public void setLongname(String longname) {
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    this.longname = longname;
    session.merge(this);
    tx.commit();
    session.close();
  }

  public Date getMovedate() {
    return movedate;
  }

  public void setMovedate(Date movedate) {
    this.movedate = movedate;
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
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
