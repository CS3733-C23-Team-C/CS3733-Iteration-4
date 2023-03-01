package edu.wpi.cs3733.C23.teamC.StaffInfo;

public final class Singleton2 {
  private static final Singleton2 instance = new Singleton2();
  private String data;

  //    private Singleton() {
  //        // Private constructor to prevent instantiation from outside the class
  //        data = "This is some data for the Singleton";
  //    }

  public static Singleton2 getInstance() {
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
