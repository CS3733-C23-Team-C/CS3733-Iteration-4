package edu.wpi.cs3733.C23.teamC.database;

import edu.wpi.cs3733.C23.teamC.database.dao.*;
import edu.wpi.cs3733.C23.teamC.database.dao.EdgeDAO;
import edu.wpi.cs3733.C23.teamC.database.dao.MoveDAO;
import edu.wpi.cs3733.C23.teamC.database.dao.NodeDAO;
import edu.wpi.cs3733.C23.teamC.database.dao.StaffDAO;
import edu.wpi.cs3733.C23.teamC.objects.hibernate.*;
import edu.wpi.cs3733.C23.teamC.objects.orm.DAOFacade;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyMapProperty;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

// this would be about 20 lines long if java supported composition in addition to inheritance
@Slf4j
public class DatabaseService implements RepoFacade2 {
  private final DAOFacade orm;
  private AudioSubmissionDAO audioSubmissionDAO;
  private CleaningSubmissionDAO cleaningSubmissionDAO;
  private ComputerSubmissionDAO computerSubmissionDAO;
  private SecuritySubmissionDAO securitySubmissionDAO;
  private TransportationSubmissionDAO transportationSubmissionDAO;
  private final LocationDAO locationDAO;
  private final NodeDAO nodeDAO;
  private StaffDAO staffDAO;
  private final EdgeDAO edgeDAO;
  private final MoveDAO moveDAO;
  private MessageDAO messageDAO;
  private AlertDAO alertDAO;

  public DatabaseService(DAOFacade orm) {
    log.info("Initializing database service.");
    this.orm = orm;
    log.info("Importing audio submissions.");
    audioSubmissionDAO = new AudioSubmissionDAO(orm);
    log.info("Importing cleaning submissions.");
    cleaningSubmissionDAO = new CleaningSubmissionDAO(orm);
    log.info("Importing computer submissions.");
    computerSubmissionDAO = new ComputerSubmissionDAO(orm);
    log.info("Importing security submissions.");
    securitySubmissionDAO = new SecuritySubmissionDAO(orm);
    log.info("Importing transportation submissions.");
    transportationSubmissionDAO = new TransportationSubmissionDAO(orm);
    log.info("Importing locations.");
    locationDAO = LocationDAO.initialize(orm);
    log.info("Importing nodes.");
    nodeDAO = NodeDAO.initialize(orm);
    log.info("Importing staff.");
    staffDAO = new StaffDAO(orm);
    log.info("Importing edges.");
    edgeDAO = EdgeDAO.initialize(orm);
    log.info("Importing moves.");
    moveDAO = MoveDAO.initialize(orm);
    log.info("Importing messages.");
    messageDAO = new MessageDAO(orm);
    log.info("Importing alerts.");
    alertDAO = new AlertDAO(orm);
    log.info("Initialization complete.");
  }

  @Override
  public ReadOnlyMapProperty<Integer, AudiosubmissionEntity> getAudioSubs() {
    return audioSubmissionDAO.getAll();
  }

  @Override
  public ReadOnlyMapProperty<Integer, CleaningsubmissionEntity> getCleaningSubs() {
    return cleaningSubmissionDAO.getAll();
  }

  @Override
  public ReadOnlyMapProperty<Integer, ComputersubmissionEntity> getComputerSubs() {
    return computerSubmissionDAO.getAll();
  }

  @Override
  public ReadOnlyMapProperty<Integer, SecuritysubmissionEntity> getSecuritySubs() {
    return securitySubmissionDAO.getAll();
  }

  @Override
  public ReadOnlyMapProperty<Integer, TransportationsubmissionEntity> getTransportationSubs() {
    return transportationSubmissionDAO.getAll();
  }

  @Override
  public ReadOnlyMapProperty<String, LocationnameEntity> getLocationNames() {
    return locationDAO.getAll();
  }

  @Override
  public ReadOnlyMapProperty<String, NodeEntity> getNodes() {
    return nodeDAO.getAll();
  }

  @Override
  public ReadOnlyMapProperty<String, StaffEntity> getStaff() {
    return staffDAO.getAll();
  }

  @Override
  public ReadOnlyListProperty<EdgeEntity> getEdges() {
    return edgeDAO.getAll();
  }

  @Override
  public ReadOnlyListProperty<MoveEntity> getMoves() {
    return moveDAO.getAll();
  }

  @Override
  public ReadOnlyMapProperty<Integer, MessagesEntity> getMessages() {
    return messageDAO.getAll();
  }

  @Override
  public ReadOnlyMapProperty getAlerts() {
    return alertDAO.getAll();
  }

  @Override
  public void addAudio(AudiosubmissionEntity submission) {
    audioSubmissionDAO.add(submission);
  }

  @Override
  public void addCleaning(CleaningsubmissionEntity submission) {
    cleaningSubmissionDAO.add(submission);
  }

