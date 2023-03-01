package edu.wpi.cs3733.C23.teamC.database;

import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.*;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.persistence.PersistenceException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  public Map<Integer, AudiosubmissionEntity> getAudioSubs() {
    return audio.getAudioSubs();
  }

  @Override
  public Map<Integer, CleaningsubmissionEntity> getCleaningSubs() {
    return cleaning.getCleaningSubs();
  }

  @Override
  public Map<Integer, ComputersubmissionEntity> getComputerSubs() {
    return computer.getComputerSubs();
  }

  @Override
  public Map<Integer, SecuritysubmissionEntity> getSecuritySubs() {
    return security.getSecuritySubs();
  }

  @Override
  public Map<Integer, TransportationsubmissionEntity> getTransportationSubs() {
    return transportation.getTransportationSubs();
  }

  @Override
  public Map<String, LocationnameEntity> getLocationnames() {
    return locationname.getLocationnames();
  }

  @Override
  public Map<String, NodeEntity> getNodes() {
    return node.getNodes();
  }

  @Override
  public Map<String, StaffEntity> getStaff() {
    return staff.getStaff();
  }

  @Override
  public List<EdgeEntity> getEdges() {
    return edge.getEdges();
  }

  @Override
  public List<MoveEntity> getMoves() {
    return move.getMoves();
  }

  @Override
  public Map<Integer, MessagesEntity> getMessages() {
    return message.getMessages();
  }

  @Override
  public Map<Integer, MessagesEntity> getMessages(String id) {
    return message.getMessages(id);
  }

  @Override
  public Map<Integer, MessagesEntity> getMessages(String id, int lastid) {
    return message.getMessages(id, lastid);
  }

  @Override
  public Map<Integer, AlertEntity> getAlerts() {
    return null;
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
    HashMap<String, MoveEntity> currentLocations = new HashMap<>();
    for (MoveEntity move : Main.db.getMoves()) {
      MoveEntity temp = currentLocations.get(move.getLongName());
      if (temp == null) {
        currentLocations.put(move.getLongName(), move);
      } else {
        if (move.getMovedate().compareTo(temp.getMovedate()) < 0
            && move.getMovedate().compareTo(new java.sql.Date(date.getTime())) < 0) {
          currentLocations.remove(temp.getLongName());
          currentLocations.put(move.getLongName(), move);
        }
      }
    }

    // count number of moves at a location
    int num = 0;
    for (MoveEntity move : currentLocations.values()) {
      if (move.getNodeID().equals(submission.getNodeID())) {
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
  public void addAlert(AlertEntity alert) {}

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

  public StaffEntity getStaff2(String firstName, String lastName) {
    for (StaffEntity s : staff.getStaff().values()) {
      if (s.getFirstname().equals(firstName) && s.getLastname().equals(lastName)) {
        return s;
      }
    }
    return null;
  }

  public StaffEntity getStaff3(String firstName, String lastName, String staffId) {
    for (StaffEntity s : staff.getStaff().values()) {
      if (s.getFirstname().equals(firstName) && s.getLastname().equals(lastName)) {
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

  @Override
  public void deleteAlert(AlertEntity entity) {}

  public void threadRefresh(int delay) {
    try {
      System.out.println("Auto update!");
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

  @Override
  public void refreshMessages() {
    message = new MessagesDAOImpl();
  }

  @Override
  public String getUser(MFXTextField staffID) {
    return null;
  }

  @Override
  public AlertEntity getAlert(int id) {
    return null;
  }

  @Override
  public int getNewAlertID() {
    return 0;
  }

  @Override
  public List<AlertStaff> getAlertStaff() {
    return null;
  }

  @Override
  public void deleteAlertStaff() {}
}
