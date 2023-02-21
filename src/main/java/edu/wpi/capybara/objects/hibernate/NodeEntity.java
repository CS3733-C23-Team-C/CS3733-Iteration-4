package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.Main;
import jakarta.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Entity
@Table(name = "node", schema = "cdb", catalog = "teamcdb")
public class NodeEntity {

  @Id
  @Column(name = "nodeid")
  private String nodeid;

  @Basic
  @Column(name = "xcoord")
  private Integer xcoord;

  @Basic
  @Column(name = "ycoord")
  private Integer ycoord;

  @Basic
  @Column(name = "floor")
  private String floor;

  @Basic
  @Column(name = "building")
  private String building;

  public NodeEntity() {}

  public NodeEntity(String nodeid, Integer xcoord, Integer ycoord, String floor, String building) {
    this.nodeid = nodeid;
    this.xcoord = xcoord;
    this.ycoord = ycoord;
    this.floor = floor;
    this.building = building;
  }

  public String getNodeid() {
    return nodeid;
  }

  public void setNodeid(String nodeid) {
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    this.nodeid = nodeid;
    session.merge(this);
    tx.commit();
    session.close();
  }

  public Integer getXcoord() {
    return xcoord;
  }

  public void setXcoord(Integer xcoord) {
    this.xcoord = xcoord;
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public Integer getYcoord() {
    return ycoord;
  }

  public void setYcoord(Integer ycoord) {
    this.ycoord = ycoord;
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getFloor() {
    return floor;
  }

  public void setFloor(String floor) {
    this.floor = floor;
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getBuilding() {
    return building;
  }

  public void setBuilding(String building) {
    this.building = building;
    Session session = Main.db.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getShortName() {
    ArrayList<MoveEntity> moves = Main.db.getMoves();
    Date temp = new Date((long) 0);
    String longname = null;
    for (MoveEntity m : moves) {
      if (m.getNodeid().equals(this.nodeid)) {
        if (m.getMovedate().compareTo(temp) > 0) {
          // System.out.println("select!");
          temp = m.getMovedate();
          longname = m.getLongname();
        }
      }
    }
    if (longname == null) {
      return "NA";
    }
    HashMap<String, LocationnameEntity> locations = Main.db.getLocationnames();
    for (LocationnameEntity location : locations.values()) {
      if (longname.equals(location.getLongname())) {
        return location.getShortname();
      }
    }

    return null;
  }

  public String getLongname() {
    ArrayList<MoveEntity> moves = Main.db.getMoves();
    Date temp = new Date((long) 0);
    String longname = null;
    for (MoveEntity m : moves) {
      if (m.getNodeid().equals(this.nodeid)) {
        if (m.getMovedate().compareTo(temp) > 0) {
          // System.out.println("select!");
          temp = m.getMovedate();
          longname = m.getLongname();
        }
      }
    }
    return longname;
  }

  public String getLocationType() {
    ArrayList<MoveEntity> moves = Main.db.getMoves();
    Date temp = new Date((long) 0);
    String longname = null;
    for (MoveEntity m : moves) {
      if (m.getNodeid().equals(this.nodeid)) {
        if (m.getMovedate().compareTo(temp) > 0) {
          // System.out.println("select!");
          temp = m.getMovedate();
          longname = m.getLongname();
        }
      }
    }
    if (longname == null) {
      return "NA";
    }
    HashMap<String, LocationnameEntity> locations = Main.db.getLocationnames();
    for (LocationnameEntity location : locations.values()) {
      if (longname.equals(location.getLongname())) {
        return location.getLocationtype();
      }
    }

    return null;
  }

  public HashSet<EdgeEntity> getEdges() {
    HashSet<EdgeEntity> ret = new HashSet<EdgeEntity>();

    for (EdgeEntity e : Main.db.getEdges()) {
      if (e.getNode1().equals(nodeid) || e.getNode2().equals(nodeid)) {
        ret.add(e);
      }
    }
    return ret;
  }

  public String toString() {
    return nodeid
        + " | "
        + getShortName()
        + " | "
        + floor
        + " | x="
        + xcoord.toString()
        + " | y="
        + ycoord.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    NodeEntity that = (NodeEntity) o;
    return Objects.equals(nodeid, that.nodeid)
        && Objects.equals(xcoord, that.xcoord)
        && Objects.equals(ycoord, that.ycoord)
        && Objects.equals(floor, that.floor)
        && Objects.equals(building, that.building);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nodeid, xcoord, ycoord, floor, building);
  }
}
