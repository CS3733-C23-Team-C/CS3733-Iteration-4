package edu.wpi.capybara.objects.orm;

import edu.wpi.capybara.Main;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "edge", schema = "cdb", catalog = "teamcdb")
@IdClass(Edge.PK.class)
public class Edge implements Persistent {
  public static class PK implements Serializable {
    @Getter @Setter private Node startNode;
    @Getter @Setter private Node endNode;

    @Override
    public int hashCode() {
      return Objects.hash(startNode, endNode);
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this) return true;
      if (obj instanceof PK other) {
        return Objects.equals(startNode, other.startNode) && Objects.equals(endNode, other.endNode);
      }
      return false;
    }
  }

  private final SimpleObjectProperty<Node> startNode = new SimpleObjectProperty<>();
  private final SimpleObjectProperty<Node> endNode = new SimpleObjectProperty<>();

  public Edge() {}

  public Edge(Node node1, Node node2) {
    setStartNode(node1);
    setEndNode(node2);
  }

  @Deprecated(forRemoval = true)
  public Edge(String node1, String node2) {
    this(Main.getRepo().getNode(node1), Main.getRepo().getNode(node2));
  }

  @Override
  public void enablePersistence(DAOFacade orm) {
    final InvalidationListener listener = evt -> orm.merge(this);
    startNode.addListener(listener);
    endNode.addListener(listener);
  }

  @Id
  @ManyToOne
  @JoinColumn(name = "node1")
  public Node getStartNode() {
    return startNode.get();
  }

  public void setStartNode(Node startNode) {
    this.startNode.set(startNode);
  }

  public SimpleObjectProperty<Node> startNodeProperty() {
    return startNode;
  }

  @Id
  @ManyToOne
  @JoinColumn(name = "node2")
  public Node getEndNode() {
    return endNode.get();
  }

  public void setEndNode(Node endNode) {
    this.endNode.set(endNode);
  }

  public SimpleObjectProperty<Node> endNodeProperty() {
    return endNode;
  }
}
