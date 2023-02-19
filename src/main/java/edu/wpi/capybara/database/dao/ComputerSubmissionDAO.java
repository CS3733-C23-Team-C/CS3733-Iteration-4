package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.hibernate.ComputersubmissionEntity;
import edu.wpi.capybara.objects.orm.DAOFacade;

public class ComputerSubmissionDAO extends MapDAOBase<Integer, ComputersubmissionEntity> {
    public ComputerSubmissionDAO(DAOFacade orm) {
        super(orm, ComputersubmissionEntity.class, ComputersubmissionEntity::getSubmissionid);
    }
}
