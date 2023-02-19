package edu.wpi.capybara;

import edu.wpi.capybara.database.AutoUpdate;
import edu.wpi.capybara.database.RepoFacade;
import edu.wpi.capybara.database.newDBConnect;

public class Main extends Thread {
  public static RepoFacade db;

  public static void main(String[] args) {
    //    DatabaseConnect.connect();
    //    DatabaseConnect.importData();
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
