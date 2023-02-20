package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.orm.AudioSubmission;
import edu.wpi.capybara.objects.orm.DAOFacade;

public class AudioSubmissionDAO extends MapDAOBase<Integer, AudioSubmission> {
  public AudioSubmissionDAO(DAOFacade orm) {
    super(orm, AudioSubmission.class, AudioSubmission::getSubmissionID);
  }
}
