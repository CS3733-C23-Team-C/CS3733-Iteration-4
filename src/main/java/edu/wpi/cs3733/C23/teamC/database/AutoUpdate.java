package edu.wpi.cs3733.C23.teamC.database;

import edu.wpi.cs3733.C23.teamC.Main;

public class AutoUpdate implements Runnable {
  boolean cont;

  public AutoUpdate() {
    cont = true;
  }

  public void run() {
    while (cont) {
      Main.db.threadRefresh(1);
    }
  }

  public void stop() {
    cont = false;
  }
}
