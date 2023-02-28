package edu.wpi.cs3733.C23.teamC.objects;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.NodeEntity;
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
