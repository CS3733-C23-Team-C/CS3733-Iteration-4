package edu.wpi.capybara.controllers.mapeditor.adapters;

import edu.wpi.capybara.objects.orm.EdgeEntity;
import javafx.beans.property.adapter.JavaBeanStringProperty;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;

public class EdgeAdapter {
  private static final JavaBeanStringPropertyBuilder startNodeBuilder =
      JavaBeanStringPropertyBuilder.create().name("node1");
  private static final JavaBeanStringPropertyBuilder endNodeBuilder =
      JavaBeanStringPropertyBuilder.create().name("node2");

  private final EdgeEntity entity;

  private final JavaBeanStringProperty startNode;
  private final JavaBeanStringProperty endNode;

  public EdgeAdapter(EdgeEntity edge) {
    entity = edge;
    try {
      startNode = startNodeBuilder.bean(edge).build();
      endNode = endNodeBuilder.bean(edge).build();
    } catch (NoSuchMethodException e) {
      throw new IllegalStateException(
          "Failed to find getter/setter methods of Edge. This should not be possible.", e);
    }
  }

  public EdgeAdapter(EdgeEntity edge, NodeAdapter startNode, NodeAdapter endNode) {
    this(edge);
    this.startNode.bind(startNode.nodeIDProperty());
    this.endNode.bind(endNode.nodeIDProperty());
  }

  public String getStartNode() {
    return startNode.get();
  }

  public void setStartNode(String startNode) {
    this.startNode.set(startNode);
  }

  public String getEndNode() {
    return endNode.get();
  }

  public void setEndNode(String endNode) {
    this.endNode.set(endNode);
  }

  public JavaBeanStringProperty startNodeProperty() {
    return startNode;
  }

  public JavaBeanStringProperty endNodeProperty() {
    return endNode;
  }

  public EdgeEntity getEntity() {
    return entity;
  }
}
