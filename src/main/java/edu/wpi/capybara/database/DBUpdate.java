package edu.wpi.capybara.database;

import static edu.wpi.capybara.database.DatabaseConnect.connection;

import edu.wpi.capybara.objects.submissions.cleaningSubmission;
import edu.wpi.capybara.objects.submissions.transportationSubmission;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class DBUpdate {

  public static void insertTransportation(
      String id, String curRoom, String dest, String reason, String status) {
    try {
      Statement stmt = connection.createStatement();
      String insert =
          "INSERT INTO transportationsubmission (employeeid, currroomnum, destroomnum, reason, status) VALUES "
              + "('"
              + id
              + "', '"
              + curRoom
              + "', '"
              + dest
              + "', '"
              + reason
              + "', '"
              + status
              + "')";
      System.out.println(insert);
      stmt.execute(insert);
      stmt.close();
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  public static void insertCleaning(
      String id,
      String location,
      String hazard,
      String description,
      cleaningSubmission.submissionStatus status) {
    try {
      Statement stmt = connection.createStatement();
      String insert =
          "INSERT INTO cleaningsubmission (memberid, location, hazardlevel, description, submissionstatus) VALUES "
              + "('"
              + id
              + "', '"
              + location
              + "', '"
              + hazard
              + "', '"
              + description
              + "', '"
              + status
              + "')";
      System.out.println(insert);
      stmt.execute(insert);
      stmt.close();
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  public static void update(
      String table, String pk, String attribute, String value, String pkType) {

    try {
      Statement stmt = connection.createStatement();
      String insert =
          "UPDATE " + table + " SET " + attribute + " = " + value + " WHERE " + pkType + " = '" + pk
              + "'";
      System.out.println(insert);
      stmt.execute(insert);
      stmt.close();
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  public static void update(
      String table,
      String pk1,
      String pk2,
      String attribute,
      String value,
      String pkType1,
      String pkType2) {

    try {
      Statement stmt = connection.createStatement();
      String insert =
          "UPDATE " + table + " SET " + attribute + " = " + value + " WHERE " + pkType1 + " = '"
              + pk1 + "' & '" + pkType2 + " = '" + pk2 + "'";
      System.out.println(insert);
      stmt.execute(insert);
      stmt.close();
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  public static void update(
          String table,
          String pk1,
          String pk2,
          String pk3,
          String attribute,
          String value,
          String pkType1,
          String pkType2,
          String pkType3) {

    try {
      Statement stmt = connection.createStatement();
      String insert =
              "UPDATE " + table + " SET " + attribute + " = " + value + " WHERE " + pkType1 + " = '"
                      + pk1 + "' & '" + pkType2 + " = '" + pk2 + "' & '" + pkType3 + " = '"
                      + pk3 + "'";
      System.out.println(insert);
      stmt.execute(insert);
      stmt.close();
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  public static void delete(String table, String pk, String pkType) {
    try {
      Statement stmt = connection.createStatement();
      String delete = "DELETE FROM " + table + " WHERE " + pkType + " = '" + pk + "'";
      System.out.println(delete);
      stmt.execute(delete);
      stmt.close();
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  public static ResultSet edges(String curNode) {
    try {
      Statement stmt = connection.createStatement();
      String edges =
          "SELECT * FROM edge WHERE node1 = '" + curNode + "' OR node2 = '" + curNode + "'";
      ResultSet rset = stmt.executeQuery(edges);
      System.out.println(edges);
      stmt.execute(edges);
      stmt.close();
      return rset;
    } catch (SQLException e) {
      System.out.println(e);
    }
    return null;
  }

  public static LinkedList<transportationSubmission> transports() {
    try {
      Statement stmt = connection.createStatement();
      String subs = "SELECT * FROM transportationsubmission";
      ResultSet rset = stmt.executeQuery(subs);
      System.out.println(subs);
      LinkedList<transportationSubmission> ret = new LinkedList<transportationSubmission>();
      while (rset.next()) { // loop through the table
        ret.add(
            new transportationSubmission(
                rset.getString("employeeid"),
                rset.getString("currroomnum"),
                rset.getString("destroomnum"),
                rset.getString("reason"),
                transportationSubmission.submissionStatus.valueOf(rset.getString("status"))));
      }
      stmt.close();
      return ret;
    } catch (SQLException e) {
      System.out.println(e);
    }
    return null;
  }

  public static LinkedList<cleaningSubmission> cleanings() {
    try {
      Statement stmt = connection.createStatement();
      String subs = "SELECT * FROM cleaningsubmission";
      ResultSet rset = stmt.executeQuery(subs);
      System.out.println(subs);
      LinkedList<cleaningSubmission> ret = new LinkedList<cleaningSubmission>();
      while (rset.next()) { // loop through the table
        ret.add(
            new cleaningSubmission(
                rset.getString("memberid"),
                rset.getString("location"),
                rset.getString("hazardlevel"),
                rset.getString("description"),
                cleaningSubmission.submissionStatus.valueOf(
                    rset.getString("submissionstatus")))); // currently not bringing in status
      }
      stmt.close();
      return ret;
    } catch (SQLException e) {
      System.out.println(e);
    }
    return null;
  }
}