  @Override
  public void addComputer(ComputersubmissionEntity submission) {
    computerSubmissionDAO.add(submission);
  }

  @Override
  public void addSecurity(SecuritysubmissionEntity submission) {
    securitySubmissionDAO.add(submission);
  }

  @Override
  public void addTransportation(TransportationsubmissionEntity submission) {
    transportationSubmissionDAO.add(submission);
  }

  @Override
  public void addLocationName(LocationnameEntity submission) {
    locationDAO.add(submission);
  }

  @Override
  public void addNode(NodeEntity submission) {
    nodeDAO.add(submission);
  }

  @Override
  public void addStaff(StaffEntity submission) {
    staffDAO.add(submission);
  }

  @Override
  public void addEdge(EdgeEntity submission) {
    edgeDAO.add(submission);
  }

  @Override
  public boolean addMove(MoveEntity submission) {
    // Get most recent locations
    java.util.Date date = new java.util.Date();
    HashMap<String, MoveEntity> currentLocations = new HashMap<>();
    for (var move : getMoves()) {
      var temp = currentLocations.get(move.getLongName());
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
    for (var move : currentLocations.values()) {
      if (move.getNodeID().equals(submission.getNodeID())) {
        num++;
      }
    }

    if (num < 2) {
      moveDAO.add(submission);
      return true;
    }
    return false;
  }

  @Override
  public void addMessage(MessagesEntity message) {
    messageDAO.add(message);
  }

  @Override
  public void addAlert(AlertEntity alert) {
    alertDAO.add(alert);
  }

  @Override
  public AudiosubmissionEntity getAudio(Integer id) {
    return audioSubmissionDAO.get(id);
  }

  @Override
  public CleaningsubmissionEntity getCleaning(Integer id) {
    return cleaningSubmissionDAO.get(id);
  }

  @Override
  public ComputersubmissionEntity getComputer(Integer id) {
    return computerSubmissionDAO.get(id);
  }

  @Override
  public SecuritysubmissionEntity getSecurity(Integer id) {
    return securitySubmissionDAO.get(id);
  }

  @Override
  public TransportationsubmissionEntity getTransportation(Integer id) {
    return transportationSubmissionDAO.get(id);
  }

  @Override
  public LocationnameEntity getLocationname(String longname) {
    return locationDAO.get(longname);
  }

  @Override
  public NodeEntity getNode(String nodeid) {
    return nodeDAO.get(nodeid);
  }

  @Override
  public StaffEntity getStaff(String staffid) {
    return staffDAO.get(staffid);
  }

  @Override
  public MessagesEntity getMessage(Integer id) {
    return messageDAO.get(id);
  }

  @Override
  public AlertEntity getAlert(Integer id) {
    return alertDAO.get(id);
  }

  @Override
  public Map<Integer, MessagesEntity> getMessages(String staff) {
    return messageDAO.getMessages(staff);
  }

  @Override
  public Map<Integer, MessagesEntity> getMessages(String staff, int lastid) {
    return messageDAO.getMessages(staff, lastid);
  }

  @Override
  public void deleteAudio(Integer id) {
    deleteAudio(getAudio(id));
  }

  @Override
  public void deleteAudio(AudiosubmissionEntity entity) {
    audioSubmissionDAO.delete(entity);
  }

  @Override
  public void deleteCleaning(Integer id) {
    deleteCleaning(getCleaning(id));
  }

  @Override
  public void deleteCleaning(CleaningsubmissionEntity entity) {
    cleaningSubmissionDAO.delete(entity);
  }

  @Override
  public void deleteComputer(Integer id) {
    deleteComputer(getComputer(id));
  }

  @Override
  public void deleteComputer(ComputersubmissionEntity entity) {
    computerSubmissionDAO.delete(entity);
  }

  @Override
  public void deleteSecurity(Integer id) {
    deleteSecurity(getSecurity(id));
  }

  @Override
  public void deleteSecurity(SecuritysubmissionEntity entity) {
    securitySubmissionDAO.delete(entity);
  }

  @Override
  public void deleteTransportation(Integer id) {
    deleteTransportation(getTransportation(id));
  }

  @Override
  public void deleteTransportation(TransportationsubmissionEntity entity) {
    transportationSubmissionDAO.delete(entity);
  }

  @Override
  public void deleteEdge(EdgeEntity edge) {
    edgeDAO.delete(edge);
  }

  @Override
  public void deleteLocationName(String longname) {
    deleteLocationName(getLocationname(longname));
  }

  @Override
  public void deleteLocationName(LocationnameEntity entity) {
    locationDAO.delete(entity);
  }

  @Override
  public void deleteMove(MoveEntity move) {
    moveDAO.delete(move);
  }

  @Override
  public void deleteNode(String nodeid) {
    deleteNode(getNode(nodeid));
  }

  @Override
  public void deleteNode(NodeEntity entity) {
    nodeDAO.delete(entity);
  }

  @Override
  public void deleteStaff(String staffid) {
    deleteStaff(getStaff(staffid));
  }

  @Override
  public void deleteStaff(StaffEntity entity) {
    staffDAO.delete(entity);
  }

  @Override
  public void deleteMessage(Integer messageid) {
    deleteMessage(getMessage(messageid));
  }

  @Override
  public void deleteMessage(MessagesEntity entity) {
    messageDAO.delete(entity);
  }

  @Override
  public void deleteAlert(AlertEntity entity) {
    alertDAO.delete(entity);
  }

  @Override
  public StaffEntity getStaff(String staffId, String password) {
    return staffDAO.get(staffId, password);
  }

  @Override
  public StaffEntity getStaff2(String firstName, String lastName) {
    return staffDAO.find(firstName, lastName);
  }

  @Override
  public StaffEntity getStaff3(String firstName, String lastName, String staffId) {
    return staffDAO.find(firstName, lastName, staffId);
  }

  @Override
  public Session getSession() {
    return orm.getSession();
  }

  /*@Override
  public UUID newID() {
    // the chance of collision is 1 in 2^128. It's hard to describe just how tiny of a chance that
    // is, but it's a small
    // enough chance we shouldn't have any collisions.
    return UUID.randomUUID();
  }*/

  @Override
  public int newID() {
    Session session = getSession();
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

  @Override
  public BufferedImage getImage(int id) {
    Session session = getSession();
    Transaction tx = null;

    try {
      tx = session.beginTransaction();
      Query q =
          session.createNativeQuery(
              "SELECT cdb.pics.pic FROM cdb.pics WHERE cdb.pics.picnum = :id ");
      q.setParameter("id", id);
      System.out.println(q.getQueryString());
      byte[] b = (byte[]) q.list().get(0);
      ByteArrayInputStream inStreambj = new ByteArrayInputStream(b);
      try {
        BufferedImage newImage = ImageIO.read(inStreambj);
        return newImage;
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    return null;
  }

  @Override
  public int setImage(String filepath) throws IOException {
    Session session = getSession();
    Transaction tx = null;

    byte[] bytes = Files.readAllBytes(Paths.get(filepath));

    try {
      tx = session.beginTransaction();
      Query q = session.createNativeQuery("INSERT INTO cdb.pics values (:id, :bytes)");
      q.setParameter("bytes", bytes);
      q.setParameter("id", getMaxImageID() + 1);
      q.executeUpdate();
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    return getMaxImageID();
  }

  private int getMaxImageID() {
    Session session = orm.getSession();
    Transaction tx = null;

    int id = 0;

    try {
      tx = session.beginTransaction();
      List n = session.createNativeQuery("SELECT MAX(cdb.pics.picnum) FROM cdb.pics").list();
      if (n != null) {
        id = (int) n.get(0);
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

  @Override
  public int getNewAlertID() {
    Session session = orm.getSession();
    Transaction tx = null;

    int id = 0;

    try {
      tx = session.beginTransaction();
      List n = session.createNativeQuery("SELECT MAX(cdb.alerts.alertid) FROM cdb.alerts").list();
      if (n != null) {
        id = (int) n.get(0);
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    return id + 1;
  }

  @Override
  public void importAll() {
    // nothing to be done, this is handled in the constructor
  }

  @Override
  public int generateMessageID() {
    return messageDAO.generateMessageID();
  }

  @Override
  public void threadRefresh(int delay) {
    System.out.println("REFRES2!");
    try {
      Thread.sleep(delay * 1000L);
      messageDAO = new MessageDAO(orm);
      Thread.sleep(delay * 1000L);
      audioSubmissionDAO = new AudioSubmissionDAO(orm);
      Thread.sleep(delay * 1000L);
      cleaningSubmissionDAO = new CleaningSubmissionDAO(orm);
      Thread.sleep(delay * 1000L);
      computerSubmissionDAO = new ComputerSubmissionDAO(orm);
      Thread.sleep(delay * 1000L);

      // please DO NOT change these 4 updates, or it will break a ton of stuff in the map editor.
      edgeDAO.update();
      Thread.sleep(delay * 1000L);
      locationDAO.update();
      Thread.sleep(delay * 1000L);
      moveDAO.update();
      Thread.sleep(delay * 1000L);
      nodeDAO.update();
      Thread.sleep(delay * 1000L);

      securitySubmissionDAO = new SecuritySubmissionDAO(orm);
      Thread.sleep(delay * 1000L);
      staffDAO = new StaffDAO(orm);
      Thread.sleep(delay * 1000L);
      transportationSubmissionDAO = new TransportationSubmissionDAO(orm);
      Thread.sleep(delay * 1000L);
      alertDAO = new AlertDAO(orm);
    } catch (InterruptedException e) {
      log.info("Shutting down auto-update.");
    }
  }

  @Override
  public void refreshMessages() {
    messageDAO = new MessageDAO(orm);
  }
}
