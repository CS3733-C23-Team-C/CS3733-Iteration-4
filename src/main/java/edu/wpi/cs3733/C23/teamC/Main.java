package edu.wpi.cs3733.C23.teamC;

import edu.wpi.cs3733.C23.teamC.database.*;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.AlertEntity;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.StaffEntity;
import edu.wpi.cs3733.C23.teamC.objects.orm.DAOFacade;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class Main extends Thread {
  public static RepoFacade db;

  @Getter @Setter private static DAOFacade orm;
  @Getter @Setter private static RepoFacade2 repo;

  @Getter private static Thread updaterThread;

  public static void main(String[] args) {
    //    DatabaseConnect.connect();
    //    DatabaseConnect.importData();

    orm = new DAOService();
    repo = new DatabaseService(orm);

    // db = new newDBConnect();
    db = new RepoFacadeAdapter(repo);
    db.importAll();
    AutoUpdate updater = new AutoUpdate();
    updaterThread = new Thread(updater, "AutoUpdater");

    System.out.println(repo.getNewAlertID());
    //    updaterThread.start();
    //    App.launch(App.class, args);
    //    }
    updater.stop();
    updaterThread.interrupt();

    java.util.Date date = new java.util.Date();
    AlertEntity testAlert =
        new AlertEntity(repo.getNewAlertID(), new java.sql.Date(date.getTime()), "please");
    Map<String, StaffEntity> staffMap = db.getStaff();
    List<StaffEntity> staffList = new ArrayList<>();
    for (StaffEntity staff : staffMap.values()) {
      staffList.add(staff);
    }
    testAlert.addStaff(staffList);
    
  }

  // shortcut: psvm

}
