package edu.wpi.capybara.controllers;

import edu.wpi.capybara.App;
import edu.wpi.capybara.Main;
import edu.wpi.capybara.objects.orm.*;
import edu.wpi.capybara.objects.submissions.SubmissionStatus;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.utils.EnumStringConverter;
import java.util.Map;
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class MyRequestsController {

  @FXML TableView<CleaningsubmissionEntity> cleanRequests;
  @FXML TableView<TransportationsubmissionEntity> transportationRequests;
  @FXML TableView<SecuritysubmissionEntity> securityRequests;
  @FXML TableView<AudiosubmissionEntity> audioRequests;
  @FXML TableView<ComputersubmissionEntity> computerRequests;

  @FXML TableColumn<CleaningsubmissionEntity, String> cleanID;
  @FXML TableColumn<TransportationsubmissionEntity, String> transportationID;
  @FXML TableColumn<SecuritysubmissionEntity, String> securityID;
  @FXML TableColumn<CleaningsubmissionEntity, String> hazardLevel;
  @FXML TableColumn<CleaningsubmissionEntity, String> cleanDescription;
  @FXML TableColumn<TransportationsubmissionEntity, String> reason;
  @FXML TableColumn<SecuritysubmissionEntity, String> securityDescription;
  @FXML TableColumn<CleaningsubmissionEntity, String> cleanLocation;
  @FXML TableColumn<TransportationsubmissionEntity, String> transportationLocation;
  @FXML TableColumn<SecuritysubmissionEntity, String> securityLocation;
  @FXML TableColumn<CleaningsubmissionEntity, SubmissionStatus> cleanStatus;
  @FXML TableColumn<TransportationsubmissionEntity, SubmissionStatus> transportationStatus;
  @FXML TableColumn<SecuritysubmissionEntity, SubmissionStatus> securityStatus;
  @FXML TableColumn<TransportationsubmissionEntity, String> destination;
  @FXML TableColumn<SecuritysubmissionEntity, String> type;
  @FXML TableColumn<CleaningsubmissionEntity, String> cleanEmployeeAssigned;
  @FXML TableColumn<SecuritysubmissionEntity, String> securityEmployeeAssigned;
  @FXML TableColumn<TransportationsubmissionEntity, String> transportationEmployeeAssigned;
  @FXML TableColumn<AudiosubmissionEntity, String> audioID;
  @FXML TableColumn<AudiosubmissionEntity, String> audioLocation;
  @FXML TableColumn<AudiosubmissionEntity, String> audioItem;
  @FXML TableColumn<AudiosubmissionEntity, String> audioDescription;
  @FXML TableColumn<AudiosubmissionEntity, String> audioAssignedID;
  @FXML TableColumn<AudiosubmissionEntity, SubmissionStatus> audioStatus;
  @FXML TableColumn<ComputersubmissionEntity, String> computerID;
  @FXML TableColumn<ComputersubmissionEntity, String> computerLocation;
  @FXML TableColumn<ComputersubmissionEntity, String> computerItem;
  @FXML TableColumn<ComputersubmissionEntity, String> computerDescription;
  @FXML TableColumn<ComputersubmissionEntity, String> computerAssignedID;
  @FXML TableColumn<ComputersubmissionEntity, SubmissionStatus> computerStatus;
  @FXML TableColumn<ComputersubmissionEntity, String> computerELevel;
  @FXML TableColumn<CleaningsubmissionEntity, String> cleanELevel;
  @FXML TableColumn<TransportationsubmissionEntity, String> transportationELevel;
  @FXML TableColumn<SecuritysubmissionEntity, String> securityELevel;
  @FXML TableColumn<AudiosubmissionEntity, String> audioELevel;

  @FXML MFXComboBox<String> requestType;
  @FXML MFXButton fieldsEdit;
  @FXML MFXButton fieldsSave;

  ObservableList<CleaningsubmissionEntity> cleaningRequestsList =
      FXCollections.observableArrayList();
  ObservableList<TransportationsubmissionEntity> transportationRequestsList =
      FXCollections.observableArrayList();
  ObservableList<SecuritysubmissionEntity> securityRequestsList =
      FXCollections.observableArrayList();
  ObservableList<AudiosubmissionEntity> audioRequestsList = FXCollections.observableArrayList();
  ObservableList<ComputersubmissionEntity> computerRequestsList =
      FXCollections.observableArrayList();
  private final ObservableList<String> options = FXCollections.observableArrayList();

  /** When it switches to page, gets data from submission collector and creates tables */
  public void initialize() {
    cleanRequests.setEditable(true);
    transportationRequests.setEditable(true);
    securityRequests.setEditable(true);
    audioRequests.setEditable(true);
    computerRequests.setEditable(true);
    options.addAll("Transportation", "Cleaning", "Security", "Audio", "Computer");
    requestType.setItems(options);

    Map<Integer, CleaningsubmissionEntity> cleaningdata = Main.db.getCleaningSubs();
    Map<Integer, TransportationsubmissionEntity> transportationdata =
        Main.db.getTransportationSubs();
    Map<Integer, SecuritysubmissionEntity> securitydata = Main.db.getSecuritySubs();
    Map<Integer, AudiosubmissionEntity> audiodata = Main.db.getAudioSubs();
    Map<Integer, ComputersubmissionEntity> computerdata = Main.db.getComputerSubs();

    /*cleaning columns*/
    cleanEmployeeAssigned.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("assignedid"));
    cleanEmployeeAssigned.setCellFactory(TextFieldTableCell.forTableColumn());
    cleanEmployeeAssigned.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<CleaningsubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<CleaningsubmissionEntity, String> event) {
            CleaningsubmissionEntity clean = event.getRowValue();
            clean.setAssignedid(event.getNewValue());
          }
        });
    cleanID.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("memberid"));
    cleanID.setCellFactory(TextFieldTableCell.forTableColumn());
    cleanID.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<CleaningsubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<CleaningsubmissionEntity, String> event) {
            CleaningsubmissionEntity clean = event.getRowValue();
            clean.setMemberid(event.getNewValue());
          }
        });
    hazardLevel.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("hazardlevel"));
    hazardLevel.setCellFactory(TextFieldTableCell.forTableColumn());
    hazardLevel.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<CleaningsubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<CleaningsubmissionEntity, String> event) {
            CleaningsubmissionEntity clean = event.getRowValue();
            clean.setHazardlevel(event.getNewValue());
          }
        });
    cleanLocation.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("location"));
    cleanLocation.setCellFactory(TextFieldTableCell.forTableColumn());
    cleanLocation.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<CleaningsubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<CleaningsubmissionEntity, String> event) {
            CleaningsubmissionEntity clean = event.getRowValue();
            clean.setLocation(event.getNewValue());
          }
        });
    cleanStatus.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, SubmissionStatus>("submissionstatus"));
    cleanStatus.setCellFactory(
        TextFieldTableCell.forTableColumn(new EnumStringConverter<>(SubmissionStatus.class)));
    cleanDescription.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("description"));
    cleanDescription.setCellFactory(TextFieldTableCell.forTableColumn());
    cleanDescription.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<CleaningsubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<CleaningsubmissionEntity, String> event) {
            CleaningsubmissionEntity clean = event.getRowValue();
            clean.setDescription(event.getNewValue());
          }
        });
    cleanELevel.setCellValueFactory(
        new PropertyValueFactory<CleaningsubmissionEntity, String>("urgency"));
    cleanELevel.setCellFactory(TextFieldTableCell.forTableColumn());
    cleanELevel.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<CleaningsubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<CleaningsubmissionEntity, String> event) {
            CleaningsubmissionEntity clean = event.getRowValue();
            clean.setUrgency(event.getNewValue());
          }
        });
    for (CleaningsubmissionEntity sub : cleaningdata.values()) {
      if (Objects.equals(App.getUser().getRole(), "admin")) {
        cleaningRequestsList.add(sub);
      } else if (Objects.equals(sub.getMemberid(), App.getUser().getStaffid())) {
        cleaningRequestsList.add(sub);
      }
    }
    cleanRequests.setItems(cleaningRequestsList);

    /*transportation columns*/
    transportationEmployeeAssigned.setCellValueFactory(
        new PropertyValueFactory<TransportationsubmissionEntity, String>("assignedid"));
    transportationEmployeeAssigned.setCellFactory(TextFieldTableCell.forTableColumn());
    transportationEmployeeAssigned.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<TransportationsubmissionEntity, String>>() {
          @Override
          public void handle(
              TableColumn.CellEditEvent<TransportationsubmissionEntity, String> event) {
            TransportationsubmissionEntity transport = event.getRowValue();
            transport.setAssignedid(event.getNewValue());
          }
        });
    transportationID.setCellValueFactory(
        new PropertyValueFactory<TransportationsubmissionEntity, String>("employeeid"));
    transportationID.setCellFactory(TextFieldTableCell.forTableColumn());
    transportationID.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<TransportationsubmissionEntity, String>>() {
          @Override
          public void handle(
              TableColumn.CellEditEvent<TransportationsubmissionEntity, String> event) {
            TransportationsubmissionEntity transport = event.getRowValue();
            transport.setEmployeeid(event.getNewValue());
          }
        });
    transportationLocation.setCellValueFactory(
        new PropertyValueFactory<TransportationsubmissionEntity, String>("currroomnum"));
    transportationLocation.setCellFactory(TextFieldTableCell.forTableColumn());
    transportationLocation.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<TransportationsubmissionEntity, String>>() {
          @Override
          public void handle(
              TableColumn.CellEditEvent<TransportationsubmissionEntity, String> event) {
            TransportationsubmissionEntity transport = event.getRowValue();
            transport.setCurrroomnum(event.getNewValue());
          }
        });
    destination.setCellValueFactory(
        new PropertyValueFactory<TransportationsubmissionEntity, String>("destroomnum"));
    destination.setCellFactory(TextFieldTableCell.forTableColumn());
    destination.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<TransportationsubmissionEntity, String>>() {
          @Override
          public void handle(
              TableColumn.CellEditEvent<TransportationsubmissionEntity, String> event) {
            TransportationsubmissionEntity transport = event.getRowValue();
            transport.setDestroomnum(event.getNewValue());
          }
        });
    reason.setCellValueFactory(
        new PropertyValueFactory<TransportationsubmissionEntity, String>("reason"));
    reason.setCellFactory(TextFieldTableCell.forTableColumn());
    reason.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<TransportationsubmissionEntity, String>>() {
          @Override
          public void handle(
              TableColumn.CellEditEvent<TransportationsubmissionEntity, String> event) {
            TransportationsubmissionEntity transport = event.getRowValue();
            transport.setReason(event.getNewValue());
          }
        });
    transportationStatus.setCellValueFactory(
        new PropertyValueFactory<TransportationsubmissionEntity, SubmissionStatus>("status"));
    transportationStatus.setCellFactory(
        TextFieldTableCell.forTableColumn(new EnumStringConverter<>(SubmissionStatus.class)));
    transportationELevel.setCellValueFactory(
        new PropertyValueFactory<TransportationsubmissionEntity, String>("urgency"));
    transportationELevel.setCellFactory(TextFieldTableCell.forTableColumn());
    transportationELevel.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<TransportationsubmissionEntity, String>>() {
          @Override
          public void handle(
              TableColumn.CellEditEvent<TransportationsubmissionEntity, String> event) {
            TransportationsubmissionEntity clean = event.getRowValue();
            clean.setUrgency(event.getNewValue());
          }
        });

    for (TransportationsubmissionEntity sub : transportationdata.values()) {
      if (Objects.equals(App.getUser().getRole(), "admin")) {
        transportationRequestsList.add(sub);
      } else if (Objects.equals(sub.getEmployeeid(), App.getUser().getStaffid())) {
        transportationRequestsList.add(sub);
      }
    }
    transportationRequests.setItems(transportationRequestsList);

    /*security columns*/
    securityID.setCellValueFactory(
        new PropertyValueFactory<SecuritysubmissionEntity, String>("employeeid"));
    securityID.setCellFactory(TextFieldTableCell.forTableColumn());
    securityID.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<SecuritysubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<SecuritysubmissionEntity, String> event) {
            SecuritysubmissionEntity security = event.getRowValue();
            security.setEmployeeid(event.getNewValue());
          }
        });
    securityLocation.setCellValueFactory(
        new PropertyValueFactory<SecuritysubmissionEntity, String>("location"));
    securityLocation.setCellFactory(TextFieldTableCell.forTableColumn());
    securityLocation.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<SecuritysubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<SecuritysubmissionEntity, String> event) {
            SecuritysubmissionEntity security = event.getRowValue();
            security.setLocation(event.getNewValue());
          }
        });
    securityDescription.setCellValueFactory(
        new PropertyValueFactory<SecuritysubmissionEntity, String>("notesupdate"));
    securityDescription.setCellFactory(TextFieldTableCell.forTableColumn());
    securityDescription.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<SecuritysubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<SecuritysubmissionEntity, String> event) {
            SecuritysubmissionEntity security = event.getRowValue();
            security.setNotesupdate(event.getNewValue());
          }
        });
    type.setCellValueFactory(new PropertyValueFactory<SecuritysubmissionEntity, String>("type"));
    type.setCellFactory(TextFieldTableCell.forTableColumn());
    type.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<SecuritysubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<SecuritysubmissionEntity, String> event) {
            SecuritysubmissionEntity security = event.getRowValue();
            security.setType(event.getNewValue());
          }
        });
    securityStatus.setCellValueFactory(
        new PropertyValueFactory<SecuritysubmissionEntity, SubmissionStatus>("submissionstatus"));
    securityStatus.setCellFactory(
        TextFieldTableCell.forTableColumn(new EnumStringConverter<>(SubmissionStatus.class)));
    securityEmployeeAssigned.setCellValueFactory(
        new PropertyValueFactory<SecuritysubmissionEntity, String>("assignedid"));
    securityEmployeeAssigned.setCellFactory(TextFieldTableCell.forTableColumn());
    securityEmployeeAssigned.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<SecuritysubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<SecuritysubmissionEntity, String> event) {
            SecuritysubmissionEntity security = event.getRowValue();
            security.setAssignedid(event.getNewValue());
          }
        });
    securityELevel.setCellValueFactory(
        new PropertyValueFactory<SecuritysubmissionEntity, String>("urgency"));
    securityELevel.setCellFactory(TextFieldTableCell.forTableColumn());
    securityELevel.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<SecuritysubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<SecuritysubmissionEntity, String> event) {
            SecuritysubmissionEntity clean = event.getRowValue();
            clean.setUrgency(event.getNewValue());
          }
        });
    for (SecuritysubmissionEntity sub : securitydata.values()) {
      if (Objects.equals(App.getUser().getRole(), "admin")) {
        securityRequestsList.add(sub);
      } else if (Objects.equals(sub.getEmployeeid(), App.getUser().getStaffid())) {
        securityRequestsList.add(sub);
      }
    }
    securityRequests.setItems(securityRequestsList);

    /*audio columns*/

    audioID.setCellValueFactory(
        new PropertyValueFactory<AudiosubmissionEntity, String>("employeeid"));
    audioID.setCellFactory(TextFieldTableCell.forTableColumn());
    audioID.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<AudiosubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<AudiosubmissionEntity, String> event) {
            AudiosubmissionEntity audio = event.getRowValue();
            audio.setEmployeeid(event.getNewValue());
          }
        });
    audioItem.setCellValueFactory(new PropertyValueFactory<AudiosubmissionEntity, String>("type"));
    audioItem.setCellFactory(TextFieldTableCell.forTableColumn());
    audioItem.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<AudiosubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<AudiosubmissionEntity, String> event) {
            AudiosubmissionEntity audio = event.getRowValue();
            audio.setType(event.getNewValue());
          }
        });
    audioLocation.setCellValueFactory(
        new PropertyValueFactory<AudiosubmissionEntity, String>("location"));
    audioLocation.setCellFactory(TextFieldTableCell.forTableColumn());
    audioLocation.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<AudiosubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<AudiosubmissionEntity, String> event) {
            AudiosubmissionEntity audio = event.getRowValue();
            audio.setLocation(event.getNewValue());
          }
        });
    audioDescription.setCellValueFactory(
        new PropertyValueFactory<AudiosubmissionEntity, String>("notesupdate"));
    audioDescription.setCellFactory(TextFieldTableCell.forTableColumn());
    audioDescription.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<AudiosubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<AudiosubmissionEntity, String> event) {
            AudiosubmissionEntity audio = event.getRowValue();
            audio.setNotesupdate(event.getNewValue());
          }
        });
    audioStatus.setCellValueFactory(
        new PropertyValueFactory<AudiosubmissionEntity, SubmissionStatus>("submissionstatus"));
    audioStatus.setCellFactory(
        TextFieldTableCell.forTableColumn(new EnumStringConverter<>(SubmissionStatus.class)));
    audioAssignedID.setCellValueFactory(
        new PropertyValueFactory<AudiosubmissionEntity, String>("assignedid"));
    audioAssignedID.setCellFactory(TextFieldTableCell.forTableColumn());
    audioAssignedID.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<AudiosubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<AudiosubmissionEntity, String> event) {
            AudiosubmissionEntity audio = event.getRowValue();
            audio.setAssignedid(event.getNewValue());
          }
        });
    audioELevel.setCellValueFactory(
        new PropertyValueFactory<AudiosubmissionEntity, String>("urgency"));
    audioELevel.setCellFactory(TextFieldTableCell.forTableColumn());
    audioELevel.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<AudiosubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<AudiosubmissionEntity, String> event) {
            AudiosubmissionEntity clean = event.getRowValue();
            clean.setUrgency(event.getNewValue());
          }
        });
    for (AudiosubmissionEntity sub : audiodata.values()) {
      if (Objects.equals(App.getUser().getRole(), "admin")) {
        audioRequestsList.add(sub);
      } else if (Objects.equals(sub.getEmployeeid(), App.getUser().getStaffid())) {
        audioRequestsList.add(sub);
      }
    }
    audioRequests.setItems(audioRequestsList);

    /*computer columns*/
    computerID.setCellValueFactory(
        new PropertyValueFactory<ComputersubmissionEntity, String>("employeeid"));
    computerID.setCellFactory(TextFieldTableCell.forTableColumn());
    computerID.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<ComputersubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<ComputersubmissionEntity, String> event) {
            ComputersubmissionEntity computer = event.getRowValue();
            computer.setEmployeeid(event.getNewValue());
          }
        });
    computerItem.setCellValueFactory(
        new PropertyValueFactory<ComputersubmissionEntity, String>("type"));
    computerItem.setCellFactory(TextFieldTableCell.forTableColumn());
    computerItem.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<ComputersubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<ComputersubmissionEntity, String> event) {
            ComputersubmissionEntity audio = event.getRowValue();
            audio.setType(event.getNewValue());
          }
        });
    computerLocation.setCellValueFactory(
        new PropertyValueFactory<ComputersubmissionEntity, String>("location"));
    computerLocation.setCellFactory(TextFieldTableCell.forTableColumn());
    computerLocation.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<ComputersubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<ComputersubmissionEntity, String> event) {
            ComputersubmissionEntity audio = event.getRowValue();
            audio.setLocation(event.getNewValue());
          }
        });
    computerDescription.setCellValueFactory(
        new PropertyValueFactory<ComputersubmissionEntity, String>("notesupdate"));
    computerDescription.setCellFactory(TextFieldTableCell.forTableColumn());
    computerDescription.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<ComputersubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<ComputersubmissionEntity, String> event) {
            ComputersubmissionEntity audio = event.getRowValue();
            audio.setNotesupdate(event.getNewValue());
          }
        });
    computerStatus.setCellValueFactory(
        new PropertyValueFactory<ComputersubmissionEntity, SubmissionStatus>("submissionstatus"));
    computerStatus.setCellFactory(
        TextFieldTableCell.forTableColumn(new EnumStringConverter<>(SubmissionStatus.class)));
    computerAssignedID.setCellValueFactory(
        new PropertyValueFactory<ComputersubmissionEntity, String>("assignedid"));
    computerAssignedID.setCellFactory(TextFieldTableCell.forTableColumn());
    computerAssignedID.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<ComputersubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<ComputersubmissionEntity, String> event) {
            ComputersubmissionEntity audio = event.getRowValue();
            audio.setAssignedid(event.getNewValue());
          }
        });
    computerELevel.setCellValueFactory(
        new PropertyValueFactory<ComputersubmissionEntity, String>("urgency"));
    computerELevel.setCellFactory(TextFieldTableCell.forTableColumn());
    computerELevel.setOnEditCommit(
        new EventHandler<TableColumn.CellEditEvent<ComputersubmissionEntity, String>>() {
          @Override
          public void handle(TableColumn.CellEditEvent<ComputersubmissionEntity, String> event) {
            ComputersubmissionEntity clean = event.getRowValue();
            clean.setUrgency(event.getNewValue());
          }
        });
    for (ComputersubmissionEntity sub : computerdata.values()) {
      if (Objects.equals(App.getUser().getRole(), "admin")) {
        computerRequestsList.add(sub);
      } else if (Objects.equals(sub.getEmployeeid(), App.getUser().getStaffid())) {
        computerRequestsList.add(sub);
      }
    }
    computerRequests.setItems(computerRequestsList);

    if (App.getUser().getRole().equals("admin")) {
      transportationStatus.setEditable(true);
      cleanStatus.setEditable(true);
      securityStatus.setEditable(true);
      computerStatus.setEditable(true);
      audioStatus.setEditable(true);

      transportationStatus.setOnEditCommit(
          new EventHandler<
              TableColumn.CellEditEvent<TransportationsubmissionEntity, SubmissionStatus>>() {
            @Override
            public void handle(
                TableColumn.CellEditEvent<TransportationsubmissionEntity, SubmissionStatus> event) {
              TransportationsubmissionEntity transport = event.getRowValue();
              transport.setStatus(event.getNewValue());
            }
          });

      cleanStatus.setOnEditCommit(
          new EventHandler<
              TableColumn.CellEditEvent<CleaningsubmissionEntity, SubmissionStatus>>() {
            @Override
            public void handle(
                TableColumn.CellEditEvent<CleaningsubmissionEntity, SubmissionStatus> event) {
              CleaningsubmissionEntity clean = event.getRowValue();
              clean.setSubmissionstatus(event.getNewValue());
            }
          });

      securityStatus.setOnEditCommit(
          new EventHandler<
              TableColumn.CellEditEvent<SecuritysubmissionEntity, SubmissionStatus>>() {
            @Override
            public void handle(
                TableColumn.CellEditEvent<SecuritysubmissionEntity, SubmissionStatus> event) {
              SecuritysubmissionEntity security = event.getRowValue();
              security.setSubmissionstatus(event.getNewValue());
            }
          });

      audioStatus.setOnEditCommit(
          new EventHandler<TableColumn.CellEditEvent<AudiosubmissionEntity, SubmissionStatus>>() {
            @Override
            public void handle(
                TableColumn.CellEditEvent<AudiosubmissionEntity, SubmissionStatus> event) {
              AudiosubmissionEntity audio = event.getRowValue();
              audio.setSubmissionstatus(event.getNewValue());
            }
          });

      computerStatus.setOnEditCommit(
          new EventHandler<
              TableColumn.CellEditEvent<ComputersubmissionEntity, SubmissionStatus>>() {
            @Override
            public void handle(
                TableColumn.CellEditEvent<ComputersubmissionEntity, SubmissionStatus> event) {
              ComputersubmissionEntity audio = event.getRowValue();
              audio.setSubmissionstatus(event.getNewValue());
            }
          });
    }

    transportationRequests.setVisible(false);
    securityRequests.setVisible(false);
    cleanRequests.setVisible(false);
    audioRequests.setVisible(false);
    computerRequests.setVisible(false);
  }

  public void updateTable() {
    String selection = requestType.getValue();
    transportationRequests.setVisible(false);
    securityRequests.setVisible(false);
    cleanRequests.setVisible(false);
    audioRequests.setVisible(false);
    computerRequests.setVisible(false);
    if (selection.equals("Transportation")) {
      transportationRequests.setVisible(true);
    } else if (selection.equals("Cleaning")) {
      cleanRequests.setVisible(true);
    } else if (selection.equals("Security")) {
      securityRequests.setVisible(true);
    } else if (selection.equals("Audio")) {
      audioRequests.setVisible(true);
    } else if (selection.equals("Computer")) {
      computerRequests.setVisible(true);
    }
  }

  public void edit(ActionEvent actionEvent) {}

  public void save(ActionEvent actionEvent) {}
}
