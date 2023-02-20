package edu.wpi.capybara.database;

import edu.wpi.capybara.database.dao.*;
import edu.wpi.capybara.database.dao.EdgeDAO;
import edu.wpi.capybara.database.dao.MoveDAO;
import edu.wpi.capybara.database.dao.NodeDAO;
import edu.wpi.capybara.database.dao.StaffDAO;
import edu.wpi.capybara.objects.orm.*;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyMapProperty;
import org.hibernate.Session;

import java.util.UUID;

// this would be about 20 lines long if java supported composition in addition to inheritance
class DatabaseService implements RepoFacade2 {
    private final DAOFacade orm;
    private final AudioSubmissionDAO audioSubmissionDAO;
    private final CleaningSubmissionDAO cleaningSubmissionDAO;
    private final ComputerSubmissionDAO computerSubmissionDAO;
    private final SecuritySubmissionDAO securitySubmissionDAO;
    private final TransportationSubmissionDAO transportationSubmissionDAO;
    private final LocationDAO locationDAO;
    private final NodeDAO nodeDAO;
    private final StaffDAO staffDAO;
    private final EdgeDAO edgeDAO;
    private final MoveDAO moveDAO;
    private final MessageDAO messageDAO;

    public DatabaseService(DAOFacade orm) {
        this.orm = orm;
        audioSubmissionDAO = new AudioSubmissionDAO(orm);
        cleaningSubmissionDAO = new CleaningSubmissionDAO(orm);
        computerSubmissionDAO = new ComputerSubmissionDAO(orm);
        securitySubmissionDAO = new SecuritySubmissionDAO(orm);
        transportationSubmissionDAO = new TransportationSubmissionDAO(orm);
        locationDAO = new LocationDAO(orm);
        nodeDAO = new NodeDAO(orm);
        staffDAO = new StaffDAO(orm);
        edgeDAO = new EdgeDAO(orm);
        moveDAO = new MoveDAO(orm);
        messageDAO = new MessageDAO(orm);
    }

    @Override
    public ReadOnlyMapProperty<UUID, AudioSubmission> getAudioSubs() {
        return audioSubmissionDAO.getAll();
    }

    @Override
    public ReadOnlyMapProperty<UUID, CleaningSubmission> getCleaningSubs() {
        return cleaningSubmissionDAO.getAll();
    }

    @Override
    public ReadOnlyMapProperty<UUID, ComputerSubmission> getComputerSubs() {
        return computerSubmissionDAO.getAll();
    }

    @Override
    public ReadOnlyMapProperty<UUID, SecuritySubmission> getSecuritySubs() {
        return securitySubmissionDAO.getAll();
    }

    @Override
    public ReadOnlyMapProperty<UUID, TransportationSubmission> getTransportationSubs() {
        return transportationSubmissionDAO.getAll();
    }

    @Override
    public ReadOnlyMapProperty<String, Location> getLocationnames() {
        return locationDAO.getAll();
    }

    @Override
    public ReadOnlyMapProperty<String, Node> getNodes() {
        return nodeDAO.getAll();
    }

    @Override
    public ReadOnlyMapProperty<String, Staff> getStaff() {
        return staffDAO.getAll();
    }

    @Override
    public ReadOnlyListProperty<Edge> getEdges() {
        return edgeDAO.getAll();
    }

    @Override
    public ReadOnlyListProperty<Move> getMoves() {
        return moveDAO.getAll();
    }

    @Override
    public ReadOnlyMapProperty<UUID, Messages> getMessages() {
        return messageDAO.getAll();
    }

    @Override
    public void addAudio(AudioSubmission submission) {
        audioSubmissionDAO.add(submission);
    }

    @Override
    public void addCleaning(CleaningSubmission submission) {
        cleaningSubmissionDAO.add(submission);
    }

    @Override
    public void addComputer(ComputerSubmission submission) {
        computerSubmissionDAO.add(submission);
    }

    @Override
    public void addSecurity(SecuritySubmission submission) {
        securitySubmissionDAO.add(submission);
    }

    @Override
    public void addTransportation(TransportationSubmission submission) {
        transportationSubmissionDAO.add(submission);
    }

    @Override
    public void addLocationName(Location submission) {
        locationDAO.add(submission);
    }

