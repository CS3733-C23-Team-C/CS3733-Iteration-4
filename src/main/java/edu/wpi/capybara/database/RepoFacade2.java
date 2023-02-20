package edu.wpi.capybara.database;

import edu.wpi.capybara.objects.orm.*;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyMapProperty;
import org.hibernate.Session;

public interface RepoFacade2 {
  // Get all methods
  // Submissions
  ReadOnlyMapProperty<Integer, AudioSubmission> getAudioSubs();

  ReadOnlyMapProperty<Integer, CleaningSubmission> getCleaningSubs();

  ReadOnlyMapProperty<Integer, ComputerSubmission> getComputerSubs();

  ReadOnlyMapProperty<Integer, SecuritySubmission> getSecuritySubs();

  ReadOnlyMapProperty<Integer, TransportationSubmission> getTransportationSubs();

  // Others
  ReadOnlyMapProperty<String, Location> getLocationNames();

  ReadOnlyMapProperty<String, Node> getNodes();

  ReadOnlyMapProperty<String, Staff> getStaff();

  ReadOnlyListProperty<Edge> getEdges();

  ReadOnlyListProperty<Move> getMoves();

  ReadOnlyMapProperty<Integer, Messages> getMessages();

  // Add method
  // Submissions
  void addAudio(AudioSubmission submission);

  void addCleaning(CleaningSubmission submission);

  void addComputer(ComputerSubmission submission);

  void addSecurity(SecuritySubmission submission);

  void addTransportation(TransportationSubmission submission);

  // Others
  void addLocationName(Location submission);

  void addNode(Node submission);

  void addStaff(Staff submission);

  void addEdge(Edge submission);

  boolean addMove(Move submission);

  void addMessage(Messages message);

  // Get single methods
  // Submissions
  AudioSubmission getAudio(Integer id);

  CleaningSubmission getCleaning(Integer id);

  ComputerSubmission getComputer(Integer id);

  SecuritySubmission getSecurity(Integer id);

  TransportationSubmission getTransportation(Integer id);

  // Others
  Location getLocationname(String longname);

  Node getNode(String nodeid);

  Staff getStaff(String staffid);

  Messages getMessage(Integer id);

  // delete methods
  // submissions
  void deleteAudio(Integer id);

  void deleteAudio(AudioSubmission entity);

  void deleteCleaning(Integer id);

  void deleteCleaning(CleaningSubmission entity);

  void deleteComputer(Integer id);

  void deleteComputer(ComputerSubmission entity);

  void deleteSecurity(Integer id);

  void deleteSecurity(SecuritySubmission entity);

  void deleteTransportation(Integer id);

  void deleteTransportation(TransportationSubmission entity);

  // Others
  void deleteEdge(Edge edge);

  void deleteLocationName(String longname);

  void deleteLocationName(Location entity);

  void deleteMove(Move move);

  void deleteNode(String nodeid);

  void deleteNode(Node entity);

  void deleteStaff(String staffid);

  void deleteStaff(Staff entity);

  void deleteMessage(Integer messageid);

  void deleteMessage(Messages entity);

  // Submission methods
  Staff getStaff(String Staffid, String password);

  Session getSession();

  int newID();

  void importAll();

  int generateMessageID();
}
