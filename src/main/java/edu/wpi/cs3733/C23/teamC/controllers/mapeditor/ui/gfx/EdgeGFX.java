package edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.gfx;

import edu.wpi.cs3733.C23.teamC.objects.Floor;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.EdgeEntity;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.scene.shape.Line;
import lombok.Getter;

public class EdgeGFX extends GFXBase {
  private static final String STYLE_CLASS = "mapEditorEdge";

  @Getter private final Line line;

  private EdgeEntity edge;
  private ObjectProperty<Floor> shownFloor;

  public EdgeGFX(EdgeEntity edge, ObjectProperty<Floor> shownFloor) {
    this.edge = edge;
    this.shownFloor = shownFloor;
    getStyleClass().addAll("selectable", STYLE_CLASS);
    line = new Line();

    line.setStrokeWidth(5);

    Platform.runLater(this::bind);

    edge.node1Property().addListener(change -> bind());
    edge.node2Property().addListener(change -> bind());

    getChildren().add(line);
    // getChildren().forEach(child -> child.getStyleClass().addAll("selectable", STYLE_CLASS));

    edge.getNode1().xcoordProperty().addListener(change -> System.out.println("node change"));
  }

  public void bind() {
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
    managedProperty().bind(visibleProperty());
  }
}
