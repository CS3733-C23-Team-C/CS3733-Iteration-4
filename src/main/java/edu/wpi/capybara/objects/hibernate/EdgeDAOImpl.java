package edu.wpi.capybara.objects.hibernate;

import java.util.ArrayList;

public class EdgeDAOImpl implements EdgeDAO {
  ArrayList<EdgeEntity> edges = new ArrayList<>();

  @Override
  public ArrayList<EdgeEntity> getEdges() {
    return edges;
  }

  @Override
  public void addEdge(EdgeEntity edge) {
    newDBConnect.insertNew(edge);
    this.edges.add(edge);
  }

  @Override
  public void deleteEdge(EdgeEntity edge) {
    newDBConnect.delete(edge);
    edges.remove(edge);
  }

  public EdgeDAOImpl(ArrayList<EdgeEntity> edges) {
    this.edges = edges;
  }
}
