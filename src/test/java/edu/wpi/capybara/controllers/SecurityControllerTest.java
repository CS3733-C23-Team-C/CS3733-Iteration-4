package edu.wpi.capybara.controllers;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;
import static org.testfx.util.NodeQueryUtils.isVisible;

import edu.wpi.capybara.App;
import edu.wpi.capybara.database.DatabaseConnect;
import java.io.IOException;
import java.util.function.Predicate;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class SecurityControllerTest extends ApplicationTest {
  @Override
  public void start(Stage stage) throws IOException {
    // manually instantiate an App and pass the test stage to its start function
    // TODO: verify that this is the best way to do this (it probably isn't)
    DatabaseConnect.connect();
    DatabaseConnect.importData();
    new App().start(stage);
  }

  @Test
  public void test1() {
    clickOn("#securityButton");
    verifyThat("#Location", isVisible());
    clickOn("#Location");
    moveTo("#Type");
    moveBy(50, 5);
    clickOn(MouseButton.PRIMARY);
    sleep(1000);
    moveBy(-50, 80);
    clickOn(MouseButton.PRIMARY);
    clickOn("#notesUpdate");
    type(KeyCode.O, KeyCode.W);
    Predicate<Node> isEnabled = node -> !node.isDisable();
    verifyThat("#SubmitButton", isEnabled);
    clickOn("#clearButton");
    verifyThat("#notesUpdate", hasText(""));
  }
}
