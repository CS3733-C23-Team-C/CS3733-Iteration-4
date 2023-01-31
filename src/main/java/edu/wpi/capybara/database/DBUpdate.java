package edu.wpi.capybara.database;

import static edu.wpi.capybara.database.DatabaseConnect.connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUpdate {
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
}
