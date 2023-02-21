package edu.wpi.capybara.database.dao;

import edu.wpi.capybara.objects.hibernate.StaffEntity;
import edu.wpi.capybara.objects.orm.DAOFacade;
import java.util.Collection;

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

  public StaffEntity find(String firstName, String lastName) {
    Collection<StaffEntity> staffMembers = getAll().values();
    for (StaffEntity staff : staffMembers) {
      if (staff.getFirstname().equals(firstName) && staff.getLastname().equals(lastName)) {
        return staff;
      }
    }
    return null;
  }
}
