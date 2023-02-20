package edu.wpi.capybara;

import edu.wpi.capybara.database.RepoFacade;
import edu.wpi.capybara.database.newDBConnect;

public class Main extends Thread {
  public static RepoFacade db;

  @Getter private static DAOFacade orm;
  @Getter private static RepoFacade2 repo;

  public static void main(String[] args) {
    //    DatabaseConnect.connect();
    //    DatabaseConnect.importData();
    orm = new DAOService();
    repo = new DatabaseService(orm);

    db = new newDBConnect();
    db.importAll();
    AutoUpdate updater = new AutoUpdate();
    Thread thread = new Thread(updater, "AutoUpdater");
    thread.start();
    App.launch(App.class, args);
    updater.stop();
  }

  // shortcut: psvm

}
