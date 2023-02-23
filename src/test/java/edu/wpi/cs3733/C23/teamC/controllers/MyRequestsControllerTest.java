package edu.wpi.cs3733.C23.teamC.controllers;

import static org.testfx.api.FxAssert.verifyThat;

import edu.wpi.cs3733.C23.teamC.App;
import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.database.newDBConnect;
import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class MyRequestsControllerTest extends ApplicationTest {
  @Override
  public void start(Stage stage) throws IOException {
    // manually instantiate an App and pass the test stage to its start function
    // TODO: verify that this is the best way to do this (it probably isn't)
    Main.db = new newDBConnect();
    new App().start(stage);
  }

  @BeforeEach
  public void before() {
    clickOn("#username");
    type(KeyCode.T, KeyCode.E, KeyCode.S, KeyCode.T);
    clickOn("#password");
    type(KeyCode.T, KeyCode.E, KeyCode.S, KeyCode.T);
    clickOn("#loginButton");
    clickOn("#userProfile");
    clickOn("#requestsButton");
  }

  @Test
  public void test1() {
    verifyThat("#fieldsEdit", Node::isVisible);
  }
}
