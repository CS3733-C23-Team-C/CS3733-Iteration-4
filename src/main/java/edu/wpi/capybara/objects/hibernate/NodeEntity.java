package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.database.DatabaseConnect;
import jakarta.persistence.*;
import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

@Entity
@Table(name = "node", schema = "cdb", catalog = "teamcdb")
public class NodeEntity {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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

  public String getNodeid() {
    return nodeid;
  }

  public void setNodeid(String nodeid) {
    this.nodeid = nodeid;
  }

  public Integer getXcoord() {
    return xcoord;
  }

  public void setXcoord(Integer xcoord) {
    this.xcoord = xcoord;
  }

  public Integer getYcoord() {
    return ycoord;
  }

  public void setYcoord(Integer ycoord) {
    this.ycoord = ycoord;
  }

  public String getFloor() {
    return floor;
  }

  public void setFloor(String floor) {
    this.floor = floor;
  }

  public String getBuilding() {
    return building;
  }

  public void setBuilding(String building) {
    this.building = building;
  }

  public String getShortName() {
    HashMap<Integer, MoveEntity> moves = DatabaseConnect.getMoves();
    Date temp = new Date((long) 0);
    String longname = null;
    for (MoveEntity m : moves.values()) {
      if (m.getNodeid().equals(this.nodeid)) {
        if (m.getMovedate().compareTo(temp) > 0) {
          // System.out.println("select!");
          temp = m.getMovedate();
          longname = m.getLongname();
        }
      }
    }

    HashMap<Integer, LocationnameEntity> locations = DatabaseConnect.getLocationNames();
    for (LocationnameEntity location : locations.values()) {
      if (longname.equals(location.getLongname())) {
        return location.getShortname();
      }
    }

    return null;
  }

  public HashSet<EdgeEntity> getEdges() {
    HashSet<EdgeEntity> ret = new HashSet<EdgeEntity>();

    for (EdgeEntity e : DatabaseConnect.getEdges().values()) {
      if (e.getNode1().equals(nodeid) || e.getNode2().equals(nodeid)) {
        ret.add(e);
      }
    }
    return ret;
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
