package edu.wpi.cs3733.C23.teamC.Info;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class InfoPageController {
  @FXML private MFXButton helpProfile;
  @FXML private Text infoMessage;
  @FXML private VBox vboxText;

  public void goToHelpProfile(ActionEvent actionEvent) {
    vboxText.setAlignment(Pos.TOP_LEFT);
    infoMessage.setText(
        "User Profile\n\n"
            + "- The user profile has 4 options:\n"
            + "       My Profile - change user information such as name and password\n"
            + "                           admins get access to csv export\n"
            + "       My Requests - look through the table or edit created requests\n"
            + "       Assigned Requests - look through the table or edit assigned requests\n"
            + "       Log Out - return to the log in page");
  }

  public void goToHelpHome(ActionEvent actionEvent) {
    vboxText.setAlignment(Pos.TOP_LEFT);
    infoMessage.setText(
        "Home Page\n\n" + "- Landing page that has a welcome message and a hospital image");
  }

  public void goToHelpService(ActionEvent actionEvent) {
    vboxText.setAlignment(Pos.TOP_LEFT);
    infoMessage.setText(
        "Service Requests Page\n\n"
            + "- There are 5 service requests that could be submitted:\n"
            + "       Transportation Request\n"
            + "       Sanitation Request\n"
            + "       Security Request\n"
            + "       Computer Request\n"
            + "       Audio/Visual Request");
  }

  public void goToHelpPathfinding(ActionEvent actionEvent) {
    vboxText.setAlignment(Pos.TOP_LEFT);
    infoMessage.setText(
        "Pathfinding Page\n\n"
            + "- Fill out the fields to find a path from one location to another\n"
            + "- Click on the nodes to learn more about them\n"
            + "- Current location and destination location can be chosen by clicking on the nodes");
  }

  public void goToHelpMap(ActionEvent actionEvent) {
    vboxText.setAlignment(Pos.TOP_LEFT);
    infoMessage.setText(
        "Map Editor Page\n\n"
            + "- Can be used to create/delete nodes, edges, and moves\n"
            + "- Select multiple objects by using shift + left click\n"
            + "- Right click to select add or delete\n"
            + "- Nodes, edges, and moves can be created through the pane on the right");
  }

  public void goToHelpEmployee(ActionEvent actionEvent) {
    vboxText.setAlignment(Pos.TOP_LEFT);
    infoMessage.setText(
        "Employee Editor\n\n"
            + "- Available only for admin user\n"
            + "- Can search for an employee and edit information");
  }

  public void goToHelpCredits(ActionEvent actionEvent) {
    vboxText.setAlignment(Pos.TOP_LEFT);
    infoMessage.setText("Credits Page\n\n" + "- Lists all of the used API and images");
  }

  public void goToHelpAbout(ActionEvent actionEvent) {
    vboxText.setAlignment(Pos.TOP_LEFT);
    infoMessage.setText("About Page\n\n" + "- Gives general information about the project");
  }
}
