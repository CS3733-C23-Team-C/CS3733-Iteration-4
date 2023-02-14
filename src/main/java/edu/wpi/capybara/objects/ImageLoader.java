package edu.wpi.capybara.objects;

import edu.wpi.capybara.App;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javafx.scene.image.Image;
import lombok.Getter;

public class ImageLoader {
  @Getter private static Future<Image> L1, L2, F1, F2, F3;
  private static ExecutorService executorService;

  public static void loadImages() {
    executorService = Executors.newSingleThreadExecutor();
    L1 = loadImage("images/blankL1.png");
    L2 = loadImage("images/blankL2.png");
    F1 = loadImage("images/blankF1.png");
    F2 = loadImage("images/blankF2.png");
    F3 = loadImage("images/blankF3.png");
    executorService.shutdown();
  }

  private static Future<Image> loadImage(String img) {
    return executorService.submit(
        () -> new Image(Objects.requireNonNull(App.class.getResourceAsStream(img))));
  }
}
