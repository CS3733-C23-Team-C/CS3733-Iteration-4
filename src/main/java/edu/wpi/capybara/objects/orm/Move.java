package edu.wpi.capybara.objects.orm;

import jakarta.persistence.*;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "move", schema = "cdb", catalog = "teamcdb")
@IdClass(Move.PK.class)
public class Move {
  public static class PK implements Serializable {
    @Getter @Setter private Node node;
    @Getter @Setter private Location location;
    @Getter @Setter private Date moveDate;

    @Override
    public int hashCode() {
      return Objects.hash(node, location, moveDate);
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this) return true;
      if (obj instanceof PK other) {
        return Objects.equals(node, other.node)
            && Objects.equals(location, other.location)
            && Objects.equals(moveDate, other.moveDate);
      }
      return false;
    }
  }

  private final SimpleObjectProperty<Node> node = new SimpleObjectProperty<>();
  private final SimpleObjectProperty<Location> location = new SimpleObjectProperty<>();
  private final SimpleObjectProperty<Date> moveDate = new SimpleObjectProperty<>();

  protected Move() {}

  static Move createPersistent(DAOFacade orm, Node node, Location location, Date moveDate) {
    final var newMove = new Move();
    newMove.setNode(node);
    newMove.setLocation(location);
    newMove.setMoveDate(moveDate);
    newMove.enableAutomaticPersistence(orm);
    return newMove;
  }

  void enableAutomaticPersistence(DAOFacade orm) {
    final InvalidationListener listener = evt -> orm.merge(this);
    node.addListener(listener);
    location.addListener(listener);
    moveDate.addListener(listener);
  }

  @Id
  @ManyToOne
  @JoinColumn(name = "nodeid")
  public Node getNode() {
    return node.get();
  }

  public void setNode(Node node) {
    this.node.set(node);
  }

  public SimpleObjectProperty<Node> nodeProperty() {
    return node;
  }

  @Id
  @ManyToOne
  @JoinColumn(name = "longname")
  public Location getLocation() {
    return location.get();
  }

  public void setLocation(Location location) {
    this.location.set(location);
  }

  public SimpleObjectProperty<Location> locationProperty() {
    return location;
  }

  @Id
  @Column(name = "movedate")
  public Date getMoveDate() {
    return moveDate.get();
  }

  public SimpleObjectProperty<Date> moveDateProperty() {
    return moveDate;
  }

  public void setMoveDate(Date moveDate) {
    this.moveDate.set(moveDate);
  }
}
