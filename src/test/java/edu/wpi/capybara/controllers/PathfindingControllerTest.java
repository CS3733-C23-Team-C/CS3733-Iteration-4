package edu.wpi.capybara.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;

import edu.wpi.capybara.App;
import edu.wpi.capybara.database.DatabaseConnect;
import java.io.IOException;
import java.util.function.Predicate;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class PathfindingControllerTest extends ApplicationTest {

  public void start(Stage stage) throws IOException {
    DatabaseConnect.connect();
    new App().start(stage);
  }

  @BeforeEach
  public void beforeAllTests() {
    clickOn("#menuM");
    clickOn("#pathfinding");
    sleep(1000);
  }

  @Test
  public void pageOpenTest() {
    // This just makes sure that the pathfinding page can open
    assertTrue(true);
  }

  @Test
  public void validateButtonTest() {
    moveTo("#currRoom");
    moveBy(50, 0);
    clickOn(MouseButton.PRIMARY);
    type(KeyCode.R, KeyCode.E, KeyCode.S, KeyCode.T, KeyCode.R, KeyCode.O, KeyCode.O, KeyCode.M);
    moveBy(-50, 100);
    clickOn(MouseButton.PRIMARY);
    verifyThat("#submitButton", Node::isDisable);

    moveTo("#destRoom");
    moveBy(50, 0);
    clickOn(MouseButton.PRIMARY);
    type(KeyCode.R, KeyCode.E, KeyCode.S, KeyCode.T, KeyCode.R, KeyCode.O, KeyCode.O, KeyCode.M);
    moveBy(-50, 150);
    clickOn(MouseButton.PRIMARY);
    verifyThat("#submitButton", Node::isDisable);

    moveTo("#idField");
    clickOn(MouseButton.PRIMARY);
    type(KeyCode.T, KeyCode.E, KeyCode.S, KeyCode.T);
    verifyThat("#submitButton", Node::isDisable);

    Predicate<Node> isEnabled = node -> !node.isDisable();

    moveTo("#dateField");
    clickOn(MouseButton.PRIMARY);
    type(
        KeyCode.DIGIT1,
        KeyCode.SLASH,
        KeyCode.DIGIT1,
        KeyCode.SLASH,
        KeyCode.DIGIT2,
        KeyCode.DIGIT3);
    verifyThat("#submitButton", isEnabled);
  }
}
