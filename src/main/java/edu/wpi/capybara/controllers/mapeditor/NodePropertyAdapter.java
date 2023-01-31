package edu.wpi.capybara.controllers.mapeditor;

import edu.wpi.capybara.objects.Node;
import edu.wpi.capybara.objects.enums.Building;
import edu.wpi.capybara.objects.enums.Floor;
import edu.wpi.capybara.objects.enums.NodeType;
import javafx.beans.property.adapter.*;

public class NodePropertyAdapter {
  private static final JavaBeanStringPropertyBuilder nodeIDBuilder =
      JavaBeanStringPropertyBuilder.create().name("nodeID");
  private static final JavaBeanIntegerPropertyBuilder xCoordBuilder =
      JavaBeanIntegerPropertyBuilder.create().name("xCoord");
  private static final JavaBeanIntegerPropertyBuilder yCoordBuilder =
      JavaBeanIntegerPropertyBuilder.create().name("yCoord");

  // JavaBeanObjectPropertyBuilder doesn't provide a mechanism to specify a type, since none of the
  // methods rely on the parameterized type. Java will complain regardless of if you specify a type
  // here or not, since you'll either be doing an unchecked cast or using a raw generic type.
  @SuppressWarnings("unchecked")
  private static final JavaBeanObjectPropertyBuilder<Floor> floorBuilder =
      JavaBeanObjectPropertyBuilder.create().name("floor");

  @SuppressWarnings("unchecked")
  private static final JavaBeanObjectPropertyBuilder<Building> buildingBuilder =
      JavaBeanObjectPropertyBuilder.create().name("building");

  @SuppressWarnings("unchecked")
  private static final JavaBeanObjectPropertyBuilder<NodeType> nodeTypeBuilder =
      JavaBeanObjectPropertyBuilder.create().name("nodeType");

  private static final JavaBeanStringPropertyBuilder longNameBuilder =
      JavaBeanStringPropertyBuilder.create().name("longName");
  private static final JavaBeanStringPropertyBuilder shortNameBuilder =
      JavaBeanStringPropertyBuilder.create().name("shortName");

  // TODO: 1/31/23 Do we really need to hold on to this reference?
  private final Node node;

  private final JavaBeanStringProperty nodeID;
  private final JavaBeanIntegerProperty xCoord;
  private final JavaBeanIntegerProperty yCoord;
  private final JavaBeanObjectProperty<Floor> floor;
  private final JavaBeanObjectProperty<Building> building;
  private final JavaBeanObjectProperty<NodeType> nodeType;
  private final JavaBeanStringProperty longName;
  private final JavaBeanStringProperty shortName;

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
  @SuppressWarnings(
      "unchecked") // see comment above on floorBuilder for why this @SuppressWarnings is needed
  public NodePropertyAdapter(Node node) throws NoSuchMethodException {
    this.node = node;
    nodeID = nodeIDBuilder.bean(node).build();
    xCoord = xCoordBuilder.bean(node).build();
    yCoord = yCoordBuilder.bean(node).build();
    floor = floorBuilder.bean(node).build();
    building = buildingBuilder.bean(node).build();
    nodeType = nodeTypeBuilder.bean(node).build();
    longName = longNameBuilder.bean(node).build();
    shortName = shortNameBuilder.bean(node).build();
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

  public Floor getFloor() {
    return floor.get();
  }

  public void setFloor(Floor floor) {
    this.floor.set(floor);
  }

  public JavaBeanObjectProperty<Floor> floorProperty() {
    return floor;
  }

  public Building getBuilding() {
    return building.get();
  }

  public void setBuilding(Building building) {
    this.building.set(building);
  }

  public JavaBeanObjectProperty<Building> buildingProperty() {
    return building;
  }

  public NodeType getNodeType() {
    return nodeType.get();
  }

  public void setNodeType(NodeType nodeType) {
    this.nodeType.set(nodeType);
  }

  public JavaBeanObjectProperty<NodeType> nodeTypeProperty() {
    return nodeType;
  }

  public String getLongName() {
    return longName.get();
  }

  public void setLongName(String longName) {
    this.longName.set(longName);
  }

  public JavaBeanStringProperty longNameProperty() {
    return longName;
  }

  public String getShortName() {
    return shortName.get();
  }

  public void setShortName(String shortName) {
    this.shortName.set(shortName);
  }

  public JavaBeanStringProperty shortNameProperty() {
    return shortName;
  }
}
