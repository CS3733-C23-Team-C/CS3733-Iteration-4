package edu.wpi.cs3733.C23.teamC.database;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import edu.wpi.cs3733.C23.teamC.Main;
import edu.wpi.cs3733.C23.teamC.database.hibernate.*;
import java.io.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DBcsv {

  public static Map<String, NodeEntity> nodes;
  public static List<EdgeEntity> edges;
  public static Map<String, LocationnameEntity> locationNames;
  public static List<MoveEntity> moves;
  public static Map<String, StaffEntity> staff;
  public static Map<Integer, AlertEntity> alert;
  public static List<AlertStaff> alertstaff;
  public static Map<Integer, TransportationsubmissionEntity> transportationSubs;
  public static Map<Integer, CleaningsubmissionEntity> cleaningSubs;
  public static Map<Integer, SecuritysubmissionEntity> securitySubs;
  public static Map<Integer, ComputersubmissionEntity> computerSubs;
  public static Map<Integer, AudiosubmissionEntity> audioSubs;

  public DBcsv() {
    nodes = Main.db.getNodes();
    edges = Main.db.getEdges();
    locationNames = Main.db.getLocationnames();
    moves = Main.db.getMoves();
    staff = Main.db.getStaff();
    alert = Main.db.getAlerts();
    alertstaff = Main.db.getAlertStaff();
    transportationSubs = Main.db.getTransportationSubs();
    cleaningSubs = Main.db.getCleaningSubs();
    securitySubs = Main.db.getSecuritySubs();
    computerSubs = Main.db.getComputerSubs();
    audioSubs = Main.db.getAudioSubs();
  }

  public static void importAlldbcsv() {
    Main.db.importAll();
    nodes = Main.db.getNodes();
    edges = Main.db.getEdges();
    locationNames = Main.db.getLocationnames();
    moves = Main.db.getMoves();
    staff = Main.db.getStaff();
    alert = Main.db.getAlerts();
    alertstaff = Main.db.getAlertStaff();
    transportationSubs = Main.db.getTransportationSubs();
    cleaningSubs = Main.db.getCleaningSubs();
    securitySubs = Main.db.getSecuritySubs();
    computerSubs = Main.db.getComputerSubs();
    audioSubs = Main.db.getAudioSubs();
  }

  public static void importDatabase(String backup_folder)
      throws IOException, CsvException, ParseException {

    String nodecsv = backup_folder + "\\" + "node.csv";
    String edgecsv = backup_folder + "\\" + "edge.csv";
    String locationnamecsv = backup_folder + "\\" + "locationname.csv";
    String movecsv = backup_folder + "\\" + "move.csv";
    String staffcsv = backup_folder + "\\" + "staff.csv";
    String alertcsv = backup_folder + "\\" + "alert.csv";
    String alertstaffcsv = backup_folder + "\\" + "alertstaff.csv";
    String cleaningcsv = backup_folder + "\\" + "cleaningsubmission.csv";
    String transportcsv = backup_folder + "\\" + "transportationsubmission.csv";
    String securitycsv = backup_folder + "\\" + "securitysubmission.csv";
    String computercsv = backup_folder + "\\" + "computersubmission.csv";
    String audiocsv = backup_folder + "\\" + "audiosubmission.csv";

    // Read nodes
    CSVReader nodeReader = new CSVReader(new FileReader(nodecsv));
    List<String[]> nodeBody = nodeReader.readAll();
    nodeReader.close();
    System.out.println("Read nodes");

    // Read edges
    CSVReader edgeReader = new CSVReader(new FileReader(edgecsv));
    List<String[]> edgeBody = edgeReader.readAll();
    edgeReader.close();

    // Read locations
    CSVReader locationReader = new CSVReader(new FileReader(locationnamecsv));
    List<String[]> locationBody = locationReader.readAll();
    locationReader.close();

    // Read moves
    CSVReader moveReader = new CSVReader(new FileReader(movecsv));
    List<String[]> moveBody = moveReader.readAll();
    moveReader.close();

    // Read staff
    CSVReader staffReader = new CSVReader(new FileReader(staffcsv));
    List<String[]> staffBody = staffReader.readAll();
    staffReader.close();

    // Read alert
    CSVReader alertReader = new CSVReader(new FileReader(alertcsv));
    List<String[]> alertBody = alertReader.readAll();
    alertReader.close();
    System.out.println("Read alert");

    // Read alertsaff
    CSVReader alertStaffReader = new CSVReader(new FileReader(alertstaffcsv));
    List<String[]> alertStaffBody = alertStaffReader.readAll();
    alertStaffReader.close();

    // Read cleaning
    CSVReader cleaningReader = new CSVReader(new FileReader(cleaningcsv));
    List<String[]> cleaningBody = cleaningReader.readAll();
    cleaningReader.close();

    // Read transportation
    CSVReader transportationReader = new CSVReader(new FileReader(transportcsv));
    List<String[]> transportationBody = transportationReader.readAll();
    transportationReader.close();

    // Read security
    CSVReader securityReader = new CSVReader(new FileReader(securitycsv));
    List<String[]> securityBody = securityReader.readAll();
    securityReader.close();

    // Read computer
    CSVReader computerReader = new CSVReader(new FileReader(computercsv));
    List<String[]> computerBody = computerReader.readAll();
    computerReader.close();

    // Read audio
    CSVReader audioReader = new CSVReader(new FileReader(audiocsv));
    List<String[]> audioBody = audioReader.readAll();
    audioReader.close();

    // Deletes the database
    List<ComputersubmissionEntity> computerKeys = List.copyOf(computerSubs.values());
    for (ComputersubmissionEntity computersubmissionEntity : computerKeys) {
      Main.db.deleteComputer(computersubmissionEntity.getSubmissionid());
    }

    List<AudiosubmissionEntity> audioKeys = List.copyOf(audioSubs.values());
    for (AudiosubmissionEntity audiosubmissionEntity : audioKeys) {
      Main.db.deleteAudio(audiosubmissionEntity.getSubmissionid());
    }

    List<TransportationsubmissionEntity> transportKeys = List.copyOf(transportationSubs.values());
    for (TransportationsubmissionEntity transportationsubmissionEntity : transportKeys) {
      Main.db.deleteTransportation(transportationsubmissionEntity.getSubmissionid());
    }

    List<SecuritysubmissionEntity> securityKeys = List.copyOf(securitySubs.values());
    for (SecuritysubmissionEntity securitysubmissionEntity : securityKeys) {
      Main.db.deleteSecurity(securitysubmissionEntity.getSubmissionid());
    }

    List<CleaningsubmissionEntity> cleaningKeys = List.copyOf(cleaningSubs.values());
    for (CleaningsubmissionEntity cleaningsubmissionEntity : cleaningKeys) {
      Main.db.deleteCleaning(cleaningsubmissionEntity.getSubmissionid());
    }

    Main.db.deleteAlertStaff();

    List<AlertEntity> alertKeys = List.copyOf(alert.values());
    for (AlertEntity alertEntity : alertKeys) {
      Main.db.deleteAlert(alertEntity);
    }

    List<StaffEntity> staffKeys = List.copyOf(staff.values());
    for (StaffEntity staffEntity : staffKeys) {
      Main.db.deleteStaff(staffEntity.getStaffid());
    }

    List<MoveEntity> moveCopy = List.copyOf(moves);
    for (MoveEntity moveEntity : moveCopy) {
      Main.db.deleteMove(moveEntity);
    }

    List<LocationnameEntity> locationKeys = List.copyOf(locationNames.values());
    for (LocationnameEntity locationnameEntity : locationKeys) {
      Main.db.deleteLocationname(locationnameEntity.getLongname());
    }

    List<EdgeEntity> edgeCopy = List.copyOf(edges);
    for (EdgeEntity edgeEntity : edgeCopy) {
      Main.db.deleteEdge(edgeEntity);
    }

    List<NodeEntity> nodeKeys = List.copyOf(nodes.values());
    for (NodeEntity nodeEntity : nodeKeys) {
      Main.db.deleteNode(nodeEntity.getNodeID());
    }

    // Inserts nodes
    csv2DB(nodeBody, new NodeEntity.Importer()).forEach(Main.db::addNode);

    // Inserts edges
    csv2DB(edgeBody, new EdgeEntity.Importer()).forEach(Main.db::addEdge);

    // Inserts locationname
    csv2DB(locationBody, new LocationnameEntity.Importer()).forEach(Main.db::addLocationname);

    // Insert move
    csv2DB(moveBody, new MoveEntity.Importer()).forEach(Main.db::addMove);

    // Insert staff
    csv2DB(staffBody, new StaffEntity.Importer()).forEach(Main.db::addStaff);

    // Insert alert
    csv2DB(alertBody, new AlertEntity.Importer()).forEach(Main.db::addAlert);

    // Insert alertstaff
    csv2DB(alertStaffBody, new AlertStaff.Importer());

    // Insert cleaning
    csv2DB(cleaningBody, new CleaningsubmissionEntity.Importer()).forEach(Main.db::addCleaning);

    // Insert transportation
    csv2DB(transportationBody, new TransportationsubmissionEntity.Importer())
        .forEach(Main.db::addTransportation);

    // Insert security
    csv2DB(securityBody, new SecuritysubmissionEntity.Importer()).forEach(Main.db::addSecurity);

    // Insert computer
    csv2DB(computerBody, new ComputersubmissionEntity.Importer()).forEach(Main.db::addComputer);

    // Insert audio
    csv2DB(audioBody, new AudiosubmissionEntity.Importer()).forEach(Main.db::addAudio);
  }

  public static void exportDatabase(String path) throws IOException, CsvException {

    DateTimeFormatter dtfDate = DateTimeFormatter.ofPattern("uuuuMMdd");
    LocalDate localDate = LocalDate.now();
    DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("HHmmss");
    LocalTime localTime = LocalTime.now();
    String currDate = dtfDate.format(localDate);
    String currTime = dtfTime.format(localTime);
    String currPath = path + "\\";
    String folder = currPath + "database_backup_" + currDate + "_" + currTime;
    new File(folder).mkdirs();

    File nodecsv = new File(folder + "\\" + "node.csv");
    File edgecsv = new File(folder + "\\" + "edge.csv");
    File locationnamecsv = new File(folder + "\\" + "locationname.csv");
    File movecsv = new File(folder + "\\" + "move.csv");
    File staffcsv = new File(folder + "\\" + "staff.csv");
    File alertcsv = new File(folder + "\\" + "alert.csv");
    File alertstaffcsv = new File(folder + "\\" + "alertstaff.csv");
    File transportationsubmissioncsv = new File(folder + "\\" + "transportationsubmission.csv");
    File cleaningsubmissioncsv = new File(folder + "\\" + "cleaningsubmission.csv");
    File securitysubmissioncsv = new File(folder + "\\" + "securitysubmission.csv");
    File computersubmissioncsv = new File(folder + "\\" + "computersubmission.csv");
    File audiosubmissioncsv = new File(folder + "\\" + "audiosubmission.csv");

    // Create node
    writeCSV(nodes.values(), nodecsv);

    // Create edge
    writeCSV(edges, edgecsv);

    // Create locationname
    writeCSV(locationNames.values(), locationnamecsv);

    // Create moves
    writeCSV(moves, movecsv);

    // Create staff
    writeCSV(staff.values(), staffcsv);

    // Create alert
    writeCSV(alert.values(), alertcsv);

    // Create alert staff
    writeCSV(alertstaff, alertstaffcsv);

    // Create transportation
    writeCSV(transportationSubs.values(), transportationsubmissioncsv);

    // Create cleaning table
    writeCSV(cleaningSubs.values(), cleaningsubmissioncsv);

    // Create security
    writeCSV(securitySubs.values(), securitysubmissioncsv);

    // Create computer
    writeCSV(computerSubs.values(), computersubmissioncsv);

    // Create audio
    writeCSV(audioSubs.values(), audiosubmissioncsv);
  }

  private static <T> List<T> csv2DB(List<String[]> csv, CSVImporter<T> converter) {
    return csv.stream().map(converter::fromCSV).toList();
  }

  public static List<String[]> db2CSV(Collection<? extends CSVExportable> toExport) {
    return toExport.stream().map(CSVExportable::toCSV).toList();
  }

  private static void writeCSV(Collection<? extends CSVExportable> toExport, File outputFile)
      throws IOException {
    final var csvList = db2CSV(toExport);
    final var writer = new CSVWriter(new FileWriter(outputFile));
    writer.writeAll(csvList);
    writer.flush();
    writer.close();
  }

  public static void main(String[] args) throws IOException, CsvException, ParseException {

    /*
       Main.setOrm(new DAOService());
       Main.setRepo(new DatabaseService(Main.getOrm()));
       Main.db = new RepoFacadeAdapter(Main.getRepo());
       importAlldbcsv();

       String backup_folder =
           "C:\\Users\\aidan\\Desktop\\CS3733-Iteration-3\\database_backup_20230220_201715";

       // exportDatabase();
       importDatabase(backup_folder);

    */
  }
}
