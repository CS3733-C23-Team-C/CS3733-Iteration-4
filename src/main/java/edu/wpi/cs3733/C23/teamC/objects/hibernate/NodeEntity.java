package edu.wpi.cs3733.C23.teamC.objects.hibernate;

import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.database.CSVExportable;
import edu.wpi.cs3733.C23.teamC.database.CSVImporter;
import edu.wpi.cs3733.C23.teamC.objects.Floor;
import edu.wpi.cs3733.C23.teamC.objects.orm.DAOFacade;
import edu.wpi.cs3733.C23.teamC.objects.orm.Persistent;
import jakarta.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

@Entity
@Table(name = "node", schema = "cdb", catalog = "teamcdb")
public class NodeEntity implements Persistent, CSVExportable {
  private final SimpleStringProperty nodeID = new SimpleStringProperty();
  private final SimpleIntegerProperty xcoord = new SimpleIntegerProperty();
  private final SimpleIntegerProperty ycoord = new SimpleIntegerProperty();
  private final SimpleObjectProperty<Floor> floor = new SimpleObjectProperty<>();
  private final SimpleStringProperty building = new SimpleStringProperty();

  public NodeEntity() {}

  public NodeEntity(String nodeid, Integer xcoord, Integer ycoord, String floor, String building) {
    setNodeID(nodeid);
    setXcoord(xcoord);
    setYcoord(ycoord);
    setFloor(Floor.fromString(floor));
    setBuilding(building);
  }

  private InvalidationListener listener;

  @Override
  public void enablePersistence(DAOFacade orm) {
    listener = evt -> orm.mergeOnlyWhenManual(this);
    nodeID.addListener(listener);
    xcoord.addListener(listener);
    ycoord.addListener(listener);
    floor.addListener(listener);
    building.addListener(listener);
    // xcoord.addListener(evt -> repairID());
    // ycoord.addListener(evt -> repairID());
  }

  @Override
  public void disablePersistence() {
    if (listener != null) {
      nodeID.removeListener(listener);
      xcoord.removeListener(listener);
      ycoord.removeListener(listener);
      floor.removeListener(listener);
      building.removeListener(listener);
      listener = null;
    }
  }

  @Id
  @Column(name = "nodeid")
  public String getNodeID() {
    return nodeID.get();
  }

  public void setNodeID(String nodeID) {
    this.nodeID.set(nodeID);
  }

  public SimpleStringProperty nodeIDProperty() {
    return nodeID;
  }

  @Basic
  @Column(name = "xcoord")
  public int getXcoord() {
    return xcoord.get();
  }

  public void setXcoord(int xCoord) {
    this.xcoord.set(xCoord);
  }

  public SimpleIntegerProperty xcoordProperty() {
    return xcoord;
  }

  @Basic
  @Column(name = "ycoord")
  public int getYcoord() {
    return ycoord.get();
  }

  public void setYcoord(int yCoord) {
    this.ycoord.set(yCoord);
  }

  public SimpleIntegerProperty ycoordProperty() {
    return ycoord;
  }

  @Basic
  @Convert(converter = Floor.SQLConverter.class)
  @Column(name = "floor")
  public Floor getFloor() {
    return floor.get();
  }

  public void setFloor(Floor floor) {
    this.floor.set(floor);
  }

  public SimpleObjectProperty<Floor> floorProperty() {
    return floor;
  }

  @Basic
  @Column(name = "building")
  public String getBuilding() {
    return building.get();
  }

  public void setBuilding(String building) {
    this.building.set(building);
  }

  public SimpleStringProperty buildingProperty() {
    return building;
  }

  @Transient
  public HashSet<EdgeEntity> getEdges() {
    HashSet<EdgeEntity> ret = new HashSet<>();

    for (EdgeEntity e : Main.db.getEdges()) {
      if (e.getNode1ID().equals(getNodeID()) || e.getNode2ID().equals(getNodeID())) {
        ret.add(e);
      }
    }
    return ret;
  }

  @Transient
  public String getShortName() {
    List<MoveEntity> moves = Main.db.getMoves();
    Date temp = new Date((long) 0);
    String longname = null;
    for (MoveEntity m : moves) {
      if (m.getNodeID().equals(getNodeID())) {
        if (m.getMovedate().compareTo(temp) > 0) {
          // System.out.println("select!");
          temp = m.getMovedate();
          longname = m.getLongName();
        }
      }
    }
    if (longname == null) {
      return "NA";
    }
    Map<String, LocationnameEntity> locations = Main.db.getLocationnames();
    for (LocationnameEntity location : locations.values()) {
      if (longname.equals(location.getLongname())) {
        return location.getShortname();
      }
    }

    return null;
  }

  @Transient
  public String getLongName() {
    List<MoveEntity> moves = Main.db.getMoves();
    Date temp = new Date((long) 0);
    String longname = null;
    for (MoveEntity m : moves) {
      if (m.getNodeID().equals(getNodeID())) {
        if (m.getMovedate().compareTo(temp) > 0) {
          // System.out.println("select!");
          temp = m.getMovedate();
          longname = m.getLongName();
        }
      }
    }
    return longname;
  }

  @Transient
  public String getLocationType() {
    List<MoveEntity> moves = Main.db.getMoves();
    Date temp = new Date((long) 0);
    String longname = null;
    for (MoveEntity m : moves) {
      if (m.getNodeID().equals(getNodeID())) {
        if (m.getMovedate().compareTo(temp) > 0) {
          // System.out.println("select!");
          temp = m.getMovedate();
          longname = m.getLongName();
        }
      }
    }
    if (longname == null) {
      return "NA";
    }
    Map<String, LocationnameEntity> locations = Main.db.getLocationnames();
    for (LocationnameEntity location : locations.values()) {
      if (longname.equals(location.getLongname())) {
        return location.getLocationtype();
      }
    }

    return null;
  }

  private void repairID() {
    // this was way simpler than I thought it was going to be.
    // this works because setting the node id triggers a DB merge after the change, thus creating a
    // new node in the
    // DB and updating all references to it. this leaves behind the old node, which can be deleted
    // without any foreign
    // key issues.
    var prevID = getNodeID();
    setNodeID(String.format("%sX%dY%d", getFloor().toString(), getXcoord(), getYcoord()));
    Main.getRepo().deleteNode(prevID);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    else if (obj == this) return true;
    else if (obj instanceof NodeEntity that) {
      return Persistent.compareProperties(
          this,
          that,
          NodeEntity::getNodeID,
          NodeEntity::getXcoord,
          NodeEntity::getYcoord,
          NodeEntity::getFloor,
          NodeEntity::getBuilding);
    } else return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getNodeID(), getXcoord(), getYcoord(), getFloor(), getBuilding());
  }

  @Override
  public String[] toCSV() {
    return new String[] {
      getNodeID(),
      Integer.toString(getXcoord()),
      Integer.toString(getYcoord()),
      getFloor().toString(),
      getBuilding()
    };
  }

  public static class Importer implements CSVImporter<NodeEntity> {
    @Override
    public NodeEntity fromCSV(String[] csv) {

      String nodeid = csv[0];
      int xcoord = Integer.parseInt(csv[1]);
      int ycoord = Integer.parseInt(csv[2]);
      String floor = csv[3];
      String building = csv[4];

      return new NodeEntity(nodeid, xcoord, ycoord, floor, building);
    }
  }
}
