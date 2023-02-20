package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.objects.orm.AudioSubmission;

import java.util.HashMap;

public interface AudiosubmissionDAO {

  HashMap<Integer, AudioSubmission> getAudioSubs();

  AudioSubmission getAudio(int id);

  void addAudio(AudioSubmission submission);

  void deleteAudio(int id);
}
