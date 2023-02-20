package edu.wpi.capybara.database;

import edu.wpi.capybara.objects.hibernate.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.wpi.capybara.objects.orm.AudioSubmission;
import edu.wpi.capybara.objects.orm.CleaningSubmission;
import org.hibernate.Session;

public interface RepoFacade {
  // Get all methods
  // Submissions
  Map<Integer, AudioSubmission> getAudioSubs();

  Map<Integer, CleaningSubmission> getCleaningSubs();

  HashMap<Integer, ComputersubmissionEntity> getComputerSubs();

  HashMap<Integer, SecuritysubmissionEntity> getSecuritySubs();

  HashMap<Integer, TransportationsubmissionEntity> getTransportationSubs();

  // Others
  HashMap<String, LocationnameEntity> getLocationnames();

  HashMap<String, NodeEntity> getNodes();

  HashMap<String, StaffEntity> getStaff();

  ArrayList<EdgeEntity> getEdges();

  ArrayList<MoveEntity> getMoves();

  HashMap<Integer, MessagesEntity> getMessages();

  HashMap<Integer, MessagesEntity> getMessages(String id);

  HashMap<Integer, MessagesEntity> getMessages(String id, int lastid);

  // Add method
  // Submissions
  void addAudio(AudioSubmission submission);

  void addCleaning(CleaningSubmission submission);

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

  // Get single methods
  // Submissions
  AudioSubmission getAudio(int id);

  CleaningSubmission getCleaning(int id);

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

  // Submission methods
  StaffEntity getStaff(String Staffid, String password);

  Session getSession();

  int newID();

  void importAll();

  int generateMessageID();

  public void threadRefresh(int delay);
}
