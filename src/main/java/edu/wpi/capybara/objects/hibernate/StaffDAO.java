package edu.wpi.capybara.objects.hibernate;

import java.util.HashMap;

public interface StaffDAO {

  HashMap<String, StaffEntity> getStaff();

  StaffEntity getStaff(String staff);

  void addStaff(StaffEntity submission);

  StaffEntity getStaff(String staffid, String password);

  void deleteStaff(String staffid);

  String generateStaffID();
}
