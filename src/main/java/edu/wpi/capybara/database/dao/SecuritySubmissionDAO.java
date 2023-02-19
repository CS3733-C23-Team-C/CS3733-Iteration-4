package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.hibernate.SecuritysubmissionEntity;
import edu.wpi.capybara.objects.orm.DAOFacade;

public class SecuritySubmissionDAO extends MapDAOBase<Integer, SecuritysubmissionEntity> {
    public SecuritySubmissionDAO(DAOFacade orm) {
        super(orm, SecuritysubmissionEntity.class, SecuritysubmissionEntity::getSubmissionid);
    }
}
