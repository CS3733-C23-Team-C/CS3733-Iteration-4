package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.objects.orm.Location;

import java.util.HashMap;

public interface LocationnameDAO {

  HashMap<String, Location> getLocationnames();

  Location getLocationname(String longname);

  void addLocationname(Location submission);

  void deleteLocationname(String longname);
}
