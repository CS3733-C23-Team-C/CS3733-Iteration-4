package edu.wpi.cs3733.C23.teamC.database.dao;

import edu.wpi.cs3733.C23.teamC.database.hibernate.CleaningsubmissionEntity;
import edu.wpi.cs3733.C23.teamC.database.orm.DAOFacade;

public class CleaningSubmissionDAO extends MapDAOBase<Integer, CleaningsubmissionEntity> {
  public CleaningSubmissionDAO(DAOFacade orm) {
    super(orm, CleaningsubmissionEntity.class, CleaningsubmissionEntity::getSubmissionid);
  }
}
