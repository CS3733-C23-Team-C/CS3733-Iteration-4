package edu.wpi.capybara.controllers;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;
import static org.testfx.util.NodeQueryUtils.isVisible;

import edu.wpi.capybara.App;
import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.objects.hibernate.StaffEntity;
import java.io.IOException;
import java.util.function.Predicate;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class TransportationAndCleaningTest extends ApplicationTest {
  @Override
  public void start(Stage stage) throws IOException {
    // manually instantiate an App and pass the test stage to its start function
    // TODO: verify that this is the best way to do this (it probably isn't)
    DatabaseConnect.connect();
    DatabaseConnect.importData();
    new App().start(stage);
    StaffEntity user = new StaffEntity();
    user.setStaffid("3000");
    user.setFirstname("Joe");
    user.setLastname("Mama");
    user.setPassword("HeHeHeHa");
    App.setUser(user);
  }

  @Test
  public void test1() {
    clickOn("#transportationButton");
    // verify that a node with id "backButton" is visible
    // verifyThat("#clearButton", isVisible());
    clickOn("#currRoom");
    type(KeyCode.DIGIT3);
    verifyThat("#submitButton", Node::isDisable);
    clickOn("#clearButton");
    verifyThat("#currRoom", hasText(""));
    clickOn("#currRoom");
    type(KeyCode.DIGIT4);
    clickOn("#destRoom");
    type(KeyCode.DIGIT5);
    clickOn("#reasonField");
    type(KeyCode.N);
    type(KeyCode.O);
    Predicate<Node> isEnabled = node -> !node.isDisable();
    verifyThat("submitButton", isEnabled);
    // clickOn("#submitButton");
    // verifyThat("reasonField", hasText(""));
    clickOn("#backButton");
    verifyThat("#transportationButton", isVisible());
    clickOn("#servicesButtonM");
    clickOn("#cleaningButtonM");
    verifyThat("#SubmitButton", isVisible());
    clickOn("#MemberID");
    type(KeyCode.DIGIT3);
    clickOn("#hazardLevel");
    type(KeyCode.DIGIT4);
    verifyThat("#SubmitButton", isVisible());
    clickOn("#ClearButton");
    verifyThat("#MemberID", hasText(""));
  }
}
