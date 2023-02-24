package edu.wpi.cs3733.C23.teamC;

import edu.wpi.cs3733.C23.teamC.database.*;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.AlertEntity;
import edu.wpi.cs3733.C23.teamC.objects.orm.DAOFacade;
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
    updaterThread.start();

    // System.out.println(db.getStaff("admin").getAlerts());

    // App.launch(App.class, args);
    for (AlertEntity alert : db.getStaff("admin").getAlerts()) {
      System.out.println(alert.getMessage());
    }
    updater.stop();
  }

  // shortcut: psvm

}
