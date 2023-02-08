package edu.wpi.capybara.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.objects.hibernate.*;
import edu.wpi.capybara.objects.submissions.submissionStatus;
import java.sql.Date;
import org.junit.jupiter.api.Test;

public class AllObjectsTest {

  @Test
  public void nodeTest() {

    DatabaseConnect.connect();
    NodeEntity ne = new NodeEntity("98765", 99, 99, "67", "Atwater");

    assertEquals(ne.getNodeid(), "98765");
    assertEquals(ne.getXcoord(), 99);
    assertEquals(ne.getYcoord(), 99);
    assertEquals(ne.getFloor(), "67");
    assertEquals(ne.getBuilding(), "Atwater");
  }

  @Test
  public void edgeTest() {

    EdgeEntity ee = new EdgeEntity("5788", "6788");

    assertEquals(ee.getNode1(), "5788");
    assertEquals(ee.getNode2(), "6788");
  }

  @Test
  public void edgeTestPK() {

    EdgeEntityPK ee = new EdgeEntityPK("5788", "6788");

    assertEquals(ee.getNode1(), "5788");
    assertEquals(ee.getNode2(), "6788");
  }

  @Test
  public void locationNameTest() {
    LocationnameEntity ln = new LocationnameEntity("test location", "tl", "office");

    assertEquals(ln.getLocationtype(), "office");
    assertEquals(ln.getLongname(), "test location");
    assertEquals(ln.getShortname(), "tl");
  }

  @Test
  public void moveTest() {

    MoveEntity move = new MoveEntity("11", "test location", new Date(100000000));

    assertEquals(move.getNodeid(), "11");
    assertEquals(move.getLongname(), "test location");
    assertEquals(move.getMovedate(), new Date(100000000));
  }

  @Test
  public void moveTestPK() {

    MoveEntityPK move = new MoveEntityPK("11", "test location", new Date(100000000));

    assertEquals(move.getNodeid(), "11");
    assertEquals(move.getLongname(), "test location");
    assertEquals(move.getMovedate(), new Date(100000000));
  }

  // needs to launch app to
  @Test
  public void cleaningSubmissionTest() {

    CleaningsubmissionEntity cs =
        new CleaningsubmissionEntity(
            "4566", "office 67", "big", "i spilt concentrated hcl oopsie", submissionStatus.BLANK);

    assertEquals(cs.getMemberid(), "4566");
    assertEquals(cs.getLocation(), "office 67");
    assertEquals(cs.getHazardlevel(), "big");
    assertEquals(cs.getDescription(), "i spilt concentrated hcl oopsie");
    assertEquals(cs.getSubmissionstatus(), submissionStatus.BLANK);
  }

  @Test
  public void cleaningSubmissionTestPK() {

    CleaningsubmissionEntityPK cs =
        new CleaningsubmissionEntityPK(
            "4566", "office 67", "big", "i spilt concentrated hcl oopsie", submissionStatus.BLANK);

    assertEquals(cs.getMemberid(), "4566");
    assertEquals(cs.getLocation(), "office 67");
    assertEquals(cs.getHazardlevel(), "big");
    assertEquals(cs.getDescription(), "i spilt concentrated hcl oopsie");
    assertEquals(cs.getSubmissionstatus(), submissionStatus.BLANK);
  }

  @Test
  public void transportationSubmissionTest() {
    TransportationsubmissionEntity ts =
        new TransportationsubmissionEntity(
            "1738", "Exam room", "ICU", "dropped my phone during surgery", submissionStatus.BLANK);

    assertEquals(ts.getEmployeeid(), "1738");
    assertEquals(ts.getCurrroomnum(), "Exam room");
    assertEquals(ts.getDestroomnum(), "ICU");
    assertEquals(ts.getReason(), "dropped my phone during surgery");
    assertEquals(ts.getStatus(), submissionStatus.BLANK);
  }

  @Test
  public void transportationSubmissionTestPK() {
    TransportationsubmissionEntityPK ts =
        new TransportationsubmissionEntityPK(
            "1738", "Exam room", "ICU", "dropped my phone during surgery", submissionStatus.BLANK);

    assertEquals(ts.getEmployeeid(), "1738");
    assertEquals(ts.getCurrroomnum(), "Exam room");
    assertEquals(ts.getDestroomnum(), "ICU");
    assertEquals(ts.getReason(), "dropped my phone during surgery");
    assertEquals(ts.getStatus(), submissionStatus.BLANK);
  }

  @Test
  public void staffTest() {
    StaffEntity user = new StaffEntity("5555", "Benjamin", "Dover", "password");

    assertEquals(user.getFirstname(), "Benjamin");
    assertEquals(user.getLastname(), "Dover");
    assertEquals(user.getStaffid(), "5555");
    assertEquals(user.getPassword(), "password");
  }

  @Test
  public void securityTest() {
    SecuritysubmissionEntity sc = new SecuritysubmissionEntity("111", "Kaven", "Good", "No report");

    assertEquals(sc.getEmployeeid(), "111");
    assertEquals(sc.getLocation(), "Kaven");
    assertEquals(sc.getType(), "Good");
    assertEquals(sc.getNotesupdate(), "No report");
  }

  @Test
  public void securityPKTest() {
    SecuritysubmissionEntityPK sc =
        new SecuritysubmissionEntityPK("111", "Kaven", "Good", "No report");

    assertEquals(sc.getEmployeeid(), "111");
    assertEquals(sc.getLocation(), "Kaven");
    assertEquals(sc.getType(), "Good");
    assertEquals(sc.getNotesupdate(), "No report");
  }
}
