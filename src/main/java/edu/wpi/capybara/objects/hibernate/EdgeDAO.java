package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.objects.orm.EdgeEntity;
import java.util.ArrayList;

public interface EdgeDAO {

  ArrayList<EdgeEntity> getEdges();

  void addEdge(EdgeEntity edge);

  void deleteEdge(EdgeEntity edge);
}
