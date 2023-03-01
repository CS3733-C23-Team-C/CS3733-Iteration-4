package edu.wpi.cs3733.C23.teamC.mapeditor.ui.gfx;

import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.database.hibernate.MoveEntity;
import edu.wpi.cs3733.C23.teamC.database.hibernate.NodeEntity;
import edu.wpi.cs3733.C23.teamC.mapeditor.Floor;
import java.sql.Date;
import java.time.Instant;
import java.util.Comparator;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableDoubleValue;
import javafx.geometry.VPos;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import lombok.Getter;

public class NodeGFX extends GFXBase {
  private static final String STYLE_CLASS = "mapEditorNode";
  private static final double RADIUS = 6;

  @Getter private final Circle circle;
  @Getter private final Text label;

  public NodeGFX(
      NodeEntity node,
      ObservableBooleanValue showLabel,
      ObjectProperty<Floor> shownFloor,
      ObservableDoubleValue scale) {
    getStyleClass().addAll("selectable", STYLE_CLASS);
    circle = new Circle(RADIUS);
    label = new Text();

    translateXProperty().bind(node.xcoordProperty());
    translateYProperty().bind(node.ycoordProperty());

    label
        .textProperty()
        .bind(
            Bindings.createStringBinding(
                () -> {
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
    label.setTextAlignment(TextAlignment.LEFT);
    label.setTextOrigin(VPos.TOP);

    final var tform = new Scale(1, 1, 0, 0);
    scale.addListener(
        observable -> {
          tform.setX(1 / scale.get());
          tform.setY(1 / scale.get());
        });
    label.getTransforms().addAll(Transform.translate(1.5 * RADIUS, 0), tform);

    getChildren().addAll(circle, label);

    final var shouldShow = shownFloor.isEqualTo(node.floorProperty());
    visibleProperty().bind(shouldShow);
    managedProperty().bind(shouldShow);

    // getChildren().forEach(child -> child.getStyleClass().addAll("selectable", STYLE_CLASS));
  }
}
