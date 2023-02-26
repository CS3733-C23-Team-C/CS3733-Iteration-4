package edu.wpi.cs3733.C23.teamC.controllers;

public final class Singleton {
  private static final Singleton instance = new Singleton();
  private String data;

  //    private Singleton() {
  //        // Private constructor to prevent instantiation from outside the class
  //        data = "This is some data for the Singleton";
  //    }

  public static Singleton getInstance() {
    //        if (instance == null) {
    //            instance = new Singleton();
    //        }
    return instance;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }
}
