package edu.wpi.capybara.objects.orm;

import jakarta.persistence.*;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "edge", schema = "cdb", catalog = "teamcdb")
@IdClass(Edge.PK.class)
public class Edge {
    public static class PK implements Serializable {
        @Getter
        @Setter
        private Node startNode;
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

    static Edge createPersistent(DAOFacade orm, Node startNode, Node endNode) {
        final var newEdge = new Edge();
        newEdge.setStartNode(startNode);
        newEdge.setEndNode(endNode);
        newEdge.enableAutomaticPersistence(orm);
        return newEdge;
    }

    void enableAutomaticPersistence(DAOFacade orm) {
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
