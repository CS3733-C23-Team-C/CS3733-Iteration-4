package edu.wpi.cs3733.C23.teamC;

import edu.wpi.cs3733.C23.teamC.Home.ScreenSaver;
import edu.wpi.cs3733.C23.teamC.Pathfinding.ImageLoader;
import edu.wpi.cs3733.C23.teamC.database.hibernate.StaffEntity;
import edu.wpi.cs3733.C23.teamC.navigation.Navigation;
import edu.wpi.cs3733.C23.teamC.navigation.Screen;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {

  @Setter @Getter private static Stage primaryStage;
  @Setter @Getter private static BorderPane rootPane;

  private ScheduledExecutorService runEveryFiveSec;

  // @Getter private static SubmissionCollector totalSubmissions = new SubmissionCollector();

  @Getter private static StaffEntity user;

  @Getter private static StaffEntity tempuser;

  @Override
  public void init() {
    log.info("Starting Up");
    user = new StaffEntity();
    ImageLoader.loadImages();

    ScreenSaver screenSaver = new ScreenSaver(60);
    Platform.runLater(screenSaver);

    runEveryFiveSec = Executors.newSingleThreadScheduledExecutor();
    runEveryFiveSec.scheduleAtFixedRate(
        () -> Platform.runLater(screenSaver), 5, 5, TimeUnit.SECONDS);

    ScreenSaver.setMoved(true);
    //    user.setStaffid("0000");
    //    user.setFirstname("Joe");
    //    user.setLastname("Mama");
    //    user.setPassword("HeHeHeHa");
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    /* primaryStage is generally only used if one of your components require the stage to display */
    App.primaryStage = primaryStage;
    App.primaryStage.setMinHeight(700.0);
    App.primaryStage.setMinWidth(950.0);

    final FXMLLoader loader = new FXMLLoader(App.class.getResource("views/EmptyRoot.fxml"));
    final BorderPane root = loader.load();

    App.rootPane = root;

    final Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();

    Navigation.navigate(Screen.LOG_IN_PAGE);
    // RootController.updateUser();
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
    runEveryFiveSec.shutdown();
  }

  public static void setUser(StaffEntity newUser) {
    user = newUser;
  }

  public static void setTempUser(StaffEntity newtempUser) {
    tempuser = newtempUser;
  }
}
