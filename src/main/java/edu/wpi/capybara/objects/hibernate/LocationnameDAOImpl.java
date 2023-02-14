package edu.wpi.capybara.objects.hibernate;

import java.util.HashMap;

public class LocationnameDAOImpl implements LocationnameDAO {
  HashMap<String, LocationnameEntity> locationnames = new HashMap();

  @Override
  public HashMap<String, LocationnameEntity> getLocationnames() {
    return locationnames;
  }

  @Override
  public LocationnameEntity getLocationname(String longname) {
    return locationnames.get(longname);
  }

  @Override
  public void addLocationname(LocationnameEntity submission) {
    newDBConnect.insertNew(submission);
    this.locationnames.put(submission.getLongname(), submission);
  }

  @Override
  public void deleteLocationname(String longname) {
    locationnames.remove(longname);
    newDBConnect.delete(getLocationname(longname));
  }

  public LocationnameDAOImpl(HashMap<String, LocationnameEntity> locationnames) {
    this.locationnames = locationnames;
  }
}
