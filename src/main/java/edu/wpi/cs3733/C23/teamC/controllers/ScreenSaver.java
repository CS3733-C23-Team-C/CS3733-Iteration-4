package edu.wpi.cs3733.C23.teamC.controllers;

import edu.wpi.cs3733.C23.teamC.navigation.Navigation;
import edu.wpi.cs3733.C23.teamC.navigation.Screen;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScreenSaver extends Thread {
  private final int duration;
  private static LocalTime lastMove;

  public ScreenSaver(int duration) {
    this.duration = duration;
  }

  //  @Override
  //  public void run() {
  //    log.info("Screensaver shmovving");
  //    try {
  //      while (true) {
  //        Thread.sleep(1000L * duration);
  //        if (!hasMoved) {
  //          Navigation.navigate(Screen.LOG_IN_PAGE_BUTTON);
  //          log.info("SCREENSAVER ACTIVATED");
  //        }
  //
  //        setMoved(false);
  //      }
  //    } catch (InterruptedException e) {
  //      log.info("Ending Screensaver Thread");
  //      this.interrupt();
  //    }
  //  }

  public void run() {
    if (lastMove == null) return;
    if (lastMove.plus(duration, ChronoUnit.SECONDS).isBefore(LocalTime.now())) {
      Navigation.navigate(Screen.LOG_IN_PAGE);
      Navigation.navigate(Screen.LOG_IN_PAGE_BUTTON);
    }
  }

  public static synchronized void setMoved(boolean moved) {
    lastMove = LocalTime.now();
  }
}
