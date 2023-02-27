package edu.wpi.cs3733.C23.teamC.controllers.mapeditor.ui.gfx;

import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.objects.Floor;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.MoveEntity;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.NodeEntity;
import java.sql.Date;
import java.time.Instant;
import java.util.Comparator;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import lombok.Getter;

public class NodeGFX extends GFXBase {
  private static final String STYLE_CLASS = "mapEditorNode";
  private static final double RADIUS = 6;

  @Getter private final Circle circle;
  @Getter private final Label label;

  public NodeGFX(
      NodeEntity node, ObservableBooleanValue showLabel, ObjectProperty<Floor> shownFloor) {
    getStyleClass().addAll("selectable", STYLE_CLASS);
    circle = new Circle(RADIUS);
    label = new Label();

    translateXProperty().bind(node.xcoordProperty());
    translateYProperty().bind(node.ycoordProperty());

    label.setLabelFor(circle);
    label
        .textProperty()
        .bind(
            Bindings.createStringBinding(
                () -> {
                  System.out.println("Computing node name.");
                  final var builder = new StringBuilder(node.getNodeID());
                  builder.append("\n");
                  final var moves =
                      Main.getRepo().getMoves().stream()
                          .filter(move -> move.getNodeID().equals(node.getNodeID()))
                          .sorted(Comparator.comparing(MoveEntity::getMovedate));
                  final var iter = moves.iterator();
                  MoveEntity lastMove = null, nextMove = null;
                  final var now = Date.from(Instant.now());
                  while (iter.hasNext()) {
                    final var move = iter.next();
                    if (move.getMovedate().before(now)) lastMove = move;
                    else {
                      nextMove = move;
                      break;
                    }
                  }

                  if (lastMove != null && nextMove != null) {
                    builder.append(lastMove.getLongName());
                    builder.append(" -> ");
                    builder.append(nextMove.getLongName());
                    builder.append(" [");
                    builder.append(nextMove.getMovedate());
                    builder.append("]");
                  } else if (lastMove == null && nextMove != null) {
                    builder.append("? -> ");
                    builder.append(nextMove.getLongName());
                    builder.append(" [");
                    builder.append(nextMove.getMovedate());
                    builder.append("]");
                  } else if (lastMove != null) {
                    builder.append(lastMove.getLongName());
                  }

                  return builder.toString();
                },
                node.nodeIDProperty(),
                Main.getRepo().getMoves()));
    label.visibleProperty().bind(showLabel);
    label.managedProperty().bind(showLabel);

    label.setTranslateX(RADIUS);

    getChildren().addAll(circle, label);

    final var shouldShow = shownFloor.isEqualTo(node.floorProperty());
    visibleProperty().bind(shouldShow);
    managedProperty().bind(shouldShow);

    // getChildren().forEach(child -> child.getStyleClass().addAll("selectable", STYLE_CLASS));
  }
}
