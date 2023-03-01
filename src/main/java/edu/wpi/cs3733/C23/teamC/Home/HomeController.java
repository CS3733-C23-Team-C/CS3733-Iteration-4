package edu.wpi.cs3733.C23.teamC.Home;

import edu.wpi.cs3733.C23.teamC.App;
import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.Messages.MessageBox;
import edu.wpi.cs3733.C23.teamC.ServiceRequests.submissions.SubmissionStatus;
import edu.wpi.cs3733.C23.teamC.database.hibernate.*;
import edu.wpi.cs3733.C23.teamC.navigation.Navigation;
import edu.wpi.cs3733.C23.teamC.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lombok.Getter;

public class HomeController {

  @FXML private Label welcomeTxt;
  @FXML private Text newMessageTxt;
  @FXML private Text assignedTxt;
  @FXML private Text pendingTxt;
  @FXML private MFXScrollPane scrollPane;
  @FXML private VBox vbox;
  @Getter static int unreadMessagesCount;

  final String SECRET_PASSWORD = "team coaching";

  private Map<Integer, MessagesEntity> messages;
  private List<AlertEntity> alerts;

  @Getter private MessageBox messageBox = new MessageBox();

  private ArrayList<Integer> keyList = new ArrayList<>();

  private int assignedRequests;
  private int pendingRequests;

  /** Initialize controller by FXML Loader. */
  @FXML
  public void initialize() {
    System.out.println("I am from HomeController.");

    if (welcomeTxt != null) {
      String text = "Welcome back, " + App.getUser().getFirstname() + "!";
      welcomeTxt.setText(text);
    }
    messages = Main.db.getMessages(App.getUser().getStaffid());
    alerts = App.getUser().allNotReadAlerts();

    for (Integer key : messages.keySet()) {
      keyList.add(key);
    }
    keyList.sort(null);
    showAlerts();
    showMessages();

    findRequests();
    newMessageTxt.setText("" + messageBox.getUnreadMessages());
    assignedTxt.setText("" + assignedRequests);
    pendingTxt.setText("" + pendingRequests);

    unreadMessagesCount = messageBox.getUnreadMessages();

    //    submit.setOnMouseClicked(event -> {});
  }

  /**
   * FXML Injected Method that handles the submit button.
   *
   * @param event The event that triggered the method.
   */
  @FXML
  private void handleButtonSubmit(ActionEvent event) {
    /*
     1. Get the text the user input
     2. Validate it against the correct information
     3. Display to the user whether their input was correct or not
    */

  }

  /**
   * Checks if a user's password is correct.
   *
   * @param input The user's password.
   * @return True if the password is correct.
   */
  private boolean validate(final String input) {
    return input.equals(SECRET_PASSWORD);
  }

  public void showMessages() {
    for (int i = (keyList.size() - 1); i >= 0; i--) {
      MessagesEntity message = messages.get(keyList.get(i));
      if (!message.getRead()) {
        HBox newMessage = messageBox.addHomeMessage(message);
        vbox.getChildren().add(newMessage);
      }
    }
  }

  public void showAlerts() {
    for (int i = (alerts.size() - 1); i >= 0; i--) {
      AlertEntity alert = alerts.get(i);
      HBox newAlert = messageBox.addHomeAlert(alert);
      vbox.getChildren().add(newAlert);
      System.out.println(alert.getMessage());
    }
  }

  public void showAssigned() {
    Navigation.navigate(Screen.ASSIGNED_REQUESTS);
  }

  public void showPending() {
    Navigation.navigate(Screen.REQUESTS);
  }

  public void showMessage() {
    Navigation.navigate(Screen.MESSAGES);
  }

  public void findRequests() {
    int i = 0;
    while (i < 5) {
      String currentID = App.getUser().getStaffid();
      switch (i) {
        case 0:
          Map<Integer, CleaningsubmissionEntity> cleaningdata = Main.db.getCleaningSubs();
          for (CleaningsubmissionEntity data : cleaningdata.values()) {
            if (data.getAssignedid().equals(currentID)
                && (!data.getSubmissionstatus().equals(SubmissionStatus.DONE))) assignedRequests++;
            if (data.getMemberid().equals(currentID)
                && (data.getSubmissionstatus().equals(SubmissionStatus.BLANK)
                    || data.getSubmissionstatus().equals(SubmissionStatus.PROCESSING)))
              pendingRequests++;
          }
          i++;
          break;
        case 1:
          Map<Integer, TransportationsubmissionEntity> transportdata =
              Main.db.getTransportationSubs();
          for (TransportationsubmissionEntity data : transportdata.values()) {
            if (data.getAssignedid().equals(currentID)
                && (!data.getStatus().equals(SubmissionStatus.DONE))) assignedRequests++;
            if (data.getEmployeeid().equals(currentID)
                && (data.getStatus().equals(SubmissionStatus.BLANK)
                    || data.getStatus().equals(SubmissionStatus.PROCESSING))) pendingRequests++;
          }
          i++;
          break;
        case 2:
          Map<Integer, SecuritysubmissionEntity> securitydata = Main.db.getSecuritySubs();
          for (SecuritysubmissionEntity data : securitydata.values()) {
            if (data.getAssignedid().equals(currentID)
                && (!data.getSubmissionstatus().equals(SubmissionStatus.DONE))) assignedRequests++;
            if (data.getEmployeeid().equals(currentID)
                && (data.getSubmissionstatus().equals(SubmissionStatus.BLANK)
                    || data.getSubmissionstatus().equals(SubmissionStatus.PROCESSING)))
              pendingRequests++;
          }
          i++;
          break;
        case 3:
          Map<Integer, AudiosubmissionEntity> audiodata = Main.db.getAudioSubs();
          for (AudiosubmissionEntity data : audiodata.values()) {
            if (data.getAssignedid().equals(currentID)
                && (!data.getSubmissionstatus().equals(SubmissionStatus.DONE))) assignedRequests++;
            if (data.getEmployeeid().equals(currentID)
                && (data.getSubmissionstatus().equals(SubmissionStatus.BLANK)
                    || data.getSubmissionstatus().equals(SubmissionStatus.PROCESSING)))
              pendingRequests++;
          }
          i++;
          break;
        case 4:
          Map<Integer, ComputersubmissionEntity> computerdata = Main.db.getComputerSubs();
          for (ComputersubmissionEntity data : computerdata.values()) {
            if (data.getAssignedid().equals(currentID)
                && (!data.getSubmissionstatus().equals(SubmissionStatus.DONE))) assignedRequests++;
            if (data.getEmployeeid().equals(currentID)
                && (data.getSubmissionstatus().equals(SubmissionStatus.BLANK)
                    || data.getSubmissionstatus().equals(SubmissionStatus.PROCESSING)))
              pendingRequests++;
          }
          i++;
          break;
      }
    }
  }
}
