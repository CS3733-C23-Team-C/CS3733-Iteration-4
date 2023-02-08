package edu.wpi.capybara.objects;

import edu.wpi.capybara.objects.hibernate.*;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AllObjectsTest {

    @Test
    public void locationNameTest() {
      LocationnameEntity ln = new LocationnameEntity();
      ln.setLongname("test location");
      ln.setShortname("tl");
      ln.setLocationtype("office");

      assertEquals(ln.getLocationtype(), "office");
      assertEquals(ln.getLongname(), "test location");
      assertEquals(ln.getShortname(), "tl");
    }

    @Test
    public void moveTest() {
      MoveEntity move = new MoveEntity();

      move.setNodeid("11");
      move.setLongname("test location");
      move.setMovedate(new Date(100000000));

      assertEquals(move.getNodeid(), "11");
      assertEquals(move.getLongname(), "test location");
      assertEquals(move.getMovedate(), new Date(100000000));
    }

    // needs to launch app to
    @Test
    public void cleaningSubmissionTest() {
      CleaningsubmissionEntity cs = new CleaningsubmissionEntity();

      cs.setLocation("office 67");
      cs.setHazardlevel("big");
      cs.setDescription("i spilt concentrated hcl oopsie");

      assertEquals(cs.getLocation(), "office 67");
      assertEquals(cs.getHazardlevel(), "big");
      assertEquals(cs.getDescription(), "i spilt concentrated hcl oopsie");
    }

    @Test
    public void transportationSubmissionTest() {
      TransportationsubmissionEntity ts = new TransportationsubmissionEntity();

      ts.setEmployeeid("1738");
      ts.setCurrroomnum("Exam room");
      ts.setDestroomnum("ICU");
      ts.setReason("dropped my phone during surgery");

      assertEquals(ts.getEmployeeid(), "1738");
      assertEquals(ts.getCurrroomnum(), "Exam room");
      assertEquals(ts.getDestroomnum(), "ICU");
      assertEquals(ts.getReason(), "dropped my phone during surgery");
    }

    @Test
    public void staffTest() {
      StaffEntity user = new StaffEntity();

      user.setFirstname("Benjamin");
      user.setLastname("Dover");
      user.setStaffid("5555");
      user.setPassword("password");

      assertEquals(user.getFirstname(), "Benjamin");
      assertEquals(user.getLastname(), "Dover");
      assertEquals(user.getStaffid(), "5555");
      assertEquals(user.getPassword(), "password");
    }
}
