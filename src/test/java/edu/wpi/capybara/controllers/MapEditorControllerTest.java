package edu.wpi.capybara.controllers;

import static org.testfx.api.FxAssert.verifyThat;

import edu.wpi.capybara.App;
import edu.wpi.capybara.Main;
import edu.wpi.capybara.objects.hibernate.newDBConnect;
import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

//public class MapEditorControllerTest extends ApplicationTest {
//  @Override
//  public void start(Stage stage) throws IOException {
//    // manually instantiate an App and pass the test stage to its start function
//    // TODO: verify that this is the best way to do this (it probably isn't)
//    Main.db = new newDBConnect();
//    new App().start(stage);
//  }
//
//  @BeforeEach
//  public void before() {
//    clickOn("#username");
//    type(KeyCode.T, KeyCode.E, KeyCode.S, KeyCode.T);
//    clickOn("#password");
//    type(KeyCode.T, KeyCode.E, KeyCode.S, KeyCode.T);
//    clickOn("#loginButton");
//    clickOn("#mapEditorButton");
//  }
//
//  @Test
//  public void test1() {
//    verifyThat("#nodeTableView", Node::isVisible);
//  }
//}
