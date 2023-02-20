package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.objects.orm.Edge;

import java.util.ArrayList;

public interface EdgeDAO {

  ArrayList<Edge> getEdges();

  void addEdge(Edge edge);

  void deleteEdge(Edge edge);
}
