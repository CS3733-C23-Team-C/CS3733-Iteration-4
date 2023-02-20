package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.objects.orm.Staff;

import java.util.HashMap;

public interface StaffDAO {

  HashMap<String, Staff> getStaff();

  Staff getStaff(String staff);

  void addStaff(Staff submission);

  Staff getStaff(String staffid, String password);

  void deleteStaff(String staffid);
}
