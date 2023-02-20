package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.hibernate.ComputersubmissionEntity;
import edu.wpi.capybara.objects.orm.ComputerSubmission;
import edu.wpi.capybara.objects.orm.DAOFacade;

import java.util.UUID;

public class ComputerSubmissionDAO extends MapDAOBase<UUID, ComputerSubmission> {
    public ComputerSubmissionDAO(DAOFacade orm) {
        super(orm, ComputerSubmission.class, ComputerSubmission::getSubmissionID);
    }
}
