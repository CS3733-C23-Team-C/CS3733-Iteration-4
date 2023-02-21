package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.objects.orm.LocationnameEntity;
import java.util.HashMap;

public interface LocationnameDAO {

  HashMap<String, LocationnameEntity> getLocationnames();

  LocationnameEntity getLocationname(String longname);

  void addLocationname(LocationnameEntity submission);

  void deleteLocationname(String longname);
}
