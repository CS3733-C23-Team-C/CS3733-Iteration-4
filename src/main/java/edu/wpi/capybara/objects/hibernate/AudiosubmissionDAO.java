package edu.wpi.capybara.objects.hibernate;

import java.util.HashMap;

public interface AudiosubmissionDAO {

  HashMap<Integer, AudiosubmissionEntity> getAudioSubs();

  AudiosubmissionEntity getAudio(int id);

  void addAudio(AudiosubmissionEntity submission);

  void deleteAudio(int id);
}
