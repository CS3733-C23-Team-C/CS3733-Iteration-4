package edu.wpi.cs3733.C23.teamC.controllers;
import edu.wpi.cs3733.C23.teamC.navigation.Screen;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
public class ScreenSaver implements Runnable {
  private int duration;

  public ScreenSaver(int duration) {
    this.duration = duration;
  }


  public void start(FXML test) {

    test.setOnMouseMoved(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        label.setText("Mouse moved to (" + event.getX() + ", " + event.getY() + ")");
      }


      public void run() {


    try {
      System.out.println("Timer started...");
      Thread.sleep(duration * 1000);
      System.out.println("Timer ended.");
    } catch (InterruptedException e) {
      System.out.println("Timer interrupted.");
      return;
    }
  }
}
