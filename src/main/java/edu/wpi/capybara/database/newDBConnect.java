package edu.wpi.capybara.database;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.objects.hibernate.*;
import edu.wpi.capybara.objects.orm.*;
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
  public Map<Integer, AudioSubmission> getAudioSubs() {
    return audio.getAudioSubs();
  }

  @Override
  public Map<Integer, CleaningSubmission> getCleaningSubs() {
    return cleaning.getCleaningSubs();
  }

  @Override
  public Map<Integer, ComputerSubmission> getComputerSubs() {
    return computer.getComputerSubs();
  }

  @Override
  public Map<Integer, SecuritySubmission> getSecuritySubs() {
    return security.getSecuritySubs();
  }

  @Override
  public Map<Integer, TransportationSubmission> getTransportationSubs() {
    return transportation.getTransportationSubs();
  }

  @Override
  public Map<String, Location> getLocationnames() {
    return locationname.getLocationnames();
  }

  @Override
  public Map<String, Node> getNodes() {
    return node.getNodes();
  }

  @Override
  public Map<String, Staff> getStaff() {
    return staff.getStaff();
  }

  @Override
  public List<Edge> getEdges() {
    return edge.getEdges();
  }

  @Override
  public List<Move> getMoves() {
    return move.getMoves();
  }

  @Override
  public Map<Integer, Messages> getMessages() {
    return message.getMessages();
  }

  @Override
  public Map<Integer, Messages> getMessages(String id) {
    return message.getMessages(id);
  }

  @Override
  public Map<Integer, Messages> getMessages(String id, int lastid) {
    return message.getMessages(id, lastid);
  }

  @Override
  public void addAudio(AudioSubmission submission) {
    audio.addAudio(submission);
  }

  @Override
  public void addCleaning(CleaningSubmission submission) {
    cleaning.addCleaning(submission);
  }

  @Override
  public void addComputer(ComputerSubmission submission) {
    computer.addComputer(submission);
  }

  @Override
  public void addSecurity(SecuritySubmission submission) {
    security.addSecurity(submission);
  }

  @Override
  public void addTransportation(TransportationSubmission submission) {
    transportation.addTransportation(submission);
  }

  @Override
  public void addLocationname(Location submission) {
    locationname.addLocationname(submission);
  }

  @Override
  public void addNode(Node submission) {
    node.addNode(submission);
  }

  @Override
  public void addStaff(Staff submission) {
    staff.addStaff(submission);
  }

  @Override
  public void addEdge(Edge submission) {
    edge.addEdge(submission);
  }

  @Override
  public boolean addMove(Move submission) {

    // Get most recent locations
    java.util.Date date = new java.util.Date();
    HashMap<String, Move> currentLocations = new HashMap<>();
    for (Move move : Main.db.getMoves()) {
      Move temp = currentLocations.get(move.getLongname());
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
    for (Move move : currentLocations.values()) {
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
  public void addMessage(Messages addition) {
    message.addMessage(addition);
  }

  @Override
  public AudioSubmission getAudio(int id) {
    return audio.getAudio(id);
  }

  @Override
  public CleaningSubmission getCleaning(int id) {
    return cleaning.getCleaning(id);
  }

  @Override
  public ComputerSubmission getComputer(int id) {
    return computer.getComputer(id);
  }

  @Override
  public SecuritySubmission getSecurity(int id) {
    return security.getSecurity(id);
  }

  @Override
  public TransportationSubmission getTransportation(int id) {
    return transportation.getTransportation(id);
  }

  @Override
  public Location getLocationname(String longname) {
    return locationname.getLocationname(longname);
  }

  @Override
  public Node getNode(String nodeid) {
    return node.getNode(nodeid);
  }

  @Override
  public Staff getStaff(String staffid) {
    return staff.getStaff(staffid);
  }

  public Staff getStaff(String Staffid, String password) {
    for (Staff s : staff.getStaff().values()) {
      if (s.getStaffid().equals(Staffid) && s.getPassword().equals(password)) {
        return s;
      }
    }
    return null;
  }

  public Staff getStaff2(String firstName, String lastName) {
    for (Staff s : staff.getStaff().values()) {
      if (s.getFirstname().equals(firstName) && s.getLastname().equals(lastName)) {
        return s;
      }
    }
    return null;
  }

  @Override
  public Messages getMessage(int messageid) {
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

  public void deleteEdge(Edge edge) {
    this.edge.deleteEdge(edge);
  }

  public void deleteLocationname(String longname) {
    locationname.deleteLocationname(longname);
  }

  public void deleteMove(Move move) {
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

  public void threadRefresh(int delay) {
    try {
      Thread.sleep(delay * 1000);
      importAudio();
      Thread.sleep(delay * 1000);
      importCleaning();
      Thread.sleep(delay * 1000);
      importComputer();
      Thread.sleep(delay * 1000);
      importSecurity();
      Thread.sleep(delay * 1000);
      importTransportation();
      Thread.sleep(delay * 1000);
      importLocationname();
      Thread.sleep(delay * 1000);
      importNode();
      Thread.sleep(delay * 1000);
      importStaff();
      Thread.sleep(delay * 1000);
      importEdge();
      Thread.sleep(delay * 1000);
      importMove();
      Thread.sleep(delay * 1000);
      importMessage();
      Thread.sleep(delay * 1000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public static void delete(Object submission) {
    Session session = factory.openSession();
    Transaction tx = session.beginTransaction();
    session.remove(submission);
    tx.commit();
    session.close();
  }

  public int newID() {
    Session session = Main.db.getSession();
    Transaction tx = null;

    int id = 0;

    try {
      tx = session.beginTransaction();
      List n =
          session
              .createNativeQuery(
                  "SELECT max(submissionID)FROM("
                      + "SELECT max(submissionid) AS submissionID FROM cdb.audiosubmission UNION "
                      + "SELECT max(submissionid) AS submissionID FROM cdb.cleaningsubmission UNION "
                      + "SELECT max(submissionid) AS submissionID FROM cdb.computersubmission UNION "
                      + "SELECT max(submissionid) AS submissionID FROM cdb.securitysubmission UNION "
                      + "SELECT max(submissionid) AS submissionID FROM cdb.transportationsubmission) as tem;")
              .list();
      if (n != null) {
        id = (int) n.get(0);
        id++;
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    return id;
  }

  public int generateMessageID() {
    return message.generateMessageID();
  }

  public String generateID() {
    return "id123";
  }
}
