package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.hibernate.CleaningsubmissionEntity;
import edu.wpi.capybara.objects.orm.DAOFacade;

public class CleaningSubmissionDAO extends MapDAOBase<Integer, CleaningsubmissionEntity> {
  public CleaningSubmissionDAO(DAOFacade orm) {
    super(orm, CleaningsubmissionEntity.class, CleaningsubmissionEntity::getSubmissionid);
  }
}
