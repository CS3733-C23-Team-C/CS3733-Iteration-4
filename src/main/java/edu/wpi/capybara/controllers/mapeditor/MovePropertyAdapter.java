package edu.wpi.capybara.controllers.mapeditor;

import edu.wpi.capybara.objects.hibernate.MoveEntity;
import java.sql.Date;
import javafx.beans.property.adapter.JavaBeanObjectProperty;
import javafx.beans.property.adapter.JavaBeanObjectPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanStringProperty;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;

public class MovePropertyAdapter {
  private static final JavaBeanStringPropertyBuilder nodeIDBuilder =
      JavaBeanStringPropertyBuilder.create().name("nodeid");
  private static final JavaBeanStringPropertyBuilder longNameBuilder =
      JavaBeanStringPropertyBuilder.create().name("longname");
  private static final JavaBeanObjectPropertyBuilder<Date> moveDateBuilder =
      JavaBeanObjectPropertyBuilder.create().name("movedate");

  private final JavaBeanStringProperty nodeID;
  private final JavaBeanStringProperty longName;
  private final JavaBeanObjectProperty<Date> moveDate;

  public MovePropertyAdapter(MoveEntity mv) {
    try {
      nodeID = nodeIDBuilder.bean(mv).build();
      longName = longNameBuilder.bean(mv).build();
      moveDate = moveDateBuilder.bean(mv).build();
    } catch (NoSuchMethodException e) {
      throw new IllegalStateException(
          "Unable to find getters/setters on move. This should not be possible.", e);
    }
  }

  MovePropertyAdapter(MoveEntity moveEntity, NodePropertyAdapter node, LocationNamePropertyAdapter location) {
    this(moveEntity);
    nodeID.bind(node.nodeIDProperty());
    longName.bind(location.longNameProperty());
  }

  public JavaBeanStringProperty nodeIDProperty() {
    return nodeID;
  }

  public JavaBeanStringProperty longNameProperty() {
    return longName;
  }

  public JavaBeanObjectProperty<Date> moveDateProperty() {
    return moveDate;
  }
}
