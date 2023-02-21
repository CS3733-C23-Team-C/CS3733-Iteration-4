package edu.wpi.capybara.objects;

import edu.wpi.capybara.objects.orm.NodeEntity;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;

public class NodeCircle extends Circle {
  @Getter @Setter NodeEntity connectedNode = null;

  public NodeCircle(double size, Paint color, NodeEntity connectedNode) {
    super(size, color);
    this.connectedNode = connectedNode;
  }
}
