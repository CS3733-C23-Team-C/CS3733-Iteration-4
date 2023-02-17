package edu.wpi.capybara.database;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.objects.hibernate.*;
import jakarta.persistence.PersistenceException;
import java.util.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class newDBConnect implements RepoFacade {
  AudiosubmissionDAO audio;
  CleaningsubmissionDAO cleaning;
  ComputersubmissionDAO computer;
  EdgeDAO edge;
  LocationnameDAO locationname;
  MoveDAO move;
  NodeDAO node;
  SecurityDAO security;
  StaffDAO staff;
  TransportationDAO transportation;

  MessagesDAO message;
  int id;

  static SessionFactory factory;

  public newDBConnect() {
    try {
      factory = new Configuration().configure().buildSessionFactory();
    } catch (Throwable ex) {
      System.err.println("Failed to create sessionFactory object." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  public void importAll() {
    id = 0;
    importAudio();
    importCleaning();
    importComputer();
    importSecurity();
    importTransportation();
    importLocationname();
    importNode();
    importStaff();
    importEdge();
    importMove();
    importMessage();
  }

  void importAudio() {
    audio = new AudiosubmissionDAOImpl();
  }

  void importCleaning() {
    cleaning = new CleaningsubmissionDAOImpl();
  }

  void importComputer() {
    computer = new ComputersubmissionDAOImpl();
  }

  void importSecurity() {
    security = new SecurityDAOImpl();
  }

  void importTransportation() {
    transportation = new TransportationDAOImpl();
  }

  void importLocationname() {
    locationname = new LocationnameDAOImpl();
  }

  void importNode() {
    node = new NodeDAOImpl();
  }

  void importStaff() {
    staff = new StaffDAOImpl();
  }

  void importEdge() {
    edge = new EdgeDAOImpl();
  }

  void importMove() {
    move = new MoveDAOImpl();
  }

  void importMessage() {
    message = new MessagesDAOImpl();
  }

  @Override
  public HashMap<Integer, AudiosubmissionEntity> getAudioSubs() {
    return audio.getAudioSubs();
  }

  @Override
  public HashMap<Integer, CleaningsubmissionEntity> getCleaningSubs() {
    return cleaning.getCleaningSubs();
  }

  @Override
  public HashMap<Integer, ComputersubmissionEntity> getComputerSubs() {
    return computer.getComputerSubs();
  }

  @Override
  public HashMap<Integer, SecuritysubmissionEntity> getSecuritySubs() {
    return security.getSecuritySubs();
  }

  @Override
  public HashMap<Integer, TransportationsubmissionEntity> getTransportationSubs() {
    return transportation.getTransportationSubs();
  }

  @Override
  public HashMap<String, LocationnameEntity> getLocationnames() {
    return locationname.getLocationnames();
  }

  @Override
  public HashMap<String, NodeEntity> getNodes() {
    return node.getNodes();
  }

  @Override
  public HashMap<String, StaffEntity> getStaff() {
    return staff.getStaff();
  }

  @Override
  public ArrayList<EdgeEntity> getEdges() {
    return edge.getEdges();
  }

  @Override
  public ArrayList<MoveEntity> getMoves() {
    return move.getMoves();
  }

  @Override
  public HashMap<Integer, MessagesEntity> getMessages() {
    return message.getMessages();
  }

  @Override
  public void addAudio(AudiosubmissionEntity submission) {
    audio.addAudio(submission);
  }

  @Override
  public void addCleaning(CleaningsubmissionEntity submission) {
    cleaning.addCleaning(submission);
  }

  @Override
  public void addComputer(ComputersubmissionEntity submission) {
    computer.addComputer(submission);
  }

  @Override
  public void addSecurity(SecuritysubmissionEntity submission) {
    security.addSecurity(submission);
  }

  @Override
  public void addTransportation(TransportationsubmissionEntity submission) {
    transportation.addTransportation(submission);
  }

  @Override
  public void addLocationname(LocationnameEntity submission) {
    locationname.addLocationname(submission);
  }

  @Override
  public void addNode(NodeEntity submission) {
    node.addNode(submission);
  }

  @Override
  public void addStaff(StaffEntity submission) {
    staff.addStaff(submission);
  }

  @Override
  public void addEdge(EdgeEntity submission) {
    edge.addEdge(submission);
  }

  @Override
  public boolean addMove(MoveEntity submission) {

    // Get most recent locations
    java.util.Date date = new java.util.Date();
    HashMap<String, MoveEntity> currentLocations = new HashMap<String, MoveEntity>();
    for (MoveEntity move : Main.db.getMoves()) {
      MoveEntity temp = currentLocations.get(move.getLongname());
      if (temp == null) {
        currentLocations.put(move.getLongname(), move);
      } else {
        if (move.getMovedate().compareTo(temp.getMovedate()) < 0
            && move.getMovedate().compareTo(new java.sql.Date(date.getTime())) < 0) {
          currentLocations.remove(temp.getLongname());
          currentLocations.put(move.getLongname(), move);
        }
      }
    }

    // count number of moves at a location
    int num = 0;
    for (MoveEntity move : currentLocations.values()) {
      if (move.getNodeid().equals(submission.getNodeid())) {
        num++;
      }
    }

    if (num < 2) {
      move.addMove(submission);
      return true;
    }
    return false;
  }

  @Override
  public void addMessage(MessagesEntity addition) {
    message.addMessage(addition);
  }

  @Override
  public AudiosubmissionEntity getAudio(int id) {
    return audio.getAudio(id);
  }

  @Override
  public CleaningsubmissionEntity getCleaning(int id) {
    return cleaning.getCleaning(id);
  }

  @Override
  public ComputersubmissionEntity getComputer(int id) {
    return computer.getComputer(id);
  }

  @Override
  public SecuritysubmissionEntity getSecurity(int id) {
    return security.getSecurity(id);
  }

  @Override
  public TransportationsubmissionEntity getTransportation(int id) {
    return transportation.getTransportation(id);
  }

  @Override
  public LocationnameEntity getLocationname(String longname) {
    return locationname.getLocationname(longname);
  }

  @Override
  public NodeEntity getNode(String nodeid) {
    return node.getNode(nodeid);
  }

  @Override
  public StaffEntity getStaff(String staffid) {
    return staff.getStaff(staffid);
  }

  public StaffEntity getStaff(String Staffid, String password) {
    for (StaffEntity s : staff.getStaff().values()) {
      if (s.getStaffid().equals(Staffid) && s.getPassword().equals(password)) {
        return s;
      }
    }
    return null;
  }

  @Override
  public MessagesEntity getMessage(int messageid) {
    return message.getMessage(messageid);
  }

  @Override
  public Session getSession() {
    return factory.openSession();
  }

  public static void insertNew(Object submission) {
    try (Session session =
        factory.openSession()) { // automatically close the session when we are done
      final var tx = session.beginTransaction();
      // switched to merge instead of save
      // it seems to check for existing keys
      session.merge(submission);
      try {
        tx.commit();
      } catch (PersistenceException e) { // this gets thrown if there's a duplicate key(?)
        tx.rollback();
        e.printStackTrace();
      }
    } catch (HibernateException e) {
      e.printStackTrace();
    }
  }

  public int newID() {
    return ++id;
  }

  public void deleteAudio(int id) {
    audio.deleteAudio(id);
  }

  public void deleteCleaning(int id) {
    cleaning.deleteCleaning(id);
  }

  public void deleteComputer(int id) {
    computer.deleteComputer(id);
  }

  public void deleteSecurity(int id) {
    security.deleteSecurity(id);
  }

  public void deleteTransportation(int id) {
    transportation.deleteTransportation(id);
  }

  public void deleteEdge(EdgeEntity edge) {
    this.edge.deleteEdge(edge);
  }

  public void deleteLocationname(String longname) {
    locationname.deleteLocationname(longname);
  }

  public void deleteMove(MoveEntity move) {
    this.move.deleteMove(move);
  }

  public void deleteNode(String id) {
    node.deleteNode(id);
  }

  public void deleteStaff(String id) {
    staff.deleteStaff(id);
  }

  public void deleteMessage(int id) {
    message.deleteMessage(id);
  }

  public static void delete(Object submission) {
    Session session = factory.openSession();
    Transaction tx = session.beginTransaction();
    session.remove(submission);
    tx.commit();
    session.close();
  }

  public int getID() {
    return id;
  }

  public void setID(int id) {
    this.id = id;
  }

  public String generateStaffID() {
    return staff.generateStaffID();
  }

  public int generateMessageID() {
    return message.generateMessageID();
  }
}
