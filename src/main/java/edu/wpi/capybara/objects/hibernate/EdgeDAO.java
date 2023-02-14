package edu.wpi.capybara.objects.hibernate;

import java.util.ArrayList;
import java.util.HashMap;

public interface EdgeDAO {

    ArrayList<EdgeEntity> getEdges();
    void addEdge(EdgeEntity edge);
    void deleteEdge(EdgeEntity edge);
}
