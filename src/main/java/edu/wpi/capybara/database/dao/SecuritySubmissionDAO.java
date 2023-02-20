package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.orm.DAOFacade;
import edu.wpi.capybara.objects.orm.SecuritySubmission;

public class SecuritySubmissionDAO extends MapDAOBase<Integer, SecuritySubmission> {
  public SecuritySubmissionDAO(DAOFacade orm) {
    super(orm, SecuritySubmission.class, SecuritySubmission::getSubmissionID);
  }
}
