package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.objects.submissions.submissionStatus;

import java.util.HashMap;

public interface AudiosubmissionDAO {

    HashMap<Integer, AudiosubmissionEntity> getAudioSubs();
    AudiosubmissionEntity getAudio(int id);
    void addAudio(AudiosubmissionEntity submission);
    void deleteAudio(int id);
}
