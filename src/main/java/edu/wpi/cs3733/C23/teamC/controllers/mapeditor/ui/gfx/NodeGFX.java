package edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.gfx;

import edu.wpi.cs3733.C23.teamC.objects.Floor;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.NodeEntity;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import lombok.Getter;

public class NodeGFX extends GFXBase {
  private static final String STYLE_CLASS = "mapEditorNode";
  private static final double RADIUS = 6;

  @Getter private final Circle circle;
  @Getter private final Label label;

  public NodeGFX(
      NodeEntity node, ObservableBooleanValue showLabel, ObservableValue<Floor> shownFloor) {
    getStyleClass().addAll("selectable", STYLE_CLASS);
    circle = new Circle(RADIUS);
    label = new Label();

    translateXProperty().bind(node.xcoordProperty());
    translateYProperty().bind(node.ycoordProperty());

    label.setLabelFor(circle);
    label.textProperty().bind(node.nodeIDProperty());
    label.visibleProperty().bind(showLabel);
    label.managedProperty().bind(showLabel);

    label.setTranslateX(RADIUS);

    getChildren().addAll(circle, label);

    final var shouldShow =
        Bindings.createBooleanBinding(
            () -> shownFloor.getValue().equals(node.getFloor()), shownFloor);
    visibleProperty().bind(shouldShow);
    managedProperty().bind(shouldShow);

    // getChildren().forEach(child -> child.getStyleClass().addAll("selectable", STYLE_CLASS));
  }
}
