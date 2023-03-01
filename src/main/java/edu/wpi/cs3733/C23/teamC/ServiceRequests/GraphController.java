package edu.wpi.cs3733.C23.teamC.controllers;

import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.Main.*;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.*;
import io.github.palexdev.materialfx.controls.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;

public class GraphController {

  @FXML private MFXDatePicker StartDate;
  @FXML private MFXDatePicker EndDate;
  @FXML private MFXComboBox<String> request;
  @FXML private MFXTextField assignedStaffID;
  @FXML private MFXCheckbox AllEmployees;
  @FXML private MFXButton GraphButton;
  @FXML private LineChart<String, Integer> CoolGraph;
  @FXML private CategoryAxis Xaxis;
  @FXML private NumberAxis Yaxis;
  @FXML private MFXButton ClearButton;
  @FXML private Text ErrorMessage;

  public void initialize() {
    request.getItems().addAll("ALL", "Audio", "Cleaning", "Computer", "Transportation", "Security");
  }

  public void
      validateButton() { // ensures that information has been filled in before allowing submission
    boolean valid = false;
    if (StartDate.getValue() != null
        && EndDate.getValue() != null
        && request.getValue() != null
        && (AllEmployees.isSelected() == true || !assignedStaffID.getText().equals("")))
      valid = true;
    GraphButton.setDisable(!valid);
  }

  public void ClearGraph() {
    CoolGraph.getData().clear();
    CoolGraph.setVisible(false);
  }

