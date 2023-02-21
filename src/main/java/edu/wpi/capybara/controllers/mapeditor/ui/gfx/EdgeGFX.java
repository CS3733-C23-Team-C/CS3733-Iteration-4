package edu.wpi.capybara.controllers.mapeditor.ui.gfx;

import edu.wpi.capybara.controllers.mapeditor.adapters.EdgeAdapter;
import edu.wpi.capybara.controllers.mapeditor.adapters.NodeAdapter;
import edu.wpi.capybara.objects.hibernate.EdgeEntity;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.shape.Line;
import lombok.Getter;

public class EdgeGFX extends GFXBase {
    private static final String STYLE_CLASS = "mapEditorEdge";

    @Getter
    private final Line line;

    public EdgeGFX(EdgeEntity edge) {
        getStyleClass().add(STYLE_CLASS);

        line = new Line();

        line.startXProperty().bind(edge.node1Property().map(NodeEntity::xcoordProperty).map(SimpleIntegerProperty::get));
        line.startYProperty().bind(edge.node1Property().map(NodeEntity::ycoordProperty).map(SimpleIntegerProperty::get));
        line.endXProperty().bind(edge.node2Property().map(NodeEntity::xcoordProperty).map(SimpleIntegerProperty::get));
        line.endYProperty().bind(edge.node2Property().map(NodeEntity::ycoordProperty).map(SimpleIntegerProperty::get));
    }
}
