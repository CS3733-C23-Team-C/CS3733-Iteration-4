package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.Main;
import java.util.HashMap;

public class StaffDAOImpl implements StaffDAO {
  HashMap<String, StaffEntity> staff = new HashMap();

  @Override
  public HashMap<String, StaffEntity> getStaff() {
    return staff;
  }

  @Override
  public StaffEntity getStaff(String nodeid) {
    return staff.get(nodeid);
  }

  @Override
  public void addStaff(StaffEntity submission) {
    Main.db.addStaff(submission);
    this.staff.put(submission.getStaffid(), submission);
  }

  public StaffDAOImpl(HashMap<String, StaffEntity> staff) {
    this.staff = staff;
  }

  @Override
  public void deleteStaff(String id) {
    staff.remove(id);
    newDBConnect.delete(getStaff(id));
  }

  public StaffEntity getStaff(String Staffid, String password) {
    for (StaffEntity s : staff.values()) {
      if (s.getStaffid().equals(Staffid) && s.getPassword().equals(password)) {
        return s;
      }
    }
    return null;
  }
}
