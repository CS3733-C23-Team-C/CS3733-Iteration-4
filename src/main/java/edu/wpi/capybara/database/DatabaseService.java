package edu.wpi.capybara.database;

import edu.wpi.capybara.database.dao.*;
import edu.wpi.capybara.database.dao.EdgeDAO;
import edu.wpi.capybara.database.dao.MoveDAO;
import edu.wpi.capybara.database.dao.NodeDAO;
import edu.wpi.capybara.database.dao.StaffDAO;
import edu.wpi.capybara.objects.orm.*;
import java.util.HashMap;
import java.util.List;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyMapProperty;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

// this would be about 20 lines long if java supported composition in addition to inheritance
@Slf4j
public class DatabaseService implements RepoFacade2 {
  private final DAOFacade orm;
  private final AudioSubmissionDAO audioSubmissionDAO;
  private final CleaningSubmissionDAO cleaningSubmissionDAO;
  private final ComputerSubmissionDAO computerSubmissionDAO;
  private final SecuritySubmissionDAO securitySubmissionDAO;
  private final TransportationSubmissionDAO transportationSubmissionDAO;
  private final LocationDAO locationDAO;
  private final NodeDAO nodeDAO;
  private final StaffDAO staffDAO;
  private final EdgeDAO edgeDAO;
  private final MoveDAO moveDAO;
  private final MessageDAO messageDAO;

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
    locationDAO = new LocationDAO(orm);
    log.info("Importing nodes.");
    nodeDAO = new NodeDAO(orm);
    log.info("Importing staff.");
    staffDAO = new StaffDAO(orm);
    log.info("Importing edges.");
    edgeDAO = new EdgeDAO(orm);
    log.info("Importing moves.");
    moveDAO = new MoveDAO(orm);
    log.info("Importing messages.");
    messageDAO = new MessageDAO(orm);
    log.info("Initialization complete.");
  }

  @Override
  public ReadOnlyMapProperty<Integer, AudioSubmission> getAudioSubs() {
    return audioSubmissionDAO.getAll();
  }

  @Override
  public ReadOnlyMapProperty<Integer, CleaningSubmission> getCleaningSubs() {
    return cleaningSubmissionDAO.getAll();
  }

  @Override
  public ReadOnlyMapProperty<Integer, ComputerSubmission> getComputerSubs() {
    return computerSubmissionDAO.getAll();
  }

  @Override
  public ReadOnlyMapProperty<Integer, SecuritySubmission> getSecuritySubs() {
    return securitySubmissionDAO.getAll();
  }

  @Override
  public ReadOnlyMapProperty<Integer, TransportationSubmission> getTransportationSubs() {
    return transportationSubmissionDAO.getAll();
  }

  @Override
  public ReadOnlyMapProperty<String, Location> getLocationNames() {
    return locationDAO.getAll();
  }

  @Override
  public ReadOnlyMapProperty<String, Node> getNodes() {
    return nodeDAO.getAll();
  }

  @Override
  public ReadOnlyMapProperty<String, Staff> getStaff() {
    return staffDAO.getAll();
  }

  @Override
  public ReadOnlyListProperty<Edge> getEdges() {
    return edgeDAO.getAll();
  }

  @Override
  public ReadOnlyListProperty<Move> getMoves() {
    return moveDAO.getAll();
  }

  @Override
  public ReadOnlyMapProperty<Integer, Messages> getMessages() {
    return messageDAO.getAll();
  }

  @Override
  public void addAudio(AudioSubmission submission) {
    audioSubmissionDAO.add(submission);
  }

  @Override
  public void addCleaning(CleaningSubmission submission) {
    cleaningSubmissionDAO.add(submission);
  }

  @Override
  public void addComputer(ComputerSubmission submission) {
    computerSubmissionDAO.add(submission);
  }

  @Override
  public void addSecurity(SecuritySubmission submission) {
    securitySubmissionDAO.add(submission);
  }

  @Override
  public void addTransportation(TransportationSubmission submission) {
    transportationSubmissionDAO.add(submission);
  }

  @Override
  public void addLocationName(Location submission) {
    locationDAO.add(submission);
  }

  @Override
  public void addNode(Node submission) {
    nodeDAO.add(submission);
  }

  @Override
  public void addStaff(Staff submission) {
    staffDAO.add(submission);
  }

  @Override
  public void addEdge(Edge submission) {
    edgeDAO.add(submission);
  }

  @Override
  public boolean addMove(Move submission) {
    // Get most recent locations
    java.util.Date date = new java.util.Date();
    HashMap<String, Move> currentLocations = new HashMap<>();
    for (var move : getMoves()) {
      var temp = currentLocations.get(move.getLocation().getLongName());
      if (temp == null) {
        currentLocations.put(move.getLocation().getLongName(), move);
      } else {
        if (move.getMoveDate().compareTo(temp.getMoveDate()) < 0
            && move.getMoveDate().compareTo(new java.sql.Date(date.getTime())) < 0) {
          currentLocations.remove(temp.getLocation().getLongName());
          currentLocations.put(move.getLocation().getLongName(), move);
        }
      }
    }

    // count number of moves at a location
    int num = 0;
    for (var move : currentLocations.values()) {
      if (move.getNode().getId().equals(submission.getNode().getId())) {
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
  public void addMessage(Messages message) {
    messageDAO.add(message);
  }

  @Override
  public AudioSubmission getAudio(Integer id) {
    return audioSubmissionDAO.get(id);
  }

  @Override
  public CleaningSubmission getCleaning(Integer id) {
    return cleaningSubmissionDAO.get(id);
  }

  @Override
  public ComputerSubmission getComputer(Integer id) {
    return computerSubmissionDAO.get(id);
  }

  @Override
  public SecuritySubmission getSecurity(Integer id) {
    return securitySubmissionDAO.get(id);
  }

  @Override
  public TransportationSubmission getTransportation(Integer id) {
    return transportationSubmissionDAO.get(id);
  }

  @Override
  public Location getLocationname(String longname) {
    return locationDAO.get(longname);
  }

  @Override
  public Node getNode(String nodeid) {
    return nodeDAO.get(nodeid);
  }

  @Override
  public Staff getStaff(String staffid) {
    return staffDAO.get(staffid);
  }

  @Override
  public Messages getMessage(Integer id) {
    return messageDAO.get(id);
  }

  @Override
  public void deleteAudio(Integer id) {
    deleteAudio(getAudio(id));
  }

  @Override
  public void deleteAudio(AudioSubmission entity) {
    audioSubmissionDAO.delete(entity);
  }

  @Override
  public void deleteCleaning(Integer id) {
    deleteCleaning(getCleaning(id));
  }

  @Override
  public void deleteCleaning(CleaningSubmission entity) {
    cleaningSubmissionDAO.delete(entity);
  }

  @Override
  public void deleteComputer(Integer id) {
    deleteComputer(getComputer(id));
  }

  @Override
  public void deleteComputer(ComputerSubmission entity) {
    computerSubmissionDAO.delete(entity);
  }

  @Override
  public void deleteSecurity(Integer id) {
    deleteSecurity(getSecurity(id));
  }

  @Override
  public void deleteSecurity(SecuritySubmission entity) {
    securitySubmissionDAO.delete(entity);
  }

  @Override
  public void deleteTransportation(Integer id) {
    deleteTransportation(getTransportation(id));
  }

  @Override
  public void deleteTransportation(TransportationSubmission entity) {
    transportationSubmissionDAO.delete(entity);
  }

  @Override
  public void deleteEdge(Edge edge) {
    edgeDAO.delete(edge);
  }

  @Override
  public void deleteLocationName(String longname) {
    deleteLocationName(getLocationname(longname));
  }

  @Override
  public void deleteLocationName(Location entity) {
    locationDAO.delete(entity);
  }

  @Override
  public void deleteMove(Move move) {
    moveDAO.delete(move);
  }

  @Override
  public void deleteNode(String nodeid) {
    deleteNode(getNode(nodeid));
  }

  @Override
  public void deleteNode(Node entity) {
    nodeDAO.delete(entity);
  }

  @Override
  public void deleteStaff(String staffid) {
    deleteStaff(getStaff(staffid));
  }

  @Override
  public void deleteStaff(Staff entity) {
    staffDAO.delete(entity);
  }

  @Override
  public void deleteMessage(Integer messageid) {
    deleteMessage(getMessage(messageid));
  }

  @Override
  public void deleteMessage(Messages entity) {
    messageDAO.delete(entity);
  }

  @Override
  public Staff getStaff(String staffId, String password) {
    return staffDAO.get(staffId, password);
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
  public void importAll() {
    // nothing to be done, this is handled in the constructor
  }

  @Override
  public int generateMessageID() {
    return messageDAO.generateMessageID();
  }
}
