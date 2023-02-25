package edu.wpi.cs3733.C23.teamC.database.dao;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.AudiosubmissionEntity;
import edu.wpi.cs3733.C23.teamC.objects.orm.DAOFacade;

public class AudioSubmissionDAO extends MapDAOBase<Integer, AudiosubmissionEntity> {
  public AudioSubmissionDAO(DAOFacade orm) {
    super(orm, AudiosubmissionEntity.class, AudiosubmissionEntity::getSubmissionid);
  }
}
