package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.orm.CleaningSubmission;
import edu.wpi.capybara.objects.orm.DAOFacade;

public class CleaningSubmissionDAO extends MapDAOBase<Integer, CleaningSubmission> {
  public CleaningSubmissionDAO(DAOFacade orm) {
    super(orm, CleaningSubmission.class, CleaningSubmission::getSubmissionID);
  }
}
