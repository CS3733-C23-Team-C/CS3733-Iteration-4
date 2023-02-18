package edu.wpi.capybara.controllers.mapeditor.ui.gfx;

import edu.wpi.capybara.controllers.mapeditor.adapters.NodeAdapter;
import javafx.beans.value.ObservableBooleanValue;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import lombok.Getter;

public class NodeGFX extends GFXBase {
    private static final String STYLE_CLASS = "mapEditorNode";
    private static final double RADIUS = 6;

    @Getter
    private final Circle circle;
    @Getter
    private final Label label;

    public NodeGFX(NodeAdapter node, ObservableBooleanValue showLabel) {
        getStyleClass().add(STYLE_CLASS);

        circle = new Circle(RADIUS);
        label = new Label();

        circle.centerXProperty().bind(node.xCoordProperty());
        circle.centerYProperty().bind(node.yCoordProperty());

        label.setLabelFor(circle);
        label.textProperty().bind(node.nodeIDProperty());
        label.visibleProperty().bind(showLabel);

        label.layoutXProperty().bind(circle.centerXProperty());
        label.layoutYProperty().bind(circle.centerYProperty());
        label.setTranslateX(RADIUS);

        getChildren().addAll(circle, label);
    }
}
