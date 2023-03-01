package edu.wpi.cs3733.C23.teamC.database.hibernate;

import java.util.HashMap;

public interface LocationnameDAO {

  HashMap<String, LocationnameEntity> getLocationnames();

  LocationnameEntity getLocationname(String longname);

  void addLocationname(LocationnameEntity submission);

  void deleteLocationname(String longname);
}
