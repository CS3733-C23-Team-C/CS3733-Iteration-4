package edu.wpi.capybara.controllers;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.isVisible;

import edu.wpi.capybara.App;
import edu.wpi.capybara.Main;
import edu.wpi.capybara.database.newDBConnect;
import java.io.IOException;
import java.util.function.Predicate;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class UserProfileControllerTest extends ApplicationTest {
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
    clickOn("#profileButton");
  }

  @Test
  public void test1() {
    verifyThat("#fieldsSave", isVisible());
    clickOn("#fieldsEdit");
    Predicate<Node> isEnabled = node -> !node.isDisable();
    verifyThat("#firstNameField", isEnabled);
    moveTo("#firstNameField");
    moveBy(-10, 10);
    clickOn(MouseButton.PRIMARY);
    clickOn(MouseButton.PRIMARY);
    clickOn(MouseButton.PRIMARY);
    type(KeyCode.DELETE);
    clickOn("#fieldsSave");
    verifyThat("#errorTxt", isVisible());
    clickOn("#firstNameField");
    type(KeyCode.J, KeyCode.O, KeyCode.E);
    clickOn("#fieldsSave");
  }
}
