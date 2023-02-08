package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.database.DatabaseConnect;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class MoveEntityPK implements Serializable {
  @Column(name = "nodeid")
  @Id
  private String nodeid;

  @Column(name = "longname")
  @Id
  private String longname;

  @Column(name = "movedate")
  @Id
  private Date movedate;

  public String getNodeid() {
    return nodeid;
  }

  public void setNodeid(String nodeid) {
    this.nodeid = nodeid;
    Session session = DatabaseConnect.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getLongname() {
    return longname;
  }

  public void setLongname(String longname) {
    this.longname = longname;
    Session session = DatabaseConnect.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public Date getMovedate() {
    return movedate;
  }

  public void setMovedate(Date movedate) {
    this.movedate = movedate;
    Session session = DatabaseConnect.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
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

  public MoveEntityPK(String nodeid, String longname, Date movedate) {
    this.nodeid = nodeid;
    this.longname = longname;
    this.movedate = movedate;
  }

  public MoveEntityPK() {}

  @Override
  public int hashCode() {
    return Objects.hash(nodeid, longname, movedate);
  }
}
