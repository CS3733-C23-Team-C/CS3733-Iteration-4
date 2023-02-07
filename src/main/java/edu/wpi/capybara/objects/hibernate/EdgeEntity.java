package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.database.DatabaseConnect;
import jakarta.persistence.*;
import java.util.Objects;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Entity
@Table(name = "edge", schema = "cdb", catalog = "teamcdb")
@IdClass(EdgeEntityPK.class)
public class EdgeEntity {
  @Id
  @Column(name = "node1")
  private String node1;

  @Id
  @Column(name = "node2")
  private String node2;

  public String getNode1() {
    return node1;
  }

  public void setNode1(String node1) {
    this.node1 = node1;
    Session session = DatabaseConnect.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getNode2() {
    return node2;
  }

  public void setNode2(String node2) {
    this.node2 = node2;
    Session session = DatabaseConnect.getSession();
    Transaction tx = session.beginTransaction();
    session.merge(this);
    tx.commit();
    session.close();
  }

  public String getOtherNode(NodeEntity node) {
    if (node.getNodeid().equals(node1)) return node2;
    return node1;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EdgeEntity that = (EdgeEntity) o;
    return Objects.equals(node1, that.node1) && Objects.equals(node2, that.node2);
  }

  @Override
  public int hashCode() {
    return Objects.hash(node1, node2);
  }

  public void delete() {
    Session session = DatabaseConnect.getSession();
    Transaction tx = session.beginTransaction();
    DatabaseConnect.getEdges().remove(this);
    session.remove(this);
    tx.commit();
    session.close();
  }
}
