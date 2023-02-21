package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.orm.DAOFacade;
import edu.wpi.capybara.objects.orm.SecuritysubmissionEntity;

public class SecuritySubmissionDAO extends MapDAOBase<Integer, SecuritysubmissionEntity> {
  public SecuritySubmissionDAO(DAOFacade orm) {
    super(orm, SecuritysubmissionEntity.class, SecuritysubmissionEntity::getSubmissionid);
  }
}
