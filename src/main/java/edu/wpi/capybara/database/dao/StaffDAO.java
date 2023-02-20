package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.orm.DAOFacade;
import edu.wpi.capybara.objects.orm.Staff;

public class StaffDAO extends MapDAOBase<String, Staff> {
  public StaffDAO(DAOFacade orm) {
    super(orm, Staff.class, Staff::getStaffID);
  }

  public Staff get(String staffId, String password) {
    final var staff = get(staffId);
    if (staff != null && staff.getPassword().equals(password)) {
      return staff;
    } else {
      return null;
    }
  }
}
