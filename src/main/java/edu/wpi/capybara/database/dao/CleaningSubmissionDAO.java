package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.hibernate.CleaningsubmissionEntity;
import edu.wpi.capybara.objects.orm.CleaningSubmission;
import edu.wpi.capybara.objects.orm.DAOFacade;

import java.util.UUID;

public class CleaningSubmissionDAO extends MapDAOBase<UUID, CleaningSubmission> {
    public CleaningSubmissionDAO(DAOFacade orm) {
        super(orm, CleaningSubmission.class, CleaningSubmission::getSubmissionID);
    }
}
