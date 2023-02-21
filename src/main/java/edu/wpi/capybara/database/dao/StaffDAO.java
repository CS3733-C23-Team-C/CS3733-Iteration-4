package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.orm.StaffEntity;
import edu.wpi.capybara.objects.orm.DAOFacade;

public class StaffDAO extends MapDAOBase<String, StaffEntity> {
  public StaffDAO(DAOFacade orm) {
    super(orm, StaffEntity.class, StaffEntity::getStaffid);
  }

  public StaffEntity get(String staffId, String password) {
    final var staff = get(staffId);
    if (staff != null && staff.getPassword().equals(password)) {
      return staff;
    } else {
      return null;
    }
  }
}
