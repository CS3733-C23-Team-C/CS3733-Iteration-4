package edu.wpi.capybara;

import edu.wpi.capybara.database.DatabaseConnect;

public class Main {


  public static void main(String[] args) {
    DatabaseConnect.connect();
    DatabaseConnect.importData();
    App.launch(App.class, args);
  }

  // shortcut: psvm

}
