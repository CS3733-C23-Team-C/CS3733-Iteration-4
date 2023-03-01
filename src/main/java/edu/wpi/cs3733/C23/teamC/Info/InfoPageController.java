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
            + "- Can be used to create/delete nodes, edges, moves, and locations\n"
            + "- Select nodes and edges by clicking on them or by clicking and dragging on the map\n"
            + "- Selecting something will deselect everything else. You can override this by holding \nSHIFT\n"
            + "- Create nodes by right-clicking on the map > Add Node. Don't forget to assign a building \nusing the table view!\n"
            + "- To create edges, select the nodes you want to connect and right-click > Connect Nodes\n"
            + "- You can delete the selected items by right-clicking the map and selecting Delete\n"
            + "- You can click and drag edges and nodes to move them around\n"
            + "- Moves and locations can be created via the right-click menu on the corresponding \ntable view\n"
            + "- You can align nodes by selecting a single edge and using the align options in the \nright-click menu"
            + "- You can pan the map by middle-clicking and dragging, and zoom by scrolling");
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
