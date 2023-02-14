package edu.wpi.capybara.database;

import edu.wpi.capybara.objects.hibernate.*;

import java.util.ArrayList;
import java.util.HashMap;

public interface RepoFacade {
    //Get all methods
    //Submissions
    HashMap<Integer, AudiosubmissionEntity> getAudioSubs();
    HashMap<Integer, CleaningsubmissionEntity> getCleaningSubs();
    HashMap<Integer, ComputersubmissionEntity> getComputerSubs();
    HashMap<Integer, SecuritysubmissionEntity> getSecuritySubs();
    HashMap<Integer, TransportationsubmissionEntity> getTransportationSubs();

    //Others
    HashMap<String, LocationnameEntity> getLocationnames();
    HashMap<String, NodeEntity> getNodes();
    HashMap<String, StaffEntity> getStaff();
    ArrayList<EdgeEntity> getEdges();
    ArrayList<MoveEntity> getMoves();


    //Add method
    //Submissions
    void addAudio(AudiosubmissionEntity submission);
    void addCleaning(CleaningsubmissionEntity submission);
    void addComputer(ComputersubmissionEntity submission);
    void addSecurity(SecuritysubmissionEntity submission);
    void addTransportation(TransportationsubmissionEntity submission);

    //Others
    void addLocationname(LocationnameEntity submission);
    void addNode(NodeEntity submission);
    void addStaff(StaffEntity submission);
    void addEdge(EdgeEntity submission);
    void addMove(MoveEntity submission);


    //Get single methods
    //Submissions
    AudiosubmissionEntity getAudio(int id);
    CleaningsubmissionEntity getCleaning(int id);
    ComputersubmissionEntity getComputer(int id);
    SecuritysubmissionEntity getSecurity(int id);
    TransportationsubmissionEntity getTransportation(int id);

    //Others
    LocationnameEntity getLocationname(String longname);
    NodeEntity getNode(String nodeid);
    StaffEntity getStaff(String staffid);

    //Submission methods
    StaffEntity getStaff(String Staffid, String password);
}
