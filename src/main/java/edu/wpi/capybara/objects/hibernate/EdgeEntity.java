package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.database.CSVExportable;
import edu.wpi.capybara.database.CSVImporter;
import edu.wpi.capybara.objects.orm.DAOFacade;
import edu.wpi.capybara.objects.orm.Persistent;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "edge", schema = "cdb", catalog = "teamcdb")
@IdClass(EdgeEntity.PK.class)
public class EdgeEntity implements Persistent, CSVExportable {
  // happy
  public static class PK implements Serializable {
    @Getter @Setter private NodeEntity node1;
    @Getter @Setter private NodeEntity node2;

    @Override
    public int hashCode() {
      return Objects.hash(node1, node2);
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this) return true;
      if (obj instanceof PK other) {
        return Objects.equals(node1, other.node1) && Objects.equals(node2, other.node2);
      }
      return false;
    }
  }

  private final SimpleObjectProperty<NodeEntity> node1 = new SimpleObjectProperty<>();
  private final SimpleObjectProperty<NodeEntity> node2 = new SimpleObjectProperty<>();

  public EdgeEntity() {}

  public EdgeEntity(NodeEntity node1, NodeEntity node2) {
    setNode1(node1);
    setNode2(node2);
  }

  @Deprecated(forRemoval = true)
  public EdgeEntity(String node1, String node2) {
    this(Main.getRepo().getNode(node1), Main.getRepo().getNode(node2));
  }

  @Override
  public void enablePersistence(DAOFacade orm) {
    final InvalidationListener listener = evt -> orm.merge(this);
    node1.addListener(listener);
    node2.addListener(listener);
  }

  @Id
  @ManyToOne
  @JoinColumn(name = "node1")
  public NodeEntity getNode1() {
    return node1.get();
  }

  public void setNode1(NodeEntity node1) {
    this.node1.set(node1);
  }

  public SimpleObjectProperty<NodeEntity> node1Property() {
    return node1;
  }

  @Id
  @ManyToOne
  @JoinColumn(name = "node2")
  public NodeEntity getNode2() {
    return node2.get();
  }

  public void setNode2(NodeEntity node2) {
    this.node2.set(node2);
  }

  public SimpleObjectProperty<NodeEntity> node2Property() {
    return node2;
  }

  @Transient
  public String getOtherNode(NodeEntity node) {
    if (node.getNodeID().equals(getNode1().getNodeID())) return getNode2().getNodeID();
    return getNode1().getNodeID();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    else if (obj == this) return true;
    else if (obj instanceof EdgeEntity that) {
      return Persistent.compareProperties(this, that, EdgeEntity::getNode1, EdgeEntity::getNode2);
    } else return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getNode1(), getNode2());
  }

  @Override
  public String[] toCSV() {
    return new String[] {getNode1().getNodeID(), getNode2().getNodeID()};
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
