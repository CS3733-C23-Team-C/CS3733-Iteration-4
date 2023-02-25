package edu.wpi.cs3733.C23.teamC;

import edu.wpi.cs3733.C23.teamC.database.*;
import edu.wpi.cs3733.C23.teamC.objects.orm.DAOFacade;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import lombok.Getter;
import lombok.Setter;

public class Main extends Thread {
  public static RepoFacade db;

  @Getter @Setter private static DAOFacade orm;
  @Getter @Setter private static RepoFacade2 repo;

  @Getter private static Thread updaterThread;

  public static void main(String[] args) {
    //    DatabaseConnect.connect();
    //    DatabaseConnect.importData();

    orm = new DAOService();
    repo = new DatabaseService(orm);

    // db = new newDBConnect();
    db = new RepoFacadeAdapter(repo);
    db.importAll();
    AutoUpdate updater = new AutoUpdate();
    updaterThread = new Thread(updater, "AutoUpdater");
    // updaterThread.start();
    // App.launch(App.class, args);
    //    try {
    //      repo.setImage(
    //
    // "C:\\Users\\owen\\Documents\\GitHub\\CS3733-Iteration-4\\src\\main\\resources\\capybara.png");
    //    } catch (IOException e) {
    //      throw new RuntimeException(e);
    //    }

    ByteArrayInputStream inStreambj = new ByteArrayInputStream(repo.getImage(1));
    try {
      BufferedImage newImage = ImageIO.read(inStreambj);
      File file = new File("C:\\Users\\owen\\Desktop\\outputImage.png");
      file.getParentFile().mkdirs();
      ImageIO.write(newImage, "png", file);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    updater.stop();
  }

  // shortcut: psvm

}
