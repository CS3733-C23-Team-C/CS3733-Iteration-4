package edu.wpi.capybara.controllers.mapeditor;

import edu.wpi.capybara.objects.Edge;
import javafx.beans.property.adapter.JavaBeanStringProperty;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;

public class EdgePropertyAdapter {
  private static final JavaBeanStringPropertyBuilder startNodeBuilder =
      JavaBeanStringPropertyBuilder.create().name("startNode");
  private static final JavaBeanStringPropertyBuilder endNodeBuilder =
      JavaBeanStringPropertyBuilder.create().name("endNode");

  private final JavaBeanStringProperty startNode;
  private final JavaBeanStringProperty endNode;

  public EdgePropertyAdapter(Edge edge) {
    try {
      startNode = startNodeBuilder.bean(edge).build();
      endNode = endNodeBuilder.bean(edge).build();
    } catch (NoSuchMethodException e) {
      throw new IllegalStateException(
          "Failed to find getter/setter methods of Edge. This should not be possible.", e);
    }
  }

  public JavaBeanStringProperty startNodeProperty() {
    return startNode;
  }

  public JavaBeanStringProperty endNodeProperty() {
    return endNode;
  }
}
