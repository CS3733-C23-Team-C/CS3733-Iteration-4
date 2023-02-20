package edu.wpi.capybara;

import edu.wpi.capybara.database.*;
import edu.wpi.capybara.objects.orm.DAOFacade;
import lombok.Getter;

public class Main {
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
    App.launch(App.class, args);
  }

  // shortcut: psvm

}
