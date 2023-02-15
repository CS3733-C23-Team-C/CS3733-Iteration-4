package edu.wpi.capybara.objects.hibernate;

import java.util.HashMap;

public class AudiosubmissionDAOImpl implements AudiosubmissionDAO {
  HashMap<Integer, AudiosubmissionEntity> audioSubs = new HashMap();

  @Override
  public HashMap<Integer, AudiosubmissionEntity> getAudioSubs() {
    return audioSubs;
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
