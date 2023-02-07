package edu.wpi.capybara.database;

import edu.wpi.capybara.objects.hibernate.*;
import edu.wpi.capybara.objects.submissions.submissionStatus;
import java.sql.*;
import java.util.*;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public class DatabaseConnect {
  // static Connection connection;
  static SessionFactory factory;
  public static TreeMap<Integer, NodeEntity> nodes;
  public static TreeMap<Integer, EdgeEntity> edges;
  public static HashMap<Integer, LocationnameEntity> locationNames;
  public static HashMap<Integer, MoveEntity> moves;

  static Scanner in = new Scanner(System.in);

  public static Session getSession() {
    return factory.openSession();
  }

  public static void main(String args[]) {
    connect();
    importData();
    // Session session = factory.openSession();
    for (LocationnameEntity n : locationNames.values()) {
      // Transaction tx = session.beginTransaction();
      if (Objects.equals(n.getLongname(), "Duncan Reid Conference Room")) {
        n.setShortname("6969");
      }
      //      session.merge(n);
      //      tx.commit();
      //      System.out.println(n.getBuilding());
    }
    factory.close();
  }

  public static void importData() {
    importNodes();
    importEdges();
    importLocations();
    importMove();
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
        ret.add((TransportationsubmissionEntity) n);
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
        ret.add((CleaningsubmissionEntity) n);
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

  //  public static void deleteNode(String nodeID) {
  //    nodes.remove(nodeID);
  //  }
  //
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
      CleaningsubmissionEntity cleaning = new CleaningsubmissionEntity();
      cleaning.setMemberid(staffid);
      cleaning.setLocation(location);
      cleaning.setHazardlevel(hazardlevel);
      cleaning.setDescription(description);
      cleaning.setSubmissionstatus(submissionstatus);
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
      TransportationsubmissionEntity transportation = new TransportationsubmissionEntity();
      transportation.setEmployeeid(staffid);
      transportation.setCurrroomnum(currroomnum);
      transportation.setDestroomnum(destroomnum);
      transportation.setReason(reason);
      transportation.setStatus(status);
      session.save(transportation);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
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