  public void MakeGraph() throws ParseException {

    String EMID = assignedStaffID.getText();
    String Person = "";
    String Request = request.getValue();
    String ThePerson = "ALL";

    // Staff ID
    if (AllEmployees.isSelected()) {
      Person = "All";
    } else if (Main.db.getStaff(EMID) == null) {
      ErrorMessage.setText("This user Does on Excise.");
      ErrorMessage.setVisible(true);
      return;
    } else {
      Person = assignedStaffID.getText();
      ThePerson =
          Main.db.getStaff(Person).getFirstname() + " " + Main.db.getStaff(Person).getLastname();
    }

    // Date
    LocalDate GetSDate = StartDate.getValue();
    Date Startdate = Date.from(GetSDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    LocalDate GetEDate = EndDate.getValue();
    Date Enddate = Date.from(GetEDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

    if (Startdate.compareTo(Enddate) > 0) {
      ErrorMessage.setText("The End date is before the Start Date.");
      ErrorMessage.setVisible(true);
      return;
    }

    Xaxis.setLabel("Dates");
    // Xaxis.setAutoRanging(true);
    Yaxis.setLabel("Requests");
    // Yaxis.setAutoRanging(true);
    String formatedStartDate = new SimpleDateFormat("yyyy-MM-dd").format(Startdate);
    String formatedEndDate = new SimpleDateFormat("yyyy-MM-dd").format(Enddate);
    CoolGraph.setTitle("Chart of Request");

    XYChart.Series series1 = new XYChart.Series();
    series1.setName("Portfolio 1");
    //        series1.getData().add(new XYChart.Data<>("2022-12-01", 1));
    //    series1.getData().add(new XYChart.Data<>("2022-12-02", 2));
    //    series1.getData().add(new XYChart.Data<>("2022-12-03", 3));
    //    series1.getData().add(new XYChart.Data<>("2022-12-04", 4));
    //    series1.getData().add(new XYChart.Data<>("2022-12-05", 5));

    Date idate = Startdate;
    String theDate;

    // Specific user
    if (!AllEmployees.isSelected()) {

      System.out.println("One person is selected");

      // All requests
      if (Request.equals("ALL")) {

        HashMap<Date, Integer> map = new HashMap<>();

        for (AudiosubmissionEntity a : Main.db.getAudioSubs().values()) {
          if (Person.equals(a.getAssignedid())) {
            if (map.containsKey(a.getCreatedate()))
              map.put(a.getCreatedate(), map.get(a.getCreatedate()) + 1);
            else map.put(a.getCreatedate(), 1);
          }
        }
        for (CleaningsubmissionEntity a : Main.db.getCleaningSubs().values()) {
          if (Person.equals(a.getAssignedid())) {
            if (map.containsKey(a.getCreatedate()))
              map.put(a.getCreatedate(), map.get(a.getCreatedate()) + 1);
            else map.put(a.getCreatedate(), 1);
          }
        }
        for (ComputersubmissionEntity a : Main.db.getComputerSubs().values()) {
          if (Person.equals(a.getAssignedid())) {
            if (map.containsKey(a.getCreatedate()))
              map.put(a.getCreatedate(), map.get(a.getCreatedate()) + 1);
            else map.put(a.getCreatedate(), 1);
          }
        }
        for (TransportationsubmissionEntity a : Main.db.getTransportationSubs().values()) {
          if (Person.equals(a.getAssignedid())) {
            if (map.containsKey(a.getCreatedate()))
              map.put(a.getCreatedate(), map.get(a.getCreatedate()) + 1);
            else map.put(a.getCreatedate(), 1);
          }
        }
        for (SecuritysubmissionEntity a : Main.db.getSecuritySubs().values()) {
          if (Person.equals(a.getAssignedid())) {
            if (map.containsKey(a.getCreatedate()))
              map.put(a.getCreatedate(), map.get(a.getCreatedate()) + 1);
            else map.put(a.getCreatedate(), 1);
          }
        }

        while (idate.compareTo(Enddate) != 0) {
          theDate = new SimpleDateFormat("dd/MM").format(idate);

          if (map.get(idate) != null) {
            series1.getData().add(new XYChart.Data(theDate, map.get(idate)));
          } else series1.getData().add(new XYChart.Data(theDate, 0));

          Calendar calendar = Calendar.getInstance();
          calendar.setTime(idate);
          calendar.add(Calendar.DATE, 1);
          idate = calendar.getTime();
        }
        String sDate = new SimpleDateFormat("dd/MM").format(Startdate);
        String eDate = new SimpleDateFormat("dd/MM").format(Enddate);
        series1.setName(Request + " From " + sDate + " to " + eDate + " for " + Person);
      }

      // Audio
      else if (Request.equals("Audio")) {

        HashMap<Date, Integer> map = new HashMap<>();
        for (AudiosubmissionEntity a : Main.db.getAudioSubs().values()) {
          if (Person.equals(a.getAssignedid())) {
            if (map.containsKey(a.getCreatedate()))
              map.put(a.getCreatedate(), map.get(a.getCreatedate()) + 1);
            else map.put(a.getCreatedate(), 1);
          }
        }

        while (idate.compareTo(Enddate) != 0) {
          theDate = new SimpleDateFormat("dd/MM").format(idate);

          if (map.get(idate) != null) {
            series1.getData().add(new XYChart.Data(theDate, map.get(idate)));
          } else series1.getData().add(new XYChart.Data(theDate, 0));

          Calendar calendar = Calendar.getInstance();
          calendar.setTime(idate);
          calendar.add(Calendar.DATE, 1);
          idate = calendar.getTime();
        }
        String sDate = new SimpleDateFormat("dd/MM").format(Startdate);
        String eDate = new SimpleDateFormat("dd/MM").format(Enddate);
        series1.setName(Request + " From " + sDate + " to " + eDate + " for " + Person);
      }

      // Cleaning
      else if (Request.equals("Cleaning")) {

        HashMap<Date, Integer> map = new HashMap<>();
        for (CleaningsubmissionEntity a : Main.db.getCleaningSubs().values()) {
          if (Person.equals(a.getAssignedid())) {
            if (map.containsKey(a.getCreatedate())) {
              map.put(a.getCreatedate(), map.get(a.getCreatedate()) + 1);
            } else {
              map.put(a.getCreatedate(), 1);
            }
          }
        }

        while (idate.compareTo(Enddate) != 0) {
          theDate = new SimpleDateFormat("dd/MM").format(idate);

          if (map.get(idate) != null) {
            series1.getData().add(new XYChart.Data(theDate, map.get(idate)));
          } else series1.getData().add(new XYChart.Data(theDate, 0));

          Calendar calendar = Calendar.getInstance();
          calendar.setTime(idate);
          calendar.add(Calendar.DATE, 1);
          idate = calendar.getTime();
        }
        String sDate = new SimpleDateFormat("dd/MM").format(Startdate);
        String eDate = new SimpleDateFormat("dd/MM").format(Enddate);
        series1.setName(Request + " From " + sDate + " to " + eDate + " for " + Person);

      }

      // Computer
      else if (Request.equals("Computer")) {

        HashMap<Date, Integer> map = new HashMap<>();
        for (ComputersubmissionEntity a : Main.db.getComputerSubs().values()) {
          if (Person.equals(a.getAssignedid())) {
            if (map.containsKey(a.getCreatedate())) {
              map.put(a.getCreatedate(), map.get(a.getCreatedate()) + 1);
            } else {
              map.put(a.getCreatedate(), 1);
            }
          }
        }

        while (idate.compareTo(Enddate) != 0) {
          theDate = new SimpleDateFormat("dd/MM").format(idate);

          if (map.get(idate) != null) {
            series1.getData().add(new XYChart.Data(theDate, map.get(idate)));
          } else series1.getData().add(new XYChart.Data(theDate, 0));

          Calendar calendar = Calendar.getInstance();
          calendar.setTime(idate);
          calendar.add(Calendar.DATE, 1);
          idate = calendar.getTime();
        }
        String sDate = new SimpleDateFormat("dd/MM").format(Startdate);
        String eDate = new SimpleDateFormat("dd/MM").format(Enddate);
        series1.setName(Request + " From " + sDate + " to " + eDate + " for " + Person);

        // Transports
      } else if (Request.equals("Transportation")) {

        HashMap<Date, Integer> map = new HashMap<>();
        for (TransportationsubmissionEntity a : Main.db.getTransportationSubs().values()) {
          if (Person.equals(a.getAssignedid())) {
            if (map.containsKey(a.getCreatedate())) {
              map.put(a.getCreatedate(), map.get(a.getCreatedate()) + 1);
            } else {
              map.put(a.getCreatedate(), 1);
            }
          }
        }

        while (idate.compareTo(Enddate) != 0) {
          theDate = new SimpleDateFormat("dd/MM").format(idate);

          if (map.get(idate) != null) {
            series1.getData().add(new XYChart.Data(theDate, map.get(idate)));
          } else series1.getData().add(new XYChart.Data(theDate, 0));

          Calendar calendar = Calendar.getInstance();
          calendar.setTime(idate);
          calendar.add(Calendar.DATE, 1);
          idate = calendar.getTime();
        }
        String sDate = new SimpleDateFormat("dd/MM").format(Startdate);
        String eDate = new SimpleDateFormat("dd/MM").format(Enddate);
        series1.setName(Request + " From " + sDate + " to " + eDate + " for " + Person);

        // Security
      } else if (Request.equals("Security")) {
        HashMap<Date, Integer> map = new HashMap<>();
        for (SecuritysubmissionEntity a : Main.db.getSecuritySubs().values()) {
          if (Person.equals(a.getAssignedid())) {
            if (map.containsKey(a.getCreatedate())) {
              map.put(a.getCreatedate(), map.get(a.getCreatedate()) + 1);
            } else {
              map.put(a.getCreatedate(), 1);
            }
          }
        }

        while (idate.compareTo(Enddate) != 0) {
          theDate = new SimpleDateFormat("dd/MM").format(idate);

          if (map.get(idate) != null) {
            series1.getData().add(new XYChart.Data(theDate, map.get(idate)));
          } else series1.getData().add(new XYChart.Data(theDate, 0));

          Calendar calendar = Calendar.getInstance();
          calendar.setTime(idate);
          calendar.add(Calendar.DATE, 1);
          idate = calendar.getTime();
        }
        String sDate = new SimpleDateFormat("dd/MM").format(Startdate);
        String eDate = new SimpleDateFormat("dd/MM").format(Enddate);
        series1.setName(Request + " From " + sDate + " to " + eDate + " for " + Person);
      }
    }

    // All employees are selected
    if (AllEmployees.isSelected()) {

      System.out.println("One person is selected");

      // All requests
      if (Request.equals("ALL")) {

        HashMap<Date, Integer> map = new HashMap<>();

        for (AudiosubmissionEntity a : Main.db.getAudioSubs().values()) {
          if (map.containsKey(a.getCreatedate()))
            map.put(a.getCreatedate(), map.get(a.getCreatedate()) + 1);
          else map.put(a.getCreatedate(), 1);
        }
        for (CleaningsubmissionEntity a : Main.db.getCleaningSubs().values()) {
          if (map.containsKey(a.getCreatedate()))
            map.put(a.getCreatedate(), map.get(a.getCreatedate()) + 1);
          else map.put(a.getCreatedate(), 1);
        }
        for (ComputersubmissionEntity a : Main.db.getComputerSubs().values()) {
          if (map.containsKey(a.getCreatedate()))
            map.put(a.getCreatedate(), map.get(a.getCreatedate()) + 1);
          else map.put(a.getCreatedate(), 1);
        }
        for (TransportationsubmissionEntity a : Main.db.getTransportationSubs().values()) {
          if (map.containsKey(a.getCreatedate()))
            map.put(a.getCreatedate(), map.get(a.getCreatedate()) + 1);
          else map.put(a.getCreatedate(), 1);
        }
        for (SecuritysubmissionEntity a : Main.db.getSecuritySubs().values()) {
          if (map.containsKey(a.getCreatedate()))
            map.put(a.getCreatedate(), map.get(a.getCreatedate()) + 1);
          else map.put(a.getCreatedate(), 1);
        }

        while (idate.compareTo(Enddate) != 0) {
          theDate = new SimpleDateFormat("dd/MM").format(idate);

          if (map.get(idate) != null) {
            series1.getData().add(new XYChart.Data(theDate, map.get(idate)));
          } else series1.getData().add(new XYChart.Data(theDate, 0));

          Calendar calendar = Calendar.getInstance();
          calendar.setTime(idate);
          calendar.add(Calendar.DATE, 1);
          idate = calendar.getTime();
        }
        String sDate = new SimpleDateFormat("dd/MM").format(Startdate);
        String eDate = new SimpleDateFormat("dd/MM").format(Enddate);
        series1.setName(Request + " From " + sDate + " to " + eDate + " for ALL");

      }

      // Audio
      else if (Request.equals("Audio")) {

        HashMap<Date, Integer> map = new HashMap<>();
        for (AudiosubmissionEntity a : Main.db.getAudioSubs().values()) {
          if (map.containsKey(a.getCreatedate()))
            map.put(a.getCreatedate(), map.get(a.getCreatedate()) + 1);
          else map.put(a.getCreatedate(), 1);
        }

        while (idate.compareTo(Enddate) != 0) {
          theDate = new SimpleDateFormat("dd/MM").format(idate);

          if (map.get(idate) != null) {
            series1.getData().add(new XYChart.Data(theDate, map.get(idate)));
          } else series1.getData().add(new XYChart.Data(theDate, 0));

          Calendar calendar = Calendar.getInstance();
          calendar.setTime(idate);
          calendar.add(Calendar.DATE, 1);
          idate = calendar.getTime();
        }
        String sDate = new SimpleDateFormat("dd/MM").format(Startdate);
        String eDate = new SimpleDateFormat("dd/MM").format(Enddate);
        series1.setName(Request + " From " + sDate + " to " + eDate + " for ALL");
      }

      // Cleaning
      else if (Request.equals("Cleaning")) {

        HashMap<Date, Integer> map = new HashMap<>();
        for (CleaningsubmissionEntity a : Main.db.getCleaningSubs().values()) {
          if (map.containsKey(a.getCreatedate())) {
            map.put(a.getCreatedate(), map.get(a.getCreatedate()) + 1);
          } else {
            map.put(a.getCreatedate(), 1);
          }
        }

        while (idate.compareTo(Enddate) != 0) {
          theDate = new SimpleDateFormat("dd/MM").format(idate);

          if (map.get(idate) != null) {
            series1.getData().add(new XYChart.Data(theDate, map.get(idate)));
          } else series1.getData().add(new XYChart.Data(theDate, 0));

          Calendar calendar = Calendar.getInstance();
          calendar.setTime(idate);
          calendar.add(Calendar.DATE, 1);
          idate = calendar.getTime();
        }
        String sDate = new SimpleDateFormat("dd/MM").format(Startdate);
        String eDate = new SimpleDateFormat("dd/MM").format(Enddate);
        series1.setName(Request + " From " + sDate + " to " + eDate + " for ALL");

      }

      // Computer
      else if (Request.equals("Computer")) {

        HashMap<Date, Integer> map = new HashMap<>();
        for (ComputersubmissionEntity a : Main.db.getComputerSubs().values()) {
          if (map.containsKey(a.getCreatedate())) {
            map.put(a.getCreatedate(), map.get(a.getCreatedate()) + 1);
          } else {
            map.put(a.getCreatedate(), 1);
          }
        }

        while (idate.compareTo(Enddate) != 0) {
          theDate = new SimpleDateFormat("dd/MM").format(idate);

          if (map.get(idate) != null) {
            series1.getData().add(new XYChart.Data(theDate, map.get(idate)));
          } else series1.getData().add(new XYChart.Data(theDate, 0));

          Calendar calendar = Calendar.getInstance();
          calendar.setTime(idate);
          calendar.add(Calendar.DATE, 1);
          idate = calendar.getTime();
        }
        String sDate = new SimpleDateFormat("dd/MM").format(Startdate);
        String eDate = new SimpleDateFormat("dd/MM").format(Enddate);
        series1.setName(Request + " From " + sDate + " to " + eDate + " for ALL");

        // Transports
      } else if (Request.equals("Transportation")) {

        HashMap<Date, Integer> map = new HashMap<>();
        for (TransportationsubmissionEntity a : Main.db.getTransportationSubs().values()) {
          if (map.containsKey(a.getCreatedate())) {
            map.put(a.getCreatedate(), map.get(a.getCreatedate()) + 1);
          } else {
            map.put(a.getCreatedate(), 1);
          }
        }

        while (idate.compareTo(Enddate) != 0) {
          theDate = new SimpleDateFormat("dd/MM").format(idate);

          if (map.get(idate) != null) {
            series1.getData().add(new XYChart.Data(theDate, map.get(idate)));
          } else series1.getData().add(new XYChart.Data(theDate, 0));

          Calendar calendar = Calendar.getInstance();
          calendar.setTime(idate);
          calendar.add(Calendar.DATE, 1);
          idate = calendar.getTime();
        }
        String sDate = new SimpleDateFormat("dd/MM").format(Startdate);
        String eDate = new SimpleDateFormat("dd/MM").format(Enddate);
        series1.setName(Request + " From " + sDate + " to " + eDate + " for ALL");

        // Security
      } else if (Request.equals("Security")) {
        HashMap<Date, Integer> map = new HashMap<>();
        for (SecuritysubmissionEntity a : Main.db.getSecuritySubs().values()) {
          if (map.containsKey(a.getCreatedate())) {
            map.put(a.getCreatedate(), map.get(a.getCreatedate()) + 1);
          } else {
            map.put(a.getCreatedate(), 1);
          }
        }

        while (idate.compareTo(Enddate) != 0) {
          theDate = new SimpleDateFormat("dd/MM").format(idate);

          if (map.get(idate) != null) {
            series1.getData().add(new XYChart.Data(theDate, map.get(idate)));
          } else series1.getData().add(new XYChart.Data(theDate, 0));

          Calendar calendar = Calendar.getInstance();
          calendar.setTime(idate);
          calendar.add(Calendar.DATE, 1);
          idate = calendar.getTime();
        }
        String sDate = new SimpleDateFormat("dd/MM").format(Startdate);
        String eDate = new SimpleDateFormat("dd/MM").format(Enddate);
        series1.setName(Request + " From " + sDate + " to " + eDate + " for ALL");
      }
    }

    CoolGraph.getData().addAll(series1);
    CoolGraph.setVisible(true);
    ErrorMessage.setVisible(false);
  }
}
