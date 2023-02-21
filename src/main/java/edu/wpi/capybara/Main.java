package edu.wpi.capybara;

import edu.wpi.capybara.database.*;
import edu.wpi.capybara.objects.orm.DAOFacade;
import lombok.Getter;

public class Main extends Thread {
  public static RepoFacade db;

  @Getter private static DAOFacade orm;
  @Getter private static RepoFacade2 repo;

  @Getter private static Thread updaterThread;

  public static void main(String[] args) {
    //    DatabaseConnect.connect();
    //    DatabaseConnect.importData();
    AutoUpdate updater = new AutoUpdate();
    updaterThread = new Thread(updater, "AutoUpdater");

    orm = new DAOService();
    repo = new DatabaseService(orm);

    // db = new newDBConnect();
    db = new RepoFacadeAdapter(repo);
    db.importAll();
    updaterThread.start();
    App.launch(App.class, args);
    updater.stop();
  }

  // shortcut: psvm

}
