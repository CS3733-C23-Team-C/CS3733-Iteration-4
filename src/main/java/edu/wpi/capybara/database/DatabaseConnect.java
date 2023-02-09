package edu.wpi.capybara.database;

import edu.wpi.capybara.objects.hibernate.*;
import edu.wpi.capybara.objects.submissions.submissionStatus;
import jakarta.persistence.PersistenceException;
import java.sql.*;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

@Slf4j(topic = "DatabaseConnect")
public class DatabaseConnect {
  // static Connection connection;
  static SessionFactory factory;
  public static TreeMap<Integer, NodeEntity> nodes;
  public static TreeMap<Integer, EdgeEntity> edges;
  public static HashMap<Integer, LocationnameEntity> locationNames;
  public static HashMap<Integer, MoveEntity> moves;
  public static HashMap<Integer, StaffEntity> staff;

  static Scanner in = new Scanner(System.in);

  public static Session getSession() {
    return factory.openSession();
  }

  public static void main(String args[]) {
    connect();
    importData();
    //    // Session session = factory.openSession();
    //    for (LocationnameEntity n : locationNames.values()) {
    //      // Transaction tx = session.beginTransaction();
    //      if (Objects.equals(n.getLongname(), "Duncan Reid Conference Room")) {
    //        n.setShortname("6969");
    //      }
    //      //      session.merge(n);
    //      //      tx.commit();
    //      //      System.out.println(n.getBuilding());
    //    }
    Session session = factory.openSession();
    Transaction tx = session.beginTransaction();
    //    for (NodeEntity n : nodes.values()) {
    //      if (n.getBuilding().equals("76868")) {
    //        nodes.remove(n.hashCode());
    //        n.delete();
    //      }
    //    }

    CleaningsubmissionEntity addSubmission =
        new CleaningsubmissionEntity("3", "1", "1", "2X2279Y0762", submissionStatus.BLANK);

    //    addSubmission.setMemberid("2");
    //    addSubmission.setDescription("1");
    //    addSubmission.setHazardlevel("1");
    //    addSubmission.setLocation("2X2279Y0762");
    //    addSubmission.setSubmissionstatus(submissionStatus.BLANK);
    session.persist(addSubmission);
    tx.commit();
    session.close();

    //    NodeEntity addNode = new NodeEntity("1000", 1000, 1000, "3", "Foisie");
    //    //    addNode.setNodeid("1337");
    //    //    addNode.setXcoord(1000);
    //    //    addNode.setYcoord(1000);
    //    //    addNode.setFloor("3");
    //    //    addNode.setBuilding("Atwater");
    //    session.persist(addNode);
    //    tx.commit();
    //    session.close();

    factory.close();
  }

  public static void importData() {
    importNodes();
    importEdges();
    importLocations();
    importMove();
    importStaff();
  }

  public static void importNodes() {
    nodes = new TreeMap<Integer, NodeEntity>();
    Session session = factory.openSession();
    Transaction tx = null;

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM NodeEntity").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        NodeEntity temp = (NodeEntity) iterator.next();
        nodes.put((temp).hashCode(), temp);
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
  }

  public static void importStaff() {
    staff = new HashMap<Integer, StaffEntity>();
    Session session = factory.openSession();
    Transaction tx = null;

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM StaffEntity ").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        StaffEntity temp = (StaffEntity) iterator.next();
        staff.put((temp).hashCode(), temp);
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
  }

  public static void importMove() {
    moves = new HashMap<Integer, MoveEntity>();
    Session session = factory.openSession();
    Transaction tx = null;

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM MoveEntity").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        MoveEntity temp = (MoveEntity) iterator.next();
        moves.put((temp).hashCode(), temp);
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
  }

  public static void importLocations() {
    locationNames = new HashMap<Integer, LocationnameEntity>();
    Session session = factory.openSession();
    Transaction tx = null;

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM LocationnameEntity ").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        LocationnameEntity temp = (LocationnameEntity) iterator.next();
        locationNames.put((temp).hashCode(), temp);
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
  }

  public static void importEdges() {
    edges = new TreeMap<Integer, EdgeEntity>();
    Session session = factory.openSession();
    Transaction tx = null;

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM EdgeEntity ").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        EdgeEntity temp = (EdgeEntity) iterator.next();
        edges.put((temp).hashCode(), temp);
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
  }

  public static LinkedList<TransportationsubmissionEntity> transports() {
    Session session = factory.openSession();
    Transaction tx = null;

    LinkedList<TransportationsubmissionEntity> ret =
        new LinkedList<TransportationsubmissionEntity>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM TransportationsubmissionEntity ").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        ret.add((TransportationsubmissionEntity) iterator.next());
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    return ret;
  }

