package edu.wpi.capybara.database;

import java.util.List;
import java.util.Map;

import edu.wpi.capybara.objects.orm.*;
import org.hibernate.Session;

public interface RepoFacade {
  // Get all methods
  // Submissions
  Map<Integer, AudioSubmission> getAudioSubs();

  Map<Integer, CleaningSubmission> getCleaningSubs();

  Map<Integer, ComputerSubmission> getComputerSubs();

  Map<Integer, SecuritySubmission> getSecuritySubs();

  Map<Integer, TransportationSubmission> getTransportationSubs();

  // Others
  Map<String, Location> getLocationnames();

  Map<String, Node> getNodes();

  Map<String, Staff> getStaff();

  List<Edge> getEdges();

  List<Move> getMoves();

  Map<Integer, Messages> getMessages();

  Map<Integer, Messages> getMessages(String id);

  Map<Integer, Messages> getMessages(String id, int lastid);

  // Add method
  // Submissions
  void addAudio(AudioSubmission submission);

  void addCleaning(CleaningSubmission submission);

  void addComputer(ComputerSubmission submission);

  void addSecurity(SecuritySubmission submission);

  void addTransportation(TransportationSubmission submission);

  // Others
  void addLocationname(Location submission);

  void addNode(Node submission);

  void addStaff(Staff submission);

  void addEdge(Edge submission);

  boolean addMove(Move submission);

  void addMessage(Messages message);

  // Get single methods
  // Submissions
  AudioSubmission getAudio(int id);

  CleaningSubmission getCleaning(int id);

  ComputerSubmission getComputer(int id);

  SecuritySubmission getSecurity(int id);

  TransportationSubmission getTransportation(int id);

  // Others
  Location getLocationname(String longname);

  Node getNode(String nodeid);

  Staff getStaff(String staffid);

  Messages getMessage(int id);

  // delete methods
  // submissions
  void deleteAudio(int id);

  void deleteCleaning(int id);

  void deleteComputer(int id);

  void deleteSecurity(int id);

  void deleteTransportation(int id);

  // Others
  void deleteEdge(Edge edge);

  void deleteLocationname(String longname);

  void deleteMove(Move move);

  void deleteNode(String nodeid);

  void deleteStaff(String staffid);

  void deleteMessage(int messageid);

  // Submission methods
  Staff getStaff(String Staffid, String password);

  Session getSession();

  int newID();

  void importAll();

  int generateMessageID();

  public void threadRefresh(int delay);
}
