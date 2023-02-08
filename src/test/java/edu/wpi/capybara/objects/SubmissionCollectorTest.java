package edu.wpi.capybara.objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import edu.wpi.capybara.App;
import edu.wpi.capybara.database.DatabaseConnect;
import edu.wpi.capybara.objects.hibernate.StaffEntity;
import edu.wpi.capybara.objects.submissions.submissionCollector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SubmissionCollectorTest {

  @BeforeEach
  public void be4() {
    DatabaseConnect.connect();
    DatabaseConnect.importData();
  }

  @Test
  public void getterTest() {
    App.setUser(new StaffEntity("aaaa", "fname", "lname", "12345"));
    submissionCollector sc = new submissionCollector();
    assertNotNull(sc.getCleaningData());
  }
}
