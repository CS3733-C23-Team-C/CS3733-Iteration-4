package edu.wpi.cs3733.C23.teamC.database;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.*;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;

public interface RepoFacade {
  // Get all methods
  // Submissions
  Map<Integer, AudiosubmissionEntity> getAudioSubs();

  Map<Integer, CleaningsubmissionEntity> getCleaningSubs();

  Map<Integer, ComputersubmissionEntity> getComputerSubs();

  Map<Integer, SecuritysubmissionEntity> getSecuritySubs();

  Map<Integer, TransportationsubmissionEntity> getTransportationSubs();

  // Others
  Map<String, LocationnameEntity> getLocationnames();

  Map<String, NodeEntity> getNodes();

  Map<String, StaffEntity> getStaff();

  List<EdgeEntity> getEdges();

  List<MoveEntity> getMoves();

  Map<Integer, MessagesEntity> getMessages();

  Map<Integer, MessagesEntity> getMessages(String id);

  Map<Integer, MessagesEntity> getMessages(String id, int lastid);

  Map<Integer, AlertEntity> getAlerts();

  // Add method
  // Submissions
  void addAudio(AudiosubmissionEntity submission);

  void addCleaning(CleaningsubmissionEntity submission);

  void addComputer(ComputersubmissionEntity submission);

  void addSecurity(SecuritysubmissionEntity submission);

  void addTransportation(TransportationsubmissionEntity submission);

  // Others
  void addLocationname(LocationnameEntity submission);

  void addNode(NodeEntity submission);

  void addStaff(StaffEntity submission);

  void addEdge(EdgeEntity submission);

  boolean addMove(MoveEntity submission);

  void addMessage(MessagesEntity message);

  void addAlert(AlertEntity alert);

  // Get single methods
  // Submissions
  AudiosubmissionEntity getAudio(int id);

  CleaningsubmissionEntity getCleaning(int id);

  ComputersubmissionEntity getComputer(int id);

  SecuritysubmissionEntity getSecurity(int id);

  TransportationsubmissionEntity getTransportation(int id);

  // Others
  LocationnameEntity getLocationname(String longname);

  NodeEntity getNode(String nodeid);

  StaffEntity getStaff(String staffid);

  MessagesEntity getMessage(int id);

  // delete methods
  // submissions
  void deleteAudio(int id);

  void deleteCleaning(int id);

  void deleteComputer(int id);

  void deleteSecurity(int id);

  void deleteTransportation(int id);

  // Others
  void deleteEdge(EdgeEntity edge);

  void deleteLocationname(String longname);

  void deleteMove(MoveEntity move);

  void deleteNode(String nodeid);

  void deleteStaff(String staffid);

  void deleteMessage(int messageid);

  void deleteAlert(AlertEntity entity);

  // Submission methods
  StaffEntity getStaff(String Staffid, String password);

  Session getSession();

  int newID();

  void importAll();

  int generateMessageID();

  void threadRefresh(int delay);

  StaffEntity getStaff2(String firstName, String lastName);

  public void refreshMessages();

  StaffEntity getStaff3(String FirstName, String LastName, String StaffID);

  String getUser(MFXTextField staffID);

  AlertEntity getAlert(int id);

  int getNewAlertID();

  List<AlertStaff> getAlertStaff();

  void deleteAlertStaff();
}
