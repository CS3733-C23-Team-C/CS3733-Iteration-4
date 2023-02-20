package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.hibernate.SecuritysubmissionEntity;
import edu.wpi.capybara.objects.orm.DAOFacade;
import edu.wpi.capybara.objects.orm.SecuritySubmission;

import java.util.UUID;

public class SecuritySubmissionDAO extends MapDAOBase<UUID, SecuritySubmission> {
    public SecuritySubmissionDAO(DAOFacade orm) {
        super(orm, SecuritySubmission.class, SecuritySubmission::getSubmissionID);
    }
}
