package edu.wpi.capybara.objects.hibernate;

import edu.wpi.capybara.Main;
import edu.wpi.capybara.database.RepoFacade;
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
  int id;

  static SessionFactory factory;

  public newDBConnect() {
    try {
      factory = new Configuration().configure().buildSessionFactory();
    } catch (Throwable ex) {
      System.err.println("Failed to create sessionFactory object." + ex);
      throw new ExceptionInInitializerError(ex);
    }
    importAll();
  }

  private void importAll() {
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
  }

  void importAudio() {
    Session session = factory.openSession();
    Transaction tx = null;

    HashMap<Integer, AudiosubmissionEntity> ret = new HashMap<Integer, AudiosubmissionEntity>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM AudiosubmissionEntity").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        AudiosubmissionEntity temp = (AudiosubmissionEntity) iterator.next();
        ret.put(temp.getSubmissionid(), temp);
        if (id < temp.getSubmissionid()) {
          id = temp.getSubmissionid();
        }
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    audio = new AudiosubmissionDAOImpl(ret);
  }

  void importCleaning() {
    Session session = factory.openSession();
    Transaction tx = null;

    HashMap<Integer, CleaningsubmissionEntity> ret =
        new HashMap<Integer, CleaningsubmissionEntity>();
    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM CleaningsubmissionEntity ").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        CleaningsubmissionEntity temp = (CleaningsubmissionEntity) iterator.next();
        ret.put(temp.getSubmissionid(), temp);
        if (id < temp.getSubmissionid()) {
          id = temp.getSubmissionid();
        }
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    cleaning = new CleaningsubmissionDAOImpl(ret);
  }

  void importComputer() {
    Session session = factory.openSession();
    Transaction tx = null;

    HashMap<Integer, ComputersubmissionEntity> ret =
        new HashMap<Integer, ComputersubmissionEntity>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM ComputersubmissionEntity ").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        ComputersubmissionEntity temp = (ComputersubmissionEntity) iterator.next();
        ret.put(temp.getSubmissionid(), temp);
        if (id < temp.getSubmissionid()) {
          id = temp.getSubmissionid();
        }
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    computer = new ComputersubmissionDAOImpl(ret);
  }

  void importSecurity() {
    Session session = factory.openSession();
    Transaction tx = null;

    HashMap<Integer, SecuritysubmissionEntity> ret =
        new HashMap<Integer, SecuritysubmissionEntity>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM SecuritysubmissionEntity ").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        SecuritysubmissionEntity temp = (SecuritysubmissionEntity) iterator.next();
        ret.put(temp.getSubmissionid(), temp);
        if (id < temp.getSubmissionid()) {
          id = temp.getSubmissionid();
        }
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    security = new SecurityDAOImpl(ret);
  }

  void importTransportation() {
    Session session = factory.openSession();
    Transaction tx = null;

    HashMap<Integer, TransportationsubmissionEntity> ret =
        new HashMap<Integer, TransportationsubmissionEntity>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM TransportationsubmissionEntity ").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        TransportationsubmissionEntity temp = (TransportationsubmissionEntity) iterator.next();
        ret.put(temp.getSubmissionid(), temp);
        if (id < temp.getSubmissionid()) {
          id = temp.getSubmissionid();
        }
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    transportation = new TransportationDAOImpl(ret);
  }

  void importLocationname() {
    Session session = factory.openSession();
    Transaction tx = null;

    HashMap<String, LocationnameEntity> ret = new HashMap<String, LocationnameEntity>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM LocationnameEntity ").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        LocationnameEntity temp = (LocationnameEntity) iterator.next();
        ret.put(temp.getLongname(), temp);
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    locationname = new LocationnameDAOImpl(ret);
  }

  void importNode() {
    Session session = factory.openSession();
    Transaction tx = null;

    HashMap<String, NodeEntity> ret = new HashMap<String, NodeEntity>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM NodeEntity ").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        NodeEntity temp = (NodeEntity) iterator.next();
        ret.put(temp.getNodeid(), temp);
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    node = new NodeDAOImpl(ret);
  }

  void importStaff() {
    Session session = factory.openSession();
    Transaction tx = null;

    HashMap<String, StaffEntity> ret = new HashMap<String, StaffEntity>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM StaffEntity ").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        StaffEntity temp = (StaffEntity) iterator.next();
        ret.put(temp.getStaffid(), temp);
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    staff = new StaffDAOImpl(ret);
  }

  void importEdge() {
    Session session = factory.openSession();
    Transaction tx = null;

    ArrayList<EdgeEntity> ret = new ArrayList<EdgeEntity>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM EdgeEntity ").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        EdgeEntity temp = (EdgeEntity) iterator.next();
        ret.add(temp);
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    edge = new EdgeDAOImpl(ret);
  }

  void importMove() {
    Session session = factory.openSession();
    Transaction tx = null;

    ArrayList<MoveEntity> ret = new ArrayList<MoveEntity>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM MoveEntity ").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        MoveEntity temp = (MoveEntity) iterator.next();
        ret.add(temp);
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    move = new MoveDAOImpl(ret);
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
        currentLocations.put(temp.getLongname(), temp);
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
  public Session getSession() {
    return factory.openSession();
  }

  static void insertNew(Object submission) {
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

  static void delete(Object submission) {
    Session session = factory.openSession();
    Transaction tx = session.beginTransaction();
    session.remove(submission);
    tx.commit();
    session.close();
  }
}
