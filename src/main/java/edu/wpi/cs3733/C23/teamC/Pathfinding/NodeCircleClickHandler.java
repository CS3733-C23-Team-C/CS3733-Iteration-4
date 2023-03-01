package edu.wpi.cs3733.C23.teamC.Pathfinding;

import javafx.scene.input.MouseEvent;

public interface NodeCircleClickHandler {
  void handle(MouseEvent event, NodeCircle circle);

  static NodeCircleClickHandler doNothing() {
    return (event, circle) -> {};
  }
}
