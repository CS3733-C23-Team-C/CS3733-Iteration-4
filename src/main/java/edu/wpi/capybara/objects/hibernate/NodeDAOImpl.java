package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.database.newDBConnect;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class NodeDAOImpl implements NodeDAO {
  HashMap<String, NodeEntity> nodes = new HashMap();

  @Override
  public HashMap<String, NodeEntity> getNodes() {
    return nodes;
  }

  @Override
  public NodeEntity getNode(String nodeid) {
    return nodes.get(nodeid);
  }

  @Override
  public void addNode(NodeEntity submission) {
    // Main.db.addNode(submission);
    newDBConnect.insertNew(submission);
    this.nodes.put(submission.getNodeid(), submission);
  }

  public NodeDAOImpl() {
    Session session = Main.db.getSession();
    Transaction tx = null;

    HashMap<String, NodeEntity> ret = new HashMap<String, NodeEntity>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM NodeEntity ").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        NodeEntity temp = (NodeEntity) iterator.next();
        ret.put(temp.getNodeid(), temp);
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    nodes = ret;
  }

  @Override
  public void deleteNode(String id) {
    newDBConnect.delete(getNode(id));
    nodes.remove(id);
  }
}
