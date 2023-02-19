package edu.wpi.capybara.controllers.mapeditor.ui.gfx;

import edu.wpi.capybara.controllers.mapeditor.adapters.EdgeAdapter;
import edu.wpi.capybara.controllers.mapeditor.adapters.NodeAdapter;
import edu.wpi.capybara.objects.hibernate.NodeEntity;
import javafx.scene.shape.Line;
import lombok.Getter;

public class EdgeGFX extends GFXBase {
    private static final String STYLE_CLASS = "mapEditorEdge";

    @Getter
    private final Line line;

    public EdgeGFX(EdgeAdapter edge) {
        getStyleClass().add(STYLE_CLASS);

        line = new Line();

        //line.startXProperty().bind(edge.startNodeProperty().map(NodeAdapter::xCoordProperty));
        //line.startYProperty().bind(edge.startNodeProperty().map(NodeAdapter::yCoordProperty));
        //line.endXProperty().bind(edge.endNodeProperty().map(NodeAdapter::xCoordProperty));
        //line.endYProperty().bind(edge.endNodeProperty().map(NodeAdapter::yCoordProperty));
    }
}
