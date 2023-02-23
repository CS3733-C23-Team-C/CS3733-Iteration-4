package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.database.CSVExportable;
import edu.wpi.capybara.database.CSVImporter;
import edu.wpi.capybara.objects.orm.DAOFacade;
import edu.wpi.capybara.objects.orm.Persistent;
import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "move", schema = "cdb", catalog = "teamcdb")
@IdClass(MoveEntity.PK.class)
public class MoveEntity implements Persistent, CSVExportable {
  public static class PK implements Serializable {
    @Getter @Setter private String nodeID;
    @Getter @Setter private String longName;
    @Getter @Setter private Date movedate;

    @Override
    public int hashCode() {
      return Objects.hash(nodeID, longName, movedate);
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this) return true;
      if (obj instanceof PK other) {
        return Objects.equals(nodeID, other.nodeID)
            && Objects.equals(longName, other.longName)
            && Objects.equals(movedate, other.movedate);
      }
      return false;
    }
  }

  private final SimpleStringProperty nodeID = new SimpleStringProperty();
  private final SimpleStringProperty longName = new SimpleStringProperty();
  private final SimpleObjectProperty<Date> movedate = new SimpleObjectProperty<>();

  public MoveEntity() {}

  public MoveEntity(NodeEntity node, LocationnameEntity location, Date moveDate) {
    this(node.getNodeID(), location.getLongname(), moveDate);
  }

  public MoveEntity(String node, String longname, Date moveDate) {
    setNodeID(node);
    setLongName(longname);
    setMovedate(moveDate);
  }

  @Override
  public void enablePersistence(DAOFacade orm) {
    final InvalidationListener listener = evt -> orm.merge(this);
    nodeID.addListener(listener);
    longName.addListener(listener);
    movedate.addListener(listener);
  }

  @Id
  @Column(name = "nodeid")
  @Cascade(org.hibernate.annotations.CascadeType.REFRESH)
  public String getNodeID() {
    return nodeID.get();
  }

  public void setNodeID(String nodeID) {
    this.nodeID.set(nodeID);
  }

  public SimpleStringProperty nodeIDProperty() {
    return nodeID;
  }

  @Transient
  public NodeEntity getNode() {
    return Main.getRepo().getNode(getNodeID());
  }

  @Id
  @Column(name = "longname")
  @Cascade(org.hibernate.annotations.CascadeType.REFRESH)
  public String getLongName() {
    return longName.get();
  }

  public void setLongName(String longName) {
    this.longName.set(longName);
  }

  public SimpleStringProperty longNameProperty() {
    return longName;
  }

  @Transient
  public LocationnameEntity getLocationName() {
    return Main.getRepo().getLocationname(getLongName());
  }

  @Id
  @Column(name = "movedate")
  public Date getMovedate() {
    return movedate.get();
  }

  public SimpleObjectProperty<Date> movedateProperty() {
    return movedate;
  }

  public void setMovedate(Date movedate) {
    this.movedate.set(movedate);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    else if (obj == this) return true;
    else if (obj instanceof MoveEntity that) {
      return Persistent.compareProperties(
          this, that, MoveEntity::getNodeID, MoveEntity::getLongName, MoveEntity::getMovedate);
    } else return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getNodeID(), getLongName(), getMovedate());
  }

  @Override
  public String[] toCSV() {
    return new String[] {getNodeID(), getLongName(), getMovedate().toString()};
  }

  public static class Importer implements CSVImporter<MoveEntity> {
    @Override
    public MoveEntity fromCSV(String[] csv) {

      String nodeid = csv[0];
      String longname = csv[1];

      java.sql.Date movedate;
      try {
        String startDate = csv[2];
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = sdf1.parse(startDate);
        movedate = new Date(date.getTime());
      } catch (ParseException e) {
        throw new IllegalArgumentException(e);
      }

      return new MoveEntity(nodeid, longname, movedate);
    }
  }
}
