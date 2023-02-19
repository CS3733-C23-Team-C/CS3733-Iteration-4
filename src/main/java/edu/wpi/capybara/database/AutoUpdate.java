package edu.wpi.capybara.database;

import edu.wpi.capybara.Main;

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
