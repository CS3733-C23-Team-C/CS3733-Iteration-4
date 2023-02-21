package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.database.newDBConnect;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class MoveDAOImpl implements MoveDAO {
  ArrayList<MoveEntity> moves = new ArrayList<>();

  @Override
  public ArrayList<MoveEntity> getMoves() {
    return moves;
  }

  @Override
  public void addMove(MoveEntity submission) {
    newDBConnect.insertNew(submission);
    this.moves.add(submission);
  }

  public MoveDAOImpl() {
    Session session = Main.db.getSession();
    Transaction tx = null;

    ArrayList<MoveEntity> ret = new ArrayList<>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM MoveEntity ").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        MoveEntity temp = (MoveEntity) iterator.next();
        ret.add(temp);
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    moves = ret;
  }

  @Override
  public void deleteMove(MoveEntity move) {
    newDBConnect.delete(move);
    moves.remove(move);
  }
}
