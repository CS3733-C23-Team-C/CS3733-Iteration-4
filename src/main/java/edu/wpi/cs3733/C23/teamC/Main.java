package edu.wpi.cs3733.C23.teamC;

import edu.wpi.cs3733.C23.teamC.database.*;
import edu.wpi.cs3733.C23.teamC.database.orm.DAOFacade;
import lombok.Getter;
import lombok.Setter;

public class Main extends Thread {
  public static RepoFacade db;

  @Getter @Setter private static DAOFacade orm;
  @Getter @Setter private static RepoFacade2 repo;

  @Getter private static Thread updaterThread;
  @Getter private static Thread screenTimeout;

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

    App.launch(App.class, args);

    updater.stop();
    updaterThread.interrupt();
  }

  // shortcut: psvm

}
