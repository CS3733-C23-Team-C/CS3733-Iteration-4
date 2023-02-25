package edu.wpi.cs3733.C23.teamC.controllers.mapeditor.adapters;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.NodeEntity;
import javafx.beans.property.adapter.*;

public class NodeAdapter {
  // these builders are designed to be reused, so we keep them in a bunch of static fields.
  private static final JavaBeanStringPropertyBuilder nodeIDBuilder =
      JavaBeanStringPropertyBuilder.create().name("nodeid");
  private static final JavaBeanIntegerPropertyBuilder xCoordBuilder =
      JavaBeanIntegerPropertyBuilder.create().name("xcoord");
  private static final JavaBeanIntegerPropertyBuilder yCoordBuilder =
      JavaBeanIntegerPropertyBuilder.create().name("ycoord");

  private static final JavaBeanStringPropertyBuilder floorBuilder =
      JavaBeanStringPropertyBuilder.create().name("floor");

  private static final JavaBeanStringPropertyBuilder buildingBuilder =
      JavaBeanStringPropertyBuilder.create().name("building");

  private final NodeEntity entity;

  private final JavaBeanStringProperty nodeID;
  private final JavaBeanIntegerProperty xCoord;
  private final JavaBeanIntegerProperty yCoord;
  private final JavaBeanStringProperty floor;
  private final JavaBeanStringProperty building;
  /**
   * Constructs a new NodePropertyAdapter, for use with JavaFX Property APIs.
   *
   * @param node the node that this adapter is associated with
   */
  // TODO: 1/31/23 implement said unit test
  // STOPSHIP: 1/31/23 see above to-do
  public NodeAdapter(NodeEntity node) {
    entity = node;
    try {
      nodeID = nodeIDBuilder.bean(node).build();
      xCoord = xCoordBuilder.bean(node).build();
      yCoord = yCoordBuilder.bean(node).build();
      floor = floorBuilder.bean(node).build();
      building = buildingBuilder.bean(node).build();
    } catch (NoSuchMethodException e) {
      // this happens if Node does not follow the JavaBean naming conventions. this is an
      // exceptional condition, as there is no way for this code to continue if it occurs. thus, we
      // throw a runtime error back to the caller instead of trying to work around it. this is
      // checked by a unit test, so this error shouldn't ever occur at runtime.
      throw new IllegalStateException(
          "Failed to find getter/setter methods of Node. This should not be possible.", e);
    }
  }

  // unfortunately, lombok doesn't seem to support the JavaFX Property conventions. thankfully,
  // IntelliJ does. all of these can be auto-generated.

  public String getNodeID() {
    return nodeID.get();
  }

  public void setNodeID(String nodeID) {
    this.nodeID.set(nodeID);
  }

  public JavaBeanStringProperty nodeIDProperty() {
    return nodeID;
  }

  public int getXCoord() {
    return xCoord.get();
  }

  public void setXCoord(int xCoord) {
    this.xCoord.set(xCoord);
  }

  public JavaBeanIntegerProperty xCoordProperty() {
    return xCoord;
  }

  public int getYCoord() {
    return yCoord.get();
  }

  public void setYCoord(int yCoord) {
    this.yCoord.set(yCoord);
  }

  public JavaBeanIntegerProperty yCoordProperty() {
    return yCoord;
  }

  public String getFloor() {
    return floor.get();
  }

  public void setFloor(String floor) {
    this.floor.set(floor);
  }

  public JavaBeanStringProperty floorProperty() {
    return floor;
  }

  public String getBuilding() {
    return building.get();
  }

  public void setBuilding(String building) {
    this.building.set(building);
  }

  public JavaBeanStringProperty buildingProperty() {
    return building;
  }

  public NodeEntity getEntity() {
    return entity;
  }
}
