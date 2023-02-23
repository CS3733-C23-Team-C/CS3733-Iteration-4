package edu.wpi.cs3733.C23.teamC.database.dao;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.SecuritysubmissionEntity;
import edu.wpi.cs3733.C23.teamC.objects.orm.DAOFacade;

public class SecuritySubmissionDAO extends MapDAOBase<Integer, SecuritysubmissionEntity> {
  public SecuritySubmissionDAO(DAOFacade orm) {
    super(orm, SecuritysubmissionEntity.class, SecuritysubmissionEntity::getSubmissionid);
  }
}
