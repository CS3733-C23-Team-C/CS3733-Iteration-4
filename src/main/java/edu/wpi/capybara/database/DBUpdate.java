package edu.wpi.teamname.database;

import static edu.wpi.teamname.database.DatabaseConnect.connection;

import java.sql.SQLException;
import java.sql.Statement;

class DBUpdate {
  static void update(String table, String pk, String attribute, String value, String pkType) {

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

  static void delete(String table, String pk, String pkType) {
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
}
