package edu.wpi.cs3733.C23.teamC;

import edu.wpi.cs3733.C23.teamC.database.*;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.AlertStaff;
import edu.wpi.cs3733.C23.teamC.objects.orm.DAOFacade;
import java.util.List;
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

    //    updaterThread.start();
    //    App.launch(App.class, args);

    List<AlertStaff> as = db.getAlertStaff();

    for (AlertStaff a : as) {
      System.out.println(a.toString());
    }

    updater.stop();
    updaterThread.interrupt();
  }

  // shortcut: psvm

}
