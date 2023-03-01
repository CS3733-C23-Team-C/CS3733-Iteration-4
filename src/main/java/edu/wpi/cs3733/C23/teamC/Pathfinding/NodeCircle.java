package edu.wpi.cs3733.C23.teamC.Pathfinding;

import edu.wpi.cs3733.C23.teamC.ServiceRequests.SubmissionAbs;
import edu.wpi.cs3733.C23.teamC.database.hibernate.NodeEntity;
import java.util.Set;
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
