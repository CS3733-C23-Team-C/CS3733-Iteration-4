package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.database.newDBConnect;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.wpi.capybara.objects.orm.Edge;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class EdgeDAOImpl implements EdgeDAO {
  ArrayList<Edge> edges = new ArrayList<>();

  @Override
  public ArrayList<Edge> getEdges() {
    return edges;
  }

  @Override
  public void addEdge(Edge edge) {
    newDBConnect.insertNew(edge);
    this.edges.add(edge);
  }

  @Override
  public void deleteEdge(Edge edge) {
    newDBConnect.delete(edge);
    edges.remove(edge);
  }

  public EdgeDAOImpl() {
    Session session = Main.db.getSession();
    Transaction tx = null;

    ArrayList<Edge> ret = new ArrayList<>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM EdgeEntity ").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        Edge temp = (EdgeEntity) iterator.next();
        ret.add(temp);
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    edges = ret;
  }
}
