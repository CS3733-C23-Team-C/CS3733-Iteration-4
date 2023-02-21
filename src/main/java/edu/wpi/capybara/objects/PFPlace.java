package edu.wpi.capybara.objects;

import edu.wpi.capybara.objects.hibernate.NodeEntity;
import java.sql.Date;

public interface PFPlace {
  String getLongname();

  String getShortname();

  String getLocationtype();

  NodeEntity getNode(Date date);

  boolean hasRecentNode(Date date);
}
