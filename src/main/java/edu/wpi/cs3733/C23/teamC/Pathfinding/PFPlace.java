package edu.wpi.cs3733.C23.teamC.Pathfinding;

import edu.wpi.cs3733.C23.teamC.database.hibernate.NodeEntity;
import java.sql.Date;

public interface PFPlace extends Comparable<PFPlace> {
  String getLongname();

  String getShortname();

  String getLocationtype();

  NodeEntity getNode(Date date);

  boolean hasRecentNode(Date date);
}
