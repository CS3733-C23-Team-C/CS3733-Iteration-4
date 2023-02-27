package edu.wpi.cs3733.C23.teamC.database;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyMapProperty;
import org.hibernate.Session;

public interface RepoFacade2 {
  // Get all methods
  // Submissions
  ReadOnlyMapProperty<Integer, AudiosubmissionEntity> getAudioSubs();

  ReadOnlyMapProperty<Integer, CleaningsubmissionEntity> getCleaningSubs();

  ReadOnlyMapProperty<Integer, ComputersubmissionEntity> getComputerSubs();

  ReadOnlyMapProperty<Integer, SecuritysubmissionEntity> getSecuritySubs();

  ReadOnlyMapProperty<Integer, TransportationsubmissionEntity> getTransportationSubs();

  // Others
  ReadOnlyMapProperty<String, LocationnameEntity> getLocationNames();

  ReadOnlyMapProperty<String, NodeEntity> getNodes();

  ReadOnlyMapProperty<String, StaffEntity> getStaff();

  ReadOnlyListProperty<EdgeEntity> getEdges();

  ReadOnlyListProperty<MoveEntity> getMoves();

  ReadOnlyMapProperty<Integer, MessagesEntity> getMessages();

  AlertEntity getAlert(Integer id);

  Map<Integer, MessagesEntity> getMessages(String id);

  Map<Integer, MessagesEntity> getMessages(String id, int lastid);

  ReadOnlyMapProperty getAlerts();

  // Add method
  // Submissions
  void addAudio(AudiosubmissionEntity submission);

  void addCleaning(CleaningsubmissionEntity submission);

  void addComputer(ComputersubmissionEntity submission);

  void addSecurity(SecuritysubmissionEntity submission);

  void addTransportation(TransportationsubmissionEntity submission);

  // Others
  void addLocationName(LocationnameEntity submission);

  void addNode(NodeEntity submission);

  void addStaff(StaffEntity submission);

  void addEdge(EdgeEntity submission);

  boolean addMove(MoveEntity submission);

  void addMessage(MessagesEntity message);

  void addAlert(AlertEntity alert);

  // Get single methods
  // Submissions
  AudiosubmissionEntity getAudio(Integer id);

  CleaningsubmissionEntity getCleaning(Integer id);

  ComputersubmissionEntity getComputer(Integer id);

  SecuritysubmissionEntity getSecurity(Integer id);

  TransportationsubmissionEntity getTransportation(Integer id);

  // Others
  LocationnameEntity getLocationname(String longname);

  NodeEntity getNode(String nodeid);

  StaffEntity getStaff(String staffid);

  StaffEntity getStaff2(String firstName, String lastName);

  MessagesEntity getMessage(Integer id);

  // delete methods
  // submissions
  void deleteAudio(Integer id);

  void deleteAudio(AudiosubmissionEntity entity);

  void deleteCleaning(Integer id);

  void deleteCleaning(CleaningsubmissionEntity entity);

  void deleteComputer(Integer id);

  void deleteComputer(ComputersubmissionEntity entity);

  void deleteSecurity(Integer id);

  void deleteSecurity(SecuritysubmissionEntity entity);

  void deleteTransportation(Integer id);

  void deleteTransportation(TransportationsubmissionEntity entity);

  // Others
  void deleteEdge(EdgeEntity edge);

  void deleteLocationName(String longname);

  void deleteLocationName(LocationnameEntity entity);

  void deleteMove(MoveEntity move);

  void deleteNode(String nodeid);

  void deleteNode(NodeEntity entity);

  void deleteStaff(String staffid);

  void deleteStaff(StaffEntity entity);

  void deleteMessage(Integer messageid);

  void deleteMessage(MessagesEntity entity);

  void deleteAlert(AlertEntity entity);

  // Submission methods
  StaffEntity getStaff(String Staffid, String password);

  Session getSession();

  int newID();

  BufferedImage getImage(int id);

  int setImage(String filepath) throws IOException;

  void importAll();

  int generateMessageID();

  void threadRefresh(int delay);

  StaffEntity getStaff3(String firstName, String lastName, String staffId);

  void refreshMessages();

  int getNewAlertID();
}
