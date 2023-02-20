package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.database.newDBConnect;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import edu.wpi.capybara.objects.orm.AudioSubmission;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AudiosubmissionDAOImpl implements AudiosubmissionDAO {
  HashMap<Integer, AudioSubmission> audioSubs = new HashMap();

  @Override
  public HashMap<Integer, AudioSubmission> getAudioSubs() {
    return audioSubs;
  }

  @Override
  public AudioSubmission getAudio(int id) {
    return audioSubs.get(id);
  }

  @Override
  public void addAudio(AudioSubmission submission) {
    newDBConnect.insertNew(submission);
    this.audioSubs.put(submission.getSubmissionid(), submission);
  }

  @Override
  public void deleteAudio(int id) {
    newDBConnect.delete(getAudio(id));
    audioSubs.remove(id);
  }

  public AudiosubmissionDAOImpl() {
    Session session = Main.db.getSession();
    Transaction tx = null;

    HashMap<Integer, AudioSubmission> ret = new HashMap<>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM AudiosubmissionEntity").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        AudioSubmission temp = (AudiosubmissionEntity) iterator.next();
        ret.put(temp.getSubmissionid(), temp);
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    audioSubs = ret;
  }
}
