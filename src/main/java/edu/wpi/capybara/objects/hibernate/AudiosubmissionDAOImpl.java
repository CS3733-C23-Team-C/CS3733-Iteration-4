package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.Main;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AudiosubmissionDAOImpl implements AudiosubmissionDAO {
  HashMap<Integer, AudiosubmissionEntity> audioSubs = new HashMap();

  @Override
  public HashMap<Integer, AudiosubmissionEntity> getAudioSubs() {
    Session session = Main.db.getSession();
    Transaction tx = null;

    HashMap<Integer, AudiosubmissionEntity> ret = new HashMap<Integer, AudiosubmissionEntity>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM AudiosubmissionEntity").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        AudiosubmissionEntity temp = (AudiosubmissionEntity) iterator.next();
        ret.put(temp.getSubmissionid(), temp);
        if (Main.db.getID() < temp.getSubmissionid()) {
          Main.db.setID(temp.getSubmissionid());
        }
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    return ret;
  }

  @Override
  public AudiosubmissionEntity getAudio(int id) {
    return audioSubs.get(id);
  }

  @Override
  public void addAudio(AudiosubmissionEntity submission) {
    newDBConnect.insertNew(submission);
    this.audioSubs.put(submission.getSubmissionid(), submission);
  }

  @Override
  public void deleteAudio(int id) {
    newDBConnect.delete(getAudio(id));
    audioSubs.remove(id);
  }

  public AudiosubmissionDAOImpl(HashMap<Integer, AudiosubmissionEntity> audioSubs) {
    this.audioSubs = audioSubs;
  }
}
