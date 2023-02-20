package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.hibernate.AudiosubmissionEntity;
import edu.wpi.capybara.objects.orm.AudioSubmission;
import edu.wpi.capybara.objects.orm.DAOFacade;

import java.util.UUID;

public class AudioSubmissionDAO extends MapDAOBase<UUID, AudioSubmission> {
    public AudioSubmissionDAO(DAOFacade orm) {
        super(orm, AudioSubmission.class, AudioSubmission::getSubmissionID);
    }
}
