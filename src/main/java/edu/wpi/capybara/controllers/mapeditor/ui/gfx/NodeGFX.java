package edu.wpi.capybara.controllers.mapeditor.ui.gfx;

import edu.wpi.capybara.objects.Floor;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
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

    circle.centerXProperty().bind(node.xcoordProperty());
    circle.centerYProperty().bind(node.ycoordProperty());

    label.setLabelFor(circle);
    label.textProperty().bind(node.nodeIDProperty());
    label.visibleProperty().bind(showLabel);

    label.layoutXProperty().bind(circle.centerXProperty());
    label.layoutYProperty().bind(circle.centerYProperty());
    label.setTranslateX(RADIUS);

    getChildren().addAll(circle, label);
    visibleProperty()
        .bind(
            Bindings.createBooleanBinding(
                () -> shownFloor.getValue().equals(node.getFloor()), shownFloor));

    getChildren().forEach(child -> child.getStyleClass().addAll(getStyleClass()));
  }
}
