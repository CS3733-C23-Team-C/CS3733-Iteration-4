package edu.wpi.capybara.controllers.mapeditor.ui.gfx;

import edu.wpi.capybara.objects.Floor;
import edu.wpi.capybara.objects.hibernate.EdgeEntity;
import javafx.beans.property.ObjectProperty;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import lombok.Getter;

public class EdgeGFX extends GFXBase {
  private static final String STYLE_CLASS = "mapEditorEdge";

  @Getter private final Line line;

  public EdgeGFX(EdgeEntity edge, ObjectProperty<Floor> shownFloor) {
    getStylesheets().add("@/styles/map_editor.css");
    getStyleClass().addAll("selectable", STYLE_CLASS);

    line = new Line();

    line.setStrokeWidth(5);

    bind(edge, shownFloor);

    edge.node1Property().addListener(change -> bind(edge, shownFloor));
    edge.node2Property().addListener(change -> bind(edge, shownFloor));

    getChildren().add(line);

    getChildren().forEach(child -> child.getStyleClass().addAll(getStyleClass()));
    line.hoverProperty().addListener(change -> System.out.println("hover"));
    line.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> System.out.println("click"));
  }

  private void bind(EdgeEntity edge, ObjectProperty<Floor> shownFloor) {
    line.startXProperty().bind(edge.getNode1().xcoordProperty());
    line.startYProperty().bind(edge.getNode1().ycoordProperty());
    line.endXProperty().bind(edge.getNode2().xcoordProperty());
    line.endYProperty().bind(edge.getNode2().ycoordProperty());
    visibleProperty()
        .bind(
            edge.getNode1()
                .floorProperty()
                .isEqualTo(shownFloor)
                .or(edge.getNode2().floorProperty().isEqualTo(shownFloor)));
  }
}
