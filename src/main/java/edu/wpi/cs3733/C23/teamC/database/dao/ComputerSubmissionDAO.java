package edu.wpi.cs3733.C23.teamC.database.dao;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.ComputersubmissionEntity;
import edu.wpi.cs3733.C23.teamC.objects.orm.DAOFacade;

public class ComputerSubmissionDAO extends MapDAOBase<Integer, ComputersubmissionEntity> {
  public ComputerSubmissionDAO(DAOFacade orm) {
    super(orm, ComputersubmissionEntity.class, ComputersubmissionEntity::getSubmissionid);
  }
}
