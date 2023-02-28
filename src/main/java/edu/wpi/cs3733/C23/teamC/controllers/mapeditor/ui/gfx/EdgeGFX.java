package edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.gfx;

import edu.wpi.cs3733.C23.teamC.objects.Floor;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.EdgeEntity;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import lombok.Getter;

public class EdgeGFX extends GFXBase {
  private static final String STYLE_CLASS = "mapEditorEdge";
  private static final PseudoClass HIDE_NODE_1 = PseudoClass.getPseudoClass("hideNode1");
  private static final PseudoClass HIDE_NODE_2 = PseudoClass.getPseudoClass("hideNode2");

  @Getter private final Line line;
  private final Circle placeholder1, placeholder2;

  private final EdgeEntity edge;
  private final ObjectProperty<Floor> shownFloor;

  private final SimpleBooleanProperty hideNode1 = new SimpleBooleanProperty();
  private final SimpleBooleanProperty hideNode2 = new SimpleBooleanProperty();

  public EdgeGFX(EdgeEntity edge, ObjectProperty<Floor> shownFloor) {
    this.edge = edge;
    this.shownFloor = shownFloor;
    getStyleClass().addAll("selectable", STYLE_CLASS);
    line = new Line();
    placeholder1 = new Circle(3, Color.TRANSPARENT);
    placeholder2 = new Circle(3, Color.TRANSPARENT);
    placeholder1.managedProperty().bind(placeholder1.visibleProperty());
    placeholder1.centerXProperty().bind(line.startXProperty());
    placeholder1.centerYProperty().bind(line.startYProperty());
    placeholder1.visibleProperty().bind(hideNode1);
    placeholder2.managedProperty().bind(placeholder2.visibleProperty());
    placeholder2.centerXProperty().bind(line.endXProperty());
    placeholder2.centerYProperty().bind(line.endYProperty());
    placeholder2.visibleProperty().bind(hideNode2);
    hideNode1.addListener(
        (observable, oldValue, newValue) -> pseudoClassStateChanged(HIDE_NODE_1, newValue));
    hideNode2.addListener(
        (observable, oldValue, newValue) -> pseudoClassStateChanged(HIDE_NODE_2, newValue));

    line.setStrokeWidth(5);

    edge.node1IDProperty().addListener(change -> bind());
    edge.node2IDProperty().addListener(change -> bind());

    bind();

    getChildren().addAll(line, placeholder1, placeholder2);
    // getChildren().forEach(child -> child.getStyleClass().addAll("selectable", STYLE_CLASS));

    edge.getNode1().xcoordProperty().addListener(change -> System.out.println("node change"));

    managedProperty().bind(visibleProperty());
  }

  public void bind() {
    line.startXProperty().unbind();
    line.startYProperty().unbind();
    line.endXProperty().unbind();
    line.endYProperty().unbind();
    visibleProperty().unbind();
    // placeholder1.visibleProperty().unbind();
    // placeholder2.visibleProperty().unbind();
    line.startXProperty().bind(edge.getNode1().xcoordProperty());
    line.startYProperty().bind(edge.getNode1().ycoordProperty());
    line.endXProperty().bind(edge.getNode2().xcoordProperty());
    line.endYProperty().bind(edge.getNode2().ycoordProperty());
    // there's a reason I'm using separate properties here, instead of just adding a listener to the
    // BooleanExpression:
    // there's no way to remove that listener when one of the nodes changes. this would lead to a
    // "zombie" listener
    // that would affect this edge when a seemingly unrelated node was updated. with properties, you
    // can unbind them.
    hideNode1.unbind();
    hideNode2.unbind();
    hideNode1.bind(edge.getNode1().floorProperty().isEqualTo(shownFloor).not());
    hideNode2.bind(edge.getNode2().floorProperty().isEqualTo(shownFloor).not());
    pseudoClassStateChanged(HIDE_NODE_1, hideNode1.get());
    pseudoClassStateChanged(HIDE_NODE_2, hideNode2.get());
    visibleProperty().bind(hideNode1.and(hideNode2).not());
  }
}
