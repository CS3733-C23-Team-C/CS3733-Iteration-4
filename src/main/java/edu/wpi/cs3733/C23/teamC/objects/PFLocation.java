package edu.wpi.cs3733.C23.teamC.objects;

import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.controllers.PathfindingController;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.LocationnameEntity;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.MoveEntity;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.NodeEntity;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;

public class PFLocation implements PFPlace {
  private PathfindingController controller;
  @Getter private LocationnameEntity locationname;
  private List<MoveInformation> moves;

  private class MoveInformation implements Comparable<MoveInformation> {
    String nodeID;
    int xCoord;
    int yCoord;
    String floor;
    String building;
    Date moveDate;
    NodeEntity node;

    MoveInformation(
        String nodeID,
        int xCoord,
        int yCoord,
        String floor,
        String building,
        Date moveDate,
        NodeEntity node) {
      this.nodeID = nodeID;
      this.xCoord = xCoord;
      this.yCoord = yCoord;
      this.floor = floor;
      this.building = building;
      this.moveDate = moveDate;
      this.node = node;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      MoveInformation that = (MoveInformation) o;
      return xCoord == that.xCoord
          && yCoord == that.yCoord
          && Objects.equals(nodeID, that.nodeID)
          && Objects.equals(floor, that.floor)
          && Objects.equals(building, that.building)
          && Objects.equals(moveDate, that.moveDate);
    }

    @Override
    public int hashCode() {
      return Objects.hash(nodeID, xCoord, yCoord, floor, building, moveDate);
    }

    @Override
    public int compareTo(MoveInformation o) {
      return this.moveDate.compareTo(o.moveDate);
    }
  }

  public PFLocation(LocationnameEntity locationname, PathfindingController controller) {
    this.controller = controller;
    this.locationname = locationname;
    moves = new ArrayList<>();

    for (MoveEntity move : Main.db.getMoves()) {
      if (move.getLongName().equals(locationname.getLongname())) {
        NodeEntity ne = Main.db.getNode(move.getNodeID());
        MoveInformation mi =
            new MoveInformation(
                ne.getNodeID(),
                ne.getXcoord(),
                ne.getYcoord(),
                ne.getFloor().toString(),
                ne.getBuilding(),
                move.getMovedate(),
                ne);
        moves.add(mi);
      }
    }

    moves.sort(MoveInformation::compareTo);
  }

  public String getLongname() {
    return locationname.getLongname();
  }

  public String getShortname() {
    return locationname.getShortname();
  }

  public String getLocationtype() {
    return locationname.getLocationtype();
  }

  public String toString() {
    return locationname.getLongname();
  }

  private MoveInformation mostRecentMoveInformation(Date moveDate) {
    MoveInformation mostRecentName = null;

    for (MoveInformation move : moves) {
      if (move.moveDate.compareTo(moveDate) > 0) return mostRecentName;
      mostRecentName = move;
    }

    return mostRecentName;
  }

  public NodeEntity getNode(Date date) {
    MoveInformation move = mostRecentMoveInformation(date);
    if (move == null) return null;
    return move.node;
  }

  public String getNodeId(Date date) {
    return mostRecentMoveInformation(date).nodeID;
  }

  public int getNodeXCoord(Date date) {
    return mostRecentMoveInformation(date).xCoord;
  }

  public int getNodeYCoord(Date date) {
    return mostRecentMoveInformation(date).yCoord;
  }

  public String getNodeFloor(Date date) {
    return mostRecentMoveInformation(date).floor;
  }

  public String getNodeBuilding(Date date) {
    return mostRecentMoveInformation(date).building;
  }

  public boolean hasRecentNode(Date date) {
    return mostRecentMoveInformation(date) != null;
  }
}
