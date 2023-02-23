package edu.wpi.cs3733.C23.teamC.objects.hibernate;

import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.database.CSVExportable;
import edu.wpi.cs3733.C23.teamC.database.CSVImporter;
import edu.wpi.cs3733.C23.teamC.objects.orm.DAOFacade;
import edu.wpi.cs3733.C23.teamC.objects.orm.Persistent;
import jakarta.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "move", schema = "cdb", catalog = "teamcdb")
@IdClass(MoveEntity.PK.class)
public class MoveEntity implements Persistent, CSVExportable {
  public static class PK implements Serializable {
    @Getter @Setter private NodeEntity node;
    @Getter @Setter private LocationnameEntity location;
    @Getter @Setter private Date movedate;

    @Override
    public int hashCode() {
      return Objects.hash(node, location, movedate);
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this) return true;
      if (obj instanceof PK other) {
        return Objects.equals(node, other.node)
            && Objects.equals(location, other.location)
            && Objects.equals(movedate, other.movedate);
      }
      return false;
    }
  }

  private final SimpleObjectProperty<NodeEntity> node = new SimpleObjectProperty<>();
  private final SimpleObjectProperty<LocationnameEntity> location = new SimpleObjectProperty<>();
  private final SimpleObjectProperty<Date> movedate = new SimpleObjectProperty<>();

  public MoveEntity() {}

  public MoveEntity(NodeEntity node, LocationnameEntity location, Date moveDate) {
    setNode(node);
    setLocation(location);
    setMovedate(moveDate);
  }

  @Deprecated(forRemoval = true)
  public MoveEntity(String node, String longname, Date moveDate) {
    this(Main.getRepo().getNode(node), Main.getRepo().getLocationname(longname), moveDate);
  }

  @Override
  public void enablePersistence(DAOFacade orm) {
    final InvalidationListener listener = evt -> orm.merge(this);
    node.addListener(listener);
    location.addListener(listener);
    movedate.addListener(listener);
  }

  @Id
  @ManyToOne
  @JoinColumn(name = "nodeid")
  @Cascade(org.hibernate.annotations.CascadeType.REFRESH)
  public NodeEntity getNode() {
    return node.get();
  }

  public void setNode(NodeEntity node) {
    this.node.set(node);
  }

  public SimpleObjectProperty<NodeEntity> nodeProperty() {
    return node;
  }

  // Caused by: org.hibernate.MappingException: Column 'nodeid' is duplicated in mapping for entity
  // 'edu.wpi.cs3733.C23.teamC.objects.hibernate.MoveEntity' (use '@Column(insertable=false,
  // updatable=false)' when mapping multiple properties to the same column)
  @Transient
  public String getNodeID() {
    return getNode().getNodeID();
  }

  @Id
  @ManyToOne
  @JoinColumn(name = "longname")
  @Cascade(org.hibernate.annotations.CascadeType.REFRESH)
  public LocationnameEntity getLocation() {
    return location.get();
  }

  public void setLocation(LocationnameEntity location) {
    this.location.set(location);
  }

  public SimpleObjectProperty<LocationnameEntity> locationProperty() {
    return location;
  }

  @Transient
  public String getLongName() {
    return getLocation().getLongname();
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
          this, that, MoveEntity::getNode, MoveEntity::getLocation, MoveEntity::getLongName);
    } else return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getNode(), getLocation(), getLongName());
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
