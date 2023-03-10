package edu.wpi.cs3733.C23.teamC.database.hibernate;

import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.database.newDBConnect;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

  public EdgeDAOImpl() {
    Session session = Main.db.getSession();
    Transaction tx = null;

    ArrayList<EdgeEntity> ret = new ArrayList<>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM EdgeEntity ").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        EdgeEntity temp = (EdgeEntity) iterator.next();
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
