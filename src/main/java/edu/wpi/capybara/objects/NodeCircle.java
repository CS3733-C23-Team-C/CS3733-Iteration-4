package edu.wpi.capybara.objects;

import java.util.Set;
import edu.wpi.capybara.objects.orm.NodeEntity;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;

public class NodeCircle extends Circle {
  @Getter @Setter private NodeEntity connectedNode;
  @Getter @Setter private Set<SubmissionAbs> serviceRequests;

  public NodeCircle(double size, Paint color, NodeEntity connectedNode) {
    super(size, color);
    this.connectedNode = connectedNode;
    this.serviceRequests = Set.of();
  }

  public NodeCircle(
      double size, Paint color, NodeEntity connectedNode, Set<SubmissionAbs> serviceRequests) {
    super(size, color);
    this.connectedNode = connectedNode;
    this.serviceRequests = serviceRequests;
  }
}
