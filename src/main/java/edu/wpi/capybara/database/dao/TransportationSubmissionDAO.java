package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.orm.DAOFacade;
import edu.wpi.capybara.objects.orm.TransportationSubmission;

public class TransportationSubmissionDAO extends MapDAOBase<Integer, TransportationSubmission> {
  public TransportationSubmissionDAO(DAOFacade orm) {
    super(orm, TransportationSubmission.class, TransportationSubmission::getSubmissionID);
  }
}
