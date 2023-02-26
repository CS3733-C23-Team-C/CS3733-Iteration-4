package edu.wpi.cs3733.C23.teamC.database.dao;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.TransportationsubmissionEntity;
import edu.wpi.cs3733.C23.teamC.objects.orm.DAOFacade;

public class TransportationSubmissionDAO
    extends MapDAOBase<Integer, TransportationsubmissionEntity> {
  public TransportationSubmissionDAO(DAOFacade orm) {
    super(
        orm, TransportationsubmissionEntity.class, TransportationsubmissionEntity::getSubmissionid);
  }
}
