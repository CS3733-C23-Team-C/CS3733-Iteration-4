package edu.wpi.capybara.controllers;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

import edu.wpi.capybara.App;
import edu.wpi.capybara.Main;
import edu.wpi.capybara.database.newDBConnect;
import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class AudioVisualControllerTest extends ApplicationTest {
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
    clickOn("#serviceRequestsButton");
  }

  @Test
  public void test1() {
    clickOn("#audioButton");
    clickOn("#assignedStaffID");
    type(KeyCode.DIGIT1);
    clickOn("#Location");
    moveBy(75, 0);
    clickOn(MouseButton.PRIMARY);
    sleep(500);
    moveBy(-75, 100);
    clickOn(MouseButton.PRIMARY);
    verifyThat("#submitButton", Node::isDisable);
    clickOn("#clearButton");
    verifyThat("#assignedStaffID", hasText(""));
    clickOn("#assignedStaffID");
    type(KeyCode.DIGIT1);
    clickOn("#Location");
    moveBy(75, 0);
    clickOn(MouseButton.PRIMARY);
    sleep(500);
    moveBy(-75, 115);
    sleep(500);
    clickOn(MouseButton.PRIMARY);
    clickOn("#requestSpecific");
    moveBy(75, 0);
    clickOn(MouseButton.PRIMARY);
    sleep(500);
    moveBy(-75, 100);
    clickOn(MouseButton.PRIMARY);
    clickOn("#emergencyLevel");
    moveBy(75, 0);
    clickOn(MouseButton.PRIMARY);
    sleep(500);
    moveBy(-75, 100);
    clickOn(MouseButton.PRIMARY);
    moveTo("#date");
    moveBy(90, 0);
    clickOn(MouseButton.PRIMARY);
    sleep(500);
    moveBy(-75, 225);
    clickOn(MouseButton.PRIMARY);
    clickOn("#notes");
    type(KeyCode.N, KeyCode.O);
    clickOn("#submitButton");
    verifyThat("#submissionReceived", Node::isVisible);
  }
}
