package edu.wpi.capybara.database;

import edu.wpi.capybara.objects.orm.*;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyMapProperty;
import org.hibernate.Session;

import java.util.UUID;

public interface RepoFacade2 {
    // Get all methods
    // Submissions
    ReadOnlyMapProperty<UUID, AudioSubmission> getAudioSubs();

    ReadOnlyMapProperty<UUID, CleaningSubmission> getCleaningSubs();

    ReadOnlyMapProperty<UUID, ComputerSubmission> getComputerSubs();

    ReadOnlyMapProperty<UUID, SecuritySubmission> getSecuritySubs();

    ReadOnlyMapProperty<UUID, TransportationSubmission> getTransportationSubs();

    // Others
    ReadOnlyMapProperty<String, Location> getLocationnames();

    ReadOnlyMapProperty<String, Node> getNodes();

    ReadOnlyMapProperty<String, Staff> getStaff();

    ReadOnlyListProperty<Edge> getEdges();

    ReadOnlyListProperty<Move> getMoves();

    ReadOnlyMapProperty<UUID, Messages> getMessages();

    // Add method
    // Submissions
    void addAudio(AudioSubmission submission);

    void addCleaning(CleaningSubmission submission);

    void addComputer(ComputerSubmission submission);

    void addSecurity(SecuritySubmission submission);

    void addTransportation(TransportationSubmission submission);

    // Others
    void addLocationName(Location submission);

    void addNode(Node submission);

    void addStaff(Staff submission);

    void addEdge(Edge submission);

    boolean addMove(Move submission);

    void addMessage(Messages message);

    // Get single methods
    // Submissions
    AudioSubmission getAudio(UUID id);

    CleaningSubmission getCleaning(UUID id);

    ComputerSubmission getComputer(UUID id);

    SecuritySubmission getSecurity(UUID id);

    TransportationSubmission getTransportation(UUID id);

    // Others
    Location getLocationname(String longname);

    Node getNode(String nodeid);

    Staff getStaff(String staffid);

    Messages getMessage(UUID id);

    // delete methods
    // submissions
    void deleteAudio(UUID id);
    void deleteAudio(AudioSubmission entity);

    void deleteCleaning(UUID id);
    void deleteCleaning(CleaningSubmission entity);

    void deleteComputer(UUID id);
    void deleteComputer(ComputerSubmission entity);

    void deleteSecurity(UUID id);
    void deleteSecurity(SecuritySubmission entity);

    void deleteTransportation(UUID id);
    void deleteTransportation(TransportationSubmission entity);

    // Others
    void deleteEdge(Edge edge);

    void deleteLocationName(String longname);
    void deleteLocationName(Location entity);

    void deleteMove(Move move);

    void deleteNode(String nodeid);
    void deleteNode(Node entity);

    void deleteStaff(String staffid);
    void deleteStaff(Staff entity);

    void deleteMessage(UUID messageid);
    void deleteMessage(Messages entity);

    // Submission methods
    Staff getStaff(String Staffid, String password);

    Session getSession();

    UUID newID();

    void importAll();
}
