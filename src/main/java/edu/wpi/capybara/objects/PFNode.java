package edu.wpi.capybara.objects;

import edu.wpi.capybara.controllers.PathfindingController;
import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.objects.hibernate.LocationnameEntity;
import edu.wpi.capybara.objects.hibernate.MoveEntity;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import java.sql.Date;
import java.util.*;

public class PFNode {
  private class MoveInformation implements Comparable<MoveInformation> {
    String longname;
    String shortname;
    String locationType;
    Date moveDate;

    MoveInformation(String longname, String shortname, String locationType, Date date) {
      this.longname = longname;
      this.shortname = shortname;
      this.locationType = locationType;
      this.moveDate = date;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      MoveInformation that = (MoveInformation) o;
      return Objects.equals(longname, that.longname)
          && Objects.equals(shortname, that.shortname)
          && Objects.equals(locationType, that.locationType)
          && Objects.equals(moveDate, that.moveDate);
    }

    @Override
    public int hashCode() {
      return Objects.hash(longname, shortname, locationType, moveDate);
    }

    @Override
    public int compareTo(MoveInformation o) {
      return this.moveDate.compareTo(o.moveDate);
    }
  }

  private NodeEntity node;
  private List<MoveInformation> moves;
  private PathfindingController controller;

  public PFNode(NodeEntity node, PathfindingController controller) {
    this.controller = controller;
    this.node = node;
    moves = new ArrayList<>();

    for (MoveEntity move : DatabaseConnect.getMoves().values()) {
      if (move.getNodeid().equals(node.getNodeid())) {
        LocationnameEntity LNE = DatabaseConnect.getLocationNames().get(move.getLongname());
        MoveInformation information =
            new MoveInformation(
                LNE.getLongname(), LNE.getShortname(), LNE.getLocationtype(), move.getMovedate());
        moves.add(information);
      }
    }

    moves.sort(MoveInformation::compareTo);
  }

  public NodeEntity getNode() {
    return node;
  }

  public void setNode(NodeEntity node) {
    this.node = node;
  }

  public List<MoveInformation> getMoves() {
    return moves;
  }

  public void setMoves(List<MoveInformation> moves) {
    this.moves = moves;
  }

  public String getLongname(Date date) {
    MoveInformation mi = mostRecentMoveInformation(date);
    if (mi == null) return null;
    return mi.longname;
  }

  public String getShortname(Date date) {
    MoveInformation mi = mostRecentMoveInformation(date);
    if (mi == null) return null;
    return mi.shortname;
  }

  public String getLocationtype(Date date) {
    MoveInformation mi = mostRecentMoveInformation(date);
    if (mi == null) return null;
    return mi.locationType;
  }

  public boolean hasRecentNode(Date date) {
    MoveInformation mi = mostRecentMoveInformation(date);
    return mi != null;
  }

  public MoveInformation mostRecentMoveInformation(Date moveDate) {
    MoveInformation mostRecentName = null;

    for (MoveInformation move : moves) {
      if (move.moveDate.compareTo(moveDate) > 0) return mostRecentName;
      mostRecentName = move;
    }

    return mostRecentName;
  }

  public String toString() {
    MoveInformation mi = mostRecentMoveInformation(controller.getMoveDate());
    if (mi == null) return node.getNodeid();
    return mi.longname;
  }
}