    @Override
    public void addNode(Node submission) {
        nodeDAO.add(submission);
    }

    @Override
    public void addStaff(Staff submission) {
        staffDAO.add(submission);
    }

    @Override
    public void addEdge(Edge submission) {
        edgeDAO.add(submission);
    }

    @Override
    public boolean addMove(Move submission) {
        // TODO: 2/19/23
        return false;
    }

    @Override
    public void addMessage(Messages message) {
        messageDAO.add(message);
    }

    @Override
    public AudioSubmission getAudio(UUID id) {
        return audioSubmissionDAO.get(id);
    }

    @Override
    public CleaningSubmission getCleaning(UUID id) {
        return cleaningSubmissionDAO.get(id);
    }

    @Override
    public ComputerSubmission getComputer(UUID id) {
        return computerSubmissionDAO.get(id);
    }

    @Override
    public SecuritySubmission getSecurity(UUID id) {
        return securitySubmissionDAO.get(id);
    }

    @Override
    public TransportationSubmission getTransportation(UUID id) {
        return transportationSubmissionDAO.get(id);
    }

    @Override
    public Location getLocationname(String longname) {
        return locationDAO.get(longname);
    }

    @Override
    public Node getNode(String nodeid) {
        return nodeDAO.get(nodeid);
    }

    @Override
    public Staff getStaff(String staffid) {
        return staffDAO.get(staffid);
    }

    @Override
    public Messages getMessage(UUID id) {
        return messageDAO.get(id);
    }

    @Override
    public void deleteAudio(UUID id) {
        deleteAudio(getAudio(id));
    }

    @Override
    public void deleteAudio(AudioSubmission entity) {
        audioSubmissionDAO.delete(entity);
    }

    @Override
    public void deleteCleaning(UUID id) {
        deleteCleaning(getCleaning(id));
    }

    @Override
    public void deleteCleaning(CleaningSubmission entity) {
        cleaningSubmissionDAO.delete(entity);
    }

    @Override
    public void deleteComputer(UUID id) {
        deleteComputer(getComputer(id));
    }

    @Override
    public void deleteComputer(ComputerSubmission entity) {
        computerSubmissionDAO.delete(entity);
    }

    @Override
    public void deleteSecurity(UUID id) {
        deleteSecurity(getSecurity(id));
    }

    @Override
    public void deleteSecurity(SecuritySubmission entity) {
        securitySubmissionDAO.delete(entity);
    }

    @Override
    public void deleteTransportation(UUID id) {
        deleteTransportation(getTransportation(id));
    }

    @Override
    public void deleteTransportation(TransportationSubmission entity) {
        transportationSubmissionDAO.delete(entity);
    }

    @Override
    public void deleteEdge(Edge edge) {
        edgeDAO.delete(edge);
    }

    @Override
    public void deleteLocationName(String longname) {
        deleteLocationName(getLocationname(longname));
    }

    @Override
    public void deleteLocationName(Location entity) {
        locationDAO.delete(entity);
    }

    @Override
    public void deleteMove(Move move) {
        moveDAO.delete(move);
    }

    @Override
    public void deleteNode(String nodeid) {
        deleteNode(getNode(nodeid));
    }

    @Override
    public void deleteNode(Node entity) {
        nodeDAO.delete(entity);
    }

    @Override
    public void deleteStaff(String staffid) {
        deleteStaff(getStaff(staffid));
    }

    @Override
    public void deleteStaff(Staff entity) {
        staffDAO.delete(entity);
    }

    @Override
    public void deleteMessage(UUID messageid) {
        deleteMessage(getMessage(messageid));
    }

    @Override
    public void deleteMessage(Messages entity) {
        messageDAO.delete(entity);
    }

    @Override
    public Staff getStaff(String staffId, String password) {
        return staffDAO.get(staffId, password);
    }

    @Override
    public Session getSession() {
        return orm.getSession();
    }

    @Override
    public UUID newID() {
        // the chance of collision is 1 in 2^128. It's hard to describe just how tiny of a chance that is, but it's a small
        // enough chance we shouldn't have any collisions.
        return UUID.randomUUID();
    }

    @Override
    public void importAll() {
        // nothing to be done, this is handled in the constructor
    }
}
