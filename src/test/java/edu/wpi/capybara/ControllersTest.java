package edu.wpi.capybara;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;
import static org.testfx.util.NodeQueryUtils.isVisible;

import edu.wpi.capybara.database.DatabaseConnect;
import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class ControllersTest extends ApplicationTest {
  @Override
  public void start(Stage stage) throws IOException {
    // manually instantiate an App and pass the test stage to its start function
    // TODO: verify that this is the best way to do this (it probably isn't)
    DatabaseConnect.connect();
    new App().start(stage);
  }

  @Test
  public void test1() {
    // click on the node with the id "navigateButton"
    clickOn("#transportationButton");
    // verify that a node with id "backButton" is visible

    verifyThat("#transportationStackPane", isVisible());

    clickOn("#idField");
    type(KeyCode.DIGIT3);
    verifyThat("#submitButton", Node::isDisable);
    clickOn("#clearButton");
    verifyThat("#idField", hasText(""));
    clickOn("#backButton");
    verifyThat("#transportationButton", isVisible());
  }
}
