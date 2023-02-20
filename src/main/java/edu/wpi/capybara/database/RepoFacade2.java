package edu.wpi.capybara.database;

import edu.wpi.capybara.objects.hibernate.*;
import edu.wpi.capybara.objects.orm.Edge;
import edu.wpi.capybara.objects.orm.Location;
import edu.wpi.capybara.objects.orm.Move;
import edu.wpi.capybara.objects.orm.Node;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyMapProperty;
import org.hibernate.Session;

import java.util.UUID;

public interface RepoFacade2 {
    // Get all methods
    // Submissions
    ReadOnlyMapProperty<Integer, AudiosubmissionEntity> getAudioSubs();

    ReadOnlyMapProperty<Integer, CleaningsubmissionEntity> getCleaningSubs();

    ReadOnlyMapProperty<Integer, ComputersubmissionEntity> getComputerSubs();

    ReadOnlyMapProperty<Integer, SecuritysubmissionEntity> getSecuritySubs();

    ReadOnlyMapProperty<Integer, TransportationsubmissionEntity> getTransportationSubs();

    // Others
    ReadOnlyMapProperty<String, Location> getLocationnames();

    ReadOnlyMapProperty<String, Node> getNodes();

    ReadOnlyMapProperty<String, StaffEntity> getStaff();

    ReadOnlyListProperty<Edge> getEdges();

    ReadOnlyListProperty<Move> getMoves();

    ReadOnlyMapProperty<Integer, MessagesEntity> getMessages();

    // Add method
    // Submissions
    void addAudio(AudiosubmissionEntity submission);

    void addCleaning(CleaningsubmissionEntity submission);

    void addComputer(ComputersubmissionEntity submission);

    void addSecurity(SecuritysubmissionEntity submission);

    void addTransportation(TransportationsubmissionEntity submission);

    // Others
    void addLocationName(Location submission);

    void addNode(Node submission);

    void addStaff(StaffEntity submission);

    void addEdge(Edge submission);

    boolean addMove(Move submission);

    void addMessage(MessagesEntity message);

    // Get single methods
    // Submissions
    AudiosubmissionEntity getAudio(int id);

    CleaningsubmissionEntity getCleaning(int id);

    ComputersubmissionEntity getComputer(int id);

    SecuritysubmissionEntity getSecurity(int id);

    TransportationsubmissionEntity getTransportation(int id);

    // Others
    Location getLocationname(String longname);

    Node getNode(String nodeid);

    StaffEntity getStaff(String staffid);

    MessagesEntity getMessage(int id);

    // delete methods
    // submissions
    void deleteAudio(int id);
    void deleteAudio(AudiosubmissionEntity entity);

    void deleteCleaning(int id);
    void deleteCleaning(CleaningsubmissionEntity entity);

    void deleteComputer(int id);
    void deleteComputer(ComputersubmissionEntity entity);

    void deleteSecurity(int id);
    void deleteSecurity(SecuritysubmissionEntity entity);

    void deleteTransportation(int id);
    void deleteTransportation(TransportationsubmissionEntity entity);

    // Others
    void deleteEdge(Edge edge);

    void deleteLocationName(String longname);
    void deleteLocationName(Location entity);

    void deleteMove(Move move);

    void deleteNode(String nodeid);
    void deleteNode(Node entity);

    void deleteStaff(String staffid);
    void deleteStaff(StaffEntity entity);

    void deleteMessage(int messageid);
    void deleteMessage(MessagesEntity entity);

    // Submission methods
    StaffEntity getStaff(String Staffid, String password);

    Session getSession();

    UUID newID();

    void importAll();
}
