package edu.wpi.cs3733.C23.teamC.objects.hibernate;

import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.database.CSVExportable;
import edu.wpi.cs3733.C23.teamC.database.CSVImporter;
import edu.wpi.cs3733.C23.teamC.objects.orm.DAOFacade;
import edu.wpi.cs3733.C23.teamC.objects.orm.Persistent;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "edge", schema = "cdb", catalog = "teamcdb")
@IdClass(EdgeEntity.PK.class)
public class EdgeEntity implements Persistent, CSVExportable {
  // happy
  public static class PK implements Serializable {
    @Getter @Setter private String node1ID;
    @Getter @Setter private String node2ID;

    @Override
    public int hashCode() {
      return Objects.hash(node1ID, node2ID);
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this) return true;
      if (obj instanceof PK other) {
        return Objects.equals(node1ID, other.node1ID) && Objects.equals(node2ID, other.node2ID);
      }
      return false;
    }
  }

  private final SimpleStringProperty node1ID = new SimpleStringProperty();
  private final SimpleStringProperty node2ID = new SimpleStringProperty();

  public EdgeEntity() {}

  public EdgeEntity(NodeEntity node1, NodeEntity node2) {
    this(node1.getNodeID(), node2.getNodeID());
  }

  public EdgeEntity(String node1ID, String node2ID) {
    setNode1ID(node1ID);
    setNode2ID(node2ID);
  }

  @Override
  public void enablePersistence(DAOFacade orm) {
    final InvalidationListener listener = evt -> orm.merge(this);
    node1ID.addListener(listener);
    node2ID.addListener(listener);
  }

  @Id
  @Column(name = "node1")
  public String getNode1ID() {
    return node1ID.get();
  }

  public void setNode1ID(String node1ID) {
    this.node1ID.set(node1ID);
  }

  public SimpleStringProperty node1IDProperty() {
    return node1ID;
  }

  @Transient
  public NodeEntity getNode1() {
    return Main.getRepo().getNode(getNode1ID());
  }

  public void setNode1(NodeEntity entity) {
    setNode1ID(entity.getNodeID());
  }

  @Id
  @Column(name = "node2")
  public String getNode2ID() {
    return node2ID.get();
  }

  public void setNode2ID(String node2ID) {
    this.node2ID.set(node2ID);
  }

  public SimpleStringProperty node2IDProperty() {
    return node2ID;
  }

  @Transient
  public NodeEntity getNode2() {
    return Main.getRepo().getNode(getNode2ID());
  }

  public void setNode2(NodeEntity entity) {
    setNode2ID(entity.getNodeID());
  }

  @Transient
  public String getOtherNode(NodeEntity node) {
    return getOtherNode(node.getNodeID());
  }

  @Transient
  public String getOtherNode(String node) {
    if (node.equals(getNode1ID())) return getNode2ID();
    return getNode1ID();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    else if (obj == this) return true;
    else if (obj instanceof EdgeEntity that) {
      return Persistent.compareProperties(
          this, that, EdgeEntity::getNode1ID, EdgeEntity::getNode2ID);
    } else return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getNode1(), getNode2());
  }

  @Override
  public String[] toCSV() {
    return new String[] {getNode1ID(), getNode2ID()};
  }

  public static class Importer implements CSVImporter<EdgeEntity> {
    @Override
    public EdgeEntity fromCSV(String[] csv) {

      String node1 = csv[0];
      String node2 = csv[1];

      return new EdgeEntity(node1, node2);
    }
  }
}
