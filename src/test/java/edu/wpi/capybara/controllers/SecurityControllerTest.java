package edu.wpi.capybara.controllers;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

import edu.wpi.capybara.App;
import edu.wpi.capybara.database.DatabaseConnect;
import java.io.IOException;
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
  }
}
