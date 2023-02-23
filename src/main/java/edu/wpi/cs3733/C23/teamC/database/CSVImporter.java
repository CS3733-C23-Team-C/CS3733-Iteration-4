package edu.wpi.cs3733.C23.teamC.database;

public interface CSVImporter<T> {

  T fromCSV(String[] csv);
}
