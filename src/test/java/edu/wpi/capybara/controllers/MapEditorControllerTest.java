package edu.wpi.capybara.controllers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.wpi.capybara.App;
import edu.wpi.capybara.database.DatabaseConnect;
import java.io.IOException;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class MapEditorControllerTest extends ApplicationTest {

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
    clickOn("#menuM");
    clickOn("#mapEditorM");
    assertTrue(true);
  }
}
