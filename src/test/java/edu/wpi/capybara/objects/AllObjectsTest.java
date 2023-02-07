package edu.wpi.capybara.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.objects.submissions.cleaningSubmission;
import edu.wpi.capybara.objects.submissions.submissionStatus;
import edu.wpi.capybara.objects.submissions.transportationSubmission;
import java.sql.Date;
import org.junit.jupiter.api.Test;

public class AllObjectsTest {

  @Test
  public void locationNameTest() {
    locationname ln = new locationname("test location", "tl", "office");

    assertEquals(ln.getLocationtype(), "office");
    assertEquals(ln.getLongname(), "test location");
    assertEquals(ln.getShortname(), "tl");
  }

  @Test
  public void moveTest() {
    move move = new move("11", "test location", new Date(100000000));

    assertEquals(move.getNodeid(), "11");
    assertEquals(move.getLongname(), "test location");
    assertEquals(move.getMovedate(), new Date(100000000));
  }

  // needs to launch app to
  @Test
  public void cleaningSubmissionTest() {
    cleaningSubmission cs =
        new cleaningSubmission("office 67", "big", "i spilt concentrated hcl oopsie");

    assertEquals(cs.getLocation(), "office 67");
    assertEquals(cs.getHazardLevel(), "big");
    assertEquals(cs.getDescription(), "i spilt concentrated hcl oopsie");
    assertEquals(cs.getStatus(), submissionStatus.BLANK);
  }

  @Test
  public void transportationSubmissionTest() {
    DatabaseConnect.connect();
    transportationSubmission ts =
        new transportationSubmission("req", "my office", "susan's office");
    assertEquals(ts.getCurrRoom(), "req");
    assertEquals(ts.getDestRoom(), "my office");
    assertEquals(ts.getDestRoom(), "my office");
    assertEquals(ts.getStatus(), submissionStatus.BLANK);
  }

  @Test
  public void staffTest() {
    staff user = new staff("Benjamin", "Dover", "5555", "password");

    assertEquals(user.getFirstName(), "Benjamin");
    assertEquals(user.getLastName(), "Dover");
    assertEquals(user.getStaffID(), "5555");
    assertEquals(user.getPassword(), "password");
  }

  //  @Test
  //  public void buttonValidationTestFalse() {
  //    TransportationController tc = new TransportationController();
  //    tc.getCurrRoom().setText("");
  //    tc.getDestRoom().setText("");
  //    tc.getReasonField().setText("mean people");
  //    tc.getIdField().setText("55");
  //    assertFalse(tc.getSubmitButton().isDisabled());
  //  }
}
