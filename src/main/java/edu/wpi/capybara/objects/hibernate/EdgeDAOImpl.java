package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.database.DatabaseConnect;

import java.util.ArrayList;
import java.util.HashMap;

public class EdgeDAOImpl implements EdgeDAO{
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
        edges.remove(edge);
        newDBConnect.delete(edge);
    }
    public EdgeDAOImpl(ArrayList<EdgeEntity> edges) {
        this.edges = edges;
    }
}
