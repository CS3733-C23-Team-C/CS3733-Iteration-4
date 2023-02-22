package edu.wpi.capybara.controllers.mapeditor.ui;

import edu.wpi.capybara.App;
import edu.wpi.capybara.controllers.mapeditor.ui.elements.*;
import edu.wpi.capybara.objects.Floor;
import edu.wpi.capybara.objects.math.Vector2;
import java.util.Objects;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.SetChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MapEditorMapView {
  // UI components
  private final ImageView floorF1, floorF2, floorF3, floorL1, floorL2;
  private final SimpleDoubleProperty viewX, viewY, zoom;
  private final StackPane rootPane;
  private final Pane viewPane;

  // rootPane (receives drag events)
  //   |- viewPane (translates)
  //        |- floorF1
  //        |- floorF2
  //        |- floorF3
  //        |- floorL1
  //        |- floorL2

  public MapEditorMapView(UIModel model, StackPane rootPane) {
    this.rootPane = rootPane;
    rootPane.setAlignment(Pos.TOP_LEFT);

    floorF1 = createFloorImage("blankF1.png", Floor.F1, model.shownFloorProperty());
    floorF2 = createFloorImage("blankF2.png", Floor.F2, model.shownFloorProperty());
    floorF3 = createFloorImage("blankF3.png", Floor.F3, model.shownFloorProperty());
    floorL1 = createFloorImage("blankL1.png", Floor.L1, model.shownFloorProperty());
    floorL2 = createFloorImage("blankL2.png", Floor.L2, model.shownFloorProperty());

    viewX = new SimpleDoubleProperty(0);
    viewY = new SimpleDoubleProperty(0);
    zoom = new SimpleDoubleProperty(1);

    viewPane = new Pane(floorF1, floorF2, floorF3, floorL1, floorL2);
    viewPane.translateXProperty().bind(viewX);
    viewPane.translateYProperty().bind(viewY);
    rootPane.scaleXProperty().bind(zoom);
    rootPane.scaleYProperty().bind(zoom);
    rootPane.getChildren().add(viewPane);

    final var dragOffsetVector = new Vector2(0, 0);
    rootPane.addEventFilter(
        MouseEvent.MOUSE_PRESSED,
        event -> {
          if (event.getButton() == MouseButton.PRIMARY) {
            dragOffsetVector.setX(event.getX() - viewX.get());
            dragOffsetVector.setY(event.getY() - viewY.get());
          }
        });
    rootPane.addEventHandler(
        MouseEvent.MOUSE_PRESSED,
        event -> {
          if (event.getButton() == MouseButton.PRIMARY) {
            rootPane.setCursor(Cursor.CLOSED_HAND);
          }
        });
    rootPane.addEventHandler(
        MouseEvent.MOUSE_DRAGGED,
        event -> {
          rootPane.setCursor(Cursor.CLOSED_HAND);
          final var eventLocation = new Vector2(event.getX(), event.getY());
          eventLocation.subtract(dragOffsetVector);
          viewX.setValue(eventLocation.getX());
          viewY.setValue(eventLocation.getY());
        });
    rootPane.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> rootPane.setCursor(Cursor.DEFAULT));
    //        rootPane.addEventHandler(
    //                MouseEvent.MOUSE_RELEASED,
    //                event -> {
    //                    if (event.getButton() == MouseButton.PRIMARY && event.isStillSincePress())
    // {
    //                        // deselectAll();
    //                        // todo
    //                    }
    //                });

    rootPane.addEventFilter(
        ScrollEvent.SCROLL,
        event -> {
          if (event.getDeltaY() == 0) return;
          final var ZOOM_COEFFICIENT = 0.003;
          final var MIN_ZOOM = 0.1;
          final var MAX_ZOOM = 5;

          final var eventOrigin = new Vector2(event.getX(), event.getY());
          final var nodeOrigin = new Vector2(viewX.get(), viewY.get());

          final var zoomOffset = Vector2.minus(nodeOrigin, eventOrigin);
          final var zoomDelta = ZOOM_COEFFICIENT * event.getDeltaY();
          var newZoom = zoom.get() * (1 + zoomDelta);
          if (newZoom > MAX_ZOOM) newZoom = MAX_ZOOM;
          else if (newZoom < MIN_ZOOM) newZoom = MIN_ZOOM;

          // normalize, rescale, then offset zoomOffset
          zoomOffset.divide(zoom.get()).multiply(newZoom).add(eventOrigin);
          // viewX.set(zoomOffset.getX());
          // viewY.set(zoomOffset.getY());

          zoom.set(newZoom);
        });

    model
        .elementsProperty()
        .addListener(
            (SetChangeListener<? super Element>)
                change -> {
                  if (change.wasAdded()) addElement(change.getElementAdded());
                  if (change.wasRemoved()) removeElement(change.getElementRemoved());
                });

    model.elementsProperty().forEach(this::addElement);

    // zoom out a bit for the user's convenience
    // zoom.set(0.4);
  }

  public void addElement(Element element) {
    if (element instanceof EdgeElement) {
      viewPane.getChildren().add(5, element.getNode());
    } else {
      viewPane.getChildren().add(element.getNode());
    }
  }

  public void removeElement(Element element) {
    viewPane.getChildren().add(element.getNode());
  }

  private ImageView createFloorImage(
      String imageName, Floor floor, ObjectProperty<Floor> shownFloor) {
    final var imageView =
        new ImageView(
            new Image(
                Objects.requireNonNull(App.class.getResourceAsStream("images/" + imageName))));
    imageView.setX(0);
    imageView.setY(0);
    imageView.setLayoutX(0);
    imageView.setLayoutY(0);
    imageView.setTranslateX(0);
    imageView.setTranslateY(0);
    imageView.visibleProperty().bind(shownFloor.isEqualTo(floor));
    return imageView;
  }
}
