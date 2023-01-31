package edu.wpi.capybara.controllers.mapeditor;

import edu.wpi.capybara.objects.Node;
import javafx.beans.property.adapter.*;

public class NodePropertyAdapter {
  private static final JavaBeanStringPropertyBuilder nodeIDBuilder =
      JavaBeanStringPropertyBuilder.create().name("nodeID");
  private static final JavaBeanIntegerPropertyBuilder xCoordBuilder =
      JavaBeanIntegerPropertyBuilder.create().name("xCoord");
  private static final JavaBeanIntegerPropertyBuilder yCoordBuilder =
      JavaBeanIntegerPropertyBuilder.create().name("yCoord");

  private static final JavaBeanStringPropertyBuilder floorBuilder =
      JavaBeanStringPropertyBuilder.create().name("floor");

  private static final JavaBeanStringPropertyBuilder buildingBuilder =
      JavaBeanStringPropertyBuilder.create().name("building");

  // TODO: 1/31/23 Do we really need to hold on to this reference?
  private final Node node;

  private final JavaBeanStringProperty nodeID;
  private final JavaBeanIntegerProperty xCoord;
  private final JavaBeanIntegerProperty yCoord;
  private final JavaBeanStringProperty floor;
  private final JavaBeanStringProperty building;

  /**
   * Constructs a new NodePropertyAdapter, for use with JavaFX Property APIs.
   *
   * @param node the node that this adapter is associated with
   * @throws NoSuchMethodException if Node does not follow the JavaBean naming conventions. this is
   *     an exceptional condition, as there is no way for this code to continue if it occurs. thus,
   *     it is handed back to the caller to deal with. this is checked by a unit test, so this error
   *     shouldn't ever occur at runtime.
   */
  // TODO: 1/31/23 implement said unit test
  public NodePropertyAdapter(Node node) throws NoSuchMethodException {
    this.node = node;
    nodeID = nodeIDBuilder.bean(node).build();
    xCoord = xCoordBuilder.bean(node).build();
    yCoord = yCoordBuilder.bean(node).build();
    floor = floorBuilder.bean(node).build();
    building = buildingBuilder.bean(node).build();
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
}
