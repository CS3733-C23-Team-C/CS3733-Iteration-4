package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.orm.ComputerSubmission;
import edu.wpi.capybara.objects.orm.DAOFacade;

public class ComputerSubmissionDAO extends MapDAOBase<Integer, ComputerSubmission> {
  public ComputerSubmissionDAO(DAOFacade orm) {
    super(orm, ComputerSubmission.class, ComputerSubmission::getSubmissionID);
  }
}
