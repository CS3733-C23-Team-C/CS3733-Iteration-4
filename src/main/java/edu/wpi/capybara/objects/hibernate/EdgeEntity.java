package edu.wpi.capybara.objects.hibernate;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "edge", schema = "cdb", catalog = "teamcdb")
@IdClass(EdgeEntityPK.class)
public class EdgeEntity {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "node1")
  private String node1;

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "node2")
  private String node2;

  public String getNode1() {
    return node1;
  }

  public void setNode1(String node1) {
    this.node1 = node1;
  }

  public String getNode2() {
    return node2;
  }

  public void setNode2(String node2) {
    this.node2 = node2;
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
}
