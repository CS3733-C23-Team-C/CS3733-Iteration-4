package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.hibernate.TransportationsubmissionEntity;
import edu.wpi.capybara.objects.orm.DAOFacade;
import edu.wpi.capybara.objects.orm.TransportationSubmission;

import java.util.UUID;

public class TransportationSubmissionDAO extends MapDAOBase<UUID, TransportationSubmission> {
    public TransportationSubmissionDAO(DAOFacade orm) {
        super(orm, TransportationSubmission.class, TransportationSubmission::getSubmissionID);
    }
}
