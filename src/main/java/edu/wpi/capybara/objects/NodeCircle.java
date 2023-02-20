package edu.wpi.capybara.objects;

import edu.wpi.capybara.objects.orm.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;

public class NodeCircle extends Circle {
  @Getter @Setter
  Node connectedNode = null;

  public NodeCircle(double size, Paint color, Node connectedNode) {
    super(size, color);
    this.connectedNode = connectedNode;
  }
}
