package edu.wpi.cs3733.C23.teamC.controllers;

import edu.wpi.cs3733.C23.teamC.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AboutPageController {
  public void infoKseniia(MouseEvent mouseEvent) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/InfoKseniia.fxml"));
      Parent root1 = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.setScene(new Scene(root1));
      stage.show();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  public void infoAidan(MouseEvent mouseEvent) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/InfoAidan.fxml"));
      Parent root1 = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.setScene(new Scene(root1));
      stage.show();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  public void infoJohn(MouseEvent mouseEvent) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/InfoJohn.fxml"));
      Parent root1 = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.setScene(new Scene(root1));
      stage.show();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  public void infoSam(MouseEvent mouseEvent) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/InfoSam.fxml"));
      Parent root1 = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.setScene(new Scene(root1));
      stage.show();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  public void infoAlex(MouseEvent mouseEvent) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/InfoAlex.fxml"));
      Parent root1 = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.setScene(new Scene(root1));
      stage.show();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  public void infoSpencer(MouseEvent mouseEvent) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/InfoSpencer.fxml"));
      Parent root1 = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.setScene(new Scene(root1));
      stage.show();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  public void infoChris(MouseEvent mouseEvent) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/InfoChris.fxml"));
      Parent root1 = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.setScene(new Scene(root1));
      stage.show();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  public void infoOwen(MouseEvent mouseEvent) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/InfoOwen.fxml"));
      Parent root1 = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.setScene(new Scene(root1));
      stage.show();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  public void infoDavid(MouseEvent mouseEvent) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/InfoDavid.fxml"));
      Parent root1 = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.setScene(new Scene(root1));
      stage.show();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  public void infoPreston(MouseEvent mouseEvent) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/InfoPreston.fxml"));
      Parent root1 = (Parent) fxmlLoader.load();
      Stage stage = new Stage();
      stage.setScene(new Scene(root1));
      stage.show();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }
}
