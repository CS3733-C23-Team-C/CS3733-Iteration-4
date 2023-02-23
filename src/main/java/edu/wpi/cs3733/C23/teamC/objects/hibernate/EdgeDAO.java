package edu.wpi.cs3733.C23.teamC.objects.hibernate;

import java.util.ArrayList;

public interface EdgeDAO {

  ArrayList<EdgeEntity> getEdges();

  void addEdge(EdgeEntity edge);

  void deleteEdge(EdgeEntity edge);
}
