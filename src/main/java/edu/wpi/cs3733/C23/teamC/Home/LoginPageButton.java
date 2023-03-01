package edu.wpi.cs3733.C23.teamC.Home;

import edu.wpi.cs3733.C23.teamC.navigation.Navigation;
import edu.wpi.cs3733.C23.teamC.navigation.Screen;
import java.io.IOException;

public class LoginPageButton {

  public void loginButton(javafx.event.ActionEvent actionEvent) throws IOException {
    Navigation.navigate(Screen.LOG_IN_PAGE);
  }
}
