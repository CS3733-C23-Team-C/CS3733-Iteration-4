package edu.wpi.capybara.database;

import edu.wpi.capybara.database.dao.*;
import edu.wpi.capybara.database.dao.EdgeDAO;
import edu.wpi.capybara.database.dao.MoveDAO;
import edu.wpi.capybara.database.dao.NodeDAO;
import edu.wpi.capybara.database.dao.StaffDAO;
import edu.wpi.capybara.objects.hibernate.*;
import edu.wpi.capybara.objects.orm.DAOFacade;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyMapProperty;

// this would be about 20 lines long if java supported composition in addition to inheritance
class DatabaseService implements RepoFacade2 {
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
    public ReadOnlyMapProperty<Integer, AudiosubmissionEntity> getAudioSubs() {
        return audioSubmissionDAO.getAll();
    }

    @Override
    public ReadOnlyMapProperty<Integer, CleaningsubmissionEntity> getCleaningSubs() {
        return cleaningSubmissionDAO.getAll();
    }

    @Override
    public ReadOnlyMapProperty<Integer, ComputersubmissionEntity> getComputerSubs() {
        return computerSubmissionDAO.getAll();
    }

    @Override
    public ReadOnlyMapProperty<Integer, SecuritysubmissionEntity> getSecuritySubs() {
        return securitySubmissionDAO.getAll();
    }

    @Override
    public ReadOnlyMapProperty<Integer, TransportationsubmissionEntity> getTransportationSubs() {
        return transportationSubmissionDAO.getAll();
    }

    @Override
    public ReadOnlyMapProperty<String, LocationnameEntity> getLocationnames() {
        return locationDAO.getAll();
    }

    @Override
    public ReadOnlyMapProperty<String, NodeEntity> getNodes() {
        return nodeDAO.getAll();
    }

    @Override
    public ReadOnlyMapProperty<String, StaffEntity> getStaff() {
        return staffDAO.getAll();
    }

    @Override
    public ReadOnlyListProperty<EdgeEntity> getEdges() {
        return edgeDAO.getAll();
    }

    @Override
    public ReadOnlyListProperty<MoveEntity> getMoves() {
        return moveDAO.getAll();
    }

    @Override
    public ReadOnlyMapProperty<Integer, MessagesEntity> getMessages() {
        return messageDAO.getAll();
    }

    @Override
    public void addAudio(AudiosubmissionEntity submission) {
        audioSubmissionDAO.add(submission);
    }

    @Override
    public void addCleaning(CleaningsubmissionEntity submission) {
        cleaningSubmissionDAO.add(submission);
    }

    @Override
    public void addComputer(ComputersubmissionEntity submission) {
        computerSubmissionDAO.add(submission);
    }

    @Override
    public void addSecurity(SecuritysubmissionEntity submission) {
        securitySubmissionDAO.add(submission);
    }

    @Override
    public void addTransportation(TransportationsubmissionEntity submission) {
        transportationSubmissionDAO.add(submission);
    }

    @Override
    public void addLocationname(LocationnameEntity submission) {
        locationDAO.add(submission);
    }

    @Override
    public void addNode(NodeEntity submission) {
        nodeDAO.add(submission);
    }

    @Override
    public void addStaff(StaffEntity submission) {
        staffDAO.add(submission);
    }

    @Override
    public void addEdge(EdgeEntity submission) {
        edgeDAO.add(submission);
    }

    @Override
    public boolean addMove(MoveEntity submission) {
        return false;
    }

    @Override
    public void addMessage(MessagesEntity message) {
        messageDAO.add(message);
    }

    @Override
    public AudiosubmissionEntity getAudio(int id) {
        return audioSubmissionDAO.get(id);
    }

    @Override
    public CleaningsubmissionEntity getCleaning(int id) {
        return null;
    }

    @Override
    public ComputersubmissionEntity getComputer(int id) {
        return null;
    }

    @Override
    public SecuritysubmissionEntity getSecurity(int id) {
        return null;
    }

    @Override
    public TransportationsubmissionEntity getTransportation(int id) {
        return null;
    }

    @Override
    public LocationnameEntity getLocationname(String longname) {
        return null;
    }

    @Override
    public NodeEntity getNode(String nodeid) {
        return null;
    }

    @Override
    public StaffEntity getStaff(String staffid) {
        return null;
    }

    @Override
    public MessagesEntity getMessage(int id) {
        return null;
    }

    @Override
    public void deleteAudio(int id) {

    }

    @Override
    public void deleteCleaning(int id) {

    }

    @Override
    public void deleteComputer(int id) {

    }

    @Override
    public void deleteSecurity(int id) {

    }

    @Override
    public void deleteTransportation(int id) {

    }

    @Override
    public void deleteEdge(EdgeEntity edge) {

    }

    @Override
    public void deleteLocationname(String longname) {

    }

    @Override
    public void deleteMove(MoveEntity move) {

    }

    @Override
    public void deleteNode(String nodeid) {

    }

    @Override
    public void deleteStaff(String staffid) {

    }

    @Override
    public void deleteMessage(int messageid) {

    }

    @Override
    public StaffEntity getStaff(String Staffid, String password) {
        return null;
    }
}
