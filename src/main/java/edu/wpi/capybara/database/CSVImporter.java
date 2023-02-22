package edu.wpi.capybara.database;

public interface CSVImporter<T> {

  T fromCSV(String[] csv);
}
