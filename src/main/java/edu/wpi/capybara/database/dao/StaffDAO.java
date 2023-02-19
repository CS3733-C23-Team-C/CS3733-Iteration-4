package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.hibernate.StaffEntity;
import edu.wpi.capybara.objects.orm.DAOFacade;

public class StaffDAO extends MapDAOBase<String, StaffEntity> {
    public StaffDAO(DAOFacade orm) {
        super(orm, StaffEntity.class, StaffEntity::getStaffid);
    }
}
