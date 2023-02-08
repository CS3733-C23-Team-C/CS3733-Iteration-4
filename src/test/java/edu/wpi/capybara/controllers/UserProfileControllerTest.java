package edu.wpi.capybara.controllers;

import static org.testfx.api.FxAssert.verifyThat;
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

public class UserProfileControllerTest extends ApplicationTest {
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
    RootController.updateUser();
    clickOn("#userProfile");
    clickOn("#profileButton");
    verifyThat("#backButton", isVisible());
    verifyThat("#firstNameSave", Node::isDisable);
    clickOn("#firstNameEdit");
    Predicate<Node> isEnabled = node -> !node.isDisable();
    verifyThat("#firstNameField", isEnabled);
    moveTo("#firstNameField");
    moveBy(-10, 10);
    clickOn(MouseButton.PRIMARY);
    clickOn(MouseButton.PRIMARY);
    clickOn(MouseButton.PRIMARY);
    type(KeyCode.DELETE);
    verifyThat("#firstNameSave", Node::isDisable);
    type(KeyCode.J, KeyCode.O, KeyCode.E);
    verifyThat("#firstNameSave", isEnabled);
    Predicate<Node> notVisable = node -> !node.isVisible();
    verifyThat("#successFirstName", notVisable);
    clickOn("#passwordEdit");
    moveTo("#passwordField");
    moveBy(-10, 10);
    clickOn(MouseButton.PRIMARY);
    type(KeyCode.H, KeyCode.E, KeyCode.H, KeyCode.E, KeyCode.H);
    verifyThat("#passwordSave", Node::isDisable);
    type(KeyCode.E, KeyCode.H, KeyCode.A);
    verifyThat("#passwordSave", isEnabled);
  }
}
