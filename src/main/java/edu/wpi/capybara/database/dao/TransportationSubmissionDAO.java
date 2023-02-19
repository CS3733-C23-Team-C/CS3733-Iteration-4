package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.hibernate.TransportationsubmissionEntity;
import edu.wpi.capybara.objects.orm.DAOFacade;

public class TransportationSubmissionDAO extends MapDAOBase<Integer, TransportationsubmissionEntity> {
    public TransportationSubmissionDAO(DAOFacade orm) {
        super(orm, TransportationsubmissionEntity.class, TransportationsubmissionEntity::getSubmissionid);
    }
}
