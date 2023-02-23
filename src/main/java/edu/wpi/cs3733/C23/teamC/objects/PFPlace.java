package edu.wpi.cs3733.C23.teamC.objects;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.NodeEntity;
import java.sql.Date;

public interface PFPlace {
  String getLongname();

  String getShortname();

  String getLocationtype();

  NodeEntity getNode(Date date);

  boolean hasRecentNode(Date date);
}
