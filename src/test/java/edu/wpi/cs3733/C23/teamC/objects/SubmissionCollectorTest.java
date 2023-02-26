// package edu.wpi.cs3733.C23.teamC.objects;
//
// import static org.junit.jupiter.api.Assertions.assertNotNull;
//
// import edu.wpi.cs3733.C23.teamC.App;
// import edu.wpi.cs3733.C23.teamC.database.DatabaseConnect;
// import edu.wpi.cs3733.C23.teamC.objects.hibernate.StaffEntity;
// import edu.wpi.cs3733.C23.teamC.objects.submissions.SubmissionCollector;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
//
// public class SubmissionCollectorTest {
//
//  @BeforeEach
//  public void be4() {
//    DatabaseConnect.connect();
//    DatabaseConnect.importData();
//  }
//
//  @Test
//  public void getterTest() {
//    App.setUser(new StaffEntity("aaaa", "fname", "lname", "12345",""));
//    SubmissionCollector sc = new SubmissionCollector();
//    assertNotNull(sc.getCleaningData());
//  }
// }