  public static LinkedList<CleaningsubmissionEntity> cleanings() {
    Session session = factory.openSession();
    Transaction tx = null;

    LinkedList<CleaningsubmissionEntity> ret = new LinkedList<CleaningsubmissionEntity>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM CleaningsubmissionEntity ").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        ret.add((CleaningsubmissionEntity) iterator.next());
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    return ret;
  }

  public static LinkedList<SecuritysubmissionEntity> security() {
    Session session = factory.openSession();
    Transaction tx = null;

    LinkedList<SecuritysubmissionEntity> ret = new LinkedList<SecuritysubmissionEntity>();

    try {
      tx = session.beginTransaction();
      List n = session.createQuery("FROM SecuritysubmissionEntity ").list();
      for (Iterator iterator = n.iterator(); iterator.hasNext(); ) {
        ret.add((SecuritysubmissionEntity) iterator.next());
      }
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
    return ret;
  }

  //  public static void deleteNode(int nodeID) {
  //    nodes.remove(nodeID);
  //  }

  //  public static void deleteEdge(String edgeID) {
  //    edges.remove(edgeID);
  //  }
  //
  public static TreeMap<Integer, EdgeEntity> getEdges() {
    return edges;
  }

  public static TreeMap<Integer, NodeEntity> getNodes() {
    return nodes;
  }

  public static HashMap<Integer, MoveEntity> getMoves() {
    return moves;
  }

  public static HashMap<Integer, LocationnameEntity> getLocationNames() {
    return locationNames;
  }

  public static void connect() {
    try {
      factory = new Configuration().configure().buildSessionFactory();
    } catch (Throwable ex) {
      System.err.println("Failed to create sessionFactory object." + ex);
      throw new ExceptionInInitializerError(ex);
    }

    // connection = null;
    // connecting to the database
    //    try {
    //      Class.forName("org.postgresql.Driver"); // driver
    //      System.out.println("good");
    //      connection =
    //          DriverManager.getConnection(
    //              "jdbc:postgresql://database.cs.wpi.edu:5432/teamcdb",
    //              "teamc",
    //              "teamc30"); // create the connection
    //    } catch (ClassNotFoundException | SQLException e) {
    //      System.out.println(e);
    //    }
    //    if (connection != null) {
    //      System.out.println("Connection success");
    //    } else {
    //      System.out.println("Connection failed");
    //    }
    //
    //    importData();

  }

  public static void insertNode(NodeEntity node) {
    insert(node);
    nodes.put(node.hashCode(), node);
  }

  public static void insertEdge(EdgeEntity edge) {
    insert(edge);
    edges.put(edge.hashCode(), edge);
  }

  public static void insertMove(MoveEntity move) {
    insert(move);
    moves.put(move.hashCode(), move);
  }

  public static void insertLocationName(LocationnameEntity locationName) {
    insert(locationName);
    locationNames.put(locationName.hashCode(), locationName);
  }

  private static <T> void insert(T entity) {
    try (final var session = factory.openSession()) {
      final var tx = session.beginTransaction();
      try {
        session.persist(entity);
        tx.commit();
      } catch (PersistenceException e) {
        tx.rollback();
        log.error("Unable to insert entity of type " + entity.getClass().getName(), e);
      }
    }
  }

  public static void insertCleaning(
      String staffid,
      String location,
      String hazardlevel,
      String description,
      submissionStatus submissionstatus) {
    Session session = factory.openSession();
    Transaction tx = null;

    try {
      tx = session.beginTransaction();
      CleaningsubmissionEntity cleaning =
          new CleaningsubmissionEntity(
              staffid, location, hazardlevel, description, submissionstatus);
      //      cleaning.setMemberid(staffid);
      //      cleaning.setLocation(location);
      //      cleaning.setHazardlevel(hazardlevel);
      //      cleaning.setDescription(description);
      //      cleaning.setSubmissionstatus(submissionstatus);
      session.save(cleaning);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
  }

  public static void insertTransportation(
      String staffid,
      String currroomnum,
      String destroomnum,
      String reason,
      submissionStatus status) {
    Session session = factory.openSession();
    Transaction tx = null;

    try {
      tx = session.beginTransaction();
      TransportationsubmissionEntity transportation =
          new TransportationsubmissionEntity(staffid, currroomnum, destroomnum, reason, status);
      //      transportation.setEmployeeid(staffid);
      //      transportation.setCurrroomnum(currroomnum);
      //      transportation.setDestroomnum(destroomnum);
      //      transportation.setReason(reason);
      //      transportation.setStatus(status);
      session.save(transportation);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
  }

  public static void insertSecurity(
      String staffid,
      String location,
      String type,
      String notesupdate,
      submissionStatus submissionstatus) {
    Session session = factory.openSession();
    Transaction tx = null;

    try {
      tx = session.beginTransaction();
      SecuritysubmissionEntity security =
          new SecuritysubmissionEntity(staffid, location, type, notesupdate, submissionstatus);
      //      transportation.setEmployeeid(staffid);
      //      transportation.setCurrroomnum(currroomnum);
      //      transportation.setDestroomnum(destroomnum);
      //      transportation.setReason(reason);
      //      transportation.setStatus(status);
      session.save(security);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
  }

  public static StaffEntity getStaff(String Staffid) {
    for (StaffEntity s : staff.values()) {
      if (s.getStaffid().equals(Staffid)) {
        return s;
      }
    }
    return null;
  }

  public static StaffEntity getStaff(String fname, String lname) {
    for (StaffEntity s : staff.values()) {
      if (s.getFirstname().equals(fname) && s.getLastname().equals(lname)) {
        return s;
      }
    }
    return null;
  }

  //  public static void importData() {
  //    importNodes();
  //    importEdges();
  //    importLocationName();
  //    importMoves(); // String is the ID
  //  }
  //
  //  private static void importNodes() {
  //    nodes = new TreeMap<String, NodeEntity>();
  //
  //    try {
  //      Statement stmt = connection.createStatement(); // initialize the statement
  //      String query = "SELECT * FROM node"; // write out the query as a string
  //      ResultSet rset = stmt.executeQuery(query); // run the query
  //
  //      String name;
  //      while (rset.next()) { // loop through the table
  //        nodes.put(
  //            rset.getString("nodeid"),
  //            new Node(
  //                rset.getString("nodeid"),
  //                rset.getInt("xcoord"),
  //                rset.getInt("ycoord"),
  //                rset.getString("floor"),
  //                rset.getString("building")));
  //      }
  //      rset.close();
  //      stmt.close();
  //    } catch (SQLException e) {
  //      System.out.println(e);
  //    }
  //  }
  //
  //  // NOTE: YOU MUST RUN THIS AFTER IMPORTING THE NODES
  //  private static void importEdges() {
  //    edges = new TreeMap<String, Edge>();
  //
  //    try {
  //      Statement stmt = connection.createStatement(); // initialize the statement
  //      String query = "SELECT * FROM edge"; // write out the query as a string
  //      ResultSet rset = stmt.executeQuery(query); // run the query
  //
  //      String name;
  //      while (rset.next()) { // loop through the table
  //        edges.put(
  //            rset.getString("node1") + rset.getString("node2"),
  //            new Edge(rset.getString("node1"), rset.getString("node2")));
  //      }
  //      rset.close();
  //      stmt.close();
  //    } catch (SQLException e) {
  //      System.out.println(e);
  //    }
  //  }
  //
  //  private static void importLocationName() {
  //    locationNames = new HashMap<String, locationname>();
  //
  //    try {
  //      Statement stmt = connection.createStatement(); // initialize the statement
  //      String query = "SELECT * FROM locationname"; // write out the query as a string
  //      ResultSet rset = stmt.executeQuery(query); // run the query
  //
  //      String name;
  //      while (rset.next()) { // loop through the table
  //        locationNames.put(
  //            rset.getString("longname"),
  //            new locationname(
  //                rset.getString("longname"),
  //                rset.getString("shortname"),
  //                rset.getString("locationtype")));
  //      }
  //      rset.close();
  //      stmt.close();
  //    } catch (SQLException e) {
  //      System.out.println(e);
  //    }
  //  }
  //
  //  private static void importMoves() {
  //    moves = new HashMap<String, move>();
  //
  //    try {
  //      Statement stmt = connection.createStatement(); // initialize the statement
  //      String query = "SELECT * FROM move"; // write out the query as a string
  //      ResultSet rset = stmt.executeQuery(query); // run the query
  //
  //      String name;
  //      while (rset.next()) { // loop through the table
  //        moves.put(
  //            rset.getString("nodeid")
  //                + rset.getString("longname")
  //                + rset.getDate("movedate").toString(),
  //            new move(
  //                rset.getString("nodeid"), rset.getString("longname"),
  // rset.getDate("movedate")));
  //      }
  //      rset.close();
  //      stmt.close();
  //    } catch (SQLException e) {
  //      System.out.println(e);
  //    }
  //  }
  //
  //  public static void query() {
  //    try {
  //      Statement stmt = connection.createStatement(); // initialize the statement
  //      String query = "SELECT * FROM l1nodes"; // write out the query as a string
  //      ResultSet rset = stmt.executeQuery(query); // run the query
  //
  //      String name;
  //      while (rset.next()) { // loop through the table
  //        name = rset.getString("nodeid"); // get value stored in collumn "name" for current row
  //        System.out.println(name);
  //      }
  //      rset.close();
  //      stmt.close();
  //    } catch (SQLException e) {
  //      System.out.println(e);
  //    }
  //  }

}
