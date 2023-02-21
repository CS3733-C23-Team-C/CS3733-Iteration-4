package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.orm.DAOFacade;
import edu.wpi.capybara.objects.orm.TransportationsubmissionEntity;

public class TransportationSubmissionDAO
    extends MapDAOBase<Integer, TransportationsubmissionEntity> {
  public TransportationSubmissionDAO(DAOFacade orm) {
    super(
        orm, TransportationsubmissionEntity.class, TransportationsubmissionEntity::getSubmissionid);
  }
}
