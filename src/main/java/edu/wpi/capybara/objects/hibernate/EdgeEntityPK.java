package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.database.DatabaseConnect;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.util.Objects;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class EdgeEntityPK implements Serializable {
  @Column(name = "node1")
  @Id
  private String node1;

  @Column(name = "node2")
  @Id
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    EdgeEntityPK that = (EdgeEntityPK) o;
    return Objects.equals(node1, that.node1) && Objects.equals(node2, that.node2);
  }

  @Override
  public int hashCode() {
    return Objects.hash(node1, node2);
  }
}
