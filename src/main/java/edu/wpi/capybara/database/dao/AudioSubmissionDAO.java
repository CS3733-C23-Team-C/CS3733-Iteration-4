package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.hibernate.AudiosubmissionEntity;
import edu.wpi.capybara.objects.orm.DAOFacade;

public class AudioSubmissionDAO extends MapDAOBase<Integer, AudiosubmissionEntity> {
    public AudioSubmissionDAO(DAOFacade orm) {
        super(orm, AudiosubmissionEntity.class, AudiosubmissionEntity::getSubmissionid);
    }
}
