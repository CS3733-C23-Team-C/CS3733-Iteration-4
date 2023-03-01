package edu.wpi.cs3733.C23.teamC.Pathfinding;

import edu.wpi.cs3733.C23.teamC.database.hibernate.NodeEntity;
import java.sql.Date;

public interface MapViewHolder {
  boolean serviceRequestSelected();

  boolean displayLocationNames();

  String getMapText();

  Date getMoveDate();

  PFPlace getPFPlace(NodeEntity node);

  void changeFloorNum(String floor);

  void removeCurrentDialog();
}
