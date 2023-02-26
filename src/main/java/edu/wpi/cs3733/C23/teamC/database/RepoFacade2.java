package edu.wpi.cs3733.C23.teamC.database;

import edu.wpi.cs3733.C23.teamC.objects.hibernate.*;
import java.util.Map;
import java.util.function.Consumer;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyMapProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
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

  Map<Integer, MessagesEntity> getMessages(String id);

  Map<Integer, MessagesEntity> getMessages(String id, int lastid);

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

  // Submission methods
  StaffEntity getStaff(String Staffid, String password);

  Session getSession();

  int newID();

  void importAll();

  int generateMessageID();

  void threadRefresh(int delay);

  void refreshMessages();

  static <K, E> MapChangeListener<K, E> createMapListener(
      Consumer<E> addCallback, Consumer<E> removeCallback) {
    return change -> {
      if (change.wasAdded()) addCallback.accept(change.getValueAdded());
      if (change.wasRemoved()) removeCallback.accept(change.getValueRemoved());
    };
  }

  static <E> ListChangeListener<E> createListListener(
      Consumer<E> addCallback, Consumer<E> removeCallback) {
    return change -> {
      while (change.next()) {
        if (change.wasAdded()) change.getAddedSubList().forEach(addCallback);
        if (change.wasRemoved()) change.getRemoved().forEach(removeCallback);
      }
    };
  }
}
