package edu.wpi.capybara.database;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import edu.wpi.capybara.Main;
import edu.wpi.capybara.objects.hibernate.*;
import edu.wpi.capybara.objects.submissions.SubmissionStatus;
import java.io.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DBcsv {

  public static HashMap<String, NodeEntity> nodes;
  public static ArrayList<EdgeEntity> edges;
  public static HashMap<String, LocationnameEntity> locationNames;
  public static ArrayList<MoveEntity> moves;
  public static HashMap<String, StaffEntity> staff;
  public static HashMap<Integer, TransportationsubmissionEntity> transportationSubs;
  public static HashMap<Integer, CleaningsubmissionEntity> cleaningSubs;
  public static HashMap<Integer, SecuritysubmissionEntity> securitySubs;

  public static HashMap<Integer, ComputersubmissionEntity> computerSubs;

  public static HashMap<Integer, AudiosubmissionEntity> audioSubs;

  public DBcsv() {
    nodes = Main.db.getNodes();
    edges = Main.db.getEdges();
    locationNames = Main.db.getLocationnames();
    moves = Main.db.getMoves();
    staff = Main.db.getStaff();
    transportationSubs = Main.db.getTransportationSubs();
    cleaningSubs = Main.db.getCleaningSubs();
    securitySubs = Main.db.getSecuritySubs();
    computerSubs = Main.db.getComputerSubs();
    audioSubs = Main.db.getAudioSubs();
  }

  public static void importAlldbcsv() {
    nodes = Main.db.getNodes();
    edges = Main.db.getEdges();
    locationNames = Main.db.getLocationnames();
    moves = Main.db.getMoves();
    staff = Main.db.getStaff();
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
    String cleaningcsv = backup_folder + "\\" + "cleaningsubmission.csv";
    String transportcsv = backup_folder + "\\" + "transportationsubmission.csv";
    String securitycsv = backup_folder + "\\" + "securitysubmission.csv";
    String computercsv = backup_folder + "\\" + "computersubmission.csv";
    String audiocsv = backup_folder + "\\" + "audiosubmission.csv";

    // Efficient? way to delete the database in the following order: node, edge, locationname, move,
    // staff, and all submissions
    //    String deleteNodes = String.format("delete from node");
    //    Query nodeQuery = Main.db.getSession().createQuery(deleteNodes);
    //    nodeQuery.executeUpdate();
    //
    //    String deleteEdges = String.format("delete from edge");
    //    Query edgeQuery = Main.db.getSession().createQuery(deleteEdges);
    //    edgeQuery.executeUpdate();
    //
    //    String deleteLocation = String.format("delete from locationname");
    //    Query locationQuery = Main.db.getSession().createQuery(deleteLocation);
    //    locationQuery.executeUpdate();
    //
    //    String deleteMove = String.format("delete from move");
    //    Query moveQuery = Main.db.getSession().createQuery(deleteMove);
    //    moveQuery.executeUpdate();
    //
    //    String deleteStaff = String.format("delete from staff");
    //    Query staffQuery = Main.db.getSession().createQuery(deleteStaff);
    //    staffQuery.executeUpdate();
    //
    //    String deleteCleaning = String.format("delete from cleaningsubmission");
    //    Query cleaningQuery = Main.db.getSession().createQuery(deleteCleaning);
    //    cleaningQuery.executeUpdate();
    //
    //    String deleteTransportation = String.format("delete from transportationsubmission");
    //    Query transportationQuery = Main.db.getSession().createQuery(deleteTransportation);
    //    transportationQuery.executeUpdate();
    //
    //    String deleteSecurity = String.format("delete from securitysubmission");
    //    Query securityQuery = Main.db.getSession().createQuery(deleteSecurity);
    //    securityQuery.executeUpdate();

    // An original method to delete the database in the following order: submissions, staff, move,
    // locationname, edge, node

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
      Main.db.deleteNode(nodeEntity.getNodeid());
    }

    // Inserts nodes
    CSVReader nodeReader = new CSVReader(new FileReader(nodecsv));
    List<String[]> nodeBody = nodeReader.readAll();
    for (int i = 0; i < nodeBody.size(); i++) {
      String nodeid = nodeBody.get(i)[0];
      int xcoord = Integer.parseInt(nodeBody.get(i)[1]);
      int ycoord = Integer.parseInt(nodeBody.get(i)[2]);
      String floor = nodeBody.get(i)[3];
      String building = nodeBody.get(i)[4];
      NodeEntity node = new NodeEntity(nodeid, xcoord, ycoord, floor, building);
      Main.db.addNode(node);
    }
    nodeReader.close();

    // Inserts edges
    CSVReader edgeReader = new CSVReader(new FileReader(edgecsv));
    List<String[]> edgeBody = edgeReader.readAll();
    for (int i = 0; i < edgeBody.size(); i++) {
      String node1 = edgeBody.get(i)[0];
      String node2 = edgeBody.get(i)[1];
      EdgeEntity edge = new EdgeEntity(node1, node2);
      Main.db.addEdge(edge);
    }
    edgeReader.close();

    // Inserts locationname
    CSVReader locationReader = new CSVReader(new FileReader(locationnamecsv));
    List<String[]> locationBody = locationReader.readAll();
    for (int i = 0; i < locationBody.size(); i++) {
      String longname = locationBody.get(i)[0];
      String shortname = locationBody.get(i)[1];
      String locationtype = locationBody.get(i)[2];
      LocationnameEntity locationname = new LocationnameEntity(longname, shortname, locationtype);
      Main.db.addLocationname(locationname);
    }
    locationReader.close();

    // Insert move
    CSVReader moveReader = new CSVReader(new FileReader(movecsv));
    List<String[]> moveBody = moveReader.readAll();
    for (int i = 0; i < moveBody.size(); i++) {
      String nodeid = moveBody.get(i)[0];
      String longname = moveBody.get(i)[1];
      Date movedate = Date.valueOf(moveBody.get(i)[2]);
      MoveEntity move = new MoveEntity(nodeid, longname, movedate);
      Main.db.addMove(move);
    }
    moveReader.close();

    // Insert staff
    CSVReader staffReader = new CSVReader(new FileReader(staffcsv));
    List<String[]> staffBody = staffReader.readAll();
    for (int i = 0; i < staffBody.size(); i++) {
      String staffid = staffBody.get(i)[0];
      String firstname = staffBody.get(i)[1];
      String lastname = staffBody.get(i)[2];
      String role = staffBody.get(i)[3];
      String password = staffBody.get(i)[4];
      StaffEntity staff = new StaffEntity(staffid, firstname, lastname, role, password);
      Main.db.addStaff(staff);
    }
    staffReader.close();

    // Insert cleaning
    CSVReader cleaningReader = new CSVReader(new FileReader(cleaningcsv));
    List<String[]> cleaningBody = cleaningReader.readAll();
    for (int i = 0; i < cleaningBody.size(); i++) {

      int submissionid = Integer.parseInt(cleaningBody.get(i)[0]);
      String memberid = cleaningBody.get(i)[1];
      String assignedid = cleaningBody.get(i)[2];
      String location = cleaningBody.get(i)[3];
      String hazardlevel = cleaningBody.get(i)[4];
      String description = cleaningBody.get(i)[5];
      SubmissionStatus submissionstatus = SubmissionStatus.valueOf(cleaningBody.get(i)[6]);
      String urgency = cleaningBody.get(i)[7];

      String startDate = cleaningBody.get(i)[8];
      SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
      java.util.Date date = sdf1.parse(startDate);
      java.sql.Date createdate = new java.sql.Date(date.getTime());

      String startDate2 = cleaningBody.get(i)[9];
      SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
      java.util.Date date2 = sdf2.parse(startDate2);
      java.sql.Date duedate = new java.sql.Date(date2.getTime());

      CleaningsubmissionEntity cleaningsubmission =
          new CleaningsubmissionEntity(
              submissionid,
              memberid,
              assignedid,
              location,
              hazardlevel,
              description,
              submissionstatus,
              urgency,
              createdate,
              duedate);
      Main.db.addCleaning(cleaningsubmission);
    }
    cleaningReader.close();

    // Insert transportation
    CSVReader transportationReader = new CSVReader(new FileReader(transportcsv));
    List<String[]> transportationBody = transportationReader.readAll();
    for (int i = 0; i < transportationBody.size(); i++) {
      int submissionid = Integer.parseInt(transportationBody.get(i)[0]);
      String employeeid = transportationBody.get(i)[1];
      String assignedid = transportationBody.get(i)[2];
      String currroomnum = transportationBody.get(i)[3];
      String destroomnum = transportationBody.get(i)[4];
      String reason = transportationBody.get(i)[5];
      SubmissionStatus status = SubmissionStatus.valueOf(transportationBody.get(i)[6]);
      String urgency = transportationBody.get(i)[7];

      String startDate = transportationBody.get(i)[8];
      SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
      java.util.Date date = sdf1.parse(startDate);
      java.sql.Date createdate = new java.sql.Date(date.getTime());

      String startDate2 = transportationBody.get(i)[9];
      SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
      java.util.Date date2 = sdf2.parse(startDate2);
      java.sql.Date duedate = new java.sql.Date(date2.getTime());

      TransportationsubmissionEntity transportationsubmission =
          new TransportationsubmissionEntity(
              submissionid,
              employeeid,
              assignedid,
              currroomnum,
              destroomnum,
              reason,
              status,
              urgency,
              createdate,
              duedate);
      Main.db.addTransportation(transportationsubmission);
    }
    transportationReader.close();

    CSVReader securityReader = new CSVReader(new FileReader(securitycsv));
    List<String[]> securityBody = securityReader.readAll();
    for (int i = 0; i < securityBody.size(); i++) {

      int submissionid = Integer.parseInt(securityBody.get(i)[0]);
      String employeeid = securityBody.get(i)[1];
      String assignedid = securityBody.get(i)[2];
      String location = securityBody.get(i)[3];
      String type = securityBody.get(i)[4];
      String notesupdate = securityBody.get(i)[5];
      SubmissionStatus submissionstatus = SubmissionStatus.valueOf(securityBody.get(i)[6]);
      String urgency = securityBody.get(i)[7];

      String startDate = securityBody.get(i)[8];
      SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
      java.util.Date date = sdf1.parse(startDate);
      java.sql.Date createdate = new java.sql.Date(date.getTime());

      String startDate2 = securityBody.get(i)[9];
      SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
      java.util.Date date2 = sdf2.parse(startDate2);
      java.sql.Date duedate = new java.sql.Date(date2.getTime());

      SecuritysubmissionEntity securitysubmission =
          new SecuritysubmissionEntity(
              submissionid,
              employeeid,
              assignedid,
              location,
              type,
              notesupdate,
              submissionstatus,
              urgency,
              createdate,
              duedate);
      Main.db.addSecurity(securitysubmission);
    }
    securityReader.close();
  }

  public static void exportDatabase() throws IOException, CsvException {

    DateTimeFormatter dtfDate = DateTimeFormatter.ofPattern("uuuuMMdd");
    LocalDate localDate = LocalDate.now();
    DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("HHmmss");
    LocalTime localTime = LocalTime.now();
    String currDate = dtfDate.format(localDate);
    String currTime = dtfTime.format(localTime);
    String currPath = System.getProperty("user.dir") + "\\";
    String folder = currPath + "database_backup_" + currDate + "_" + currTime;
    new File(folder).mkdirs();

    File nodecsv = new File(folder + "\\" + "node.csv");
    File edgecsv = new File(folder + "\\" + "edge.csv");
    File locationnamecsv = new File(folder + "\\" + "locationname.csv");
    File movecsv = new File(folder + "\\" + "move.csv");
    File staffcsv = new File(folder + "\\" + "staff.csv");
    File transportationsubmissioncsv = new File(folder + "\\" + "transportationsubmission.csv");
    File cleaningsubmissioncsv = new File(folder + "\\" + "cleaningsubmission.csv");
    File securitysubmissioncsv = new File(folder + "\\" + "securitysubmission.csv");

    // File securitysubmissioncsv = new File("C:\\Users\\aidan\\Desktop\\securitysubmission.csv");

    List<String[]> outputNodes = new ArrayList<>();
    List<String[]> outputEdges = new ArrayList<>();
    List<String[]> outputLocationnname = new ArrayList<>();
    List<String[]> outputMoves = new ArrayList<>();
    List<String[]> outputStaff = new ArrayList<>();
    List<String[]> outputTransportationsubs = new ArrayList<>();
    List<String[]> outputCleaningsubs = new ArrayList<>();
    List<String[]> outputSecuritysubs = new ArrayList<>();

    // Create node table
    for (Map.Entry<String, NodeEntity> nodeEntityEntry : nodes.entrySet()) {
      String nodeid = nodeEntityEntry.getValue().getNodeid();
      String xcoord = String.valueOf(nodeEntityEntry.getValue().getXcoord());
      String ycoord = String.valueOf(nodeEntityEntry.getValue().getYcoord());
      String floor = nodeEntityEntry.getValue().getFloor();
      String building = nodeEntityEntry.getValue().getBuilding();

      String[] row = {nodeid, xcoord, ycoord, floor, building};
      outputNodes.add(row);
    }
    CSVWriter nodewriter = new CSVWriter(new FileWriter(nodecsv));
    nodewriter.writeAll(outputNodes);
    nodewriter.flush();
    nodewriter.close();

    // Create edge table
    for (EdgeEntity edgeEntity : edges) {
      String node1 = edgeEntity.getNode1();
      String node2 = edgeEntity.getNode2();
      String[] row = {node1, node2};
      outputEdges.add(row);
    }
    CSVWriter edgewriter = new CSVWriter(new FileWriter(edgecsv));
    edgewriter.writeAll(outputEdges);
    edgewriter.flush();
    edgewriter.close();

    // Create locationname table
    for (Map.Entry<String, LocationnameEntity> locationnameEntityEntry : locationNames.entrySet()) {
      String longname = locationnameEntityEntry.getValue().getLongname();
      String shortname = locationnameEntityEntry.getValue().getShortname();
      String locationtype = locationnameEntityEntry.getValue().getLocationtype();
      String[] row = {longname, shortname, locationtype};
      outputLocationnname.add(row);
    }
    CSVWriter locationwriter = new CSVWriter(new FileWriter(locationnamecsv));
    locationwriter.writeAll(outputLocationnname);
    locationwriter.flush();
    locationwriter.close();

    // Create moves table
    for (MoveEntity moveEntity : moves) {
      String nodeid = moveEntity.getNodeid();
      String longname = moveEntity.getLongname();
      String movedate = String.valueOf(moveEntity.getMovedate());
      String[] row = {nodeid, longname, movedate};
      outputMoves.add(row);
    }
    CSVWriter movewriter = new CSVWriter(new FileWriter(movecsv));
    movewriter.writeAll(outputMoves);
    movewriter.flush();
    movewriter.close();

    // Create staff table
    for (Map.Entry<String, StaffEntity> staffEntityEntry : staff.entrySet()) {
      String staffid = staffEntityEntry.getValue().getStaffid();
      String first = staffEntityEntry.getValue().getFirstname();
      String last = staffEntityEntry.getValue().getLastname();
      String role = staffEntityEntry.getValue().getRole();
      String password = staffEntityEntry.getValue().getPassword();
      String[] row = {staffid, first, last, role, password};
      outputStaff.add(row);
    }
    CSVWriter staffwriter = new CSVWriter(new FileWriter(staffcsv));
    staffwriter.writeAll(outputStaff);
    staffwriter.flush();
    staffwriter.close();

    // Create transportation table
    for (Map.Entry<Integer, TransportationsubmissionEntity> transportationsubmissionEntityEntry :
        transportationSubs.entrySet()) {
      String submissionid =
          String.valueOf(transportationsubmissionEntityEntry.getValue().getSubmissionid());
      String employeeid = transportationsubmissionEntityEntry.getValue().getEmployeeid();
      String assignedid = transportationsubmissionEntityEntry.getValue().getAssignedid();
      String currroomnum = transportationsubmissionEntityEntry.getValue().getCurrroomnum();
      String destroomnum = transportationsubmissionEntityEntry.getValue().getDestroomnum();
      String reason = transportationsubmissionEntityEntry.getValue().getReason();
      String status = String.valueOf(transportationsubmissionEntityEntry.getValue().getStatus());
      String urgency = transportationsubmissionEntityEntry.getValue().getUrgency();
      String createdate =
          String.valueOf(transportationsubmissionEntityEntry.getValue().getCreatedate());
      String duedate = String.valueOf(transportationsubmissionEntityEntry.getValue().getDuedate());
      String[] row = {
        submissionid,
        employeeid,
        assignedid,
        currroomnum,
        destroomnum,
        reason,
        status,
        urgency,
        createdate,
        duedate
      };
      outputTransportationsubs.add(row);
    }
    CSVWriter transportswriter = new CSVWriter(new FileWriter(transportationsubmissioncsv));
    transportswriter.writeAll(outputTransportationsubs);
    transportswriter.flush();
    transportswriter.close();

    // Create cleaning table
    for (Map.Entry<Integer, CleaningsubmissionEntity> cleaningsubmissionEntityEntry :
        cleaningSubs.entrySet()) {
      String submissionid =
          String.valueOf(cleaningsubmissionEntityEntry.getValue().getSubmissionid());
      String memberid = cleaningsubmissionEntityEntry.getValue().getMemberid();
      String assignedid = cleaningsubmissionEntityEntry.getValue().getAssignedid();
      String location = cleaningsubmissionEntityEntry.getValue().getLocation();
      String hazardlevel = cleaningsubmissionEntityEntry.getValue().getHazardlevel();
      String description = cleaningsubmissionEntityEntry.getValue().getDescription();
      String status =
          String.valueOf(cleaningsubmissionEntityEntry.getValue().getSubmissionstatus());
      String urgency = cleaningsubmissionEntityEntry.getValue().getUrgency();
      String createdate = String.valueOf(cleaningsubmissionEntityEntry.getValue().getCreatedate());
      String duedate = String.valueOf(cleaningsubmissionEntityEntry.getValue().getDuedate());
      String[] row = {
        submissionid,
        memberid,
        assignedid,
        location,
        hazardlevel,
        description,
        status,
        urgency,
        createdate,
        duedate
      };
      outputCleaningsubs.add(row);
    }
    CSVWriter cleaningswriter = new CSVWriter(new FileWriter(cleaningsubmissioncsv));
    cleaningswriter.writeAll(outputCleaningsubs);
    cleaningswriter.flush();
    cleaningswriter.close();

    // Create security table
    for (Map.Entry<Integer, SecuritysubmissionEntity> securitysubmissionEntityEntry :
        securitySubs.entrySet()) {
      String submissionid =
          String.valueOf(securitysubmissionEntityEntry.getValue().getSubmissionid());
      String employeeid = securitysubmissionEntityEntry.getValue().getEmployeeid();
      String assignedid = securitysubmissionEntityEntry.getValue().getAssignedid();
      String location = securitysubmissionEntityEntry.getValue().getLocation();
      String type = securitysubmissionEntityEntry.getValue().getType();
      String notesupdate = securitysubmissionEntityEntry.getValue().getNotesupdate();
      String status =
          String.valueOf(securitysubmissionEntityEntry.getValue().getSubmissionstatus());
      String urgency = securitysubmissionEntityEntry.getValue().getUrgency();
      String createdate = String.valueOf(securitysubmissionEntityEntry.getValue().getCreatedate());
      String duedate = String.valueOf(securitysubmissionEntityEntry.getValue().getDuedate());
      String[] row = {
        submissionid,
        employeeid,
        assignedid,
        location,
        type,
        notesupdate,
        status,
        urgency,
        createdate,
        duedate
      };
      outputSecuritysubs.add(row);
    }
    CSVWriter securitywriter = new CSVWriter(new FileWriter(securitysubmissioncsv));
    securitywriter.writeAll(outputSecuritysubs);
    securitywriter.flush();
    securitywriter.close();

    // Create computer

    // Create audio
  }

  public static void main(String[] args) throws IOException, CsvException, ParseException {

    Main.db = new newDBConnect();
    importAlldbcsv();


    String backup_folder =
        "C:\\Users\\aidan\\Desktop\\CS3733-Iteration-2\\database_backup_20230214_194316";

    //    exportDatabase();
    //
    //    StaffEntity test = new StaffEntity("654321", "Hugh", "Jass", "654321", "staff");
    //    StaffEntity test1 = new StaffEntity("765432", "Heywood", "Jablowme", "765432", "staff");
    //    Main.db.addStaff(test);
    //    Main.db.addStaff(test1);

    importDatabase(backup_folder);
  }
}
