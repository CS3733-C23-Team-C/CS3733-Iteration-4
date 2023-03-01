package edu.wpi.cs3733.C23.teamC.database.hibernate;

import java.util.HashMap;

public interface StaffDAO {

  HashMap<String, StaffEntity> getStaff();

  StaffEntity getStaff(String staff);

  void addStaff(StaffEntity submission);

  StaffEntity getStaff(String staffid, String password);

  void deleteStaff(String staffid);
}
