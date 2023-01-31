package edu.wpi.capybara.database;

import edu.wpi.capybara.objects.Edge;
import edu.wpi.capybara.objects.Node;
import edu.wpi.capybara.objects.locationname;
import edu.wpi.capybara.objects.move;
import java.sql.*;
import java.util.*;

public class DatabaseConnect {
  static Connection connection;
  public static HashMap<String, Node> nodes;
  public static HashMap<String, Edge> edges;
  public static HashMap<String, locationname> locationNames;
  public static HashMap<String, move> moves;

  static Scanner in = new Scanner(System.in);

  public static void main(String args[]) {
    connect();
    // connect();
    // test();
    // query();
    // importData();
    // menu();
    try {
      connection.close(); // close the connection
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  public static void deleteNode(String nodeID) {
    nodes.remove(nodeID);
  }

  public static void deleteEdge(String edgeID) {
    edges.remove(edgeID);
  }

  public static void menu() {
    boolean cont = true;
    do {
      System.out.println("Select an option: ");
      System.out.println("1. Display node and edge information");
      System.out.println("2. Update node coordinates");
      System.out.println("3. Update name of location node");
      System.out.println("4. Delete a node or an edge");
      System.out.println("5. Display Help on how to use the database program");
      System.out.println("6. Exit the program");
      String option = in.nextLine();

      if (option.equals("1")) {
        System.out.println("Do you want to display a 1. node or 2. edge?");
        option = in.nextLine();

        if (option.equals("1")) {
          System.out.println("enter the nodeid of the edge you want to display");
          String nodeid = in.nextLine();
          System.out.println(nodes.get(nodeid));
        } else if (option.equals("2")) {
          System.out.println("enter the edgeid of the edge you want to display");
          String edgeid = in.nextLine();
          System.out.println(edges.get(edgeid));
        } else {
          System.out.println("invalid option");
        }
      } else if (option.equals("2")) {
        System.out.println("do you want to update 1. xcoord 2. ycoord 3. both");
        option = in.nextLine();
        System.out.println("enter the id of the node you want to update");
        String nodeid = in.nextLine();
        if (option.equals("1") || option.equals("3")) {
          System.out.println("enter the new xcoord value");
          String xcoord = in.nextLine();
          nodes.get(nodeid).setXCoord(Integer.valueOf(xcoord));
        }
        if (option.equals("2") || option.equals("3")) {
          System.out.println("enter the new ycoord value");
          String ycoord = in.nextLine();
          nodes.get(nodeid).setYCoord(Integer.valueOf(ycoord));
        }
      } else if (option.equals("3")) {
        System.out.println("do you want to update 1. building 2. floor 3. both");
        option = in.nextLine();
        System.out.println("enter the id of the node you want to update");
        String nodeid = in.nextLine();
        if (option.equals("1") || option.equals("3")) {
          System.out.println("enter the new building value");
          String building = in.nextLine();
          nodes.get(nodeid).setBuilding(building);
        }
        if (option.equals("2") || option.equals("3")) {
          System.out.println("enter the new floor value");
          String floor = in.nextLine();
          nodes.get(nodeid).setFloor(floor);
        }
      } else if (option.equals("4")) {
        System.out.println("Do you want to delete a 1. node or 2. edge?");
        option = in.nextLine();

        if (option.equals("1")) {
          System.out.println("enter the nodeid of the node you want to delete");
          String nodeid = in.nextLine();
          nodes.get(nodeid).deleteNode();
        } else if (option.equals("2")) {
          System.out.println("enter the edgeid of the edge you want to delete");
          String edgeid = in.nextLine();
          // edges.get(edgeid).deleteEdge();
        } else {
          System.out.println("invalid option");
        }
      } else if (option.equals("5")) {
        System.out.println(
            "Hello dear users of our database!\n"
                + "If you want to see the information of a specific node or edge, enter '1'. Then, enter '1' if you "
                + "want to see a node, or '2' if you want to see an edge. After that, enter the nodeID or the "
                + "edgeID of a desired node/edge.\n"
                + "If you want to update the coordinates of the node, press '2'. Then, press '1' if you want to "
                + "update the X coordinate; press '2' if you want to update the Y coordinate, and press '3' if you "
                + "want to update both. After that, enter the nodeID. Lastly, you should be able to enter the "
                + "desired values.\n"
                + "If you want to update the location of the node, enter '3' and enter the nodeID. Then, enter '1' "
                + "if you want to update the building; '2' if you want to update the floor; '3' if you want to "
                + "update both. After that, enter the desired building and/or floor.\n"
                + "If you want to delete a node or an edge, enter '4'. Then, enter '1' if you want to delete a node "
                + "; enter '2' if you want to delete an edge. After that, type the nodeID/edgeID to delete the "
                + "desired node/edge.\n"
                + "If you want to exit the program, enter '6'.");
        System.out.println("enter any character to continue");
        in.nextLine();
      } else if (option.equals("6")) {
        cont = false;
      } else {
        System.out.println("Invalid input");
      }
    } while (cont);
  }

  public static void connect() {
    connection = null;
    // connecting to the database
    try {
      Class.forName("org.postgresql.Driver"); // driver
      connection =
          DriverManager.getConnection(
              "jdbc:postgresql://wpi-softeng-postgres-db.coyfss2f91ba.us-east-1.rds.amazonaws.com:2112/dbc",
              "teamc",
              "rKh6XMtoclLOTGqPJ66qJxacUlgYwbqU"); // create the connection
    } catch (ClassNotFoundException | SQLException e) {
      System.out.println(e);
    }
    if (connection != null) {
      System.out.println("Connection success");
    } else {
      System.out.println("Connection failed");
    }
  }

  public static void importData() {
    importNodes();
    importEdges();
    importLocationName();
    importMoves(); // String is the ID
  }

  private static void importNodes() {
    nodes = new HashMap<String, Node>();

    try {
      Statement stmt = connection.createStatement(); // initialize the statement
      String query = "SELECT * FROM nodes"; // write out the query as a string
      ResultSet rset = stmt.executeQuery(query); // run the query

      String name;
      while (rset.next()) { // loop through the table
        nodes.put(
            rset.getString("nodeid"),
            new Node(
                rset.getString("nodeid"),
                rset.getInt("xcoord"),
                rset.getInt("ycoord"),
                rset.getString("floor"),
                rset.getString("building")));
      }
      rset.close();
      stmt.close();
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  // NOTE: YOU MUST RUN THIS AFTER IMPORTING THE NODES
  private static void importEdges() {
    edges = new HashMap<String, Edge>();

    try {
      Statement stmt = connection.createStatement(); // initialize the statement
      String query = "SELECT * FROM edges"; // write out the query as a string
      ResultSet rset = stmt.executeQuery(query); // run the query

      String name;
      while (rset.next()) { // loop through the table
        edges.put(
            rset.getString("startnode") + rset.getString("endnode"),
            new Edge(rset.getString("startnode"), rset.getString("endnode")));
      }
      rset.close();
      stmt.close();
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  private static void importLocationName() {
    locationNames = new HashMap<String, locationname>();

    try {
      Statement stmt = connection.createStatement(); // initialize the statement
      String query = "SELECT * FROM locationname"; // write out the query as a string
      ResultSet rset = stmt.executeQuery(query); // run the query

      String name;
      while (rset.next()) { // loop through the table
        locationNames.put(
            rset.getString("longname"),
            new locationname(
                rset.getString("longname"),
                rset.getString("shortname"),
                rset.getString("locationtype")));
      }
      rset.close();
      stmt.close();
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  private static void importMoves() {
    moves = new HashMap<String, move>();

    try {
      Statement stmt = connection.createStatement(); // initialize the statement
      String query = "SELECT * FROM move"; // write out the query as a string
      ResultSet rset = stmt.executeQuery(query); // run the query

      String name;
      while (rset.next()) { // loop through the table
        moves.put(
            rset.getString(
                    rset.getString("nodeid")
                        + rset.getString("longname")
                        + rset.getDate("movedate"))
                .toString(),
            new move(
                rset.getString("nodeid"), rset.getString("longname"), rset.getDate("movedate")));
      }
      rset.close();
      stmt.close();
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  //  public static void test() {
  //    Node temp =
  //        new Node(
  //            "CCONF001L1",
  //            2255,
  //            849,
  //            "L1",
  //            "45 Francis",
  //            "CONF",
  //            "Anesthesia Conf Floor L1",
  //            "Conf C001L1");
  //
  //    // Xcoord
  //    System.out.println(temp.getXCoord());
  //    temp.setXCoord(12);
  //    System.out.println(temp.getXCoord());
  //    temp.setXCoord(2255);
  //    System.out.println(temp.getXCoord());
  //    System.out.println();
  //
  //    // Ycoord
  //    System.out.println(temp.getYCoord());
  //    temp.setYCoord(11);
  //    System.out.println(temp.getYCoord());
  //    temp.setYCoord(849);
  //    System.out.println(temp.getYCoord());
  //    System.out.println();
  //
  //    // floor
  //    System.out.println(temp.getFloor());
  //    temp.setFloor("L2");
  //    System.out.println(temp.getFloor());
  //    temp.setFloor("L1");
  //    System.out.println(temp.getFloor());
  //    System.out.println();
  //
  //    // building
  //    System.out.println(temp.getBuilding());
  //    temp.setBuilding("100 Institute");
  //    System.out.println(temp.getBuilding());
  //    temp.setBuilding("45 Francis");
  //    System.out.println(temp.getBuilding());
  //    System.out.println();
  //
  //    // nodetype
  //    System.out.println(temp.getNodeType());
  //    temp.setNodeType("ELEV");
  //    System.out.println(temp.getNodeType());
  //    temp.setNodeType("CONF");
  //    System.out.println(temp.getNodeType());
  //    System.out.println();
  //
  //    // longname
  //    System.out.println(temp.getLongName());
  //    temp.setLongName("Worcester Polytechnic Institute");
  //    System.out.println(temp.getLongName());
  //    temp.setLongName("Anesthesia Conf Floor L1");
  //    System.out.println(temp.getLongName());
  //    System.out.println();
  //
  //    // shortname
  //    System.out.println(temp.getShortName());
  //    temp.setShortName("WPI");
  //    System.out.println(temp.getShortName());
  //    temp.setShortName("Conf C001L1");
  //    System.out.println(temp.getShortName());
  //    System.out.println();
  //  }

  public static void query() {
    try {
      Statement stmt = connection.createStatement(); // initialize the statement
      String query = "SELECT * FROM l1nodes"; // write out the query as a string
      ResultSet rset = stmt.executeQuery(query); // run the query

      String name;
      while (rset.next()) { // loop through the table
        name = rset.getString("nodeid"); // get value stored in collumn "name" for current row
        System.out.println(name);
      }
      rset.close();
      stmt.close();
    } catch (SQLException e) {
      System.out.println(e);
    }
  }
}
