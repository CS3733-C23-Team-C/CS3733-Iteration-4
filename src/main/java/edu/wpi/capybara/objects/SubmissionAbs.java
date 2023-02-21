package edu.wpi.capybara.objects;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.objects.hibernate.LocationnameEntity;
import edu.wpi.capybara.objects.hibernate.MoveEntity;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import java.util.Date;

public abstract class SubmissionAbs {
  public abstract String getLocation();

  public LocationnameEntity getLocationEntity() {
    // System.out.println("Node " + getLocation());
    return Main.db.getLocationname(getLocation());
  }

  public abstract int getSubmissionid();

  public NodeEntity getLocationNode(Date date) {
    LocationnameEntity le = getLocationEntity();
    if (le == null) return null;
    NodeEntity mostRecent = null;
    Date mostRecentDate = new Date(0);
    for (MoveEntity move : Main.db.getMoves()) {
      if (move.getLongName().equals(le.getLongname()) && move.getMovedate().compareTo(date) < 0) {
        if (move.getMovedate().compareTo(mostRecentDate) > 0) {
          mostRecent = Main.db.getNode(move.getNodeID());
        }
      }
    }
    return mostRecent;
  }

  public abstract String submissionType();
}
