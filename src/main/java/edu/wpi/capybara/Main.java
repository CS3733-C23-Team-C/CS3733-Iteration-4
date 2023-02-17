package edu.wpi.capybara;

import edu.wpi.capybara.database.RepoFacade;
import edu.wpi.capybara.database.newDBConnect;

public class Main {
  public static RepoFacade db;

  public static void main(String[] args) {
    //    DatabaseConnect.connect();
    //    DatabaseConnect.importData();
    db = new newDBConnect();
    db.importAll();
    App.launch(App.class, args);
  }

  // shortcut: psvm

}
