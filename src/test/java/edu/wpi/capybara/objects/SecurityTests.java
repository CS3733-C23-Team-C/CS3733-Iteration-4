package edu.wpi.capybara.objects;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.objects.hibernate.SecuritysubmissionEntity;
import edu.wpi.capybara.objects.hibernate.newDBConnect;
import edu.wpi.capybara.objects.submissions.SubmissionStatus;
import java.io.IOException;
import java.sql.Date;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

public class SecurityTests extends ApplicationTest {
  @Override
  public void start(Stage stage) throws IOException {
    // manually instantiate an App and pass the test stage to its start function
    // TODO: verify that this is the best way to do this (it probably isn't)
    Main.db = new newDBConnect();
  }

  @Test
  public void test1() {
    SecuritysubmissionEntity test =
        new SecuritysubmissionEntity(
            "12345",
            "15 Lobby Entrance",
            "Police Department",
            "Test",
            SubmissionStatus.BLANK,
            "12345",
            150,
            "URGENT",
            new Date(1000),
            new Date(1000));
    Main.db.addSecurity(test);
    test.setAssignedid("12345");
    test.setSubmissionid(150);
    test.setCreatedate(new Date(1000));
    test.setDuedate(new Date(1000));
    test.setNotesupdate("Test");
    test.setSubmissionstatus(SubmissionStatus.BLANK);
    test.setType("Police Department");
    assertTrue(test.getAssignedid().equals("12345"));
    assertFalse(test.getAssignedid().equals("0"));
    Main.db.deleteSecurity(test.getSubmissionid());
  }
}
